package mandelbrot

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-02.
 */
class Grid<T> {

    final private Config<T> config

    def Grid(Config<T> config) {
        this.config = config
    }

    List<Point> getPointsList() {

        final  List<Point> points = []

        (config.one() + config.height).times { y ->
            (config.one() + config.width).times { x ->
                def point = new Point<T>()
                point.x = x
                point.y = y
                points.add(point)
            }
        }

        Collections.unmodifiableList(points)
    }


}
