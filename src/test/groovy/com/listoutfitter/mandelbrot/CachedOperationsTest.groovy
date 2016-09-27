package com.listoutfitter.mandelbrot

import spock.lang.Specification

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
class CachedOperationsTest extends Specification {
    def "test square"() {
        given:
            def cache = new CachedOperations<Double>()
        when:
            def result = cache.square(2d)

            profile {
                10000.times{
                    222.333d * 222.333d
                }
            }.prettyPrint()

            profile {
                10000.times{
                    cache.square(222.333d)
                }
            }.prettyPrint()
        then:
            result == 4d
    }
}
