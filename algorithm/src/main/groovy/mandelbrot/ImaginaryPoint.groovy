package mandelbrot
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-09-10.
 */
class ImaginaryPoint<T> {

    /**
     * The original x coordinate of the point on the grid
     */
    Integer x

    /**
     * The original y coordinate of the point on the grid
     */
    Integer y

    /**
     * The (complex plane) real coordinate on the complex plain that corresponds to x
     */
    T real

    /**
     * The (complex plane) imaginary coordinate on the complex plane that corresponds to y
     */
    T imag
}
