package mandelbrot

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-12
 */
class DoubleToIntegerConverter implements ToIntegerConverter<Double> {

    @Override
    int convertToInteger(Double input) {
        input.toInteger()
    }
}
