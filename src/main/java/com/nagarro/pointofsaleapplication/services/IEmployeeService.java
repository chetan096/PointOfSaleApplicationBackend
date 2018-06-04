package com.nagarro.pointofsaleapplication.services;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;

/**
 * @author chetanmahajan interface for employee related services
 */
public interface IEmployeeService {

    /**
     * 
     * @return all employees from the employees table in the db
     */
    List<EmployeeEO> getEmployees();

    /**
     * @param employee
     *            to be added to the product table
     * @return the same after successfully adding
     */
    EmployeeEO addEmployee(EmployeeEO employee);

    /**
     * @param employee
     *            to be authenticate
     * @return the same after successful authentication
     */
    EmployeeEO authenticateEmployee(EmployeeEO employee);

    /**
     * @param id
     *            of the employee
     * @return employee after fetching from the db
     */
    EmployeeEO getEmployeeById(final int id);

    /**
     * @param id
     *            employee id whose report we want
     * @param start
     *            starting date
     * @param end
     *            ending date
     * @return list of transactions
     */
    List<Transaction> createExcelFile(final int id, final String start, final String end);

}
