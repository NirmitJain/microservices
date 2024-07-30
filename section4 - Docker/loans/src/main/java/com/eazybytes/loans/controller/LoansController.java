package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST API for Loans in Sample bank",
        description = "CRUD REST API in Sample bank to CREATE,FETCH,UPDATE,DELETE loan details"
)
@RestController
@RequestMapping(path = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class LoansController {

    private ILoanService iLoanService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create Loan in Sample Bank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status Created"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})",
                                                          message = "Mobile number must be 10 digit") String mobileNumber) {
        iLoanService.createLoan(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));


    }

    @Operation(
            summary = "Fetch Loan REST API",
            description = "REST API to fetch Loan details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@RequestParam
                                              @Pattern(regexp = "(^$|[0-9]{10})"
                                                      , message = "Mobile Number must be 10 digit") String mobileNumber) {
        LoansDto loansDto = iLoanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }


    @Operation(
            summary = "UPDATE Loan REST API",
            description = "REST API to update Loan details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateLoansDetails(@Valid @RequestBody LoansDto loansDto) {
        boolean updatedLoans = iLoanService.updateLoan(loansDto);
        if(updatedLoans)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        else
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
    }


    @Operation(
            summary = "DELETE Loan REST API",
            description = "REST API to delete Loan details based on mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "HTTP Status Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )}
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteLoansDetails(@RequestParam
                                                          @Pattern(regexp = "(^$|[0-9]{10})",
                                                                  message = "Mobile number must be 10 digit") String mobileNumber) {
        boolean isDeleted = iLoanService.deleteLoan(mobileNumber);
        if(isDeleted)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        else
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
    }


}
