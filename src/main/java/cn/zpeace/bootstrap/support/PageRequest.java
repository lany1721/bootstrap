package cn.zpeace.bootstrap.support;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

/**
 * @author skiya
 * @date Created on 2021-11-18.
 */
@Getter
@ToString
@Schema(description = "分页参数封装")
public class PageRequest {

    @Schema(description = "页码", defaultValue = "1", example = "1")
    private Long current;

    @Schema(description = "每页条数", defaultValue = "10", example = "10")
    private Long size;

    public PageRequest() {
        this.current = 1L;
        this.size = 10L;
    }

    public PageRequest(Long current, Long size) {
        this.current = current;
        this.size = size;
    }

    public void setCurrent(Long current) {
        if (current < 0) {
            current = 1L;
        }
        this.current = current;
    }

    public void setSize(Long size) {
        if (size < 0) {
            size = 10L;
        }
        this.size = size;
    }
}
