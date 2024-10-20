package com.reference.accounts.services.impl;


import com.reference.accounts.constants.AccountsConstants;
import com.reference.accounts.dto.AccountsDto;
import com.reference.accounts.dto.CustomerDto;
import com.reference.accounts.entity.Accounts;
import com.reference.accounts.entity.Customer;
import com.reference.accounts.exception.CustomerAlreadyExistsException;
import com.reference.accounts.exception.ResourceNotFoundException;
import com.reference.accounts.mapper.AccountsMapper;
import com.reference.accounts.mapper.CustomerMapper;
import com.reference.accounts.repository.AccountsRepository;
import com.reference.accounts.repository.CustomerRepository;
import com.reference.accounts.services.IAccountsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = new Customer();
        CustomerMapper.mapToCustomer(customerDto, customer);
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        optionalCustomer.ifPresent(c -> {
            throw new CustomerAlreadyExistsException("Customer Already registered with given number " + customer.getMobileNumber());
        });
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    @Override
    public CustomerDto getAccountDetails(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "CustomerId", customer.getCustomerId().toString()));

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer);
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts));
        return customerDto;

    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        AccountsDto accountsDto = customerDto.getAccountsDto();
        Accounts existingAccount = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
        );
        Customer existingCustomer = customerRepository.findById(existingAccount.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "CustomerID", existingAccount.getCustomerId().toString())
        );
        try {
            CustomerMapper.mapToCustomer(customerDto, existingCustomer);
            customerRepository.save(existingCustomer);

            AccountsMapper.mapToAccounts(accountsDto, existingAccount);
            accountsRepository.save(existingAccount);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        try {
            accountsRepository.deleteByCustomerId(customer.getCustomerId());
            customerRepository.deleteById(customer.getCustomerId());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    private Accounts createNewAccount(Customer savedCustomer) {
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        return Accounts.builder()
                .customerId(savedCustomer.getCustomerId())
                .accountNumber(randomAccountNumber)
                .accountType(AccountsConstants.SAVINGS.getValue())
                .branchAddress(AccountsConstants.ADDRESS.getValue())
                .build();
    }


}
