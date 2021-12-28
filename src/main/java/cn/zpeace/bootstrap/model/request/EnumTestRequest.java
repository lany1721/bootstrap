package cn.zpeace.bootstrap.model.request;

import cn.zpeace.bootstrap.constant.JudgeEnum;
import cn.zpeace.bootstrap.validator.en.EnumCheck;
import cn.zpeace.bootstrap.validator.in.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;

/**
 * @author skiya
 * @date Created on 2021-11-27.
 */
@Data
@NoArgsConstructor
public class EnumTestRequest {

    @Nonnull
    @In(allowableValues = {"2"})
    @Schema(description = "主键")
    private Long id;

    @EnumCheck(clazz = JudgeEnum.class, required = false)
    @Schema(description = "是否启用")
    private Integer enable;
}
