package cn.zpeace.bootstrap.util.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.util.List;

/**
 * Created on 2021-12-24.
 *
 * @author skiya
 */
@Data
@Accessors(chain = true)
public class ValidEntity {

    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    @Max(200)
    private Integer age;

    @Email
    private String email;

    @NotEmpty
    private List<String> addresses;
}
