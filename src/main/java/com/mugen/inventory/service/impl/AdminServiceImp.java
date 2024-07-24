package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.Admin;
import com.mugen.inventory.mapper.AdminMapper;
import com.mugen.inventory.service.AdminService;
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
public class AdminServiceImp extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Resource
    private AdminMapper mapper;

    @Override
    public String saveHandler(Admin admin) {
        if (this.save(admin))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Admin admin) {
        if (this.updateById(admin))
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
