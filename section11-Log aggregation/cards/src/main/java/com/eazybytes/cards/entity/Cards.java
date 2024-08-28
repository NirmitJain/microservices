package com.eazybytes.cards.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor@ToString
@NoArgsConstructor
@Entity
public class Cards extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "card_type")
    private String cardType;
    @Column(name = "total_limits")
    private int totalLimits;
    @Column(name = "amount_used")
    private int amountUsed;
    @Column(name = "available_amount")
    private int availableAmount;


}
