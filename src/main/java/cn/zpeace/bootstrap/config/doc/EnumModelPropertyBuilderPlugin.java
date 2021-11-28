package cn.zpeace.bootstrap.config.doc;

import cn.hutool.core.collection.CollUtil;
import cn.zpeace.bootstrap.anno.DocEnum;
import cn.zpeace.bootstrap.util.EnumUtils;
import cn.zpeace.bootstrap.util.StringUtils;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Optional;

import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Profile({"dev", "local"})
@Component
public class EnumModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public boolean supports(@Nonnull DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        this.findDocEnumAnnotatedField(context)
                .map(p -> AnnotatedElementUtils.getMergedAnnotationAttributes(p, DocEnum.class))
                .ifPresent(c -> context.getSpecificationBuilder()
                        .description(StringUtils.joinSkipEmpties(",",
                                this.getDescription(context),
                                CollUtil.join(EnumUtils.getFieldMappingList(c.getClass("clazz"), "value", "label"), ","))));
    }

    private Optional<Field> findDocEnumAnnotatedField(ModelPropertyContext context) {
        return context.getBeanPropertyDefinition()
                .map(BeanPropertyDefinition::getField)
                .map(AnnotatedField::getAnnotated);
    }

    private String getDescription(ModelPropertyContext context) {
        return context.getBeanPropertyDefinition()
                .flatMap(b -> findPropertyAnnotation(b, Schema.class))
                .map(Schema::description)
                .orElse("");
    }
}
