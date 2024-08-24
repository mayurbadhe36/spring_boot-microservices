package com.app.accounts.service;

import com.app.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     *
     * @param customerDto --customer Dto object
     */
    void createAccounts(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return account details based on given mobile number
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto
     * @return boolean value that record updated or not
     */
    boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber
     * @return boolean value that record deleted or not
     */
    boolean deleteAccount(String mobileNumber);
}
