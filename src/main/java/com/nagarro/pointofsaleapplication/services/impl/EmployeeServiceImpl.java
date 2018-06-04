package com.nagarro.pointofsaleapplication.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.pointofsaleapplication.DAO.IEmployeeDao;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;
import com.nagarro.pointofsaleapplication.services.IEmployeeService;

/**
 * @author chetanmahajan implementation of interface providing employee related services
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeDao employeeDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IEmployeeService#getEmployees()
     */
    @Override
    public List<EmployeeEO> getEmployees() {
        return employeeDao.getEmployees();
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IEmployeeService#addEmployee(com.nagarro.pointofsaleapplication.
     * models.EmployeeEO)
     */
    @Override
    public EmployeeEO addEmployee(EmployeeEO employee) {
        return employeeDao.addEmployee(employee);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IEmployeeService#authenticateEmployee(com.nagarro.
     * pointofsaleapplication.models.EmployeeEO)
     */
    @Override
    public EmployeeEO authenticateEmployee(EmployeeEO employee) {
        return employeeDao.authenticateEmployee(employee);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IEmployeeService#getEmployeeById(long)
     */
    @Override
    public EmployeeEO getEmployeeById(final int id) {
        return employeeDao.getEmployeeById(id);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IEmployeeService#createExcelFile()
     */
    @Override
    public List<Transaction> createExcelFile(final int id, final String startDate, final String endingDate) {
        List<Transaction> transactions = employeeDao.getReports(id, startDate, endingDate);
        return transactions;
    }

}
