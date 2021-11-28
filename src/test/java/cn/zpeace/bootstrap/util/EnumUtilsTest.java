package cn.zpeace.bootstrap.util;

import cn.zpeace.bootstrap.constant.JudgeEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
public class EnumUtilsTest {

    @Test
    void getFieldMappings() {
        Map<String, String> valueLabelMapping = EnumUtils.getFieldMappings(JudgeEnum.class, "value", "label");
        Assertions.assertEquals("{0=否, 1=是}", valueLabelMapping.toString());
    }

    @Test
    void getFieldMappingList() {
        List<String> valueLabels = EnumUtils.getFieldMappingList(JudgeEnum.class, "value", "label");
        Assertions.assertEquals("[0-否, 1-是]", valueLabels.toString());
    }
}
