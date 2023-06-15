package com.example.kafkaapplication.kafka.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class TestListener {

  @KafkaListener(topics = "partition-test-topic-2", groupId = "test-group-2")
  public void test(@Payload String message,
                   @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
                   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                   @Header(KafkaHeaders.OFFSET) String offset) {
    log.info("partition : " + partition + " offset : " + offset + " message : " + message);
  }

}
