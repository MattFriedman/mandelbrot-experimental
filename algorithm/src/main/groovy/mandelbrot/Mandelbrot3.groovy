package mandelbrot

import org.apfloat.Apfloat
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

        final int maxIterations = 100

        def config = new DoublePrecisionDefaultConfig()

        def processor = new MandelbrotProcessor<Double>(config)

        processor.process(maxIterations)

        System.exit(1)
    }
}








