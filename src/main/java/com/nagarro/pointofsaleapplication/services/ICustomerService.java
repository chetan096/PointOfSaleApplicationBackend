package com.nagarro.pointofsaleapplication.services;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CustomerEO;

/**
 * @author chetanmahajan interface for customer related services
 */
public interface ICustomerService {

    /**
     * @return all customers from the customers table in the db
     */
    List<CustomerEO> getAllCustomers();

    /**
     * @param customer
     *            to be added to the Customer table
     * @return the same after successfully adding
     */
    CustomerEO addCustomer(CustomerEO customer);

    /**
     * @param findBy
     *            search query string
     * @return all customers whose customer code name email username matched
     */
    Response<List<CustomerEO>> getCustomers(final String findBy);

    /**
     * @param id
     *            of the customer
     * @return customer with same id
     */
    CustomerEO getCustomerById(final int id);

}
