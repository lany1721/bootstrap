package cn.zpeace.bootstrap.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
class StringUtilsTest {

    @Test
    void joinSkipEmpties() {
        String s = StringUtils.joinSkipEmpties(",", "", null, "1");
        Assertions.assertEquals("1", s);
    }
}