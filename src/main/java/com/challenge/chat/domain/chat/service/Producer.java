package com.challenge.chat.domain.chat.service;

import com.challenge.chat.domain.chat.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Producer {

    @Autowired
    private KafkaTemplate<String, ChatDto> kafkaTemplate;

    public void send(String topic, ChatDto data) {
        log.info("sending data='{}' to topic='{}'", data, topic);
        kafkaTemplate.send(topic, data); // send to react clients via websocket (STOMP)
    }
}

