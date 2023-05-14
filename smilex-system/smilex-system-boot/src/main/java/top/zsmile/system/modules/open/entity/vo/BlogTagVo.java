package top.zsmile.system.modules.open.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "博客 - 标签", description = "博客 - 标签")
public class BlogTagVo {
    /**
     * ID
     */
    @ApiModelProperty(value = "Id，更新时需要传")
    private Long id;
    /**
     * 标签名
     */
    @ApiModelProperty(value = "标签名")
    private String tagName;
}
