package top.zsmile.tool.gen.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class MenuModel {
    /**
     * 菜单id
     */
    private Long parentId;
    /**
     * id列表
     */
    private List<Long> menuIds;
}
