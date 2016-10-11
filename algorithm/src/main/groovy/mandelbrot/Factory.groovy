package mandelbrot

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-12
 */
interface Factory<T> {

    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations)

    ToIntegerConverter<T> toIntegerConverter()

    T one()
}
