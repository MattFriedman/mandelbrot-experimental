package com.listoutfitter.mandelbrot.producer

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Point
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.CorrelationData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.integration.IntegrationMessageHeaderAccessor
import org.springframework.stereotype.Component

import static mandelbrot.shared.SharedConfig.mandelbrotExchangeName
import static mandelbrot.shared.SharedConfig.pointsQueueName

/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@Component
class PointsGatewayImpl implements PointsGateway {

    @Autowired
    RabbitTemplate rabbitTemplate

    ObjectMapper objectMapper = new ObjectMapper()

    @Override
    void send(List<Point> pointList, long total, UUID id) {

        def bytes = objectMapper.writeValueAsBytes(pointList)

        // see: http://docs.spring.io/spring-integration/docs/current/reference/html/messaging-routing-chapter.html#aggregator-functionality
        def msg = MessageBuilder.withBody(bytes)
                .setHeader(IntegrationMessageHeaderAccessor.CORRELATION_ID, id)
                .setHeader(IntegrationMessageHeaderAccessor.SEQUENCE_SIZE, total)
                .build()

        def cd = new CorrelationData(id.toString())

        rabbitTemplate.send(mandelbrotExchangeName, pointsQueueName, msg, cd)
    }
}
