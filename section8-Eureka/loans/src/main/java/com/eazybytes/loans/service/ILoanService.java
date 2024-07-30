package com.eazybytes.loans.service;

import com.eazybytes.loans.dto.LoansDto;
import org.springframework.stereotype.Service;

public interface ILoanService {

    /**
     *
     * @param mobileNumber
     */
    void createLoan(String mobileNumber);

    /**
     *
     * @param mobileNumber
     * @return loan details based on mobileNumber
     */
    LoansDto fetchLoan(String mobileNumber);

    /**
     *
     * @param loansDto
     * @return boolean indicating if the update of loan is successful or not
     */
    boolean updateLoan(LoansDto loansDto);

    /**
     *
     * @param mobileNumber
     * @return boolean indicating if the delete of loan is successful or not
     */
    boolean deleteLoan(String mobileNumber);
}
