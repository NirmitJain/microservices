package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Schema(
        name = "Loan",description = "Schema to hold Loan information"
)
@NoArgsConstructor
public class LoansDto {

    @NotEmpty(message = "Loan number cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digit")
    @Schema(
            description = "Mobile number of the customer",example = "9829098278"
    )
    private String mobileNumber;

    @NotEmpty(message = "Loan number cannot be empty or null")
    @Pattern(regexp = "(^$|[o-9]{12})",message = "LoanNumber must be 12 digit")
    @Schema(
            description = "Loan number of the customer",example = "773865493083"
    )
    private String loanNumber;


    @PositiveOrZero(message = "Loan type can not be null or empty")
    @Schema(
            description = "Type of loan",example = "Home Loan"
    )
    private String loanType;

    @Positive(message = "Total loan amount can not be negative or zero")
    @Schema(
            description = "Total loan amount",example = "1000000"
    )
    private int totalLoan;

    @PositiveOrZero(message = "Total amount paid amount can not be a negative")
    @Schema(
            description = "Total loan amount paid",example = "10000"
    )
    private int amountPaid;

    @PositiveOrZero(message = "Total outstanding amount can not be negative")
    @Schema(
            description = "Total outstanding amount against a laon",example = "99000"
    )
    private int outstandingAmount;




}
