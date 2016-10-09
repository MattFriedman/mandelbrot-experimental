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


    public static final defaultRealStart = -2.0
    public static final defaultRealEnd = 1.0
    public static final defaultImagStart = -1.5
    public static final defaultImagEnd = 1.5

    static Grid<Double> of(double width, double height) {
        new Grid<Double>(width, height, defaultRealStart, defaultRealEnd, defaultImagStart, defaultImagEnd, 1)
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

    private final defaultPartitionHandler = { List<Point<?>> pointList ->
        println pointList
    }

    int calculateTotalPartitions(int size) {

        // assume that width and height are the same.
        // todo need to enforce/simplify that width and height are the same...
        int numPoints = (width + 1) * (width + 1)

        int remainder = numPoints % size

        def add = remainder > 0 ? 1 : 0

        numPoints / size + add
    }


    void handlePartitions(int partitionSize, PartitionHandler partitionHandler) {


        def totalPartitions = calculateTotalPartitions(partitionSize)

        def totalPoints = (width+1) * (width+1)

        int counter = 0
        List<Point<T>> pointList = []

        IntStream.rangeClosed(0, height as Integer).forEach { y ->

            IntStream.rangeClosed(0, width as Integer).forEach { x ->

                counter++

                def point = new Point<T>()
                point.x = x
                point.y = y
                point.real = realStart + incrementReal * point.x
                point.imag = imagStart + incrementImag * point.y

                pointList.add(point)

                if (counter == partitionSize) {
                    partitionHandler.handle(totalPoints,  totalPartitions, pointList)
                    pointList = []
                    counter = 0
                }
            }
        }

        if (pointList.size() > 0) {
            partitionHandler.handle(totalPoints,  totalPartitions, pointList)
        }
    }

    List<Point> getPointsInGrid(Closure<Point> createsAPoint = defaultPointCreator) {

        final List<Point> points = []

//        compute sequence size from grid
//        should be: w+1 * h+1....

        IntStream.rangeClosed(0, height as Integer).forEach { y ->
            IntStream.rangeClosed(0, width as Integer).forEach { x ->
                points.add(createsAPoint.call(x, y))
            }
        }

        // TODO looks like this is the source of oome when there's a large grid. what about using disk to cache, then streaming from there?
        // todo perhaps partition from here and not cache in a collection??
        /*
        create points, then send to xml integration flow, then aggregate... then send aggregated to rabbit
        should allow to have only one partition in mem at a time.
        - then, the sequenceSize is not known, however...
        - UNLESS, the sequence size can be computed first, from the grid dimensions, that should be possible.
         */
        Collections.unmodifiableList(points)
    }
}
