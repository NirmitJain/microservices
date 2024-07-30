package com.eazybytes.cards.mapper;

import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;

public class CardsMapper {

    public static CardsDto mapToCardsDTO(Cards cards, CardsDto cardsDto){
        cardsDto.setCardNumber(cards.getCardNumber());
        cardsDto.setCardType(cards.getCardType());
        cardsDto.setAmountUsed(cards.getAmountUsed());
        cardsDto.setTotalLimit(cards.getTotalLimit());
        cardsDto.setMobileNumber(cards.getMobileNumber());
        cardsDto.setAvailableAmount(cards.getAvailableAmount());

        return cardsDto;

    }

    public static Cards mapToCards(CardsDto cardsDto, Cards cards){

        cards.setCardType(cardsDto.getCardType());
        cards.setCardNumber(cardsDto.getCardNumber());
        cards.setAvailableAmount(cardsDto.getAvailableAmount());
        cards.setAmountUsed(cards.getAmountUsed());
        cards.setTotalLimit(cards.getTotalLimit());
        cards.setMobileNumber(cards.getMobileNumber());

        return cards;
    }

}
