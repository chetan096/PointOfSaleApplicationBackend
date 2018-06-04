package com.nagarro.pointofsaleapplication.DAO;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CustomerEO;

/**
 * @author chetanmahajan interface specfying customer related operations
 */
public interface ICustomerDao {

    /**
     * @return all customers from the customer table in the db
     */
    List<CustomerEO> getAllCustomers();

    /**
     * @param customer
     *            instance to be added into the customer table in the db
     * @return the same after successfully adding
     */
    CustomerEO addCustomer(CustomerEO customer);

    /**
     * @param cust_id
     *            customer id
     * @return customer with the same customer id
     */
    CustomerEO getCustomerById(final int cust_id);

    /**
     * @param findBy
     *            query string by which we have to search for customer name code email mobile no.
     * @return all matched customers
     */
    Response<List<CustomerEO>> getCustomersByQuery(String findBy);
}
