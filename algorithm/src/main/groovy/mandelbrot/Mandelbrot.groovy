/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import org.apfloat.Apfloat

/**
 * Created by Matt Friedman on 2016-10-12.
 */
class Mandelbrot {

    static void init() {}

    static {

        Apfloat.metaClass.'static'.valueOf = { String value ->
            new Apfloat(value)
        }

        Apfloat.metaClass.'static'.valueOf = { Number value ->
            new Apfloat(String.valueOf(value))
        }

        Apfloat.metaClass.plus = { Apfloat addend ->
            delegate.add(addend)
        }

        Apfloat.metaClass.minus = { Apfloat subtrahend ->
            delegate.subtract(subtrahend)
        }

        Apfloat.metaClass.div = { Apfloat divisor ->
            delegate.divide(divisor)
        }

        Apfloat.metaClass.next = {
            delegate.add(new Apfloat(1))
        }
    }
}
