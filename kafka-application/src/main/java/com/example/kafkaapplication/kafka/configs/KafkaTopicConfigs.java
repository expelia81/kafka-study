package com.example.kafkaapplication.kafka.configs;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "spring.kafka")
@Setter
@Getter
@Slf4j
@RequiredArgsConstructor
public class KafkaTopicConfigs {

  private List<TopicInfo> topics;

  private final KafkaAdmin kafkaAdmin;

  private final KafkaTemplate<String, String> kafkaTemplate;



  @PostConstruct
  public void createTopics() {
    Map<String, NewTopic> newTopics = new HashMap<>();

    topics.forEach(topic -> {
      log.info("topic : {}", topic);
      newTopics.put(topic.getName(), new NewTopic(topic.getName(), topic.getPartitions(), (short) topic.getReplicationFactor()));
    });

    kafkaAdmin.createOrModifyTopics(newTopics.values().toArray(new NewTopic[0]));

//
//    Flux.interval(java.time.Duration.ofSeconds(2))
//        .doOnEach(aLong -> kafkaTemplate.send("test", aLong.get() + " : 테스트 메시지"))
//        .subscribe();

  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class TopicInfo {
    private String name;
    private int partitions;
    private int replicationFactor;

    @Override
    public String toString() {
      return "Topic : {" +
          "name='" + name + '\'' +
          ", partitions=" + partitions +
          ", replicationFactor=" + replicationFactor +
          '}';
    }
  }
}
