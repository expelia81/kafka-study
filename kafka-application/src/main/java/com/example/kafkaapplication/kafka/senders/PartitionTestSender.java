package com.example.kafkaapplication.kafka.senders;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
@Slf4j
public class PartitionTestSender {

  private final KafkaTemplate<String, String> kafkaTemplate;

  @PostConstruct
  public void init() {
    Flux.interval(java.time.Duration.ofSeconds(2))
        .doOnEach(aLong -> kafkaTemplate.send("partition-test-topic-2", aLong.get() + " 번째 테스트 메시지"))
        .subscribe();
    System.out.println("PartitionTestSender init");
  }
}
