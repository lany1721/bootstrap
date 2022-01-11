package cn.zpeace.bootstrap.support;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author skiya
 * @date Created on 2021-11-25.
 */
@Schema(description = "分页封装对象")
public class PageResponse {

    @Schema(description = "每页条数", example = "10")
    private Long size;

    @Schema(description = "当前页", example = "1")
    private Long current;

    @Schema(description = "总页数", example = "5")
    private Long pages;

    @Schema(description = "总记录数", example = "50")
    private Long total;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
