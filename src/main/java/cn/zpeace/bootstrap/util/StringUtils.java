package cn.zpeace.bootstrap.util;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
public class StringUtils {

    public static String joinSkipEmpties(CharSequence conjunction, String... str) {
        return Arrays.stream(str).filter(e -> e != null && !e.isEmpty()).collect(Collectors.joining(conjunction));
    }
}
