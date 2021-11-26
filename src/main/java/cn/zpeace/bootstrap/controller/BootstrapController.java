package cn.zpeace.bootstrap.controller;

import cn.hutool.core.collection.ListUtil;
import cn.zpeace.bootstrap.constant.JudgeEnum;
import cn.zpeace.bootstrap.support.ApiResponse;
import cn.zpeace.bootstrap.support.PageRequest;
import cn.zpeace.bootstrap.support.PageResponse;
import cn.zpeace.bootstrap.validator.EnumCheck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<String> hello() {
        return ApiResponse.ok("hello");
    }

    @PostMapping("/page")
    public ApiResponse<PageResponse<String>> page(@RequestBody PageRequest page) {
        PageResponse<String> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(page.getCurrent());
        pageResponse.setSize(page.getSize());
        pageResponse.setPages(10L);
        pageResponse.setTotal(100L);
        pageResponse.setRecords(ListUtil.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10"));
        return ApiResponse.ok(pageResponse);
    }

    @GetMapping("/i18n")
    public ApiResponse<String> i18n() {
        String message = messageSource.getMessage("welcome", null, LocaleContextHolder.getLocale());
        return ApiResponse.ok(message);
    }

    @GetMapping("/enum")
    public ApiResponse<Void> checkEnum(@EnumCheck(clazz = JudgeEnum.class) Integer enable) {
        log.info("enable:{}", enable);
        return ApiResponse.ok();
    }
}
