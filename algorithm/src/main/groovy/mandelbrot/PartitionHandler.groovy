package mandelbrot
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-08.
 */
interface PartitionHandler {

    void handle(int totalPoints,  int totalPartitions, List<Point> partition)
}
