package mandelbrot

import mandelbrot.Point
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
class Mandelbrot {


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

        double ratio = 4 / 3

        double we = 40
        double he = we * (1 / ratio)

        println "W: ${we} H: ${he}"

        double realStart = -2
        double realEnd = 1

        double imagStart = -1
        double imagEnd = 1

        double incrementReal = (realEnd - realStart) / we
        double incrementImag = (imagEnd - imagStart) / he

        List<List<Point>> points = []

        double imag = imagStart

        (1 + he).times { y -> // imag axis

            List<Point> row = []

            double real = realStart

            (1 + we).times { x -> // real axis
                def point = Point.builder()
                        .x(real)
                        .y(imag)
                        .real(realStart + x * incrementReal)
                        .imag(imagStart + y * incrementImag)
                        .build()
                row.add(point)
                real += incrementReal
            }


            points.add(row)

            imag += incrementImag
        }




        double maxIters = 1000
        double iters = 0

        points.each {
            it.each { point ->

                double x=0,y=0

                double c_re = point.x
                double c_im = point.y

                while(x * x + y*y < 4D && iters < maxIters) {


                    double x_new = x * x - y * y + c_re
                    y = 2 * x * y + c_im
                    x = x_new
                    iters++
                }



                if( iters < maxIters) {
                    print ' '
                } else {
                    print '*'
                }

            }
            println ''

        }




        System.exit(1)




        System.exit(0)



        final long w = 1280
        final long h = 960

        final long precision = 8

        final Apfloat ZERO = new Apfloat(0, precision)
        final Apfloat ONE = new Apfloat(1, precision)
        final Apfloat TWO = new Apfloat(2, precision)
        final Apfloat FOUR = new Apfloat(4, precision)


        int max = 1000

        Apfloat width = new Apfloat(w, precision), height = new Apfloat(h, precision)

        final Apfloat horizontalShift = new Apfloat('0.0000', precision) * width


        BufferedImage image = new BufferedImage(w.toInteger(), h.toInteger(), BufferedImage.TYPE_INT_RGB);

//        int black = 0x000000, white = 0xFFFFFF;


        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i < max; i++) {
            float hue = i / 256f
            float saturation = 1f
            float brighness = i / (i + 8f)
            colors[i] = Color.HSBtoRGB(hue, saturation, brighness);
        }









        for (Apfloat row = ZERO; row < height; row++) {

            for (Apfloat col = ZERO; col < width; col++) {

                Apfloat c_re = ((col - width / TWO) * FOUR / width) - horizontalShift;

                Apfloat c_im = (row - height / TWO) * FOUR / width;

                Apfloat x = ZERO, y = ZERO;
                int numIterations = 0;
                while (x * x + y * y < FOUR && numIterations < max) {
                    Apfloat x_new = x * x - y * y + c_re;
                    y = TWO * x * y + c_im;
                    x = x_new;
                    numIterations++;
                }

                if (numIterations < max) {
                    image.setRGB(col.intValue(), row.intValue(), colors[numIterations])
                } else {
                    image.setRGB(col.intValue(), row.intValue(), black)
                }
            }
        }

        ImageIO.write(image, "png", new File("/tmp/mandelbrot.png"));
    }
}








