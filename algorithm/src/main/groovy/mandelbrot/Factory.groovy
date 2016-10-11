/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-12
 */
interface Factory<T> {

    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations)

    ToIntegerConverter<T> toIntegerConverter()

    T one()
}
