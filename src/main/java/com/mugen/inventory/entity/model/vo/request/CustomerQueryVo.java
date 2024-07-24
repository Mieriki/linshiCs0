package com.mugen.inventory.entity.model.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder(toBuilder = true)
public class CustomerQueryVo {
    String name;
    Integer currentPage;
    Integer pageSize;
}
