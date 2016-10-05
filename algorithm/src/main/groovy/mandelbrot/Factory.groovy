package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
interface Factory<T> {

    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations)

    ToIntegerConverter<T> toIntegerConverter()

    T one()
}
