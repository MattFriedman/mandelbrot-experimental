package mandelbrot

import org.apfloat.Apfloat

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class ApfloatToIntegerConverter implements ToIntegerConverter<Apfloat> {

    @Override
    int convertToInteger(Apfloat input) {
        input.intValue()
    }
}
