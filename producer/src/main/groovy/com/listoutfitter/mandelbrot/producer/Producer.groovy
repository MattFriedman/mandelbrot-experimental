package com.listoutfitter.mandelbrot.producer

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ImportResource
/**
 * (c) Exchange Solutions Inc.
 * <br>
 * Created by mfriedman on 2016-10-06.
 */
@SpringBootApplication
@ImportResource("/mandelbrot-integration.xml")
class Producer {

    final queueName = 'mandelbrot'

    @Bean
    Queue mandelbrotQueue() {
        new Queue(queueName, false)
    }

    @Bean
    TopicExchange mandelbrotExchange() {
        new TopicExchange('mandelbrot-exchange')
    }

    @Bean
    Binding binding() {
        BindingBuilder.bind(mandelbrotQueue()).to(mandelbrotExchange()).with(queueName)
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
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
        def template =new RabbitTemplate(connectionFactory())
        template
    }

    @Bean
    ConnectionFactory connectionFactory() {
        new CachingConnectionFactory().with {
            host = 'docker-host'
            port = 5672
            delegate
        }
    }

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Producer, args);

//        System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            System.out.println(beanName);
//        }
    }
}
