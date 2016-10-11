/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

/**

 * Created by Matt Friedman 2016-09-12
 */
interface ToIntegerConverter<T> {

    int convertToInteger(T input)

}
