package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.Menu;
import com.mugen.inventory.entity.model.dto.MenuDto;
import com.mugen.inventory.mapper.MenuMapper;
import com.mugen.inventory.service.MenuService;
import com.mugen.inventory.utils.constant.InventoryMessageConstant;
import jakarta.annotation.Resource;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@Service
@Transactional
public class MenuServiceImp extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Resource
    private MenuMapper mapper;

    @Override
    public List<MenuDto> queryMenuDtoListByAdminId(Integer id) {
        return mapper.selectMenuDtoList(id);
    }

    @Override
    public String saveHandler(Menu menu) {
        if (this.save(menu))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Menu menu) {
        if (this.updateById(menu))
            return null;
        else
            return InventoryMessageConstant.MODIFY_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(Integer id) {
        if (this.removeById(id))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }

    @Override
    public String removeHandler(List<Integer> idList) {
        if (this.removeByIds(idList))
            return null;
        else
            return InventoryMessageConstant.REMOVE_FAILURE_MESSAGE;
    }
}
