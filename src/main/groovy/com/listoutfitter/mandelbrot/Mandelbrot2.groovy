package com.listoutfitter.mandelbrot

import org.apfloat.Apfloat

import javax.imageio.ImageIO
import java.awt.Color
import java.awt.image.BufferedImage

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-06.
 */
class Mandelbrot2 {


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

        double ratio = 4d / 3d

        double width = 640

        double height = width * (1d / ratio)

        double realStart = -2.0
        double realEnd = 1

        double imagStart = -1.5
        double imagEnd = 1.5

        double incrementReal = (realEnd - realStart) / width
        double incrementImag = (imagEnd - imagStart) / height

        List<List<Point>> grid = []


        (1d + height).times { y ->

            List<Point> row = []

            (1d + width).times { x ->
                def point = Point.builder().x(x).y(y).build()
                row.add(point)
            }
            grid.add(row)
        }


        double maxIters = 500

        def imgWidth =  grid.first().size()
        def imgHeight =  grid.size()

        BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);

        int black = 0;
        int[] colors = new int[maxIters];
        for (int i = 0; i < maxIters; i++) {
            float hue = i / 256f
            float saturation = 1f
            float brighness = i / (i + 8f)
            colors[i] = Color.HSBtoRGB(hue, saturation, brighness);
        }


        grid.stream().each { row ->
            row.stream().map { point ->

                point.imag = imagStart + incrementImag * point.y
                point.real = realStart + incrementReal * point.x
                point

            }.map { point ->

                double c_re = point.real
                double c_im = point.imag
                double x = 0, y = 0;
                int iterations = 0;
                while (x * x + y * y < 4 && iterations < maxIters) {
                    double x_new = x * x - y * y + c_re;
                    y = 2 * x * y + c_im;
                    x = x_new;
                    iterations++;
                }
                point.inSet = iterations < maxIters
                point.numIterations = iterations
                point

            }.forEach({  point ->

                if(point.inSet) {

                    image.setRGB(point.x.intValue(), point.y.intValue(), colors[point.numIterations-1])

                } else {

                    image.setRGB(point.x.intValue(), point.y.intValue(), black)

                }

            })
        }


        ImageIO.write(image, "png", new File("/tmp/mandelbrot.png"));


    }
}








