package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import mandelbrot.PartitionHandler
import mandelbrot.Point
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@ContextConfiguration(classes = [Config])
@ComponentScan(basePackageClasses = Config)
class GridPartitionConfigTest extends Specification {

    @Autowired
    GridPartitionProducer gridPartitionProducer



    @Autowired
    PointsGateway pointsGateway

//    void produce(Grid<?> grid, int partitionSize, int width, int maxIterations) {
//
//        def correlationId = UUID.randomUUID()
//
//        List<List<Point>> partitions = partition(grid.getPointsInGrid(), partitionSize)
//
//        // total number of partitions
//        def total = partitions.size()
//
//        partitions.each { pointList ->
//            pointsGateway.send(pointList, total, correlationId, width, maxIterations)
//        }
//    }

    def 'using partition handler'() {
        given:

            def correlationId = UUID.randomUUID()

            final int maxIterations = 1000

            def width = 1280 * 4
            def height = width

            def realStart = Grid.defaultRealStart
            def realEnd = Grid.defaultRealEnd
            def imagStart = Grid.defaultImagStart
            def imagEnd = Grid.defaultImagEnd

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imagEnd, 1d)

            def partitionSize = 50000

        when:
            grid.handlePartitions(partitionSize, new PartitionHandler() {

                @Override
                void handle(int totalPoints, int totalPartitions, List<Point> partition) {
                    pointsGateway.send(partition, totalPartitions, correlationId, width, maxIterations)
                }
            })
        then:
            noExceptionThrown()
    }


    def "Produce"() {
        given:

            final int maxIterations = 1000

            def width = 128
            def height = width

            def realStart = -2.0
            def realEnd = 1.0
            def imagStart = -1.5
            def imageEnd = 1.5

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imageEnd, 1d)

            def partitionSize = 200
        when:

            gridPartitionProducer.produce(grid, partitionSize, width, maxIterations)

        then:
            noExceptionThrown()
    }
}
