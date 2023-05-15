package top.zsmile.system.boot.modules.open.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "文章-上下页")
public class BlogArticleLNBodyVo {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "文章标题", hidden = false)
    private String articleTitle;
}
