package cn.zpeace.bootstrap.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.zpeace.bootstrap.util.entity.Update;
import cn.zpeace.bootstrap.util.entity.ValidEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Created on 2021-12-24.
 *
 * @author skiya
 */
public class ValidateUtilsTest {


    @Test
    public void hasErrors() {
        ValidEntity validEntity = new ValidEntity()
                .setId(null)
                .setName("hello")
                .setAge(-1)
                .setEmail("110@163.com")
                .setAddresses(ListUtil.of("1", "2"));
        System.out.println(ValidateUtils.validate(validEntity).hasErrors());
        System.out.println(ValidateUtils.validate(validEntity).mergeErrorMessage());
        Assertions.assertEquals(true, ValidateUtils.validate(validEntity).hasErrors());
        Assertions.assertEquals("age最小不能小于0", ValidateUtils.validate(validEntity).mergeErrorMessage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidateUtils.validate(null));
    }

    @Test
    public void pass() {
        ValidEntity validEntity = new ValidEntity()
                .setId(1L)
                .setName("hello")
                .setAge(18)
                .setEmail("110@163.com")
                .setAddresses(ListUtil.of("1", "2"));
        System.out.println(ValidateUtils.validate(validEntity).hasErrors());
        System.out.println(ValidateUtils.validate(validEntity).mergeErrorMessage());
        Assertions.assertEquals(false, ValidateUtils.validate(validEntity).hasErrors());
        Assertions.assertEquals("", ValidateUtils.validate(validEntity).mergeErrorMessage());
    }

    @Test
    public void validateProperty() {
        ValidEntity validEntity = new ValidEntity()
                // id is null
                .setId(null)
                .setName("hello")
                .setAge(18)
                .setEmail("110@163.com")
                .setAddresses(ListUtil.of("1", "2"));
        System.out.println(ValidateUtils.validateProperty(validEntity, ListUtil.of("age")).hasErrors());
        System.out.println(ValidateUtils.validateProperty(validEntity, ListUtil.of("age")).mergeErrorMessage());
        Assertions.assertEquals(false, ValidateUtils.validateProperty(validEntity, ListUtil.of("age")).hasErrors());
        Assertions.assertEquals("", ValidateUtils.validateProperty(validEntity, ListUtil.of("age")).mergeErrorMessage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidateUtils.validateProperty(validEntity, CollUtil.newHashSet()));
    }

    @Test
    public void validateGroups() {
        ValidEntity validEntity = new ValidEntity()
                .setId(null)
                .setName("hello")
                .setAge(-1)
                .setEmail("110@163.com")
                .setAddresses(ListUtil.of("1", "2"));
        System.out.println(ValidateUtils.validate(validEntity, Update.class).hasErrors());
        System.out.println(ValidateUtils.validate(validEntity, Update.class).mergeErrorMessage());
        Assertions.assertEquals(true, ValidateUtils.validate(validEntity, Update.class).hasErrors());
        Assertions.assertEquals("id不能为null", ValidateUtils.validate(validEntity, Update.class).mergeErrorMessage());
        Assertions.assertThrows(IllegalArgumentException.class, () -> ValidateUtils.validate(validEntity, (Class<?>) null));
    }
}