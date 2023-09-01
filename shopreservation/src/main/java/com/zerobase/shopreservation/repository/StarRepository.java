package com.zerobase.shopreservation.repository;

import com.zerobase.shopreservation.entity.Shop;
import com.zerobase.shopreservation.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {

    Optional<Star> findByShop(Shop shop);
}
