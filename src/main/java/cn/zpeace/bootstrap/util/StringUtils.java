package cn.zpeace.bootstrap.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
public class StringUtils {


    /**
     * 连接字符串,并且忽略空字符
     *
     * @param conjunction 连接符
     * @param str         字符串
     * @return {@link String}
     */
    public static String joinSkipEmpties(CharSequence conjunction, String... str) {
        return Arrays.stream(str).filter(e -> e != null && !e.isEmpty()).collect(Collectors.joining(conjunction));
    }

    /**
     * 连接字符串,并且忽略空字符(去除空格后)
     *
     * @param conjunction 连接符
     * @param str         字符串
     * @return {@link String}
     */
    public static String joinSkipBlank(CharSequence conjunction, String... str) {
        return Arrays.stream(str).filter(e -> e != null && !"".equals(e.trim())).collect(Collectors.joining(conjunction));
    }
}
