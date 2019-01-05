package com.ddd.books.in.spring.configuration.Serialization;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES;
import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;
import static com.fasterxml.jackson.databind.SerializationFeature.*;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Locale.ENGLISH;

@Component
public class CustomObjectMapper extends ObjectMapper {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final DateTimeFormatter FORMATTER = ofPattern(DATE_TIME_FORMAT);

    public CustomObjectMapper() {
        super();
        configure(WRITE_DATES_AS_TIMESTAMPS, false);
        configure(WRITE_DURATIONS_AS_TIMESTAMPS, false);
        configure(FAIL_ON_EMPTY_BEANS, false);
        configure(ALLOW_SINGLE_QUOTES, true);
        configure(DEFAULT_VIEW_INCLUSION, false);

        setLocale(ENGLISH);

        registerModule(dateTimeModule());
        registerModule(trimmingModule());

        setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private SimpleModule dateTimeModule() {
        return new JavaTimeModule()
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(FORMATTER))
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(FORMATTER));
    }

    private SimpleModule trimmingModule() {
        final JsonDeserializer<String> trimmingDeserializer =
                new StdScalarDeserializer<String>(String.class) {
                    @Override
                    public String deserialize(
                            final JsonParser parser,
                            final DeserializationContext context) throws IOException {
                        return parser.getValueAsString().trim();
                    }
                };

        return new SimpleModule().addDeserializer(String.class, trimmingDeserializer);
    }
}
