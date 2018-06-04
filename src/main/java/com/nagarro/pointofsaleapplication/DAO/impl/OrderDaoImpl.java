package com.nagarro.pointofsaleapplication.DAO.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao;
import com.nagarro.pointofsaleapplication.DAO.ICustomerDao;
import com.nagarro.pointofsaleapplication.DAO.IEmployeeDao;
import com.nagarro.pointofsaleapplication.DAO.IOrderDao;
import com.nagarro.pointofsaleapplication.DAO.IProductDAO;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.enums.PaymentMode;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;
import com.nagarro.pointofsaleapplication.models.CustomerEO;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;
import com.nagarro.pointofsaleapplication.models.OrderDetailEO;
import com.nagarro.pointofsaleapplication.models.OrderEO;
import com.nagarro.pointofsaleapplication.models.ProductEO;
import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

/**
 * @author chetanmahajan class implementing the interface specifying the operations related to order
 */
@Repository
public class OrderDaoImpl implements IOrderDao {

    @Autowired
    private ICashDrawerDao cashDrawerDao;
    @Autowired
    private IEmployeeDao employeeDao;
    @Autowired
    private ICustomerDao customerDao;
    @Autowired
    private IProductDAO productDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#addOrder(com.nagarro.pointofsaleapplication.models.OrderEO)
     */
    @Override
    public Response<OrderEO> addOrder(OrderEO order, final int emp_id, final int cust_id) {

        Response<OrderEO> response = null;
        try {
            EmployeeEO employee = employeeDao.getEmployeeById(emp_id);
            CustomerEO customer = customerDao.getCustomerById(cust_id);
            if (employee == null || customer == null) {
                response = new Response<>();
                if (employee == null) {
                    response.setMessage(PointOfSaleApplicationMessages.EMPLOYEE_NOT_FOUND_MESSAGE);
                } else {
                    response.setMessage(PointOfSaleApplicationMessages.CUSTOMER_NOT_FOUND_MESSAGE);
                }
                response.setStatus(false);
                response.setResponseStatus(HttpStatus.FORBIDDEN);
                return response;
            }

            for (OrderDetailEO orderdetail : order.getOrderdetails()) {
                ProductEO product = productDao.getProductById(orderdetail.getProductId());
                if (product.getQuantity() < orderdetail.getQuantity()) {
                    response = new Response<>(false,
                            product.getProductName() + PointOfSaleApplicationMessages.PRODUCT_STOCK_EMPTY_MESSAGE,
                            HttpStatus.FORBIDDEN);
                    return response;
                } else {
                    if ("PLACED".equals(order.getStatus().toUpperCase())) {
                        product.setQuantity(product.getQuantity() - orderdetail.getQuantity());
                        product = productDao.updateProductStock(product);
                    }

                }
                orderdetail.setProduct(product);
                orderdetail.setOrder(order);
            }

            order.setCustomer(customer);
            order.setEmployee(employee);
            order.setOrderDate(new Date());
            SimpleDateFormat formatter1 = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT);
            order.setDate(formatter1.format(order.getOrderDate()));
            SimpleDateFormat formatter2 = new SimpleDateFormat(PointOfSaleApplicationConstants.TIME_FORMAT);
            order.setTime(formatter2.format(order.getOrderDate()));

            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(order);
            Response<CashDrawerEO> updateCashResponse = null;
            // if order is placed and payment mode is cash then update cashdrawer balance
            if ("PLACED".equals(order.getStatus().toUpperCase())
                    && (PaymentMode.CASH.getValue() == order.getModeId())) {
                updateCashResponse = cashDrawerDao.updateCash(order.getAmount(), order.getEmployee().getId(),
                        order.getDate());
                if (updateCashResponse.getModelObj() == null) {
                    response = new Response<OrderEO>(false, PointOfSaleApplicationMessages.ERROR_UPDATING_CASH_MESSAGE,
                            HttpStatus.FORBIDDEN);
                    session.getTransaction().rollback();
                    return response;
                }

            }
            session.getTransaction().commit();
            response = new Response<OrderEO>(order, HttpStatus.CREATED,
                    PointOfSaleApplicationMessages.SUCCESSFULLY_ADDED_ORDER_MESSAGE, true);

        } catch (Exception exception) {
            response = new Response<OrderEO>(false, PointOfSaleApplicationMessages.INTERNAL_SERVER_ERROR_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#getCustomerOrders(int, int)
     */
    @Override
    public Response<List<OrderEO>> getCustomerOrders(final int empId, final int custId) {
        Response<List<OrderEO>> response = null;
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<OrderEO> orders = null;
        try {
            TypedQuery<OrderEO> query = session.createNamedQuery(OrderEO.GET_ORDERS_BY_CUSTID, OrderEO.class)
                    .setParameter("empid", empId).setParameter("custid", custId);

            orders = query.getResultList();
            response = new Response<>(orders, HttpStatus.OK,
                    PointOfSaleApplicationMessages.SUCCESSFULLY_FETCHED_ORDERS_MESSAGE, true);

        } catch (NoResultException exception) {
            response = new Response<>(orders, HttpStatus.NO_CONTENT,
                    PointOfSaleApplicationMessages.ERROR_FETCHING_ORDERS_MESSAGE, false);
        }
        session.getTransaction().commit();
        session.close();
        return response;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#getEmployeeOrders(int)
     */
    @Override
    public Response<List<OrderEO>> getEmployeeOrders(final int empId, final String status) {
        Response<List<OrderEO>> response = null;
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<OrderEO> orders = null;
        try {
            if (status == null) {
                TypedQuery<OrderEO> query = session.createNamedQuery(OrderEO.GET_ORDERS_BY_EMPID, OrderEO.class)
                        .setParameter("empid", empId);

                orders = query.getResultList();
            } else {
                TypedQuery<OrderEO> query = session.createNamedQuery(OrderEO.GET_ORDERS_BY_EMPID_STATUS, OrderEO.class)
                        .setParameter("empid", empId).setParameter("status", status.toLowerCase());

                orders = query.getResultList();
            }

            response = new Response<>(orders, HttpStatus.OK,
                    PointOfSaleApplicationMessages.SUCCESSFULLY_FETCHED_ORDERS_MESSAGE, true);
        } catch (NoResultException exception) {
            response = new Response<>(orders, HttpStatus.NO_CONTENT,
                    PointOfSaleApplicationMessages.ERROR_FETCHING_ORDERS_MESSAGE, false);
        }
        session.getTransaction().commit();
        session.close();
        return response;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#deleteOrder(int)
     */
    @Override
    public Response<OrderEO> deleteOrder(final int orderId) {
        Response<OrderEO> response = null;
        OrderEO order = this.getOrderById(orderId);
        if (order == null) {
            response = new Response<>(order, HttpStatus.FORBIDDEN,
                    PointOfSaleApplicationMessages.ERROR_DELETING_ORDER_MESSAGE + orderId, false);
        } else {
            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
            session.close();
            response = new Response<>(order, HttpStatus.OK,
                    PointOfSaleApplicationMessages.SUCCESSFULLY_DELETED_ORDER_MESSAGE + orderId, true);
        }

        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#getOrderById(int)
     */
    @Override
    public OrderEO getOrderById(final int orderId) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        OrderEO order = null;
        try {

            TypedQuery<OrderEO> query = session.createNamedQuery(OrderEO.GET_ORDER_BY_ID, OrderEO.class)
                    .setParameter("id", orderId);
            order = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return order;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IOrderDao#getTransactions(int)
     */
    @Override
    public Response<List<Transaction>> getTransactions(final int id, final String date) {
        Response<List<Transaction>> response = null;
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<OrderEO> orders = null;
        try {
            CashDrawerEO cashDrawer = this.cashDrawerDao.getCashdrawerByEmployeeId(id);
            TypedQuery<OrderEO> query = session.createNamedQuery(OrderEO.GET_ORDERS_FOR_TRANSACTION, OrderEO.class)
                    .setParameter("id", id).setParameter("mode", PaymentMode.CASH.getValue()).setParameter("date", date)
                    .setParameter("status", "placed");

            orders = query.getResultList();
            List<Transaction> transactions = new ArrayList<>();
            float startBalance = cashDrawer.getStartBalance();
            for (final OrderEO order : orders) {
                Transaction transaction = new Transaction(order.getId(), order.getDate(), order.getTime(),
                        order.getAmount(), startBalance, startBalance + order.getAmount());
                transactions.add(transaction);
                startBalance = startBalance + order.getAmount();
            }
            response = new Response<>(transactions, HttpStatus.OK,
                    PointOfSaleApplicationMessages.SUCCESSFULLY_FETCHED_ORDERS_MESSAGE, true);

        } catch (NoResultException exception) {
            response = new Response<>(null, HttpStatus.NO_CONTENT,
                    PointOfSaleApplicationMessages.ERROR_FETCHING_ORDERS_MESSAGE, false);
        }
        session.getTransaction().commit();
        session.close();
        return response;
    }

}
