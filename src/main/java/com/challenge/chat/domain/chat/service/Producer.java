package com.challenge.chat.domain.chat.service;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.exception.RestApiException;
import com.challenge.chat.exception.dto.ChatErrorCode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {

    @Autowired
    private KafkaTemplate<String, Chat> kafkaTemplate;

    public void send(String topic, Chat data) {
        log.info("sending data='{}' to topic='{}'", data, topic);
        try {
            kafkaTemplate.send(topic, data).get(); // send to react clients via websocket (STOMP)
        } catch (Exception e) {
            throw new RestApiException(ChatErrorCode.KAFKA_PRODUCER_ERROR);
        }
    }
}

