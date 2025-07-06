package com.lingosphinx.quiz.domain;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

class LanguageCodeSerializer extends JsonSerializer<LanguageCode> {
    @Override
    public void serialize(LanguageCode value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getValue());
    }
}
