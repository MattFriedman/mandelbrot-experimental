/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-12
 */
interface Config<T> {

    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations)

    ToIntegerConverter<T> toIntegerConverter()

    T one()

    int getPrecision()

    T getWidth()

    T getHeight()

    T getRealStart()

    T getRealEnd()

    T getImagStart()

    T getImagEnd()
}
