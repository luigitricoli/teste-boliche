package com.richardson.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSerializer extends JsonSerializer<Date> {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    
    @Override
    public void serialize(Date data, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        String dataFormatada = this.sdf.format(data);
        jsonGenerator.writeString(dataFormatada);
    }
}