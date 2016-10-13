/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import org.apfloat.Apfloat

/**

 *
 * Created by Matt Friedman 2016-09-12
 */
class ApfloatPrecisionMandelbrotAlgorithm extends GenericMandelbrotAlgorithm<Apfloat> {

    final long precision

    ApfloatPrecisionMandelbrotAlgorithm(final int maxIterations, final long precision) {
        super(maxIterations)
        this.precision = precision
    }

    @Override
    MandelbrotResult compute(Apfloat c_re, Apfloat c_im) {

        final Apfloat four = new Apfloat(4, precision)
        final Apfloat two = new Apfloat(2, precision)

        Apfloat x = Apfloat.ZERO.precision(precision)
        Apfloat y = Apfloat.ZERO.precision(precision)

        int iterations = 0;

        while (x * x  + y * y < four && iterations < maxIterations) {
            Apfloat x_new = x * x - y * y + c_re;
            y = two * x * y + c_im;
            x = x_new;
            iterations++;
        }

        MandelbrotResult.builder()
                .inSet(iterations < maxIterations)
                .totalIterations(iterations)
                .build()
    }
}
