package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exception.LoanAlreadyExistsException;
import com.eazybytes.loans.exception.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class LoansServiceImpl implements ILoanService {

    private LoansRepository loansRepository;
    /**
     * @param mobileNumber
     */
    @Override
    public void createLoan(String mobileNumber) {

        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistsException("Loan already registered with given mobile number : " + mobileNumber);
        }
        Loans loan = createNewLoan(mobileNumber);
        loansRepository.save(loan);
    }

    private Loans createNewLoan(String mobileNumber){
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextLong(900000000000L);
        newLoan.setLoanNumber(String.valueOf(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN);
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(LoansConstants.NEW_LOAN_LIMIT);

        return newLoan;

    }

    /**
     * @param mobileNumber
     * @return loan details based on mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Loan","mobileNumber", mobileNumber)
        );

        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     * @param loansDto
     * @return boolean indicating if the update of loan is successful or not
     */
    @Override
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.getLoanNumber().toString()).orElseThrow(
                ()->new ResourceNotFoundException("Loans","LoanNumber", loansDto.getLoanNumber().toString())
        );
        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);

        return true;
    }

    /**
     * @param mobileNumber
     * @return boolean indicating if the delete of loan is successful or not
     */
    @Override
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Loans","mobileNumber", mobileNumber)
        );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
