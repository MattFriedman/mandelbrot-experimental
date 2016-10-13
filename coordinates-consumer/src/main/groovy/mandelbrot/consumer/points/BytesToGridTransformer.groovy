/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Grid
import org.apfloat.Apfloat
import org.springframework.integration.annotation.Transformer
import org.springframework.integration.support.MessageBuilder
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component
/**

 * Created by Matt Friedman 2016-10-10
 */
@Component
class BytesToGridTransformer {

    ObjectMapper objectMapper = new ObjectMapper()

    @Transformer
    Message transform(Message message, @Header('grid') byte[] gridBytes, @Header('precision') precision) {

        def typeDef

        if( precision > 0) {
            typeDef = new TypeReference<Grid<Apfloat>>(){}
        } else {
            typeDef = new TypeReference<Grid<Double>>(){}
        }

        def grid = objectMapper.readValue(gridBytes, typeDef)

        MessageBuilder.fromMessage(message)
                .setHeader('grid', grid)
                .build()
    }
}
