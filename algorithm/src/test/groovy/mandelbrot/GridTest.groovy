package mandelbrot

import org.apache.commons.collections4.ListUtils
import spock.lang.Specification

import java.util.stream.Collectors

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-02.
 */
class GridTest extends Specification {


    def "test getPoints"() {
        given:
            def grid = new Grid(new DoublePrecisionDefaultConfig2(new PropertiesLoader('/mandelbrot.properties')))
        when:
            def points = grid.getPointsList()
        then:

            def parts = ListUtils.partition(points, 1000)

            def serializedParts = parts.parallelStream().map { list ->
                list.parallelStream().map { p ->
                    "${p.x}:${p.y}"
                }.collect(Collectors.toList())
            }.collect()

            println "foo"


    }
}
