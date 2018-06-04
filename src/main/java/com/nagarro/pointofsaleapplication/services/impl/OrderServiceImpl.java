package com.nagarro.pointofsaleapplication.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.pointofsaleapplication.DAO.IOrderDao;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.models.OrderEO;
import com.nagarro.pointofsaleapplication.services.IOrderService;

/**
 * @author chetanmahajan implementation of interface providing order related services
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    /*
     * (non-Javadoc)
     * @see
     * com.nagarro.pointofsaleapplication.services.IOrderService#addOrder(com.nagarro.pointofsaleapplication.models.
     * OrderEO)
     */
    @Override
    public Response<OrderEO> addOrder(OrderEO order, final int emp_id, int cust_id) {
        return orderDao.addOrder(order, emp_id, cust_id);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IOrderService#getCustomerOrders(int, int)
     */
    @Override
    public Response<List<OrderEO>> getCustomerOrders(final int empId, final int custId) {
        return orderDao.getCustomerOrders(empId, custId);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IOrderService#getEmployeeOrders(int)
     */
    @Override
    public Response<List<OrderEO>> getEmployeeOrders(final int empId, final String status) {
        return orderDao.getEmployeeOrders(empId, status);
    }

    @Override
    public Response<OrderEO> deleteOrder(final int orderId) {
        return orderDao.deleteOrder(orderId);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IOrderService#getTransactions(int)
     */
    @Override
    public Response<List<Transaction>> getTransactions(final int id, final String date) {
        return orderDao.getTransactions(id, date);
    }

}
