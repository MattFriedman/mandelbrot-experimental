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
    MandelbrotGateway mandelbrotGateway

    void produce(Grid<?> grid) {

        def id = UUID.randomUUID()

        def pointsInGrid = grid.getPointsInGrid()
        def total = pointsInGrid.size()

        List<List<Point>> partitions = partition(grid.getPointsInGrid(), 4)

        for (List<Point> pointList : partitions) {

            mandelbrotGateway.send(pointList, total, id)
        }
    }
}
