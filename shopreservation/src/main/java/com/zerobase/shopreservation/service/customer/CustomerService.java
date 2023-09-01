package com.zerobase.shopreservation.service.customer;

import com.zerobase.shopreservation.entity.*;
import com.zerobase.shopreservation.entity.dto.*;
import com.zerobase.shopreservation.exception.CustomException;
import com.zerobase.shopreservation.exception.ErrorCode;
import com.zerobase.shopreservation.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.zerobase.shopreservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final StarRepository starRepository;

    public Customer findValidCustomer(SignInForm form) {
        Customer customer = customerRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (!form.getPassword().equals(customer.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return customer;
    }

    public List<ShopDto> findByShopName(String name) {
        List<Shop> shopList = shopRepository.findByName(name);
        List<ShopDto> shopDtoList = new ArrayList<>();

        for (Shop shop : shopList) {
            shopDtoList.add(ShopDto.from(shop));
        }

        return shopDtoList;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void addReservation(ReservationForm form, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        Shop shop = shopRepository.findById(form.getShopId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        if (shop.getTotalTableCount() <= 0 || shop.getTotalTableCount() < form.getTableCount()) {
            throw new CustomException(NOT_ENOUGH_TABLE);
        }

        reservationRepository.save(Reservation.builder()
                .reservationDateTime(form.getReservationDateTime())
                .tableCount(form.getTableCount())
                .customer(customer)
                .shop(shop)
                .build());
    }

    public List<ReservationDto> findByCustomerId(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        List<Reservation> reservationList = reservationRepository.findByCustomer(customer)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        List<ReservationDto> reservationDtoList = new ArrayList<>();
        for (Reservation reservation : reservationList) {
            reservationDtoList.add(ReservationDto.from(reservation));
        }

        return reservationDtoList;
    }

    @Transactional(rollbackFor = Exception.class)
    public void visitConfirm(Long customerId, Long reservationId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        if (reservation.isVisitation()) {
            throw new CustomException(ALREADY_VISIT_CONFIRM);
        }

        if (reservation.isRefusal()) {
            throw new CustomException(THIS_RESERVATION_IS_REFUSED);
        }

        if (!reservation.isApproval()) {
            throw new CustomException(NOT_APPROVE_RESERVATION);
        }

        if (reservation.getCustomer() != customer) {
            throw new CustomException(NOT_MATCH_CUSTOMER);
        }

        if (reservation.getReservationDateTime().minusMinutes(10).isAfter(LocalDateTime.now())) {
            throw new CustomException(TOO_EARLY_TIME);
        }

        if (reservation.getReservationDateTime().isBefore(LocalDateTime.now())) {
            throw new CustomException(TIME_OUT);
        }

        reservation.setVisitation(true);
        reservationRepository.save(reservation);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void starRenewal(int reviewStar, Shop shop) {

        Star star = starRepository.findByShop(shop)
                .orElse(Star.builder()
                        .totalStar(0)
                        .reviewCount(0)
                        .shop(shop)
                        .build());

        star.setTotalStar(star.getTotalStar() + reviewStar);
        star.setReviewCount(reviewRepository.findByShop(shop).size());

        starRepository.save(star);

        shop.setStar(star.getTotalStar() / star.getReviewCount());
        shopRepository.save(shop);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void createReview(Long customerId, Long reservationId, ReviewForm form) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        if (!reservation.isVisitation()) {
            throw new CustomException(NOT_VISIT_RESERVATION);
        }

        Shop shop = reservation.getShop();

        Review review = Review.builder()
                .customer(customer)
                .shop(shop)
                .title(form.getTitle())
                .contents(form.getContents())
                .build();

        reviewRepository.save(review);
        starRenewal(form.getStar(), shop);
    }
}
