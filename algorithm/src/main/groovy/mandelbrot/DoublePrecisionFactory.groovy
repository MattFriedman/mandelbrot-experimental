/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-10-04
 */
class DoublePrecisionFactory<T> implements Factory<Double> {

    @Override
    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations) {
        return new DoublePrecisionMandelbrotAlgorithm(maxIterations)
    }

    @Override
    ToIntegerConverter<Double> toIntegerConverter() {
        new ToIntegerConverter<Double>() {
            @Override
            int convertToInteger(Double input) {
                input.toInteger()
            }
        }
    }

    @Override
    Double one() {
        1D
    }
}
