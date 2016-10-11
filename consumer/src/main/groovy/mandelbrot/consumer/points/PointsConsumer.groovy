package mandelbrot.consumer.points

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

/**
 *
 * Mandelbrot Experiment
*
 * Created by Matt Friedman 2016-10-07
 */
@SpringBootApplication
@ImportResource([
        'points-consumer.xml',
        'results-consumer.xml',
        'shared-rabbit-config.xml'
])
class PointsConsumer {

    public static void main(String[] args) {
            SpringApplication.run(PointsConsumer, args)
    }
}
