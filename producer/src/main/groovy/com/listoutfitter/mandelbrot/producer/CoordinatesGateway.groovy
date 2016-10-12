/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.Point
/**

 * Created by Matt Friedman 2016-10-06
 */
interface CoordinatesGateway {
    void send(List<Point> pointList, Grid grid, int sequenceSize, UUID id, int maxIterations)
}
