package cn.zpeace.bootstrap.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author skiya
 * @date Created on 2021-11-26.
 */
public class ToTimestampSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    void setUp() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new ToTimestampSerializer());
        module.addSerializer(LocalDate.class, new ToTimestampSerializer());
        module.addSerializer(LocalDateTime.class, new ToTimestampSerializer());
        MAPPER.registerModule(module);
    }

    @Test
    public void convertDateToTimestamp() throws JsonProcessingException {
        Date date = new Date();
        String timestampStr = MAPPER.writeValueAsString(date);
        Assertions.assertEquals(Long.parseLong(timestampStr), date.getTime());
    }

    @Test
    public void convertLocalDateToTimestamp() throws JsonProcessingException {
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
        String timestampStr = MAPPER.writeValueAsString(localDate);
        Assertions.assertEquals(Long.parseLong(timestampStr), localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    @Test
    public void convertLocalDateTimeToTimestamp() throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.systemDefault());
        String timestampStr = MAPPER.writeValueAsString(localDateTime);
        Assertions.assertEquals(Long.parseLong(timestampStr), localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}