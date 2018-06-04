package com.nagarro.pointofsaleapplication.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.pointofsaleapplication.DAO.ICustomerDao;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CustomerEO;
import com.nagarro.pointofsaleapplication.services.ICustomerService;

/**
 * @author chetanmahajan implementation of interface providing customer related services
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerDao customerDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICustomerService#getAllCustomers()
     */
    @Override
    public List<CustomerEO> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICustomerService#addCustomer(com.nagarro.pointofsaleapplication.
     * models.CustomerEO)
     */
    @Override
    public CustomerEO addCustomer(CustomerEO customer) {
        return customerDao.addCustomer(customer);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICustomerService#getCustomers(java.lang.String)
     */
    @Override
    public Response<List<CustomerEO>> getCustomers(final String findBy) {
        return customerDao.getCustomersByQuery(findBy);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.ICustomerService#getCustomerById(int)
     */
    @Override
    public CustomerEO getCustomerById(final int id) {
        return customerDao.getCustomerById(id);
    }

}
