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
    PointsGateway pointsGateway

    void produce(Grid<?> grid, int partitionSize, int width, int maxIterations) {

        def correlationId = UUID.randomUUID()

        List<List<Point>> partitions = partition(grid.getPointsInGrid(), partitionSize)

        // total number of partitions
        def total = partitions.size()

        partitions.each { pointList ->
            pointsGateway.send(pointList, total, correlationId, width, maxIterations)
        }
    }
}
