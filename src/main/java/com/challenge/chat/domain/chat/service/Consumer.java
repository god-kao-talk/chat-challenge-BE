package com.challenge.chat.domain.chat.service;

import com.challenge.chat.domain.chat.constant.KafkaConstants;
import com.challenge.chat.domain.chat.dto.ChatDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    /**
     * @KafkaLister 어노테이션을 통해 Kafka로부터 메세지를 받을 수 있음
     * template.convertAndSend를 통해 WebSocket으로 메시지를 전송
     * Message를 작성할 때 경로 잘 보고 import
     */
    @Autowired
    SimpMessagingTemplate msgOperation;

    @KafkaListener(
            topics = KafkaConstants.KAFKA_TOPIC,
            groupId = KafkaConstants.GROUP_ID
    )
    public void consume(ChatDto chatDto) {
        log.info("난 컨슈머야 message={}", chatDto);
        msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), chatDto);
    }
}
