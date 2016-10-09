package com.listoutfitter.mandelbrot.producer

import mandelbrot.Point
import org.springframework.messaging.handler.annotation.Header

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
interface PointsGateway {
    void send(
            List<Point> pointList,
            @Header('sequenceSize') int totalPartitions,
            @Header('correlationId') UUID id,
            @Header('width') int width,
            @Header('maxIterations') int maxIterations
    )
}
