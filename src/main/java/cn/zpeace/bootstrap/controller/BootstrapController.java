package cn.zpeace.bootstrap.controller;

import cn.zpeace.bootstrap.model.entity.DateTimeTest;
import cn.zpeace.bootstrap.model.request.EnumTestRequest;
import cn.zpeace.bootstrap.support.ApiResponse;
import cn.zpeace.bootstrap.support.PageRequest;
import cn.zpeace.bootstrap.support.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.TimeZone;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class BootstrapController {

    private final MessageSource messageSource;

    @GetMapping("/hello")
    public String hello(@NotBlank String word) {
        return "hello," + word;
    }

    @GetMapping("/null")
    public void nil() {
    }

    @GetMapping("/dateTime")
    public DateTimeTest dateTimeTest() {
        return new DateTimeTest();
    }

    @GetMapping("/tz")
    public TimeZone tz(TimeZone tz) {
        System.out.println(tz);
        return tz;
    }

    @PostMapping("/page")
    public ApiResponse<String> page(@RequestBody PageRequest page) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setCurrent(page.getCurrent());
        pageResponse.setSize(page.getSize());
        pageResponse.setPages(10L);
        pageResponse.setTotal(100L);
        return ApiResponse.page(null, pageResponse);
    }

    @GetMapping("/i18n")
    public ApiResponse<String> i18n() {
        String message = messageSource.getMessage("welcome", null, LocaleContextHolder.getLocale());
        return ApiResponse.ok(message);
    }

    @PostMapping("/enum")
    public ApiResponse<Void> checkEnum(@Valid @RequestBody EnumTestRequest request) {
        return ApiResponse.ok();
    }

}
