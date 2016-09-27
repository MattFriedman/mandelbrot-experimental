package com.listoutfitter.mandelbrot

import org.apfloat.Apfloat

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.util.List

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-06.
 */
class Mandelbrot3 {


    public static void main(String[] args) {

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

        double ratio = 4d / 4d

        double width = 320

        double height = width * (1d / ratio)

        double realStart = -2.0
        double realEnd = 1

        double imagStart = -1.5
        double imagEnd = 1.5

        double incrementReal = (realEnd - realStart) / width
        double incrementImag = (imagEnd - imagStart) / height

        List<Point> points = []

        (1d + height).times { y ->
            (1d + width).times { x ->
                def point = new Point<Double>()
                point.x = x
                point.y = y
                points.add(point)
            }
        }


        final int maxIterations = 1000

        def config = new DoublePrecisionDefaultConfig()

        def processor = new MandelbrotProcessor<Double>(config)

        processor.process(maxIterations)


        System.exit(1)











        int imgWidth = width + 1d
        int imgHeight = height + 1d

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        int black = 0;
        int[] colors = new int[maxIterations];
        for (int i = 0; i < maxIterations; i++) {
            float hue = i / 256f
            float saturation = 1f
            float brighness = i / (i + 8f)
            colors[i] = Color.HSBtoRGB(hue, saturation, brighness);
        }



        final MandelbrotAlgorithm doublePrecisionMandelbrotAlgorithm =
                new DoublePrecisionMandelbrotAlgorithm(maxIterations)

        final MandelbrotAlgorithm apfloatPrecisionMandelbrotAlgorithm =
                new ApfloatPrecisionMandelbrotAlgorithm(maxIterations, 4)


        final algorithm = doublePrecisionMandelbrotAlgorithm

        points.parallelStream().map { point ->

            double real = realStart + incrementReal * point.x
            double imag = imagStart + incrementImag * point.y

            def imaginaryPoint = new ImaginaryPoint<Double>()
            imaginaryPoint.x = point.x
            imaginaryPoint.y = point.y
            imaginaryPoint.real = real
            imaginaryPoint.imag = imag

            imaginaryPoint

        }.map { imaginaryPoint ->

            def mandelbrotResult = algorithm.compute(imaginaryPoint.real, imaginaryPoint.imag)
            mandelbrotResult.x = imaginaryPoint.x
            mandelbrotResult.y = imaginaryPoint.y
            mandelbrotResult

        }.map { MandelbrotResult result ->

            result.inSet ?
                    image.setRGB(result.x, result.y, colors[result.totalIterations]) :
                    image.setRGB(result.x, result.y, black)

        }.collect()

        ImageIO.write(image, "png", new File("/tmp/mandelbrot.png"));
    }
}








