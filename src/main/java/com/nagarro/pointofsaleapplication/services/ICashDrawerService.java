package com.nagarro.pointofsaleapplication.services;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;

/**
 * @author chetanmahajan interface for cash drawer related services
 */
public interface ICashDrawerService {

    /**
     * @param cashDrawer
     *            instance to be added in the cash drawer table in db
     * @return the same after adding
     */
    Response<CashDrawerEO> addCash(CashDrawerEO cashDrawer, final int emp_id);

    /**
     * @param amount
     *            to be added
     * @param id
     *            employee id to whom cash drawer belongs
     * @return update cash drawer
     */
    Response<CashDrawerEO> updateCash(final float amount, final int id, final String orderDate);

    /**
     * @return cashdrawer of employee having this emp_id i.e employee id for the given date
     */
    CashDrawerEO getCashdrawerByEmployeeId(final int emp_id, final String orderDate);

    /**
     * @return cashdrawer of employee having this emp_id i.e employee id for today
     */
    CashDrawerEO getCashdrawerByEmployeeId(final int emp_id);

    /**
     * @param id
     *            employee id
     * @return cash drawer entries till now
     */
    Response<List<CashDrawerEO>> getCashDrawerEntries(final int id);
}
