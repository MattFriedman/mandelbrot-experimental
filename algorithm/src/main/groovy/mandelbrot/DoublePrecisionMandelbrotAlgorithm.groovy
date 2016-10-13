/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot
/**

 * Created by Matt Friedman 2016-09-11
 */
class DoublePrecisionMandelbrotAlgorithm extends GenericMandelbrotAlgorithm<Double> {

    DoublePrecisionMandelbrotAlgorithm(final int maxIterations) {
        super(maxIterations)
    }

    @Override
    MandelbrotResult compute(Double c_re, Double c_im) {

        double x = 0D
        double y = 0D

        int iterations = 0;

        while (x * x  + y * y < 4D && iterations < maxIterations) {
            double x_new = x * x - y * y + c_re;
            y = 2D * x * y + c_im;
            x = x_new;
            iterations++;
        }

        MandelbrotResult.builder()
                .inSet(iterations < maxIterations)
                .totalIterations(iterations)
                .build()
    }

//    @Override
//    MandelbrotResult compute(final double c_re, final double c_im) {
//
//        double x = 0D
//        double y = 0D
//
//        int iterations = 0;
//
//        while (x * x  + y * y < 4D && iterations < maxIterations) {
//            double x_new = x * x - y * y + c_re;
//            y = 2D * x * y + c_im;
//            x = x_new;
//            iterations++;
//        }
//
//        MandelbrotResult.builder()
//                .inSet(iterations < maxIterations)
//                .totalIterations(iterations)
//                .build()
//    }

//    @Override
//    Double two() {
//        2D
//    }
//
//    @Override
//    Double zero() {
//        0D
//    }
//
//    @Override
//    Double four() {
//        4D
//    }
}
