package com.listoutfitter.mandelbrot

import org.apfloat.Apfloat

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class ApfloatPrecisionMandelbrotAlgorithm extends GenericMandelbrotAlgorithm<Apfloat> {

    final ZERO = new Apfloat(0, precision)
    final TWO = new Apfloat(2, precision)
    final FOUR = new Apfloat(4, precision)

    final int precision

    ApfloatPrecisionMandelbrotAlgorithm(final int maxIterations, final int precision) {
        super(maxIterations)
        this.precision = precision
    }

    @Override
    MandelbrotResult compute(Apfloat real, Apfloat imag) {
        super.compute(real, imag)
    }

    @Override
    Apfloat two() {
        TWO
    }

    @Override
    Apfloat zero() {
        ZERO
    }

    @Override
    Apfloat four() {
        FOUR
    }
}
