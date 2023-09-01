package com.zerobase.shopreservation.repository;

import com.zerobase.shopreservation.entity.Customer;
import com.zerobase.shopreservation.entity.Reservation;
import com.zerobase.shopreservation.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<List<Reservation>> findByCustomer(Customer customer);

    Optional<List<Reservation>> findByShop(Shop shop);
}
