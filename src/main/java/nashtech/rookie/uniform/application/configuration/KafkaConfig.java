package nashtech.rookie.uniform.application.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic ratingCreatedTopic() {
        return TopicBuilder.name("rating-created")
                .build();
    }

    @Bean
    public NewTopic ratingUpdatedTopic() {
        return TopicBuilder.name("rating-updated")
                .build();
    }
}
