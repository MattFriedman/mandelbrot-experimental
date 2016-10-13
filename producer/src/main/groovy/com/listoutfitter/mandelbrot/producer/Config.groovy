/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import org.apfloat.Apfloat
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
/**

 * Created by Matt Friedman 2016-10-06
 */
@SpringBootApplication
@ImportResource(['shared-rabbit-config.xml'])
class Config {

    @Bean
    Factory<Apfloat> apfloatPrecisionMandelbrotFactory() {
        new ApfloatPrecisionMandelbrotFactory()
    }

    @Bean
    Factory<Double> doublePrecisionMandelbrotFactory() {
        new DoublePrecisionMandelbrotFactory()
    }

    @Bean
    CoordinatesGateway<Double> doubleCoordinatesGateway() {
        new CoordinatesGatewayImpl<Double>()
    }

    @Bean
    CoordinatesGateway<Apfloat> apfloatCoordinatesGateway() {
        new CoordinatesGatewayImpl<Apfloat>()
    }

    public static void main(String[] args) {
        SpringApplication.run(Config, args);
    }
}
