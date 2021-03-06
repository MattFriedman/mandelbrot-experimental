/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-12
 */

@Deprecated
class DoublePrecisionDefaultConfig implements Config<Double> {

    @Override
    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations) {
        new DoublePrecisionMandelbrotAlgorithm(maxIterations)
    }

    @Override
    ToIntegerConverter<Double> toIntegerConverter() {
        new DoubleToIntegerConverter()
    }

    @Override
    Double one() {
        1D
    }

    @Override
    int getPrecision() {
        return 0
    }

    @Override
    Double getWidth() {
        1280D
    }

    @Override
    Double getHeight() {
        getWidth()
    }

    @Override
    Double getRealStart() {
        -2D
    }

    @Override
    Double getRealEnd() {
        1D
    }

    @Override
    Double getImagStart() {
        -1.5D
    }

    @Override
    Double getImagEnd() {
        1.5D
    }
}
