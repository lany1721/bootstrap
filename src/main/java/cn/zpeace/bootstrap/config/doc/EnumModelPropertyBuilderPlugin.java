package cn.zpeace.bootstrap.config.doc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Profile({"dev", "local"})
@Component
public class EnumModelPropertyBuilderPlugin implements ModelPropertyBuilderPlugin {

    @Override
    public void apply(ModelPropertyContext context) {
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }
}
