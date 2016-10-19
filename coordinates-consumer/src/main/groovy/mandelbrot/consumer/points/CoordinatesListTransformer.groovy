/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import mandelbrot.*
import org.apfloat.Apfloat
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

import java.util.stream.Collectors

/**

 * Created by Matt Friedman 2016-10-07
 */
@Component
class CoordinatesListTransformer {

    static {
        Mandelbrot.init()
    }

    @Transformer
    List<MandelbrotResult> transform(
            List<Point> points,
            @Header('maxIterations') int maxIterations,
            @Header('grid') Grid grid,
            @Header('sequenceSize') seqSize,
            @Header('correlationId') id,
            @Header('id') String messageId,
            @Header('precision') Long precision
    ) {

        final MandelbrotAlgorithm algo
        final converter

        if (precision > 0) {
            algo = new ApfloatPrecisionMandelbrotAlgorithm(maxIterations, precision)
            converter = { int i ->
                new Apfloat(i, precision)
            }
        } else {
            algo = new DoublePrecisionMandelbrotAlgorithm(maxIterations)
            converter = { int i ->
                Double.valueOf(i)
            }
        }

        println "Processing partition. ID: ${messageId}"



        points.parallelStream().map { point ->

            point.real = grid.realStart + grid.incrementReal * converter.call(point.x)
            point.imag = grid.imagStart + grid.incrementImag * converter.call(point.y)



            def result = algo.compute(point.real, point.imag)


            result.x = point.x
            result.y = point.y
            result

        }.collect(Collectors.toList())
    }
}
