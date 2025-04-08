package br.com.pulsar.products.configs;

import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import com.pulsar.common.common_kafka.factory.KafkaListenerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FileUploadEvent> fileUploadKafkaListenerContainerFactory() {
        return KafkaListenerFactory.createFactory(
                bootstrapServers,
                "file-upload-consumer",
                FileUploadEvent.class,
                kafkaTemplate
        );
    }
}
