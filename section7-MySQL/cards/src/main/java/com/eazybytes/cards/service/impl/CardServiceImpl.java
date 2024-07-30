package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstant;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardsService {

    public CardsRepository cardsRepository;

    /**
     * @param mobileNumber
     */
    @Override
    public void createCard(String mobileNumber) {

        Optional<Cards> optionalCards = cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent())
            throw new CardAlreadyExistsException("Card already exists with given mobile number" + mobileNumber);
        cardsRepository.save(createNewCard(mobileNumber));
    }

    /**
     *
     * @param mobileNumber - mobile number of the customer
     * @return new card details
     */
    private Cards createNewCard(String mobileNumber){

        Cards cards = new Cards();
        long randomCardNumber = 2000000000L + new Random().nextInt(200000000);
        cards.setCardNumber(Long.toString(randomCardNumber));
        cards.setCardType(CardsConstant.CREDIT_CARD);
        cards.setAvailableAmount(CardsConstant.NEW_CARD_LIMIT);
        cards.setMobileNumber(mobileNumber);
        cards.setTotalLimit(CardsConstant.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);

        return  cards;
    }

    /**
     * @param mobileNumber
     * @return card details based on provided mobile number
     */
    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        Cards existingCard = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Card","mobileNumber", mobileNumber)
                );

        return CardsMapper.mapToCardsDTO(existingCard,new CardsDto());
    }

    /**
     * @param cardsDto
     * @return boolean indicating if update card details was successful or not
     */
    @Override
    public boolean updateCardsDetails(CardsDto cardsDto) {
        Cards cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Card","cardNumber",cardsDto.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDto,cards);
        cardsRepository.save(cards);
        return true;
    }

    /**
     * @param mobileNumber
     * @return boolean indicating if deleting card details was successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Cards","mobileNumber",mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
