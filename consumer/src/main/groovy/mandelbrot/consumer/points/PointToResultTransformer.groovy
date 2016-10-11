/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.consumer.points

import mandelbrot.DoublePrecisionMandelbrotAlgorithm
import mandelbrot.MandelbrotResult
import mandelbrot.Point
import org.springframework.messaging.handler.annotation.Header
/**

 * Created by Matt Friedman 2016-10-07
 */
@Deprecated
class PointToResultTransformer {

    MandelbrotResult transform(Point<Double> point, @Header('maxIterations') int maxIterations) {
        def algorithm = new DoublePrecisionMandelbrotAlgorithm(maxIterations)
        algorithm.compute(point.real, point.imag)
    }
}
