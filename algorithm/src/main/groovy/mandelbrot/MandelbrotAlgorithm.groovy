package mandelbrot

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-11
 */
interface MandelbrotAlgorithm<T> {
    MandelbrotResult compute(T real, T imag)
    T two()
    T zero()
    T four()
}
