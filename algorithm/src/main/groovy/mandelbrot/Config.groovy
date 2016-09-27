package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-12.
 */
interface Config<T> {

    MandelbrotAlgorithm getMandelbrotAlgorithm(int maxIterations)

    ToIntegerConverter<T> toIntegerConverter()

    T one()

    int getPrecision()

    T getWidth()

    T getHeight()

    T getRealStart()

    T getRealEnd()

    T getImagStart()

    T getImagEnd()
}
