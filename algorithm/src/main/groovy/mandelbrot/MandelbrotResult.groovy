package mandelbrot

import groovy.transform.builder.Builder

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-11
 */
@Builder
class MandelbrotResult {
    Integer x
    Integer y
    Integer totalIterations
    Boolean  inSet


    @Override
    public String toString() {
        return "MandelbrotResult{" +
                "x=" + x +
                ", y=" + y +
                ", totalIterations=" + totalIterations +
                ", inSet=" + inSet +
                '}';
    }
}
