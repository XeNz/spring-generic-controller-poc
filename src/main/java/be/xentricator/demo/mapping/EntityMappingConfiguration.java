package be.xentricator.demo.mapping;

import be.xentricator.demo.entity.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class EntityMappingConfiguration {
    /**
     * This is the only mapping configuration a consumer should do to get the generic controller mechanism working
     */
    @Bean
    public EntityMapping entityMapping() {
        Map<String, EntityMappingInformation> mappingInformation = Map.of(
                "book", new EntityMappingInformation(Long.class, Book.class)
        );
        return new EntityMapping(mappingInformation);
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
