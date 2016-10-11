package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.Point
/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-06
 */
interface PointsGateway {

//    void send(
//            List<Point> pointList,
//            @Header('sequenceSize') int totalPartitions,
//            @Header('correlationId') UUID id,
//            @Header('width') int width,
//            @Header('maxIterations') int maxIterations
//    )

    void send(
            List<Point> pointList,
             Grid grid,
            int sequenceSize,
             UUID id,
             int maxIterations
    )
}
