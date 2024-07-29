package com.mugen.inventory.entity.model.vo.response;

import com.mugen.inventory.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPageVo {
    Integer count;
    List<Customer> customerList;
}
