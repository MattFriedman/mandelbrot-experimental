package mandelbrot

import org.apfloat.Apfloat

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-12
 */
class ApfloatToIntegerConverter implements ToIntegerConverter<Apfloat> {

    @Override
    int convertToInteger(Apfloat input) {
        input.intValue()
    }
}
