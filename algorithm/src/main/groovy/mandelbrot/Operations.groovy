/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-12
 */
interface Operations<T> {
    T square(T input)

    T mult(T x, T y)
}
