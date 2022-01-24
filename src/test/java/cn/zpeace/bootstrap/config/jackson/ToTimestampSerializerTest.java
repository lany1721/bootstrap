package cn.zpeace.bootstrap.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.Data;
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
        System.out.println(timestampStr);
        Assertions.assertEquals(Long.parseLong(timestampStr), date.getTime());
    }

    @Test
    public void convertLocalDateToTimestamp() throws JsonProcessingException {
        LocalDate localDate = LocalDate.now(ZoneId.of("UTC"));
        String timestampStr = MAPPER.writeValueAsString(localDate);
        System.out.println(timestampStr);
        Assertions.assertEquals(Long.parseLong(timestampStr), localDate.atStartOfDay(ZoneId.of("UTC")).toInstant().toEpochMilli());
    }

    @Test
    public void convertLocalDateTimeToTimestamp() throws JsonProcessingException {
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("UTC"));
        String timestampStr = MAPPER.writeValueAsString(localDateTime);
        System.out.println(timestampStr);
        Assertions.assertEquals(Long.parseLong(timestampStr), localDateTime.atZone(ZoneId.of("UTC")).toInstant().toEpochMilli());
    }

    @Test
    public void convertIllegalTypeToTimestamp() {
        TestObj obj = new TestObj();
        obj.setS1("123");
        Assertions.assertThrows(JsonMappingException.class, () -> {
            String s = MAPPER.writeValueAsString(obj);
            System.out.println(s);
        });

    }

    @Data
    static class TestObj {

        @JsonSerialize(using = ToTimestampSerializer.class)
        private String s1;


    }
}