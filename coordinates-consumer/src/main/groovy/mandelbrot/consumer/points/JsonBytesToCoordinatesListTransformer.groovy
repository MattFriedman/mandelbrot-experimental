/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Point
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component

/**

 * Created by Matt Friedman 2016-10-07
 */
@Component
class JsonBytesToCoordinatesListTransformer {

    ObjectMapper objectMapper = new ObjectMapper()

    @Transformer
    List<Point> pointList(byte[] bytes) {
        def tr = new TypeReference<List<Point>>(){}
        objectMapper.readValue(bytes, tr)
    }
}
