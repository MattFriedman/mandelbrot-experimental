package mandelbrot

import mandelbrot.Point

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

    final T incrementReal
    final T incrementImag

    final List<Point> points = []

    final Config<T> config

    MandelbrotProcessor(Config<T> config) {

        this.config = config

        incrementImag = (config.imagEnd - config.imagStart) / config.height
        incrementReal = (config.realEnd - config.realStart) / config.width

        (config.one() + config.height).times { y ->
            (config.one() + config.width).times { x ->
                def point = new Point<T>()
                point.x = x
                point.y = y
                points.add(point)
                Collections.unmodifiableList(points)
            }
        }
    }

    void process(final int maxIterations) {

        def converter = config.toIntegerConverter()

        T imgWidth = config.width + config.one()
        T imgHeight = config.height + config.one()

        BufferedImage image = new BufferedImage(
                converter.convertToInteger(imgWidth),
                converter.convertToInteger(imgHeight),
                BufferedImage.TYPE_INT_RGB
        );

        int black = 0;
        int[] colors = new int[maxIterations];
        for (int i = 0; i < maxIterations; i++) {
            float hue = i / 256f
            float saturation = 1f
            float brighness = i / (i + 8f)
            colors[i] = Color.HSBtoRGB(hue, saturation, brighness);
        }


        MandelbrotAlgorithm mandelbrotAlgorithm =
                config.getMandelbrotAlgorithm(maxIterations)

        points.parallelStream().map { point ->

            T real = config.realStart + incrementReal * point.x
            T imag = config.imagStart + incrementImag * point.y

            def imaginaryPoint = new ImaginaryPoint<T>()
            imaginaryPoint.x = point.x
            imaginaryPoint.y = point.y
            imaginaryPoint.real = real
            imaginaryPoint.imag = imag

            imaginaryPoint

        }.map { imaginaryPoint ->

            def mandelbrotResult

            mandelbrotResult = mandelbrotAlgorithm.compute(imaginaryPoint.real, imaginaryPoint.imag)


            mandelbrotResult.x = imaginaryPoint.x
            mandelbrotResult.y = imaginaryPoint.y
            mandelbrotResult

        }.map { MandelbrotResult result ->

            result.inSet ?
                    image.setRGB(result.x, result.y, colors[result.totalIterations]) :
                    image.setRGB(result.x, result.y, black)

        }.collect()

        ImageIO.write(image, "png", new File("/tmp/mandelbrota.png"))

    }
}
