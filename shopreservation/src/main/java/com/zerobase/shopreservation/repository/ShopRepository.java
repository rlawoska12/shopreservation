package com.zerobase.shopreservation.repository;

import com.zerobase.shopreservation.entity.Manager;
import com.zerobase.shopreservation.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findByName(String name);

    Optional<Shop> findByManager(Manager manager);
}
