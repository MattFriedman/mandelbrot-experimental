/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package mandelbrot.shared

import org.springframework.context.annotation.Configuration
/**

 * Created by Matt Friedman 2016-10-06
 */
@Deprecated
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
