package cn.zpeace.bootstrap.util.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Created on 2022-2-24.
 *
 * @author skiya
 */
@Data
public class Post {

    private Long userId;

    private Long id;

    private String body;
}
