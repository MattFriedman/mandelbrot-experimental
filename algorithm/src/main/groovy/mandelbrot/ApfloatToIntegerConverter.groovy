/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import org.apfloat.Apfloat

/**

 * Created by Matt Friedman 2016-09-12
 */
class ApfloatToIntegerConverter implements ToIntegerConverter<Apfloat> {

    @Override
    int convertToInteger(Apfloat input) {
        input.intValue()
    }
}
