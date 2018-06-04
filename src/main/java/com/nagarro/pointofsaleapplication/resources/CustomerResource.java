package com.nagarro.pointofsaleapplication.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.MyError;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CustomerEO;
import com.nagarro.pointofsaleapplication.services.ICustomerService;

/**
 * @author chetanmahajan customer resource will provide customer related data
 */
@RestController
@CrossOrigin
public class CustomerResource {

    @Autowired
    private ICustomerService customerService;

    /**
     * @param key
     *            api key for authentication
     * @return response entity object of list of customers
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity listAllCustomers(@RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        List<CustomerEO> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<CustomerEO>>(customers, HttpStatus.OK);
    }

    /**
     * @param customer
     *            to be added
     * @param key
     *            api key for authentication
     * @return added customer or error if not added successfully
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public ResponseEntity addNewCustomer(@RequestBody CustomerEO customer, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        CustomerEO addedCustomer = customerService.addCustomer(customer);
        if (addedCustomer == null) {
            MyError error = new MyError(PointOfSaleApplicationMessages.ERROR_ADDING_CUSTOMER_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CustomerEO>(addedCustomer, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/search/{findBy}", method = RequestMethod.GET)
    public ResponseEntity<?> findCustomers(@PathVariable
    final String findBy, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<List<CustomerEO>> response = customerService.getCustomers(findBy);
        return new ResponseEntity<Response<List<CustomerEO>>>(response, response.getResponseStatus());
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<?> findCustomer(@PathVariable
    final int customerId, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        CustomerEO response = customerService.getCustomerById(customerId);
        return new ResponseEntity<CustomerEO>(response, HttpStatus.OK);
    }

}
