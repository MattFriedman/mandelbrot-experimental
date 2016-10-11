/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.PartitionHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**

 * Created by Matt Friedman 2016-10-06
 */
@ContextConfiguration(classes = [Config])
@ComponentScan(basePackageClasses = Config)
class GridPartitionConfigTest extends Specification {

    @Autowired
    PointsGateway pointsGateway

    def 'using partition handler'() {
        given:

            def correlationId = UUID.randomUUID()

            final int maxIterations = 1000

            def width = 256
            def height = width

            def realStart = Grid.defaultRealStart
            def realEnd = Grid.defaultRealEnd
            def imagStart = Grid.defaultImagStart
            def imagEnd = Grid.defaultImagEnd

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imagEnd, 1d)

            def partitionSize = 3000

        when:

            grid.handlePartitions(partitionSize, new PartitionHandler() {

                @Override
                void handle(List partition) {

                    def totalPartitions = grid.calculateTotalPartitions(partitionSize)
                    pointsGateway.send(partition, grid, totalPartitions, correlationId, maxIterations)
                }
            })

        then:

            noExceptionThrown()
    }
}
