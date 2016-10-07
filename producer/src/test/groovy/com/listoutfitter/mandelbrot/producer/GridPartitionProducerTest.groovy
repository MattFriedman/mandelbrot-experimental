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
            def width = 16
            def height = width
            def grid = Grid.of(width, height)
        when:
            gridPartitionProducer.produce(grid)
        then:
            println 'foo'
    }
}
