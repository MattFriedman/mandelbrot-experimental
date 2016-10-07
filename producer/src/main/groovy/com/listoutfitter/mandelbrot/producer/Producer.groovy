package com.listoutfitter.mandelbrot.producer

import mandelbrot.shared.SharedConfig
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource

import static mandelbrot.shared.SharedConfig.*
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@SpringBootApplication
@ImportResource("/mandelbrot-integration.xml")
@Import(SharedConfig)
class Producer {

    @Bean
    Queue pointsQueue() {
        new Queue(pointsQueueName, false)
    }

    @Bean
    resultsQueue() {
        new Queue(resultsQueueName, false)
    }

    @Bean
    TopicExchange mandelbrotExchange() {
        new TopicExchange(mandelbrotExchangeName)
    }

    @Bean
    Binding pointsBinding() {
        BindingBuilder.bind(pointsQueue()).to(mandelbrotExchange()).with(pointsQueueName)
    }

    @Bean
    Binding resultsBinding() {
        BindingBuilder.bind(resultsQueue()).to(mandelbrotExchange()).with(resultsQueueName)
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(pointsQueueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    Receiver receiver() {
        return new Receiver();
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    def rabbitTemplate() {
        def template = new RabbitTemplate(connectionFactory())
        template
    }

    public static void main(String[] args) {
        SpringApplication.run(Producer, args);
    }
}
