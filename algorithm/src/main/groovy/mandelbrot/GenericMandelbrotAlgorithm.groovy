/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot
/**

 * Created by Matt Friedman 2016-09-12
 */
abstract class GenericMandelbrotAlgorithm<T> implements MandelbrotAlgorithm<T> {

    final int maxIterations

    T FOUR = (T) 4
    T TWO = (T) 2
    T ZER0 = (T) 0

    GenericMandelbrotAlgorithm(int maxIterations) {
        this.maxIterations = maxIterations
    }

    @Override
    abstract MandelbrotResult compute(final T c_re, final T c_im)

//    {
//
//        T x = (T) ZER0
//        T y = (T) ZER0
//
//        int iterations = 0;
//
//        while (x * x  + y * y < FOUR && iterations < maxIterations) {
//            T x_new = x * x - y * y + c_re;
//            y = TWO * x * y + c_im;
//            x = x_new;
//            iterations++;
//        }
//
//        MandelbrotResult.builder()
//                .inSet(iterations < maxIterations)
//                .totalIterations(iterations)
//                .build()
//    }

}
