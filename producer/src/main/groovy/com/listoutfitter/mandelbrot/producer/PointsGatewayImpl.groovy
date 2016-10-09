package com.listoutfitter.mandelbrot.producer

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Point
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-08.
 */
@Component
class PointsGatewayImpl implements PointsGateway {

    RabbitTemplate rabbitTemplate

    ObjectMapper objectMapper

    @Autowired
    ConnectionFactory connectionFactory

    @PostConstruct
    def init() {
        rabbitTemplate = new RabbitTemplate(connectionFactory)
        objectMapper = new ObjectMapper()
    }

    /**
     * Note: I tried sending this via the xml configuration style but it seemed very slow. Using
     * rabbitTemplate appears to be faster but I don't know why; perhaps I'll profile this.
     * Might have been a heap space thing, needs investigation...
     */
    @Override
    void send(List<Point> pointList,
              int totalPartitions,
              UUID id,
              int width,
              int maxIterations) {

        /*
        TODO given a sufficiently large grid this will use up all mem and give a oome - can the data be streamed to avoid this?

         */

        def json = objectMapper.writeValueAsBytes(pointList)

        def message = MessageBuilder
                .withBody(json)
                .setHeader('correlationId', id)
                .setHeader('sequenceSize', totalPartitions)
                .setHeader('width', width)
                .setHeader('maxIterations', maxIterations)
                .build()

        rabbitTemplate.send('mandelbrot-exchange', 'points', message)
    }
}
