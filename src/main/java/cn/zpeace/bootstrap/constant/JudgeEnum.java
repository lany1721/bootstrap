package cn.zpeace.bootstrap.constant;

import java.util.*;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
public enum JudgeEnum implements BaseEnum {
    /**
     * 是
     */
    NO(0, "否"),

    /**
     * 否
     */
    YES(1, "是");

    public final Integer value;

    public final String label;

    JudgeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    /**
     * 通过枚举值来获取枚举类
     * 枚举类中不存在该枚举值则抛出异常,存在则返回JudgeEnum
     *
     * @param value 值
     * @return JudgeEnum or null
     */
    public static JudgeEnum valueOf(Integer value) {
        JudgeEnum[] values = JudgeEnum.values();
        Optional<JudgeEnum> first = Arrays.stream(values).filter(e -> e.getValue().equals(value)).findFirst();
        return first.orElseThrow(() -> new IllegalArgumentException(String.format(
                "Invalid value '%s' for value given! Has to be either '0' or '1' (case insensitive).", value
        )));
    }

    public static List<Map<String, String>> mappings = new ArrayList<>(JudgeEnum.values().length);

    static {
        for (JudgeEnum e : JudgeEnum.values()) {
            Map<String, String> enumMap = new HashMap<>();
            enumMap.put("key", String.valueOf(e.value));
            enumMap.put("value", e.label);
            mappings.add(enumMap);
        }
    }
}
