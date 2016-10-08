package mandelbrot.consumer.points

import mandelbrot.DoublePrecisionMandelbrotAlgorithm
import mandelbrot.MandelbrotResult
import mandelbrot.Point
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@Component
class PointToResultTransformer {

    @Transformer
    MandelbrotResult transform(Point<Double> point) {

        def algo = new DoublePrecisionMandelbrotAlgorithm(1000)
        algo.compute(point.real, point.imag)
//
//
//
//
//        def result =new MandelbrotResult()
//        result.inSet = true
//        result.totalIterations = 1
//        result.x = 0
//        result.y = 0
//        result
    }
}
