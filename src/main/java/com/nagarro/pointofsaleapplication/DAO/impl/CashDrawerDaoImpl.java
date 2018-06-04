package com.nagarro.pointofsaleapplication.DAO.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao;
import com.nagarro.pointofsaleapplication.DAO.IEmployeeDao;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CashDrawerEO;
import com.nagarro.pointofsaleapplication.models.EmployeeEO;
import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

/**
 * @author chetanmahajan class implementing the interface specifying the operations related to cash drawer
 */
@Repository
public class CashDrawerDaoImpl implements ICashDrawerDao {

    @Autowired
    private IEmployeeDao employeeDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#addCash(com.nagarro.pointofsaleapplication.models.
     * CashDrawerEO)
     */
    @Override
    public Response<CashDrawerEO> addCash(CashDrawerEO cashDrawer, final int emp_id) {
        CashDrawerEO newCashDrawerEntry = null;
        Response<CashDrawerEO> response = new Response<>();
        try {
            EmployeeEO employee = employeeDao.getEmployeeById(emp_id);
            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            if (employee != null) {
                cashDrawer.setCurrentBalance(cashDrawer.getStartBalance());
                cashDrawer.setEmployee(employee);
                cashDrawer.setUpdatedAt(new Date());
                SimpleDateFormat formatter = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT);
                cashDrawer.setDate(formatter.format(cashDrawer.getUpdatedAt()));
                SimpleDateFormat formatter1 = new SimpleDateFormat(PointOfSaleApplicationConstants.TIME_FORMAT);
                cashDrawer.setLastUpdationTime((formatter1.format(cashDrawer.getUpdatedAt())));
                session.save(cashDrawer);
                newCashDrawerEntry = cashDrawer;
                response.setMessage(PointOfSaleApplicationMessages.SUCCESSFULL_ADDED_CASH_MESSAGE);
                response.setStatus(true);
                response.setResponseStatus(HttpStatus.CREATED);
            } else {
                response.setMessage(PointOfSaleApplicationMessages.ERROR_ADDED_CASH_MESSAGE);
                response.setStatus(false);
                response.setResponseStatus(HttpStatus.FORBIDDEN);
            }
            response.setModelObj(newCashDrawerEntry);
            session.getTransaction().commit();
            session.close();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#updateCash(com.nagarro.pointofsaleapplication.models.
     * CashDrawerEO, int)
     */
    @Override
    public Response<CashDrawerEO> updateCash(final float amount, final int emp_id, final String orderDate) {
        CashDrawerEO updatedCashDrawerEntry = null;
        Response<CashDrawerEO> response = new Response<>();
        try {
            CashDrawerEO cashDrawer = this.getCashdrawerByEmployeeId(emp_id, orderDate);
            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            if (cashDrawer != null) {
                cashDrawer.setCurrentBalance(cashDrawer.getCurrentBalance() + amount);
                cashDrawer.setUpdatedAt(new Date());
                session.update(cashDrawer);
                updatedCashDrawerEntry = cashDrawer;
                response.setMessage(PointOfSaleApplicationMessages.SUCCESSFULL_UPDATE_CASH_MESSAGE);
                response.setStatus(true);
                response.setResponseStatus(HttpStatus.OK);
            } else {
                response.setMessage(PointOfSaleApplicationMessages.ERROR_UPDATING_CASH_MESSAGE);
                response.setStatus(false);
                response.setResponseStatus(HttpStatus.FORBIDDEN);
            }
            response.setModelObj(updatedCashDrawerEntry);
            session.getTransaction().commit();
            session.close();
        } catch (Exception exception) {
        }
        return response;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#getCashdrawerByEmployeeId(int)
     */
    @Override
    public CashDrawerEO getCashdrawerByEmployeeId(final int emp_id, final String orderDate) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        CashDrawerEO drawer = null;
        try {
            TypedQuery<CashDrawerEO> query = session.createNamedQuery(CashDrawerEO.GET_DRAWER_BY_ID, CashDrawerEO.class)
                    .setParameter("id", emp_id).setParameter("date", orderDate);
            drawer = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return drawer;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#getCashdrawerByEmployeeId(int)
     */
    public CashDrawerEO getCashdrawerByEmployeeId(final int emp_id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        CashDrawerEO drawer = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT);
            final String date = formatter.format(new Date());
            TypedQuery<CashDrawerEO> query = session.createNamedQuery(CashDrawerEO.GET_DRAWER_BY_ID, CashDrawerEO.class)
                    .setParameter("id", emp_id).setParameter("date", date);
            drawer = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return drawer;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#getCashDrawerEntries(int)
     */
    @Override
    public Response<List<CashDrawerEO>> getCashDrawerEntries(final int id) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<CashDrawerEO> drawerEntries = null;
        try {
            TypedQuery<CashDrawerEO> query = session
                    .createNamedQuery(CashDrawerEO.GET_DRAWER_ALL_ENTRIES, CashDrawerEO.class).setParameter("id", id);
            drawerEntries = query.getResultList();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return new Response<List<CashDrawerEO>>(drawerEntries, HttpStatus.OK,
                PointOfSaleApplicationMessages.SUCCESFULLY_FETCHED_DRAWER, true);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICashDrawerDao#getCashdrawerForGivenDates(int, java.lang.String,
     * java.lang.String)
     */
    @Override
    public List<CashDrawerEO> getCashdrawerForGivenDates(final int id, final String startDate,
            final String endingDate) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        List<CashDrawerEO> drawerEntries = null;
        try {
            Date start = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT).parse(startDate);
            Date end = new SimpleDateFormat(PointOfSaleApplicationConstants.DATE_FORMAT).parse(endingDate);
            TypedQuery<CashDrawerEO> query = session
                    .createNamedQuery(CashDrawerEO.GET_DRAWER_ALL_ENTRIES_FOR_GIVEN_DATES, CashDrawerEO.class)
                    .setParameter("id", id).setParameter("startDate", start).setParameter("endingDate", end);
            drawerEntries = query.getResultList();

        } catch (NoResultException | ParseException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return drawerEntries;
    }

}
