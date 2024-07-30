package com.eazybytes.accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(
        description = "Schema to hold Card details"
)
@Data
public class CardsDto {

    @NotEmpty(message = "Mobile Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "mobile number must be 10 digit")
    @Schema(
            description = "Mobile Number of Customer", example = "9829898298"
    )
    private String mobileNumber;
    @NotEmpty(message = "Card Number can not be null or empty")
    @Pattern(regexp = "(^$|[0-9]{12})", message = "card number must be 12 digit")
    @Schema(
            description = "Card Number of Customer", example = "3423 3455 7689"
    )
    private String cardNumber;
    @NotEmpty(message = "Card Type can not be null or empty")
    @Schema(
            description = "Card Type", example = "Credit Card"
    )
    private String cardType;
    @NotEmpty(message = "Total Limit can not be null or empty")
    @Schema(
            description = "Total amount limit available against a card", example = "250000"
    )
    private int totalLimits;
    @NotEmpty(message = "Amount used can not be null or empty")
    @Schema(
            description = "Amount Used from the Card Limit", example = "10000"
    )
    private int amountUsed;
    @NotEmpty(message = "Available amount  can not be null or empty")
    @Schema(
            description = "Total available amount against the card", example = "240000"
    )
    private int availableAmount;
}
