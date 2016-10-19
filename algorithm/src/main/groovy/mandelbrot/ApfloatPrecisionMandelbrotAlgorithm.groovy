/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import org.apfloat.Apcomplex
import org.apfloat.ApcomplexMath
import org.apfloat.Apfloat
/**

 *
 * Created by Matt Friedman 2016-09-12
 */
class ApfloatPrecisionMandelbrotAlgorithm extends GenericMandelbrotAlgorithm<Apfloat> {

    double logBase = 1.0 / Math.log(2.0)

    double halfLogBase = Math.log(0.5) * logBase;

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

        while (x * x + y * y < four && iterations < maxIterations) {
            Apfloat x_new = x * x - y * y + c_re;
            y = two * x * y + c_im;
            x = x_new;
            iterations++;
        }

        boolean inSet = iterations < maxIterations

        def c = new Apcomplex(c_re, c_im)

        def cAbs = ApcomplexMath.abs(c)

        def log1 = Math.log(1 + cAbs.doubleValue())

        def log2 = Math.log(log1)

        def smooth = 5 + iterations - halfLogBase - log2 * logBase

        double smooth2 = 0.25d + smooth / 100d

        def brightness = inSet ? 1 : 0

        def saturation = 0.7f

        MandelbrotResult.builder()
                .hue(smooth2.toFloat())
                .saturation(saturation)
                .brightness(brightness)
                .inSet(inSet)
                .totalIterations(iterations)
                .build()
    }
}
