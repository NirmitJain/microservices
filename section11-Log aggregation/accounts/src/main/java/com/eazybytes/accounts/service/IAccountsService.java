package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * This is the mthod to create new account
     * @param customerDto
     */
    void createAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto
     * @return true if update was successful.
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return
     */
    boolean deleteAccount(String mobileNumber);
}
