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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.MyError;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;
import com.nagarro.pointofsaleapplication.models.OrderEO;
import com.nagarro.pointofsaleapplication.services.ICashDrawerService;
import com.nagarro.pointofsaleapplication.services.IEmployeeService;
import com.nagarro.pointofsaleapplication.services.IOrderService;

/**
 * @author chetanmahajan employee resource providing employee related data
 */
@RestController
@CrossOrigin
public class EmployeeResource {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private ICashDrawerService cashDrawerService;

    @Autowired
    private IOrderService orderService;

    /**
     * @param key
     *            api key for authentication
     * @return all employees in response entity
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity listAllEmployees(@RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        List<EmployeeEO> employees = employeeService.getEmployees();
        if (employees.isEmpty()) {
            MyError error = new MyError(PointOfSaleApplicationMessages.NO_EMPLOYEE_FOUND_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<EmployeeEO>>(employees, HttpStatus.OK);
    }

    /**
     * @param employee
     *            to be added in the db
     * @param key
     *            api key for authentication
     * @return added employee or error if not added successfully
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/employees", method = RequestMethod.POST)
    public ResponseEntity addNewEmployee(@RequestBody EmployeeEO employee, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        EmployeeEO addedEmployee = employeeService.addEmployee(employee);
        if (addedEmployee == null) {
            MyError error = new MyError(PointOfSaleApplicationMessages.ERROR_ADDING_EMPLOYEE_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<EmployeeEO>(addedEmployee, HttpStatus.CREATED);
    }

    /**
     * @param employee
     *            to be authenticate
     * @param key
     *            api key
     * @return null if not authenticate otherwise authenticated employee
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/employees/login", method = RequestMethod.POST)
    public ResponseEntity authenticateEmployee(@RequestBody EmployeeEO employee, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        EmployeeEO returnedEmployee = employeeService.authenticateEmployee(employee);
        if (returnedEmployee == null) {
            MyError error = new MyError(PointOfSaleApplicationMessages.INVALID_CREDENTIALS_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<EmployeeEO>(returnedEmployee, HttpStatus.OK);
    }

    /**
     * @param cashDrawer
     *            cash drawer of the employee to insert initial money to the drawer
     * @param key
     *            api key
     * @param id
     *            employee id to whom drawer belongs
     * @return added cash drawer instance
     */
    @RequestMapping(value = "/employees/{id}/cashdrawer", method = RequestMethod.POST)
    public ResponseEntity<?> addNewCash(@RequestBody CashDrawerEO cashDrawer, @RequestHeader("apikey")
    final String key, @PathVariable
    final int id) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<CashDrawerEO> response = cashDrawerService.addCash(cashDrawer, id);
        return new ResponseEntity<Response<CashDrawerEO>>(response, response.getResponseStatus());
    }

    /**
     * @param empId
     *            employee id of the employee whose orders we want
     * @param key
     *            api key
     * @param status
     *            placed or saved order we want
     * @return if nothing provided as query parameter then all orders will be returned otherwise according to the status
     */
    @RequestMapping(value = "/employees/{empId}/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployeeOrders(@PathVariable
    final int empId, @RequestHeader("apikey")
    final String key, @RequestParam(value = "status", required = false)
    final String status) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<List<OrderEO>> response = orderService.getEmployeeOrders(empId, status);
        return new ResponseEntity<Response<List<OrderEO>>>(response, response.getResponseStatus());
    }

    /**
     * @param key
     *            api key
     * @param id
     *            employee's id to whom drawer belongs
     * @return cash drawer having starting and current balance
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/employees/{id}/cashdrawer", method = RequestMethod.GET)
    public ResponseEntity getCashDrawer(@RequestHeader("apikey")
    final String key, @PathVariable
    final int id) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        CashDrawerEO cashDrawer = cashDrawerService.getCashdrawerByEmployeeId(id);
        if (cashDrawer == null) {
            MyError error = new MyError(PointOfSaleApplicationMessages.NO_CASH_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<CashDrawerEO>(cashDrawer, HttpStatus.OK);
    }

    /**
     * @param order
     *            to be added in the order table
     * @param empId
     *            employee id
     * @param custId
     *            customer id
     * @param key
     *            api key
     * @return added order with order details
     */
    @RequestMapping(value = "/employees/{empId}/customers/{custId}/orders", method = RequestMethod.POST)
    public ResponseEntity<?> addNewOrder(@RequestBody OrderEO order, @PathVariable
    final int empId, @PathVariable
    final int custId, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<OrderEO> response = orderService.addOrder(order, empId, custId);
        return new ResponseEntity<Response<OrderEO>>(response, response.getResponseStatus());
    }

    /**
     * @param empId
     *            employee id
     * @param custId
     *            customer
     * @param key
     *            api key
     * @return customer order list with order details attached
     */
    @RequestMapping(value = "/employees/{empId}/customers/{custId}/orders", method = RequestMethod.GET)
    public ResponseEntity<?> getCustomerOrder(@PathVariable
    final int empId, @PathVariable
    final int custId, @RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        Response<List<OrderEO>> response = orderService.getCustomerOrders(empId, custId);
        return new ResponseEntity<Response<List<OrderEO>>>(response, response.getResponseStatus());
    }

    /**
     * @param key
     *            api key
     * @param id
     *            employee id
     * @return transaction response
     */
    @RequestMapping(value = "/employees/{id}/transactions", method = RequestMethod.GET)
    public ResponseEntity<?> getTransactions(@RequestHeader("apikey")
    final String key, @PathVariable
    final int id, @RequestParam(value = "date", required = false)
    final String date) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        if (date != null) {
            Response<List<Transaction>> response = orderService.getTransactions(id, date);
            return new ResponseEntity<Response<List<Transaction>>>(response, HttpStatus.OK);
        } else {
            Response<List<CashDrawerEO>> response = cashDrawerService.getCashDrawerEntries(id);
            return new ResponseEntity<Response<List<CashDrawerEO>>>(response, HttpStatus.OK);
        }

    }

    /**
     * @param key
     *            api key
     * @param startDate
     *            starting date
     * @param endDate
     *            ending date
     * @param id
     *            employee id whose transactions you want to see
     * @return list of transactions
     * 
     */
    @RequestMapping(value = "/employees/{id}/reports", method = RequestMethod.GET)
    public ResponseEntity<?> downloadFile(@RequestHeader("apikey") String key,
            @RequestParam(value = "startDate", required = true)
            final String startDate, @RequestParam(value = "endDate", required = true)
            final String endDate, @PathVariable
            final int id) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<List<Transaction>>(employeeService.createExcelFile(id, startDate, endDate),
                HttpStatus.OK);

    }
}
