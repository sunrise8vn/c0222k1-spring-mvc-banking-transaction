package com.cg.service.customer;

import com.cg.model.Customer;
import com.cg.model.Deposit;
import com.cg.service.IGeneralService;

public interface CustomerService extends IGeneralService<Customer> {

    void deposit(Customer customer, Deposit deposit);
}
