package com.listoutfitter.mandelbrot.producer

import groovy.transform.builder.Builder
import mandelbrot.Point

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@Builder
class Container {

    long total

    UUID uuid

    List<Point> pointList
}
