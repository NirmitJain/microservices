package com.eazybytes.cards.repository;

import com.eazybytes.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Cards, Long> {


    /**
     *
     * @param mobileNumber
     * @return
     */
    Optional<Cards> findByMobileNumber(String mobileNumber);

    /**
     *
     * @param cardNumber
     * @return
     */
    Optional<Cards> findByCardNumber(String cardNumber);
}
