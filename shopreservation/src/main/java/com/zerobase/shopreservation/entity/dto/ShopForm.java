package com.zerobase.shopreservation.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopForm {
    private String name;
    private String location;
    private String description;
    private Integer totalTableCount;
}
