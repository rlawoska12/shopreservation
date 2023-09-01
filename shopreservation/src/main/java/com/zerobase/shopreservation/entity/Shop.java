package com.zerobase.shopreservation.entity;

import com.zerobase.shopreservation.entity.dto.ShopForm;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(targetEntity = Manager.class, fetch = FetchType.LAZY)
    private Manager manager;
    private String name;
    private String location;
    private String description;
    private Integer totalTableCount;
    private Integer star;
    private LocalDate regDate;
    private LocalDate updateDate;

    public static Shop from(ShopForm shopForm) {
        return Shop.builder()
                .name(shopForm.getName())
                .location(shopForm.getLocation())
                .description(shopForm.getDescription())
                .totalTableCount(shopForm.getTotalTableCount())
                .build();
    }
}
