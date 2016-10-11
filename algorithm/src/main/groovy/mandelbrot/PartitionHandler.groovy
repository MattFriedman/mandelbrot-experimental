package mandelbrot
/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-08
 */
interface PartitionHandler<T> {

    void handle( List<Point<T>> partition)
}
