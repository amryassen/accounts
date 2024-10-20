package com.reference.accounts.services;

import com.reference.accounts.dto.CustomerDto;

public interface IAccountsService {

    /**
     * @param customerDto customerDto object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return CustomerDto
     */
    CustomerDto getAccountDetails(String mobileNumber);


    /**
     * @param customerDto - CustomerDto Object
     */
    boolean updateAccount(CustomerDto customerDto);


    /**
     * @param mobileNumber
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(String mobileNumber);
}