package com.mugen.inventory.entity.model.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mugen.inventory.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@TableName("t_menu")
public class MenuDto {
    private static final long serialVersionUID = 1L;

    // id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 后端请求url
    @TableField("url")
    private String url;

    // 前端路由url
    @TableField("path")
    private String path;

    // 组件
    @TableField("component")
    private String component;

    // 菜单名
    @TableField("name")
    private String name;

    // 图标
    @TableField("icon_cls")
    private String iconCls;

    // 父id
    @TableField("parent_id")
    private Integer parentId;

    // 是否启用
    @TableField("enabled")
    private Boolean enabled;

    // 孩子节点
    private List<Menu> children;
}
