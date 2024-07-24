package com.mugen.inventory.service;

import com.mugen.inventory.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mugen.inventory.entity.model.dto.MenuDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
public interface MenuService extends IService<Menu> {
    List<MenuDto> queryMenuDtoListByAdminId(Integer id);
    String saveHandler(Menu menu);
    String modifyHandler(Menu menu);
    String removeHandler(Integer id);
    String removeHandler(List<Integer> idList);
}
