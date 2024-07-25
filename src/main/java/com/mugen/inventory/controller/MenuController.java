package com.mugen.inventory.controller;

import com.mugen.inventory.entity.Menu;
import com.mugen.inventory.service.MenuService;
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
 * menu 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-24
 */
@RestController
@RequestMapping("/menus")
public class MenuController {
    @Resource
    private MenuService service;

    @GetMapping("/get")
    public <T>RestBean<List> list(){
        return RestBean.success(service.list());
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Menu> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Menu vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Menu vo) {
        return RestBean.messageHandle(vo, service::modifyHandler);
    }

    @GetMapping("/delete/{id}")
    public <T>RestBean<Void> remove(@PathVariable Integer id) {
        return RestBean.messageHandle(id, service::removeHandler);
    }

    @PostMapping("/delete")
    public <T>RestBean<Void> remove(@RequestBody List<Integer> idList) {
        return RestBean.messageHandle(idList, service::removeHandler);
    }

    @GetMapping("/get/count")
    public <T>RestBean<Long> count() {
        return RestBean.success(service.count());
    }

    @GetMapping("/get/router/{id}")
    public <T>RestBean<List> getRouter(@PathVariable Integer id){
        return RestBean.success(service.queryMenuDtoListByAdminId(id));
    }
}
