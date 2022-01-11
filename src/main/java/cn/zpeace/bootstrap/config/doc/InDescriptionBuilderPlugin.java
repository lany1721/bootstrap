package cn.zpeace.bootstrap.config.doc;

import cn.zpeace.bootstrap.validator.in.In;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;

import static springfox.bean.validators.plugins.Validators.*;
import static springfox.documentation.schema.Annotations.findPropertyAnnotation;

/**
 * Created on 2022-1-11.
 *
 * @author skiya
 */
@Component
@Profile({"dev", "local"})
public class InDescriptionBuilderPlugin implements ModelPropertyBuilderPlugin, ParameterBuilderPlugin {

    private String getDescription(ModelPropertyContext context) {
        return context.getBeanPropertyDefinition()
                .flatMap(b -> findPropertyAnnotation(b, Schema.class))
                .map(Schema::description)
                .orElse("");
    }

    @Override
    public void apply(ModelPropertyContext context) {
        annotationFromBean(context, In.class)
                .map(Optional::of)
                .orElse(annotationFromField(context, In.class))
                .ifPresent(a -> context.getSpecificationBuilder()
                        .required(a.required())
                        .enumerationFacet(e -> e.allowedValues(Arrays.asList(a.allowableValues())))
                );
    }

    private String getDescription(ParameterContext context) {
        return context.resolvedMethodParameter().findAnnotation(Schema.class)
                .map(Schema::description)
                .orElse("");
    }


    @Override
    public void apply(ParameterContext context) {
        annotationFromParameter(context, In.class)
                .ifPresent(a -> context.requestParameterBuilder()
                        .required(a.required())
                        .query(q -> q.enumerationFacet(e -> e.allowedValues(Arrays.asList(a.allowableValues()))))
                );
    }

    @Override
    public boolean supports(@Nonnull DocumentationType delimiter) {
        return true;
    }
}
