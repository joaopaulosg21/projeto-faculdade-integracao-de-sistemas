package projeto.faculdade.dadosClimaticos.configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JsonConfig {
    private static final String FORMATO_DATA_HORA = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_DATA_HORA);
            builder.serializers(new LocalDateTimeSerializer(formatter));
            builder.deserializers(new LocalDateTimeDeserializer(formatter));
        };
    }
}
