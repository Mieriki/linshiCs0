package com.mugen.inventory.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mugen.inventory.entity.Role;
import com.mugen.inventory.entity.model.vo.request.RoleQueryPageVo;
import com.mugen.inventory.entity.model.vo.response.RoleOrganizeVo;
import com.mugen.inventory.entity.model.vo.response.RolePageVo;
import com.mugen.inventory.service.RoleService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;
import com.mugen.inventory.utils.RestBean;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * role 前端控制器
 * </p>
 *
 * @author Mieriki
 * @since 2024-07-28
 */
@RestController
@RequestMapping("/roles")
public class RoleController {
    @Resource
    private RoleService service;

    @GetMapping("/get")
    public <T>RestBean<List> list(){
        return RestBean.success(service.list());
    }

    @PostMapping("/get")
    public <T>RestBean<RolePageVo> queryPage(@RequestBody @Validated RoleQueryPageVo vo) {
        return RestBean.success(service.queryPage(vo));
    }

    @GetMapping("/get/{id}")
    public <T>RestBean<Role> query(@PathVariable Integer id) {
        return RestBean.success(service.getById(id));
    }

    @PostMapping("/post")
    public <T>RestBean<Void> save(@RequestBody @Validated Role vo) {
        return RestBean.messageHandle(vo, service::saveHandler);
    }

    @PostMapping("/put")
    public <T>RestBean<Void> modify(@RequestBody @Validated Role vo) {
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

    @GetMapping("/get/assignment/{id}")
    public <T>RestBean<RoleOrganizeVo> getAssignment(@PathVariable Integer id) {
        return RestBean.success(service.queryRoleOrganizeByUserId(id));
    }

    @PostMapping("/put/assignment/{id}")
    public <T>RestBean<Void> assignment(@PathVariable Integer id, @RequestBody @Validated List<Integer> idList) {
        service.organizeHandler(id, idList);
        return RestBean.success("角色分配成功");
    }

    @SneakyThrows
    @GetMapping("/get/excel")
    public void exportData(HttpServletResponse response) {
        String fileName = "Role_" + new Date() + ".xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream out = response.getOutputStream();
        List<Role> rowss = CollUtil.newArrayList();
        rowss.addAll(service.list());
        ExcelWriter writer= ExcelUtil.getBigWriter();
        writer.write(rowss);
        writer.flush(out);
        writer.close();
    }

    @SneakyThrows
    @PostMapping("/post/excel")
    public <T> RestBean<Void> handleFileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<Role> roleList = reader.readAll(Role.class);
        return RestBean.messageHandle(roleList, service::saveHandler);
    }
}
