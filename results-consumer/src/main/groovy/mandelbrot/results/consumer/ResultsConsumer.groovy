/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.results.consumer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

/**
 * Created by Matt Friedman 2016-10-07
 */
@SpringBootApplication
@ImportResource([
        'results-consumer.xml',
        'shared-rabbit-config.xml'
])
class ResultsConsumer {

    public static void main(String[] args) {
            SpringApplication.run(ResultsConsumer, args)
    }
}
