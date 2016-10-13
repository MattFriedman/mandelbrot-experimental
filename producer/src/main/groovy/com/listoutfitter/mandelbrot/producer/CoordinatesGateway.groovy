/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.Point
import mandelbrot.Zoom

/**

 * Created by Matt Friedman 2016-10-06
 */
interface CoordinatesGateway<T> {

    void send(List<Point<T>> pointList, Grid<T> grid, int sequenceSize, UUID id, int maxIterations, Zoom<T> zoom)

    void send(List<Point<T>> pointList, Grid<T> grid, int sequenceSize, UUID id, int maxIterations, Zoom<T> zoom, Long precision)
}
