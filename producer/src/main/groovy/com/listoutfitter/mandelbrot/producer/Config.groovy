package com.listoutfitter.mandelbrot.producer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource
/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-06
 */
@SpringBootApplication
@ImportResource(['shared-rabbit-config.xml'])
class Config {

    public static void main(String[] args) {
        SpringApplication.run(Config, args);
    }
}
