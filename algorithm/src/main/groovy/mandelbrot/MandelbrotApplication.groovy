package mandelbrot

import org.apfloat.Apfloat
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-06.
 */

class MandelbrotApplication {

    public static void main(String[] args) {


        final int maxIterations = 100

        Config<Double> config = new DoublePrecisionDefaultConfig2(new PropertiesLoader('/mandelbrot.properties'))

        def processor = new MandelbrotProcessor<Double>(config)

        processor.process(maxIterations)

        System.exit(1)
    }
}








