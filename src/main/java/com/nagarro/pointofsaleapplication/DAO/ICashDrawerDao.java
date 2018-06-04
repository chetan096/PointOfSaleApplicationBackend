package com.nagarro.pointofsaleapplication.DAO;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;

/**
 * @author chetanmahajan cash drawer interface specifying operations for cash drawer
 */
public interface ICashDrawerDao {

    /**
     * @param cashDrawer
     *            instance to be added in the cash drawer table with the given employee id in db
     * @return the same after adding
     */
    Response<CashDrawerEO> addCash(CashDrawerEO cashDrawer, final int emp_id);

    /**
     * @param amount
     *            to be added to current balance
     * @param id
     *            employee id to whom cash drawer belongs
     * @return update cash drawer
     */
    Response<CashDrawerEO> updateCash(final float amount, final int id, final String orderDate);

    /**
     * @param emp_id
     *            employee id
     * @return cash drawer of corresponding employee
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

    /**
     * @param id
     *            employee id whose cash drawer we want
     * @param startDate
     *            starting date
     * @param endingDate
     *            ending date
     * @return all drawers record between these dates
     */
    List<CashDrawerEO> getCashdrawerForGivenDates(int id, String startDate, String endingDate);

}
