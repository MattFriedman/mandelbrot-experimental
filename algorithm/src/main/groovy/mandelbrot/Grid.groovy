package mandelbrot

import java.util.stream.IntStream

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-02.
 */
class Grid<T> {

    final T one
    final T imagEnd
    final T imagStart
    final T realEnd
    final T realStart

    final T height
    final T width

    final T incrementImag
    final T incrementReal

    static Grid<Double> of(double width, double height) {
        new Grid<Double>(width, height, 2, 1, -1.5, 1.5, 1)
    }

    def Grid(T width, T height, T realStart, T realEnd, T imagStart, T imagEnd, T one) {

        this.one = one

        this.height = height
        this.width = width
        this.imagEnd = imagEnd
        this.imagStart = imagStart
        this.realEnd = realEnd
        this.realStart = realStart

        this.incrementImag = (imagEnd - imagStart) / height
        this.incrementReal = (realEnd - realStart) / width
    }

    private final defaultPointCreator = { int x, int y ->
        def point = new Point<T>()
        point.x = x
        point.y = y
        point.real = realStart + incrementReal * point.x
        point.imag = imagStart + incrementImag * point.y
        point
    }

    List<Point> getPointsInGrid(Closure<Point> createsAPoint = defaultPointCreator) {

        final List<Point> points = []

        IntStream.rangeClosed(0, height as Integer).forEach { y ->
            IntStream.rangeClosed(0, width as Integer).forEach { x ->
                points.add(createsAPoint.call(x, y))
            }
        }

        Collections.unmodifiableList(points)
    }
}