/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

import java.util.stream.IntStream

/**

 * Created by Matt Friedman 2016-10-02
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

    public static final double defaultRealStart = -2.0
    public static final double defaultRealEnd = 1.0
    public static final double defaultImagStart = -1.5
    public static final double defaultImagEnd = 1.5

//    static Grid<Double> of(double width, double height) {
//        new Grid<Double>(width, height, defaultRealStart, defaultRealEnd, defaultImagStart, defaultImagEnd, 1)
//    }

   static  Grid<T> withZoom(T width, T height, Zoom zoom, T one) {

        def realStart = zoom.X - 2 / zoom.zoomFactor
        def realEnd = zoom.X + 1 / zoom.zoomFactor
        def imagStart = zoom.Y - 1.5 / zoom.zoomFactor
        def imageEnd = zoom.Y + 1.5 / zoom.zoomFactor

        new Grid<T>(width, height, realStart, realEnd, imagStart, imageEnd, one)
    }

    //        def realStart = X - 2/zoom
//        def realEnd = X + 1/zoom
//        def imagStart = Y - 1.5/zoom
//        def imageEnd = Y + 1.5/zoom


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Grid(
            @JsonProperty('width') T width,
            @JsonProperty('height') T height,
            @JsonProperty('realStart') T realStart,
            @JsonProperty('realEnd') T realEnd,
            @JsonProperty('imagStart') T imagStart,
            @JsonProperty('imagEnd') T imagEnd,
            @JsonProperty('one') T one) {

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

    @JsonIgnore
    private final defaultPointCreator = { int x, int y ->
        def point = new Point<T>()
        point.x = x
        point.y = y
        point.real = realStart + incrementReal * point.x
        point.imag = imagStart + incrementImag * point.y
        point
    }

    @JsonIgnore
    int calculateTotalPartitions(int partitionSize) {

        // assume that width and height are the same.
        // todo need to enforce/simplify that width and height are the same...
        int numPoints = (width + 1) * (width + 1)

        int remainder = numPoints % partitionSize

        def add = remainder > 0 ? 1 : 0

        numPoints / partitionSize + add
    }

    @JsonIgnore
    void handlePartitions(int partitionSize, PartitionHandler partitionHandler) {

        int counter = 0

        List<Point<T>> partition = []

        IntStream.rangeClosed(0, height as Integer).forEach { y ->

            IntStream.rangeClosed(0, width as Integer).forEach { x ->

                counter++

                def point = new Point<T>()
                point.x = x
                point.y = y

                partition.add(point)

                if (counter == partitionSize) {
                    partitionHandler.handle(partition)
                    partition = []
                    counter = 0
                }
            }
        }

        if (partition.size() > 0) {
            partitionHandler.handle(partition)
        }
    }

    @JsonIgnore
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
