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
 * 时间类型序列化时,序列化为时间戳
 * <p>用法: </p>
 * <li>单个字段:在时间类型成员属性标注注解 @JsonSerialize(using = ToTimestampSerializer.class)</li>
 * <li>全局配置:使用 jackson 注册 module,如:module.addSerializer(LocalDateTime.class, new ToTimestampSerializer());</li>
 *
 *
 * <p>只支持：LocalDate,LocalDateTime,Date. 其他类型会抛出IllegalArgumentException</p>
 *
 * @author skiya
 * @date Created on 2021-11-24.
 */
public class ToTimestampSerializer extends StdSerializer<Object> {

    public ToTimestampSerializer() {
        super(Object.class);
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
            final long milli = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            jsonGenerator.writeNumber(milli);
        } else if (value instanceof Date) {
            Date date = (Date) value;
            final long milli = date.getTime();
            jsonGenerator.writeNumber(milli);
        } else {
            throw new IllegalArgumentException("[" + value.getClass().getTypeName() + "] can't serialize to timestamp");
        }
    }
}
