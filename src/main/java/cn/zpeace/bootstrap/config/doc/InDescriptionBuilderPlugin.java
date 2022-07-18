// package cn.zpeace.bootstrap.config.doc;
//
// import cn.zpeace.bootstrap.validator.in.In;
// import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
// import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
// import springfox.documentation.spi.service.ParameterBuilderPlugin;
// import springfox.documentation.spi.service.contexts.ParameterContext;
//
// import javax.annotation.Nonnull;
// import java.util.Arrays;
// import java.util.Optional;
//
// import static springfox.bean.validators.plugins.Validators.*;
//
// /**
//  * 为 @In 注解生成 api 文档说明
//  * Created on 2022-1-11.
//  *
//  * @author skiya
//  */
// @Component
// @Profile({"dev", "local"})
// public class InDescriptionBuilderPlugin implements ModelPropertyBuilderPlugin, ParameterBuilderPlugin {
//
//     @Override
//     public void apply(ModelPropertyContext context) {
//         annotationFromBean(context, In.class)
//                 .map(Optional::of)
//                 .orElse(annotationFromField(context, In.class))
//                 .ifPresent(a -> context.getSpecificationBuilder()
//                         .required(a.required())
//                         .enumerationFacet(e -> e.allowedValues(Arrays.asList(a.allowableValues())))
//                 );
//     }
//
//     @Override
//     public void apply(ParameterContext context) {
//         annotationFromParameter(context, In.class)
//                 .ifPresent(a -> context.requestParameterBuilder()
//                         .required(a.required())
//                         .query(q -> q.enumerationFacet(e -> e.allowedValues(Arrays.asList(a.allowableValues()))))
//                 );
//     }
//
//     @Override
//     public boolean supports(@Nonnull DocumentationType delimiter) {
//         return true;
//     }
// }
