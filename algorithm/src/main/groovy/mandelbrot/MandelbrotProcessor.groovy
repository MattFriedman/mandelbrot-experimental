package mandelbrot

import org.apfloat.Apfloat

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.util.List

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class MandelbrotProcessor<T> {

    static {

        Apfloat.metaClass.plus = { Apfloat addend ->
            delegate.add(addend)
        }

        Apfloat.metaClass.minus = { Apfloat subtrahend ->
            delegate.subtract(subtrahend)
        }

        Apfloat.metaClass.div = { Apfloat divisor ->
            delegate.divide(divisor)
        }

        Apfloat.metaClass.next = {
            delegate.add(new Apfloat(1))
        }
    }

//     T incrementReal
//     T incrementImag

    final List<Point> points = []

//     Config<T> config

//    MandelbrotProcessor(Config<T> config) {
//
//        this.config = config
//
//        incrementImag = (config.imagEnd - config.imagStart) / config.height
//        incrementReal = (config.realEnd - config.realStart) / config.width
//
//        (config.one() + config.height).times { y ->
//            (config.one() + config.width).times { x ->
//                def point = new Point<T>()
//                point.x = x
//                point.y = y
//                points.add(point)
//                Collections.unmodifiableList(points)
//            }
//        }
//    }

    Grid<T> grid
    Factory<T> factory

    MandelbrotProcessor(Grid<T> grid, Factory<T> factory) {
        this.grid = grid
        this.factory = factory
    }

        int black = 0;

        int[] getColors(int maxIterations) {
            int[] colors = new int[maxIterations];
            for (int i = 0; i < maxIterations; i++) {
                float hue = i / 256f
                float saturation = 1f
                float brightness = i / (i + 8f)
                colors[i] = Color.HSBtoRGB(hue, saturation, brightness);
            }
            colors
        }

    void process(final int maxIterations) {

        def colors = getColors(maxIterations)

        def converter = factory.toIntegerConverter()

        T imgWidth = grid.width + factory.one()
        T imgHeight = grid.height + factory.one()


        BufferedImage image = new BufferedImage(
                converter.convertToInteger(imgWidth),
                converter.convertToInteger(imgHeight),
                BufferedImage.TYPE_INT_RGB
        );


        MandelbrotAlgorithm mandelbrotAlgorithm = factory.getMandelbrotAlgorithm(maxIterations)

        grid.getPointsInGrid().parallelStream().map { point ->

            def mandelbrotResult

            mandelbrotResult = mandelbrotAlgorithm.compute(point.real, point.imag)

            mandelbrotResult.x = point.x
            mandelbrotResult.y = point.y
            mandelbrotResult

        }.forEach { MandelbrotResult result ->

            result.inSet ?
                    image.setRGB(result.x, result.y, colors[result.totalIterations]) :
                    image.setRGB(result.x, result.y, black)

        }


        ImageIO.write(image, "png", new File("/tmp/mandelbrotb.png"))

//        def converter = config.toIntegerConverter()
//
//        T imgWidth = config.width + config.one()
//        T imgHeight = config.height + config.one()
//
//        BufferedImage image = new BufferedImage(
//                converter.convertToInteger(imgWidth),
//                converter.convertToInteger(imgHeight),
//                BufferedImage.TYPE_INT_RGB
//        );
//
//
//
//        MandelbrotAlgorithm mandelbrotAlgorithm =
//                config.getMandelbrotAlgorithm(maxIterations)
//
//        points.parallelStream().map { point ->
//
//            T real = config.realStart + incrementReal * point.x
//            T imag = config.imagStart + incrementImag * point.y
//
//            def imaginaryPoint = new ImaginaryPoint<T>()
//            imaginaryPoint.x = point.x
//            imaginaryPoint.y = point.y
//            imaginaryPoint.real = real
//            imaginaryPoint.imag = imag
//
//            imaginaryPoint
//
//        }.map { imaginaryPoint ->
//
//            def mandelbrotResult
//
//            mandelbrotResult = mandelbrotAlgorithm.compute(imaginaryPoint.real, imaginaryPoint.imag)
//
//            mandelbrotResult.x = imaginaryPoint.x
//            mandelbrotResult.y = imaginaryPoint.y
//            mandelbrotResult
//
//        }.map { MandelbrotResult result ->
//
//            result.inSet ?
//                    image.setRGB(result.x, result.y, colors[result.totalIterations]) :
//                    image.setRGB(result.x, result.y, black)
//
//        }.collect()


    }
}
