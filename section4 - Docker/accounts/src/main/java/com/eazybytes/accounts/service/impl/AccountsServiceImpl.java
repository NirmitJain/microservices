package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * This is the method to create new account
     *
     * @param customerDto
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer already exists with given mobile number"+ customer.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));

    }

    /**
     *
     * @param customer
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer){
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 2323230000L + new Random().nextInt(2000000000);
        account.setAccountNumber(randomAccNumber);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setAccountType(AccountsConstants.SAVINGS);
        return account;
    }

    /**
     *
     * @param mobileNumber
     * @return Account details based on provided mobile number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer existingCustomer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer", "mobileNumber",mobileNumber)
        );

        Accounts existingAccount = accountsRepository.findByCustomerId(existingCustomer.getCustomerId()).orElseThrow(
                ()->new ResourceNotFoundException("Account", "customerId" ,existingCustomer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(existingCustomer,new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(existingAccount,new AccountsDto());
        customerDto.setAccountsDto(accountsDto);
        return customerDto;
    }

    /**
     * @param customerDto
     * @return true if update was successful.
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto!=null){
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    ()->new ResourceNotFoundException("Account", "accountNumber",
                            accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto,accounts);
            accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    ()->new ResourceNotFoundException("Customer", "customerId",
                            customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
