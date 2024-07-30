package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param mobileNumber
     */
    void createCard(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return card details based on provided mobile number
     */
    CardsDto fetchCardDetails(String mobileNumber);

    /**
     *
     * @param cardsDto
     * @return boolean indicating if update card details was successful or not
     */
    boolean updateCardsDetails(CardsDto cardsDto);

    /**
     *
     * @param mobileNumber
     * @return boolean indicating if deleting card details was successful or not
     */
    boolean deleteCard(String mobileNumber);



}
