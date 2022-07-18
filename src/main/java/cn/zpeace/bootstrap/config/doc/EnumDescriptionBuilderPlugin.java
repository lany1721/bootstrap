// package cn.zpeace.bootstrap.config.doc;
//
// import cn.hutool.core.collection.CollUtil;
// import cn.zpeace.bootstrap.util.EnumUtils;
// import cn.zpeace.bootstrap.util.StringUtils;
// import cn.zpeace.bootstrap.validator.en.Enum;
// import com.fasterxml.jackson.databind.introspect.AnnotatedField;
// import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
// import io.swagger.v3.oas.annotations.media.Schema;
// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
// import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
// import springfox.documentation.spi.service.ParameterBuilderPlugin;
// import springfox.documentation.spi.service.contexts.ParameterContext;
//
// import javax.annotation.Nonnull;
// import java.lang.reflect.Field;
// import java.util.Optional;
//
// import static springfox.bean.validators.plugins.Validators.annotationFromParameter;
// import static springfox.documentation.schema.Annotations.findPropertyAnnotation;
//
//
// /**
//  * 结合@Enum 注解, 自动生成api 接口参数枚举说明
//  *
//  * @author skiya
//  * @date Created on 2021-11-25.
//  */
// @Component
// @Profile({"dev", "local"})
// public class EnumDescriptionBuilderPlugin implements ModelPropertyBuilderPlugin, ParameterBuilderPlugin {
//
//     @Override
//     public boolean supports(@Nonnull DocumentationType documentationType) {
//         return true;
//     }
//
//     private Optional<Field> findEnumAnnotatedField(ModelPropertyContext context) {
//         return context.getBeanPropertyDefinition()
//                 .map(BeanPropertyDefinition::getField)
//                 .map(AnnotatedField::getAnnotated);
//     }
//
//     private String getDescription(ModelPropertyContext context) {
//         return context.getBeanPropertyDefinition()
//                 .flatMap(b -> findPropertyAnnotation(b, Schema.class))
//                 .map(Schema::description)
//                 .orElse("");
//     }
//
//     /**
//      * ModelPropertyBuilderPlugin
//      * 实体类属性展示
//      */
//     @Override
//     public void apply(ModelPropertyContext context) {
//         this.findEnumAnnotatedField(context)
//                 .map(p -> p.getAnnotation(Enum.class))
//                 .ifPresent(a -> context.getSpecificationBuilder()
//                         .required(a.required())
//                         .description(StringUtils.joinSkipEmpties(",",
//                                 this.getDescription(context),
//                                 CollUtil.join(EnumUtils.getFieldMappingList(a.clazz(), "value", "label"), ",")))
//                 );
//     }
//
//
//     private String getDescription(ParameterContext context) {
//         return context.resolvedMethodParameter().findAnnotation(Schema.class)
//                 .map(Schema::description)
//                 .orElse("");
//     }
//
//     /**
//      * ParameterBuilderPlugin
//      * 方法参数展示
//      */
//     @Override
//     public void apply(ParameterContext context) {
//         annotationFromParameter(context, Enum.class)
//                 .ifPresent(a -> context.requestParameterBuilder()
//                         .required(a.required())
//                         .description(StringUtils.joinSkipEmpties(",",
//                                 this.getDescription(context),
//                                 CollUtil.join(EnumUtils.getFieldMappingList(a.clazz(), "value", "label"), ",")))
//                 );
//
//     }
// }
