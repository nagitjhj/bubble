package com.my.bubble.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    /**
     * 1. Queue로 메시지를 발행
     * 2. Producer 역할 -> Direct Exchange 전략
     */
    public void sendMessage(MessageDTO messageDTO) {
        log.info("message send: {}", messageDTO.toString());
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDTO);
    }

    /**
     * 1. Queue에서 메시지를 구독
     * 2. Consumer 역할
     */
//    @RabbitListener(queues = "${rabbitmq.queue.name}")
//    public void receiveMessage(MessageDTO messageDTO) {
//        log.info("Received Message : {}", messageDTO.toString());
//    }
}
