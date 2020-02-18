package com.richardson.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {
    
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        //sdf.setTimeZone(TimeZone.getTimeZone(ZoneId.of( "America/Sao_Paulo" )));
        String data = jsonParser.getText();
        try { // 2019/06/01 15:35
            return sdf.parse(data);
        } catch (Exception e) {
            return null;
        }
    }
}