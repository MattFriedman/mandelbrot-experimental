/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import org.apfloat.ApcomplexMath
import org.apfloat.Apfloat
import spock.lang.Specification
/**
 * Created by Matt Friedman on 2016-10-18.
 */
class ApMathFiddle extends Specification {

    def 'test negative logs'() {

        given:
            def precision = 1000
            def neg = new Apfloat(-2, precision)
        when:
            def result = ApcomplexMath.log(neg)
        then:
            println result.real()
            println result.imag()

            println ApcomplexMath.abs(result)

            noExceptionThrown()
    }


}
