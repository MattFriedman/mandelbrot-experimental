/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.PartitionHandler
import mandelbrot.Zoom

interface Factory<T> {

    PartitionHandler<T> partitionHandler(
            int partitionSize,
            Grid<T> grid,
            int totalPartitions,
            UUID correlationId,
            int maxIterations,
            Zoom<T> zoom,
            Long precision
    )

    PartitionHandler<T> partitionHandler(
            int partitionSize,
            Grid<T> grid,
            int totalPartitions,
            UUID correlationId,
            int maxIterations,
            Zoom<T> zoom
    )

    Zoom<T> zoom(T X, T Y, T zoomFactor, Long precision)
    Zoom<T> zoom(T X, T Y, T zoomFactor)

    Grid<T> grid(int width, int height, Zoom<T> zoom, Long precision)
    Grid<T> grid(int width, int height, Zoom<T> zoom)
}
