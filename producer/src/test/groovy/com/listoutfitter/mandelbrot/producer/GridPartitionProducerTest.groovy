package com.listoutfitter.mandelbrot.producer

import mandelbrot.Grid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@ContextConfiguration(classes = [Producer])
@ComponentScan(basePackageClasses = Producer)
class GridPartitionProducerTest extends Specification {

    @Autowired
    GridPartitionProducer gridPartitionProducer

    def "Produce"() {
        given:

            final int maxIterations = 1000

            def width = 1280 * 2
            def height = width

            def realStart = -2.0
            def realEnd = 1.0
            def imagStart = -1.5
            def imageEnd = 1.5

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imageEnd, 1d)


            def partitionSize = 7500
        when:

            gridPartitionProducer.produce(grid, partitionSize, width, maxIterations)

        then:
            println 'foo'
    }
}
