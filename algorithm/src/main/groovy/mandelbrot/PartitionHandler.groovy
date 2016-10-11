/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot
/**

 * Created by Matt Friedman 2016-10-08
 */
interface PartitionHandler<T> {

    void handle( List<Point<T>> partition)
}
