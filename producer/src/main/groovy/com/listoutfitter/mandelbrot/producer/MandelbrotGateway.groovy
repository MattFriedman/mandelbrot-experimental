package com.listoutfitter.mandelbrot.producer

import mandelbrot.Point
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
interface MandelbrotGateway {
    void send(List<Point> pointList, long total, UUID id)
}
