package mandelbrot

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-12
 */
interface Operations<T> {
    T square(T input)

    T mult(T x, T y)
}
