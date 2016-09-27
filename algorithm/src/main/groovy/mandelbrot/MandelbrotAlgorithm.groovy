package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-11.
 */
interface MandelbrotAlgorithm<T> {
    MandelbrotResult compute(T real, T imag)
    T two()
    T zero()
    T four()
}
