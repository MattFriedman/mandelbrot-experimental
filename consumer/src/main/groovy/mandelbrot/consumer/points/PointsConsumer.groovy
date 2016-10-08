package mandelbrot.consumer.points

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-07.
 */
@SpringBootApplication
@ImportResource('points-consumer-integration.xml')
class PointsConsumer {

    public static void main(String[] args) {
        SpringApplication.run(PointsConsumer, args)
    }
}
