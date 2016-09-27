package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class DoubleToIntegerConverter implements ToIntegerConverter<Double> {

    @Override
    int convertToInteger(Double input) {
        input.toInteger()
    }
}
