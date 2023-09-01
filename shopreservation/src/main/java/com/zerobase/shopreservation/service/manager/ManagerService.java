package com.zerobase.shopreservation.service.manager;

import com.zerobase.shopreservation.entity.Manager;
import com.zerobase.shopreservation.entity.Reservation;
import com.zerobase.shopreservation.entity.Shop;
import com.zerobase.shopreservation.entity.dto.ReservationDto;
import com.zerobase.shopreservation.entity.dto.ShopForm;
import com.zerobase.shopreservation.entity.dto.SignInForm;
import com.zerobase.shopreservation.exception.CustomException;
import com.zerobase.shopreservation.exception.ErrorCode;
import com.zerobase.shopreservation.repository.ManagerRepository;
import com.zerobase.shopreservation.repository.ReservationRepository;
import com.zerobase.shopreservation.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.zerobase.shopreservation.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ShopRepository shopRepository;
    private final ReservationRepository reservationRepository;

    public Optional<Manager> findByIdAndEmail(Long id, String email) {
        return managerRepository.findById(id)
                .stream().filter(manager -> manager.getEmail().equals(email))
                .findFirst();
    }

    public Manager findValidManager(SignInForm form) {
        Manager manager = managerRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (!form.getPassword().equals(manager.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        return manager;
    }

    public void registerShop(ShopForm shopForm, Long managerId) {
        if (shopForm.getName() == null) {
            throw new CustomException(NAME_IS_NULL);
        }

        if (shopForm.getLocation() == null) {
            throw new CustomException(LOCATION_IS_NULL);
        }

        if (shopForm.getDescription() == null) {
            throw new CustomException(DESCRIPTION_IS_NULL);
        }

        if (shopForm.getTotalTableCount() == null) {
            throw new CustomException(TABLE_IS_NULL);
        }

        Shop shop = Shop.from(shopForm);
        shop.setRegDate(LocalDate.now());
        shop.setStar(0);
        shop.setManager(managerRepository.findById(managerId).orElseThrow(
                () -> new CustomException(NOT_FOUND_USER)));
        shopRepository.save(shop);
    }

    public List<ReservationDto> findByManagerId(Long managerId) {
        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = shopRepository.findByManager(manager)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        List<Reservation> reservationList = reservationRepository.findByShop(shop)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            reservationDtoList.add(ReservationDto.from(reservation));
        }

        return reservationDtoList;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void approveReservation(Long managerId, Long reservationId) {

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = shopRepository.findByManager(manager)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        if(reservation.isRefusal()) {
            reservation.setRefusal(false);
        }

        reservation.setApproval(true);
        reservationRepository.save(reservation);

        if (shop.getTotalTableCount() <= 0 || shop.getTotalTableCount() < reservation.getTableCount()) {
            throw new CustomException(NOT_ENOUGH_TABLE);
        }

        shop.setTotalTableCount(shop.getTotalTableCount() - reservation.getTableCount());
        shopRepository.save(shop);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void refuseReservation(Long managerId, Long reservationId) {

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        Shop shop = shopRepository.findByManager(manager)
                .orElseThrow(() -> new CustomException(NOT_FOUND_SHOP));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_RESERVATION));

        if(reservation.isApproval()) {
            reservation.setApproval(false);
            shop.setTotalTableCount(shop.getTotalTableCount() + reservation.getTableCount());
            shopRepository.save(shop);
        }

        reservation.setRefusal(true);
        reservationRepository.save(reservation);
    }
}
