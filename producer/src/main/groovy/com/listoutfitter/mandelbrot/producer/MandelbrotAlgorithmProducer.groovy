package com.listoutfitter.mandelbrot.producer

import mandelbrot.MandelbrotAlgorithm
import mandelbrot.MandelbrotResult

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-02.
 */
class MandelbrotAlgorithmProducer<T> implements MandelbrotAlgorithm<T> {
    @Override
    MandelbrotResult compute(T real, T imag) {
        return null
    }

    @Override
    T two() {
        return null
    }

    @Override
    T zero() {
        return null
    }

    @Override
    T four() {
        return null
    }
}
