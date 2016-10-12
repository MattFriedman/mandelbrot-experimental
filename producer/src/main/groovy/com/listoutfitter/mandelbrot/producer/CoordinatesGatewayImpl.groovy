/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Grid
import mandelbrot.Point
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
/**

 * Created by Matt Friedman 2016-10-08
 */
@Component
class CoordinatesGatewayImpl implements CoordinatesGateway {

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
//    @Deprecated
//    @Override
//    void send(List<Point> pointList,
//              int totalPartitions,
//              UUID id,
//              int width,
//              int maxIterations) {
//
//        def json = objectMapper.writeValueAsBytes(pointList)
//
//        def message = MessageBuilder
//                .withBody(json)
//                .setHeader('correlationId', id)
//                .setHeader('sequenceSize', totalPartitions)
//                .setHeader('width', width)
//                .setHeader('maxIterations', maxIterations)
//                .build()
//
//        rabbitTemplate.send('mandelbrot-exchange', 'points', message)
//    }

    @Override
    void send(List<Point> pointList, Grid grid, int totalPartitions, UUID correlationId, int maxIterations) {

        def jsonPointList = objectMapper.writeValueAsBytes(pointList)
        def jsonGrid = objectMapper.writeValueAsBytes(grid)

        def message = MessageBuilder
                .withBody(jsonPointList)
                .setHeader('correlationId', correlationId)
                .setHeader('maxIterations', maxIterations)
                .setHeader('grid', jsonGrid)
                .setHeader('sequenceSize', totalPartitions)
                .build()

        rabbitTemplate.send('mandelbrot-exchange', 'points', message)
    }
}
