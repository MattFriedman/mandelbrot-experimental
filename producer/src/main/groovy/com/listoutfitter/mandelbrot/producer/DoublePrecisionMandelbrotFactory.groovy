/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.PartitionHandler
import mandelbrot.Point
import mandelbrot.Zoom
import org.springframework.beans.factory.annotation.Autowired


class DoublePrecisionMandelbrotFactory implements Factory<Double> {

    @Autowired
    CoordinatesGateway<Double> doubleCoordinatesGateway

    @Override
    PartitionHandler<Double> partitionHandler(
            int partitionSize,
            Grid<Double> grid,
            int totalPartitions,
            UUID correlationId,
            int maxIterations,
            Zoom<Double> zoom,
            Long precision = null) {

        new PartitionHandler<Double>() {

            // passing precision of -1 to indicate that this is the Double impl, not the apfloat one.

            @Override
            void handle(List<Point<Double>> partition) {
                doubleCoordinatesGateway.send(partition, grid, totalPartitions, correlationId, maxIterations, zoom, -1L)
            }
        }
    }

    @Override
    Zoom<Double> zoom(Double X, Double Y, Double zoomFactor, Long precision = null) {
        new Zoom<Double>().with {
            delegate.X = X
            delegate.Y = Y
            delegate.zoomFactor = zoomFactor
            delegate
        }
    }

    @Override
    Grid<Double> grid(int width, int height, Zoom<Double> zoom, Long precision = null) {

        def realStart = zoom.X - 2 / zoom.zoomFactor
        def realEnd = zoom.X + 1 / zoom.zoomFactor
        def imagStart = zoom.Y - 1.5 / zoom.zoomFactor
        def imageEnd = zoom.Y + 1.5 / zoom.zoomFactor

        new Grid<Double>(width, height, realStart, realEnd, imagStart, imageEnd, 1D)
    }
}
