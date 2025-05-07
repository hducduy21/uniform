package nashtech.rookie.uniform.application.configuration;

import nashtech.rookie.uniform.shared.dtos.RatingCreateEvent;
import nashtech.rookie.uniform.shared.dtos.RatingUpdatedEvent;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class KafkaMessageConverter extends JsonMessageConverter {
    public KafkaMessageConverter() {
        super();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("nashtech.rookie.uniform.shared.dtos");
        typeMapper.setIdClassMapping(Collections.singletonMap("ratingCreated", RatingCreateEvent.class));
        typeMapper.setIdClassMapping(Collections.singletonMap("ratingUpdated", RatingUpdatedEvent.class));
        this.setTypeMapper(typeMapper);
    }
}
