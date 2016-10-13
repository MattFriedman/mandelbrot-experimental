/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Mandelbrot
import org.apfloat.Apfloat
import spock.lang.Specification
/**
 * Created by Matt Friedman on 2016-10-12.
 */
class ApfloatSerialization extends Specification {

    static {
        Mandelbrot.init()
    }

    def 'check precision'() {
        given:
            def two = new Apfloat('2', 10)
            def X = new Apfloat('0.0000001', 10)
            def zoomFactor = new Apfloat('1000', 10)
            def realStart = X - two / zoomFactor
        when:
            def precision = realStart.precision()
        then:
            precision == 10L
    }

    def 'value of for apfloat'() {
        given:
            def s = '0.123456789'
        when:
            def f = Apfloat.valueOf(s)
        then:
            f == new Apfloat(s)
            f.precision() == 9
            f.radix() == 10

    }


    def 'apfloat'() {
        given:
            def f = new Apfloat('-0.12345', 2)
        when:
            def result = f.toString()
        then:
            result == '-1.2e-1'
        when:
            def apf = new Apfloat(result)
        then:
            apf.toString(true) == '-0.12'
            apf == Apfloat.valueOf(result)
    }
}
