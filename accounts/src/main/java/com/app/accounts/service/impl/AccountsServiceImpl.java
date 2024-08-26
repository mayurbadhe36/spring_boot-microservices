package com.app.accounts.service;

import com.app.accounts.Constants.AccountsConstants;
import com.app.accounts.dto.AccountsDto;
import com.app.accounts.dto.CustomerDto;
import com.app.accounts.entity.Accounts;
import com.app.accounts.entity.Customer;
import com.app.accounts.exception.CustomerAlreadyExistsException;
import com.app.accounts.exception.ResourceNotFoundException;
import com.app.accounts.mapper.AccountsMapper;
import com.app.accounts.mapper.CustomerMapper;
import com.app.accounts.repository.IAccountsRepository;
import com.app.accounts.repository.ICustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService{

    private IAccountsRepository accountsRepository;

    private ICustomerRepository customerRepository;
    /**
     * @param customerDto --customer Dto object
     */
    @Override
    public void createAccounts(CustomerDto customerDto) {
        Customer customer =CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer =customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer is already registered with given mobile number "+customer.getMobileNumber());
        }
        Customer savedCustomer=customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     *
     * @param customer --Customer object
     * @return accounts --Accounts object
     */
    private Accounts createNewAccount(Customer customer){
        Accounts newAccounts= new Accounts();
        newAccounts.setCustomerId(customer.getCustomerId());
        long randomNumber =1000000000L + new Random().nextInt(900000000);

        newAccounts.setAccountNumber(randomNumber);
        newAccounts.setAccountType(AccountsConstants.SAVINGS);
        newAccounts.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccounts;
    }


    /**
     * @param mobileNumber
     * @return account details based on given mobile number
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer =customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","Mobile Number",mobileNumber)
        );
        Accounts accounts=accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                ()-> new ResourceNotFoundException("Accounts","Customer Id",customer.getCustomerId().toString())
        );
        CustomerDto customerDto=CustomerMapper.mapToCustomerDto(customer,new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts,new AccountsDto()));
        return customerDto;
    }

    /**
     * @param customerDto
     * @return boolean value that record updated or not
     */
    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated=false;
        AccountsDto accountsDto=customerDto.getAccountsDto();
        Accounts accounts=accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow( () -> new ResourceNotFoundException("Accounts ","Account Number ",accountsDto.getAccountNumber().toString()));
        AccountsMapper.mapToAccounts(accountsDto,accounts);
        accountsRepository.save(accounts);
        Customer customer=customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow( ()-> new ResourceNotFoundException("Customer ","mobile number",customerDto.getMobileNumber()));
        CustomerMapper.mapToCustomer(customerDto,customer);
        customerRepository.save(customer);
        isUpdated=true;
        return isUpdated;
    }

    /**
     * @param mobileNumber
     * @return boolean value that record deleted or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer =customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobile Number",mobileNumber)
                );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
