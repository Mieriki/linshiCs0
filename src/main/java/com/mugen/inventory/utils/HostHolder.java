package com.mugen.inventory.utils;

import com.mugen.inventory.entity.Admin;
import org.springframework.stereotype.Component;

/**
 *持有用户信息，用于代替session对象
 */
@Component
public class HostHolder {

    //ThreadLocal本质是以线程为key存储元素
    private ThreadLocal<Admin> local = new ThreadLocal<>();

    public void setAccount(Admin admin){
        local.set(admin);
    }

    public Admin getAdmin(){
        return local.get();
    }

    public void clear(){
        local.remove();
    }
}
