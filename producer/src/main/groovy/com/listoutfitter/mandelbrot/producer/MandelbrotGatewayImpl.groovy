package com.listoutfitter.mandelbrot.producer

import com.fasterxml.jackson.databind.ObjectMapper
import mandelbrot.Point
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.support.CorrelationData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@Component
class MandelbrotGatewayImpl implements MandelbrotGateway {

    @Autowired
    RabbitTemplate rabbitTemplate

    ObjectMapper objectMapper = new ObjectMapper()

    @Override
    void send(List<Point> pointList, long total, UUID id) {

        def bytes = objectMapper.writeValueAsBytes(pointList)

        def msg = MessageBuilder.withBody(bytes)
                .setHeader('id', id)
                .setHeader('total', total)
                .build()

        def cd = new CorrelationData(id.toString())

        rabbitTemplate.send('mandelbrot-exchange', 'mandelbrot', msg, cd)
    }
}
