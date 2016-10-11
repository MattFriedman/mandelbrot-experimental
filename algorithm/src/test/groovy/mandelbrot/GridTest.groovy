package mandelbrot

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.collections4.ListUtils
import spock.lang.Specification

import java.util.stream.Collectors

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-03
 */
class GridTest extends Specification {


    def 'test serialize'() {
        given:

            def mapper = new ObjectMapper()

            def width = 128
            def height = 128

            def realStart = Grid.defaultRealStart
            def realEnd = Grid.defaultRealEnd
            def imagStart = Grid.defaultImagStart
            def imagEnd = Grid.defaultImagEnd

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imagEnd, 1d)
        when:

            def str = mapper.writeValueAsString(grid)
        then:
            noExceptionThrown()
        when:
            def grid1 = mapper.readValue(str, Grid)
        then:
            grid1.height == 128
    }


    def 'test calculate total partitions'() {

        given:
            def height = width

            def realStart = -2.0
            def realEnd = 1.0
            def imagStart = -1.5
            def imageEnd = 1.5

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imageEnd, 1d)
        when:
            int totalPartitions = grid.calculateTotalPartitions(partitionSize)
        then:
            totalPartitions == expectedNumPartitions
        where:
            width | partitionSize | expectedNumPartitions
            1     | 1             | 4
            1     | 2             | 2
            1     | 3             | 2
            1     | 4             | 1
            2     | 1             | 9
            2     | 2             | 5
            2     | 3             | 3
            2     | 4             | 3
            2     | 5             | 2
            2     | 6             | 2
            2     | 7             | 2
            2     | 8             | 2
            2     | 9             | 1
            3     | 1             | 16
            3     | 2             | 8
            3     | 3             | 6
            3     | 4             | 4
            3     | 15            | 2
    }


    def 'create partitions'() {
        given:
            def partitionHandler = new PartitionHandler() {

                @Override
                void handle(int totalPoints, int totalPartitions, List<Point> partition) {
                    println partition
                }

                @Override
                void handle(List partition) {

                }
            }

            def width = 3
            def height = width

            def realStart = -2.0
            def realEnd = 1.0
            def imagStart = -1.5
            def imageEnd = 1.5

            def grid = new Grid<Double>(width, height, realStart, realEnd, imagStart, imageEnd, 1d)
        when:
            grid.handlePartitions(2, partitionHandler)
        then:
            1 == 0
    }


    def "test getPointsInGrid"() {

        given:
            def width = 16
            def height = width
            def grid = Grid.of(width, height)
        when:
            def points = grid.getPointsInGrid()
        then:

            def ll = ListUtils.partition(points, width + 1)

            ll.each {
                println it.stream().map { "${it.x}:${it.y}".padLeft(5, ' ') }.collect(Collectors.toList()).join(', ')
            }
    }
}
