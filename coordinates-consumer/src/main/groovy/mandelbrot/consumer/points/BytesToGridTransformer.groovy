/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Grid
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
    Message transform(Message message, @Header('grid') byte[] gridBytes) {

        def grid = objectMapper.readValue(gridBytes, Grid)

        MessageBuilder.fromMessage(message)
                .setHeader('grid', grid)
                .build()
    }
}
