package com.nagarro.pointofsaleapplication.DAO.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import com.nagarro.pointofsaleapplication.DAO.ICustomerDao;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.Response;
import com.nagarro.pointofsaleapplication.models.CustomerEO;
import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

/**
 * @author chetanmahajan class implementing the interface specifying the operations related to customer
 */
@Repository
public class CustomerDaoImpl implements ICustomerDao {

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICustomerDao#getAllCustomers()
     */
    @Override
    public List<CustomerEO> getAllCustomers() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        TypedQuery<CustomerEO> query = session.createNamedQuery(CustomerEO.GET_ALL_CUSTOMERS, CustomerEO.class);
        session.getTransaction().commit();
        List<CustomerEO> list = query.getResultList();
        session.close();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICustomerDao#addCustomer(com.nagarro.pointofsaleapplication.models.
     * CustomerEO)
     */
    @Override
    public CustomerEO addCustomer(final CustomerEO customer) {
        CustomerEO newCustomer = null;
        try {

            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(customer);
            newCustomer = customer;
            session.getTransaction().commit();
            session.close();
        } catch (Exception exception) {

        }
        return newCustomer;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICustomerDao#getCustomerById(int)
     */
    @Override
    public CustomerEO getCustomerById(final int cust_id) {

        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        CustomerEO customer = null;
        try {

            TypedQuery<CustomerEO> query = session.createNamedQuery(CustomerEO.GET_CUSTOMER_BY_ID, CustomerEO.class)
                    .setParameter("id", cust_id);
            customer = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return customer;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.ICustomerDao#getCustomersByQuery(java.lang.String)
     */
    @Override
    public Response<List<CustomerEO>> getCustomersByQuery(final String findBy) {
        try {
            Response<List<CustomerEO>> response = new Response<>();
            Session session = HibernateUtility.getSessionFactory().openSession();
            session.beginTransaction();
            TypedQuery<CustomerEO> query = session
                    .createNamedQuery(CustomerEO.GET_ALL_CUSTOMERS_BY_QUERY_STRING, CustomerEO.class)
                    .setParameter("input", "%" + findBy + "%");
            session.getTransaction().commit();
            List<CustomerEO> list = query.getResultList();
            if (list.isEmpty()) {
                response.setMessage(PointOfSaleApplicationMessages.NO_CUSTOMER_FOUND);
            } else {
                response.setMessage(PointOfSaleApplicationMessages.SUCCESFULLY_FETCHED_CUSTOMERS);
            }
            response.setModelObj(list);
            response.setStatus(true);
            response.setResponseStatus(HttpStatus.OK);
            session.close();
            return response;

        } catch (Exception exception) {
            return new Response<List<CustomerEO>>(null, HttpStatus.INTERNAL_SERVER_ERROR,
                    PointOfSaleApplicationMessages.INTERNAL_SERVER_ERROR_MESSAGE, false);
        }
    }

}
