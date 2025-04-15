package br.com.pulsar.products.configs.kafka;

import br.com.pulsar.products.dtos.kafka.FileUploadEvent;
import br.com.pulsar.products.dtos.kafka.ProductCreateEvent;
import com.pulsar.common.common_kafka.dispatcher.KafkaEventDispatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaDispatcher {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean(name = "fileUploadKafkaTemplate")
    public KafkaTemplate<String, FileUploadEvent> kafkaTemplateFileEvent() {
        return KafkaEventDispatcher.createTemplate(bootstrapServers);
    }

    @Bean(name = "productCreateKafkaTemplate")
    public KafkaTemplate<String, ProductCreateEvent> kafkaTemplateProductCreateEvent() {
        return KafkaEventDispatcher.createTemplate(bootstrapServers);
    }
}
