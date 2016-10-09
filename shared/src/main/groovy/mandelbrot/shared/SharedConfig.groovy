package mandelbrot.shared

import org.springframework.context.annotation.Configuration
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@Configuration
class SharedConfig {
    public final static pointsQueueName = 'points'
    public final static resultsQueueName = 'results'
    public final static mandelbrotExchangeName = 'mandelbrot-exchange'


//    @Bean
//    ConnectionFactory connectionFactory() {
//        new CachingConnectionFactory().with {
//            host = 'docker-host'
//            port = 5672
//            delegate
//        }
//    }
}
