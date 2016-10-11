/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import mandelbrot.DoublePrecisionMandelbrotAlgorithm
import mandelbrot.Grid
import mandelbrot.MandelbrotResult
import mandelbrot.Point
import org.springframework.integration.annotation.Transformer
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

import java.util.stream.Collectors
/**

 * Created by Matt Friedman 2016-10-07
 */
@Component
class PointListTransformer {

    @Transformer
    List<MandelbrotResult> transform(
            List<Point<Double>> points,
            @Header('maxIterations') int maxIterations,
            @Header('grid') Grid grid,
            @Header('sequenceSize') seqSize,
            @Header('correlationId') id

    ) {

        def algo = new DoublePrecisionMandelbrotAlgorithm(maxIterations)

        points.parallelStream().map { point ->

            point.real = grid.realStart + grid.incrementReal * point.x
            point.imag = grid.imagStart + grid.incrementImag * point.y

            def result = algo.compute(point.real, point.imag)
            result.x = point.x
            result.y = point.y
            result

        }.collect(Collectors.toList())
    }
}
