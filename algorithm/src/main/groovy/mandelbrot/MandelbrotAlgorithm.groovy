/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-11
 */
interface MandelbrotAlgorithm<T> {
    MandelbrotResult compute(T real, T imag)
    T two()
    T zero()
    T four()
}
