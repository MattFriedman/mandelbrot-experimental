/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import mandelbrot.Mandelbrot
import org.apfloat.Apfloat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
/**

 * Created by Matt Friedman 2016-10-06
 */
@ContextConfiguration(classes = [Config])
@ComponentScan(basePackageClasses = Config)
class GridPartitionConfigTest extends Specification {




    @Autowired
    Factory<Double> doublePrecisionMandelbrotFactory

    @Autowired
    Factory<Apfloat> apfloatPrecisionMandelbrotFactory

    def setup() {
        Mandelbrot.init()
    }

    def 'apfloat precision mandelbrot'() {
        given:

            long precision = Apfloat.INFINITE

            def correlationId = UUID.randomUUID()

            final int maxIterations = 2500

            def width = 2560

            def height = width

            def partitionSize = 100000


        /*
        -0.13856524454488
Center Y Coordinate	-0.64935990748190
Size	.00000000045
Max Iterations Per Pixel	5000000000
         */


        /*
        Zoom level: 30.90
Magnification: 2.004e9 Coordinates (real,imaginary): (-0.896320622847, +0000000000029) Comment: 41.361th power.



Zoom level: 219.81
Magnification: 1.480e66 Coordinates (real,imaginary): 
-1.985540371654130485531439267191269851811165434636382820704394766801377 
+0.000000000000000000000000000001565120217211466101983496092509512479178
         */

//            0.274,0.482
//            -0.1592,-1.0317
            def X = new Apfloat('-1.985540371654130485531439267191269851811165434636382820704394766801377', precision)
            def Y = new Apfloat('+0.000000000000000000000000000001565120217211466101983496092509512479178', precision)


            def zoomFactor =  new Apfloat('256e10', precision)


            def zoom = apfloatPrecisionMandelbrotFactory.zoom(X, Y, zoomFactor, precision)
            def grid = apfloatPrecisionMandelbrotFactory.grid(width, height, zoom, precision)
            def totalPartitions = grid.calculateTotalPartitions(partitionSize)

            def partitionHandler = apfloatPrecisionMandelbrotFactory.partitionHandler(
                    partitionSize, grid, totalPartitions, correlationId, maxIterations, zoom, precision
            )


        when:

            grid.handlePartitions(partitionSize, partitionHandler)

        then:

            noExceptionThrown()
    }


    def 'double precision mandelbrot'() {
        given:

            def correlationId = UUID.randomUUID()

            final int maxIterations = 2000

            def width = 128
            def height = width

            def partitionSize = 1000

            def zoom = doublePrecisionMandelbrotFactory.zoom(0, 0, 1)
            def grid = doublePrecisionMandelbrotFactory.grid(width, height, zoom)
            def totalPartitions = grid.calculateTotalPartitions(partitionSize)

            def partitionHandler = doublePrecisionMandelbrotFactory.partitionHandler(
                    partitionSize, grid, totalPartitions, correlationId, maxIterations, zoom
            )


        when:

            grid.handlePartitions(partitionSize, partitionHandler)

        then:

            noExceptionThrown()
    }
}
