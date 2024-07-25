package com.mugen.inventory.service.impl;

import com.mugen.inventory.entity.Customer;
import com.mugen.inventory.entity.model.vo.request.CustomerPageVo;
import com.mugen.inventory.entity.model.vo.response.CustomerQueryPageVo;
import com.mugen.inventory.mapper.CustomerMapper;
import com.mugen.inventory.service.CustomerService;
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
 * @since 2024-07-25
 */
@Service
@Transactional
public class CustomerServiceImp extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Resource
    private CustomerMapper mapper;

    @Override
    public String saveHandler(Customer customer) {
        if (this.save(customer))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String saveHandler(List<Customer> customerList) {
        if (this.saveBatch(customerList))
            return null;
        else
            return InventoryMessageConstant.SAVE_FAILURE_MESSAGE;
    }

    @Override
    public String modifyHandler(Customer customer) {
        if (this.updateById(customer))
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

    @Override
    public CustomerQueryPageVo queryPage(CustomerPageVo vo) {
        vo.setCurrentPage((vo.getCurrentPage() - 1) * vo.getPageSize());
        return new CustomerQueryPageVo(mapper.selectCountLikeName(vo), mapper.selectPageListLikeName(vo));
    }
}
