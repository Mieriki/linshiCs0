package com.mugen.inventory.mapper;

import com.mugen.inventory.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mugen.inventory.entity.model.dto.MenuDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    //    @TableId(value = "id", type = IdType.AUTO)
    //    private Integer id;
    //
    //    // 后端请求url
    //    @TableField("url")
    //    private String url;
    //
    //    // 前端路由url
    //    @TableField("path")
    //    private String path;
    //
    //    // 组件
    //    @TableField("component")
    //    private String component;
    //
    //    // 菜单名
    //    @TableField("name")
    //    private String name;
    //
    //    // 图标
    //    @TableField("icon_cls")
    //    private String iconCls;
    //
    //    // 父id
    //    @TableField("parent_id")
    //    private Integer parentId;
    //
    //    // 是否启用
    //    @TableField("enabled")
    //    private Boolean enabled;
    //
    //    // 孩子节点
    //    private List<Menu> children;
    @Select("select distinct mf.* from t_menu m\n" +
            "    right join t_menu_role mr on m.id = mr.mid\n" +
            "    right join t_role r on mr.rid = r.id\n" +
            "    right join t_admin_role ar on r.id = ar.rid\n" +
            "    right join t_admin a on ar.adminId = a.id\n" +
            "    inner join t_menu mf on m.parent_id = mf.id\n" +
            "where a.id = #{id} ;")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "url", column = "url"),
            @Result(property = "path", column = "path"),
            @Result(property = "component", column = "component"),
            @Result(property = "name", column = "name"),
            @Result(property = "iconCls", column = "icon_cls"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "children", column = "id", javaType = List.class, many = @Many(select = "com.mugen.inventory.mapper.MenuMapper.selectMenuList"))
    })
    List<MenuDto> selectMenuDtoList(Integer id);

    @Select("select * from t_menu where parent_id = #{id}")
    List<Menu> selectMenuList(Integer id);
}
