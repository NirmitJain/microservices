package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Scheme to hold Customer, Account,Loan and Cards detail"
)
public class CustomerDetailsDto {

    @Schema(
            description = "Name of the customer",example = "Eazy Bytes"
    )
    @NotEmpty(message = "name can not be null or empty")
    @Size(min=5, max=30, message = "name can be between 5 and 30 characters")
    private String name;

    @Schema(
            description = "Email of the customer",example = "test@eazybytes.com"
    )
    @NotEmpty(message = "email can not be null or empty")
    @Email(message = "email address should be a valid value")
    private String email;

    @Schema(
            description = "Mobile Number of the customer",example = "9830098300"
    )
    @NotEmpty()
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digit" )
    private String mobileNumber;

    @Schema(
            description = "Account details of the customer"
    )
    private AccountsDto accountsDto;

    @Schema(
            description = "Loan details of the customer"
    )
    private LoansDto loansDto;

    @Schema(
            description = "Card details of the customer"
    )
    private CardsDto cardsDto;
}
