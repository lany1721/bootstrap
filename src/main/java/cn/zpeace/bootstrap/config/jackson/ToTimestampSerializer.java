package cn.zpeace.bootstrap.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 把时间类型转换为时间戳
 * 用法，在时间类型成员属性标注注解 @JsonSerialize(using = ToTimestampSerializer.class)
 * 只支持：LocalDate、LocalDateTime、Date
 * 其他类型会序列化为null
 *
 * @author skiya
 * @date Created on 2021-11-24.
 */
public class ToTimestampSerializer extends StdSerializer<Object> {

    public ToTimestampSerializer() {
        super(Object.class);
    }

    protected ToTimestampSerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            jsonGenerator.writeNull();
        } else if (value instanceof LocalDate) {
            LocalDate localDate = (LocalDate) value;
            final long milli = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            jsonGenerator.writeNumber(milli);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) value;
            long milli = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            jsonGenerator.writeNumber(milli);
        } else if (value instanceof Date) {
            Date date = (Date) value;
            long milli = date.getTime();
            jsonGenerator.writeNumber(milli);
        } else {
            jsonGenerator.writeNull();
        }
    }
}
