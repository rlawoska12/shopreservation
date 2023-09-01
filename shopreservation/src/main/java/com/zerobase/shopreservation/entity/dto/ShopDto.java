package com.zerobase.shopreservation.entity.dto;

import com.zerobase.shopreservation.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopDto {

    private Long id;
    private String name;
    private String location;
    private String description;
    private Integer totalTableCount;
    private Integer star;

    public static ShopDto from(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .name(shop.getName())
                .location(shop.getLocation())
                .description(shop.getDescription())
                .totalTableCount(shop.getTotalTableCount())
                .star(shop.getStar())
                .build();
    }
}
