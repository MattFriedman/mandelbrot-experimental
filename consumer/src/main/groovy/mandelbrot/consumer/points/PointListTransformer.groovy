package mandelbrot.consumer.points

import mandelbrot.DoublePrecisionMandelbrotAlgorithm
import mandelbrot.MandelbrotResult
import mandelbrot.Point
import org.springframework.integration.annotation.Transformer
import org.springframework.stereotype.Component

import java.util.stream.Collectors

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@Component
class PointListTransformer {

    def algo = new DoublePrecisionMandelbrotAlgorithm(1000)

    @Transformer
    List<MandelbrotResult> transform(List<Point<Double>> points) {

        points.parallelStream().map { p ->
            def result = algo.compute(p.real, p.imag)
            result.x = p.x
            result.y = p.y
            result
        }.collect(Collectors.toList())
    }
}
