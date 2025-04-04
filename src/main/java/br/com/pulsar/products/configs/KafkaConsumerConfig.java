package br.com.pulsar.products.configs;

import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import com.pulsar.common.common_kafka.factory.KafkaListenerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FileUploadEvent> fileUploadKafkaListenerContainerFactory() {
        return KafkaListenerFactory.createFactory(
                bootstrapServers,
                "file-upload-consumer",
                FileUploadEvent.class
        );
    }
}
