package br.com.pulsar.products.infra.configs.kafka;

import br.com.pulsar.products.domain.dtos.kafka.FileUploadEvent;
import com.pulsar.common.common_kafka.consumer.KafkaEventConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@Configuration
public class KafkaConsumer {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = "fileUploadListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, FileUploadEvent> kafkaListenerContainerFactory() {
        return KafkaEventConsumer.createFactory(FileUploadEvent.class, bootstrapServers);
    }
}
