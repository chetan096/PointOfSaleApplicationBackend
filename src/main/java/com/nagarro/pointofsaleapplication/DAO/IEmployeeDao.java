package com.nagarro.pointofsaleapplication.DAO;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;

/**
 * @author chetanmahajan interface specyfying employee related operations
 */
public interface IEmployeeDao {

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
     * get all orders between these dates
     * 
     * @param startDate
     *            starting date
     * @param endingDate
     *            ending date
     * @return list of all that orders converted to transaction entity
     */
    List<Transaction> getReports(final int id, final String startDate, final String endingDate);
}
