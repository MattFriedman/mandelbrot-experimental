package com.listoutfitter.mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
interface Operations<T> {
    T square(T input)

    T mult(T x, T y)
}
