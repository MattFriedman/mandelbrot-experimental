/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.PartitionHandler
import mandelbrot.Point
import mandelbrot.Zoom
import org.apfloat.Apfloat
import org.springframework.beans.factory.annotation.Autowired

import static com.google.common.base.Preconditions.checkNotNull

class ApfloatPrecisionMandelbrotFactory implements Factory<Apfloat> {

    @Autowired
    CoordinatesGateway<Apfloat> apfloatCoordinatesGateway

    @Override
    PartitionHandler<Apfloat> partitionHandler(
            int partitionSize,
            Grid<Apfloat> grid,
            int totalPartitions,
            UUID correlationId,
            int maxIterations,
            Zoom<Apfloat> zoom,
            Long precision = null
    ) {

        checkNotNull(precision, "The apfloat implementation requires the precision argument.")

        new PartitionHandler<Apfloat>() {
            @Override
            void handle(List<Point<Apfloat>> partition) {
                apfloatCoordinatesGateway.send(
                        partition, grid, totalPartitions, correlationId, maxIterations, zoom, precision
                )
            }
        }
    }

    @Override
    Zoom<Apfloat> zoom(Apfloat X, Apfloat Y, Apfloat zoomFactor, Long precision = null) {

        checkNotNull(precision, "The apfloat implementation requires the precision argument.")

        new Zoom<Apfloat>().with {
            delegate.X = X.precision(precision)
            delegate.Y = Y.precision(precision)
            delegate.zoomFactor = zoomFactor.precision(precision)
            delegate
        }
    }



    @Override
    Grid<Apfloat> grid(int width, int height, Zoom<Apfloat> zoom, Long precision = null) {

        checkNotNull(precision, "The apfloat implementation requires the precision argument.")

        def ONE = new Apfloat(1L, precision)
        def TWO = new Apfloat(2L, precision)
        def ONE_AND_A_HALF = new Apfloat(1.5D, precision)

        def preciseWidth = new Apfloat(width, precision)
        def preciseHeight = new Apfloat(height, precision)
        def preciseX = zoom.X.precision(precision)
        def preciseY = zoom.Y.precision(precision)
        def preciseZoomFactor = zoom.zoomFactor.precision(precision)

        // todo refactor this duplicate code.
        Apfloat realStart = preciseX - TWO / preciseZoomFactor
        Apfloat realEnd = preciseX + ONE / preciseZoomFactor
        Apfloat imagStart = preciseY - ONE_AND_A_HALF / preciseZoomFactor
        Apfloat imageEnd = preciseY + ONE_AND_A_HALF / preciseZoomFactor

        new Grid<Apfloat>(preciseWidth, preciseHeight, realStart, realEnd, imagStart, imageEnd, ONE)
    }
}
