package com.nagarro.pointofsaleapplication.services;

import java.util.List;

import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.OrderEO;

/**
 * @author chetanmahajan interface for order related services
 */
public interface IOrderService {

    /**
     * @param order
     *            to be added to the order table having all details of it
     * @return the same after successfully adding
     */
    Response<OrderEO> addOrder(OrderEO order, final int empId, final int custId);

    /**
     * @param empId
     *            employee id
     * @param custId
     *            customer id
     * @return order object of that customer
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
     *            delete order having status saved as passed its id
     * @return deleted saved order
     */
    Response<OrderEO> deleteOrder(final int orderId);

    /**
     * @param id
     *            employee id
     * @return cash transactions details of that employee
     */
    Response<List<Transaction>> getTransactions(final int id, final String date);
}
