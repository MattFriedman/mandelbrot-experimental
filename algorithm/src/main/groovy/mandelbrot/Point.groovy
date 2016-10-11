package mandelbrot
/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-09-10
 */
class Point<T> {

    Integer x
    Integer y

    T real
    T imag

    String toString() {
        "[x: ${x}, real: $real, y: $y imag: $imag]"
    }
}
