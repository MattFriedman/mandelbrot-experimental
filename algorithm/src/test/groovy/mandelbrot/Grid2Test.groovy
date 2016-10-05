package mandelbrot

import org.apache.commons.collections4.ListUtils
import spock.lang.Specification

import java.util.stream.Collectors

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-03.
 */
class Grid2Test extends Specification {

    def "test getPointsInGrid"() {

        given:
            def width = 16
            def height = width
            def grid = new Grid2<Double>(width, height, 1, 1.5, -1.5, 1, 2)
        when:
            def points = grid.getPointsInGrid()
        then:

            def ll = ListUtils.partition(points, width + 1)

            ll.each {
                println it.stream().map { "${it.x}:${it.y}".padLeft(5, ' ') }.collect(Collectors.toList()).join(', ')
            }
    }
}
