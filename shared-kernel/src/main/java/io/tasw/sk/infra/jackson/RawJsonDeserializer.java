package io.tasw.sk.infra.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * {@link JsonDeserializer} that deserializes the parsed JSON into a JSON string. To be used for the deserialization
 * of {@link com.fasterxml.jackson.annotation.JsonRawValue} fields.
 */
public class RawJsonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        return mapper.writeValueAsString(mapper.readTree(p));
    }
}