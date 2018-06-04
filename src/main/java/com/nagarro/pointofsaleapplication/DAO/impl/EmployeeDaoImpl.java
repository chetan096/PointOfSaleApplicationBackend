package com.nagarro.pointofsaleapplication.DAO.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao;
import com.nagarro.pointofsaleapplication.DAO.IEmployeeDao;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.dto.Transaction;
import com.nagarro.pointofsaleapplication.enums.PaymentMode;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;
import com.nagarro.pointofsaleapplication.models.OrderEO;
import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

/**
 * @author chetanmahajan class implementing the interface specifying the operations related to employee
 */
@Repository
public class EmployeeDaoImpl implements IEmployeeDao {

    @Autowired
    private ICashDrawerDao cashDrawerDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IEmployeeDao#getEmployees()
     */
    @Override
    public List<EmployeeEO> getEmployees() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        TypedQuery<EmployeeEO> query = session.createNamedQuery(EmployeeEO.GET_ALL_EMPLOYEES, EmployeeEO.class);
        session.getTransaction().commit();
        List<EmployeeEO> list = query.getResultList();
        session.close();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IEmployeeDao#addEmployee(com.nagarro.pointofsaleapplication.models.
     * EmployeeEO)
     */
    @Override
    public EmployeeEO addEmployee(final EmployeeEO employee) {
        EmployeeEO newEmployee = null;
        try {

            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(employee);
            newEmployee = employee;
            session.getTransaction().commit();
            session.close();

        } catch (Exception exception) {

        }
        return newEmployee;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IEmployeeDao#authenticateEmployee(com.nagarro.pointofsaleapplication.
     * models.EmployeeEO)
     */
    @Override
    public EmployeeEO authenticateEmployee(final EmployeeEO employee) {

        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        EmployeeEO authenticatedEmployee = null;
        try {

            TypedQuery<EmployeeEO> query = session.createNamedQuery(EmployeeEO.GET_EMPLOYEE, EmployeeEO.class)
                    .setParameter("username", employee.getUsername()).setParameter("password", employee.getPassword());
            authenticatedEmployee = query.getSingleResult();

        } catch (NoResultException exception) {

        }
        session.getTransaction().commit();
        session.close();
        return authenticatedEmployee;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IEmployeeDao#getEmployeeById(long)
     */
    @Override
    public EmployeeEO getEmployeeById(final int id) {

        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        EmployeeEO employee = null;
        try {

            TypedQuery<EmployeeEO> query = session.createNamedQuery(EmployeeEO.GET_EMPLOYEE_BY_ID, EmployeeEO.class)
                    .setParameter("id", id);
            employee = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return employee;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IEmployeeDao#getReports(int, java.lang.String, java.lang.String)
     */
    @Override
    public List<Transaction> getReports(final int id, final String startDate, final String endingDate) {
        List<Transaction> transactions = new ArrayList<>();
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<OrderEO> orders = null;
        try {
            Date start = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT).parse(startDate);
            Date end = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT).parse(endingDate);
            List<CashDrawerEO> cashDrawer = this.cashDrawerDao.getCashdrawerForGivenDates(id, startDate, endingDate);
            if (cashDrawer == null) {
                return transactions;
            } else if (cashDrawer.size() == 0) {
                return transactions;
            }
            Map<String, Float> drawerMap = this.createDrawerMap(cashDrawer);
            TypedQuery<OrderEO> query = session
                    .createNamedQuery(OrderEO.GET_ORDERS_FOR_TRANSACTION_FOR_REPORTS, OrderEO.class)
                    .setParameter("id", id).setParameter("mode", PaymentMode.CASH.getValue())
                    .setParameter("startDate", start).setParameter("status", "placed").setParameter("endingDate", end);

            orders = query.getResultList();
            Map<String, List<OrderEO>> orderMap = this.orderMap(orders);

            orderMap.forEach((key, value) -> {
                float startBalance = drawerMap.get(key);
                for (OrderEO order : value) {
                    Transaction transaction = new Transaction(order.getId(), order.getDate(), order.getTime(),
                            order.getAmount(), startBalance, startBalance + order.getAmount());
                    transactions.add(transaction);
                    startBalance = startBalance + order.getAmount();
                }
            });

        } catch (NoResultException exception) {

        } catch (ParseException e) {
            System.out.println(e);
        }
        session.getTransaction().commit();
        session.close();
        return transactions;
    }

    /**
     * @param orders
     *            list of orders from which to create map map will have date as key and list of orders as values
     * @return created order map
     */
    private Map<String, List<OrderEO>> orderMap(List<OrderEO> orders) {
        Map<String, List<OrderEO>> orderMap = new HashMap<>();
        for (OrderEO order : orders) {
            if (orderMap.containsKey(order.getDate())) {
                orderMap.get(order.getDate()).add(order);
            } else {
                List<OrderEO> orderList = new ArrayList<>();
                orderList.add(order);
                orderMap.put(order.getDate(), orderList);
            }
        }
        return orderMap;
    }

    /**
     * @param cashDrawer
     *            drawer entries from which to create cash drawer map map key is date and startbalance is the value
     * @return created drawer map
     */
    private Map<String, Float> createDrawerMap(List<CashDrawerEO> cashDrawer) {
        Map<String, Float> drawerMap = cashDrawer.stream()
                .collect(Collectors.toMap(CashDrawerEO::getDate, CashDrawerEO::getStartBalance));
        return drawerMap;
    }

}
