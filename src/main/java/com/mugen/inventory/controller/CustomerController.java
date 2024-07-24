package com.mugen.inventory.controller;

import com.mugen.inventory.entity.Customer;
import com.mugen.inventory.entity.model.vo.request.CustomerQueryVo;
import com.mugen.inventory.service.CustomerService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.annotation.Validated;
import com.mugen.inventory.utils.RestBean;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * customer 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-25
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Resource
    private CustomerService service;

    @GetMapping("/get")
    public <T>RestBean<List> list(){
        return RestBean.success(service.list());
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Customer> get(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Customer vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Customer vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> delete(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }

    @PostMapping("/delete")
    public <T>RestBean<Void> delete(@RequestBody List<Integer> idList) {
        return RestBean.messageHandle(idList, service::removeHandler);
    }

    @PostMapping("get")
    public <T>RestBean<List> queryPage(@RequestBody @Validated CustomerQueryVo vo) {
        return RestBean.success(service.queryPage(vo));
    }
}
