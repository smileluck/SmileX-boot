package top.zsmile.modules.open.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "文章-上下页")
public class BlogArticleLNVo {

    @ApiModelProperty(value = "上一篇")
    private BlogArticleLNBodyVo prev;

    @ApiModelProperty(value = "下一篇")
    private BlogArticleLNBodyVo next;
}
