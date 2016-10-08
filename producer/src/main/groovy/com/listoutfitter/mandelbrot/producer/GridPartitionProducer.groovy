package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.Point
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import static org.apache.commons.collections4.ListUtils.partition

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-02.
 */
@Component
class GridPartitionProducer {

    @Autowired
    PointsGateway mandelbrotGateway

    void produce(Grid<?> grid, int partitionSize, int width, int maxIterations) {

        def id = UUID.randomUUID()

        List<List<Point>> partitions = partition(grid.getPointsInGrid(), partitionSize)

        // total number of partitions
        def total = partitions.size()

        for (List<Point> pointList : partitions) {

            mandelbrotGateway.send(pointList, total, id, width, maxIterations)
        }
    }
}
