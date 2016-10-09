package mandelbrot.consumer.points

import mandelbrot.DoublePrecisionMandelbrotAlgorithm
import mandelbrot.MandelbrotResult
import mandelbrot.Point
import org.springframework.messaging.handler.annotation.Header
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@Deprecated
class PointToResultTransformer {

    MandelbrotResult transform(Point<Double> point, @Header('maxIterations') int maxIterations) {
        def algorithm = new DoublePrecisionMandelbrotAlgorithm(maxIterations)
        algorithm.compute(point.real, point.imag)
    }
}
