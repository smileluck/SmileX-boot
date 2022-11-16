package top.zsmile.test.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

@Slf4j
@SpringBootTest
public class DateTest {
    @Test
    public void date() {
        // 直接输出
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Date => {}, year={}, month={}", date, date.getYear(), date.getMonth());
        log.info("LocalDateTime => {}, year={}, month={}", localDateTime, localDateTime.getYear(), localDateTime.getMonthValue());

        // localDateTime extra operation
        log.info("localDateTime getDayOfMonth={}, getDayOfWeek={}, ", localDateTime.getDayOfMonth(), localDateTime.getDayOfWeek());


        // 指定年月生成对象
        Date pointDate = new Date(2022, 1, 1);
        LocalDateTime pointDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);
        log.info("pointDate => {}", pointDate);
        log.info("pointDateTime => {}", pointDateTime);

        // 格式化输出
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("Date format =>{}", simpleDateFormat.format(date));
        log.info("LocalDateTime format =>{}", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    }

    @Test
    public void localDateTimeDemo() {

        log.info("==========创建时间对象==========");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime pointDateTime = LocalDateTime.of(2022, 1, 1, 0, 0);

        log.info("localDateTime => {}", localDateTime);
        log.info("==========获取时间信息==========");
        log.info("Year : {}", localDateTime.getYear());
        log.info("Month : {}", localDateTime.getMonth());
        log.info("Day : {}", localDateTime.getDayOfMonth());
        log.info("Hour : {}", localDateTime.getHour());
        log.info("Minute : {}", localDateTime.getMinute());
        log.info("Second : {}", localDateTime.getSecond());

        log.info("==========转换时间==========");
        log.info("LocalDate : {}", localDateTime.toLocalDate());
        log.info("LocalTime : {}", localDateTime.toLocalTime());


        log.info("==========加减时间==========");
        log.info("操作年 : {}", localDateTime.plusYears(1));
        log.info("操作月 : {}", localDateTime.plusMonths(1));
        log.info("操作日 : {}", localDateTime.plusDays(1));
        log.info("操作时 : {}", localDateTime.plusHours(1));
        log.info("操作分 : {}", localDateTime.plusMinutes(1));
        log.info("操作秒 : {}", localDateTime.plusSeconds(1));

        log.info("==========设置时间==========");
        log.info("操作年 : {}", localDateTime.withYear(2021));
        log.info("操作月 : {}", localDateTime.withMonth(1));
        log.info("操作日 : {}", localDateTime.withDayOfMonth(1));
        log.info("操作时 : {}", localDateTime.withHour(1));
        log.info("操作分 : {}", localDateTime.withMinute(1));
        log.info("操作秒 : {}", localDateTime.withSecond(1));


        log.info("==========格式化==========");
        log.info("BASIC_ISO_DATE : {}", localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        log.info("ISO_DATE_TIME : {}", localDateTime.format(DateTimeFormatter.ISO_DATE_TIME));
        log.info("自定义 : {}", localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


        log.info("==========反解析==========");
        log.info("parse : {}", localDateTime.parse("2021-01,01 11:22-02", DateTimeFormatter.ofPattern("yyyy-MM,dd HH:mm-ss")));

        log.info("==========间隔(Period)==========");
        LocalDate beforeLocalDate = LocalDate.of(2021, 1, 1);
        LocalDate afterLocalDate = LocalDate.of(2022, 12, 1);
        log.info("first : {}", beforeLocalDate);
        log.info("sec : {}", afterLocalDate);
        Period period = Period.between(beforeLocalDate, afterLocalDate);
        log.info("period => {}", period);
        log.info("period Year : {}", period.getYears());
        log.info("period Month : {}", period.getMonths());
        log.info("period Day : {}", period.getDays());
        log.info("period TotalMonths : {}", period.toTotalMonths());


        log.info("==========时间间隔(Duration)==========");
        LocalDateTime beforeLocalDateTime = LocalDateTime.of(2021, 1, 1, 1, 1);
        LocalDateTime afterLocalDateTime = LocalDateTime.of(2022, 12, 1, 1, 1);
        log.info("first : {}", beforeLocalDateTime);
        log.info("sec : {}", afterLocalDateTime);
        Duration duration = Duration.between(beforeLocalDateTime, afterLocalDateTime);
        log.info("duration => {}", duration);
        log.info("duration to days : {}", duration.toDays());
        log.info("duration to hours : {}", duration.toHours());
        log.info("duration to millis : {}", duration.toMillis());
        log.info("duration seconds : {}", duration.getSeconds());


        log.info("==========时间比较==========");
        log.info("first : {}", beforeLocalDateTime);
        log.info("sec : {}", afterLocalDateTime);
        log.info("now : {}", localDateTime);
        log.info("first isAfter sec: {}", beforeLocalDateTime.isAfter(afterLocalDateTime));
        log.info("sec isAfter now: {}", afterLocalDateTime.isAfter(localDateTime));
        log.info("first isBefore sec: {}", beforeLocalDateTime.isBefore(afterLocalDateTime));
        log.info("sec isBefore now: {}", afterLocalDateTime.isBefore(localDateTime));
    }

    @Test
    public void durationTest() {
        log.info("==========时间间隔(Duration)==========");
        LocalDateTime beforeLocalDateTime = LocalDateTime.of(2022, 1, 1, 1, 0);
        LocalDateTime afterLocalDateTime = LocalDateTime.of(2022, 1, 2, 0, 59);
        log.info("first : {}", beforeLocalDateTime);
        log.info("sec : {}", afterLocalDateTime);
        Duration duration = Duration.between(beforeLocalDateTime, afterLocalDateTime);
        log.info("duration => {}", duration);
        log.info("duration to days : {}", duration.toDays());
        log.info("duration to hours : {}", duration.toHours());
        log.info("duration to minute : {}", duration.toMinutes());
        log.info("duration seconds : {}", duration.getSeconds());
        log.info("duration to millis : {}", duration.toMillis());
    }

}
