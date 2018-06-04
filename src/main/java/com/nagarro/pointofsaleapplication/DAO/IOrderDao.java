package com.nagarro.pointofsaleapplication.DAO;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.OrderEO;

/**
 * @author chetanmahajan interface specifying order related operations
 */
public interface IOrderDao {

    /**
     * @param order
     *            to be added to the order table having all details of it
     * @param cust_id
     *            customer id
     * @param emp_id
     *            employee id
     * @return the same after successfully adding
     */
    Response<OrderEO> addOrder(OrderEO order, final int emp_id, final int cust_id);

    /**
     * @param empId
     *            employee id
     * @param custId
     *            customer id
     * @return their order's
     */
    Response<List<OrderEO>> getCustomerOrders(final int empId, final int custId);

    /**
     * @param empId
     *            employee id
     * @return all orders belongs to that employee
     */
    Response<List<OrderEO>> getEmployeeOrders(final int empId, final String status);

    /**
     * @param orderId
     *            of order having status saved
     * @return deleted saved order
     */
    Response<OrderEO> deleteOrder(final int orderId);

    /**
     * @param orderId
     *            id of the order to be fetch
     * @return fetched order
     */
    OrderEO getOrderById(final int orderId);

    /**
     * @param id
     *            employee id
     * @return cash transactions details of that employee
     */
    Response<List<Transaction>> getTransactions(final int id, final String date);

}
