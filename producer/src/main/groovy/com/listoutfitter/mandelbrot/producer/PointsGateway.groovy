package com.listoutfitter.mandelbrot.producer

import mandelbrot.Point
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
interface PointsGateway {
    void send(List<Point> pointList, int totalPoints, UUID id, int width, int maxIterations)
}
