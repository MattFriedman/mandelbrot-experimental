/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import groovy.transform.builder.Builder

/**
 * Created by Matt Friedman on 2016-10-11.
 */
@Builder
class Zoom<T> {

    T X
    T Y
    T zoomFactor

}
