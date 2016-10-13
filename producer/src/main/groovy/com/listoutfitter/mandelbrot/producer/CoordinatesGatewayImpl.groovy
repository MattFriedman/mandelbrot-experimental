/*
 * Copyright (c) 2016. Matt Friedman <matt.friedman@gmail.com>
 * All Rights Reserved.
 */

package com.listoutfitter.mandelbrot.producer

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Grid
import mandelbrot.Point
import mandelbrot.Zoom
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**

 * Created by Matt Friedman 2016-10-08
 */
class CoordinatesGatewayImpl<T> implements CoordinatesGateway<T> {

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
    void send(List<Point<T>> pointList, Grid<T> grid,
              int totalPartitions, UUID correlationId,
              int maxIterations, Zoom<T> zoom,
              Long precision = null) {

        def jsonPointList = objectMapper.writeValueAsBytes(pointList)

        def jsonGrid = objectMapper.writeValueAsBytes(grid)

        def builder = MessageBuilder
                .withBody(jsonPointList)
                .setHeader('X', String.valueOf(zoom.X))
                .setHeader('Y', String.valueOf(zoom.Y))
                .setHeader('zoomFactor', String.valueOf(zoom.zoomFactor))
                .setHeader('correlationId', correlationId)
                .setHeader('maxIterations', maxIterations)
                .setHeader('grid', jsonGrid)
                .setHeader('sequenceSize', totalPartitions)
                .setHeader('precision', precision)

        def message = builder.build()

        rabbitTemplate.send('mandelbrot-exchange', 'points', message)
    }
}
