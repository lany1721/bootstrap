package cn.zpeace.bootstrap.model.entity;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created on 2021-12-28.
 *
 * @author skiya
 */
@Data
public class DateTimeTest {

    private LocalDateTime localDateTime = LocalDateTime.now();

    private LocalDate localDate = LocalDate.now();

    private LocalTime localTime = LocalTime.now();

    private Date date = new Date();

}
