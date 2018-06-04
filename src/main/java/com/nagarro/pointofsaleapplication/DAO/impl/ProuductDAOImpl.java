package com.nagarro.pointofsaleapplication.DAO.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.nagarro.pointofsaleapplication.DAO.IProductDAO;
import com.nagarro.pointofsaleapplication.models.ProductEO;
import com.nagarro.pointofsaleapplication.utils.HibernateUtility;

/**
 * @author chetanmahajan class implementing the interface specifying the operations related to product
 */
@Repository
public class ProuductDAOImpl implements IProductDAO {

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IProductDAO#getProducts()
     */
    @Override
    public List<ProductEO> getProducts() {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        TypedQuery<ProductEO> query = session.createNamedQuery(ProductEO.GET_ALL_PRODUCTS, ProductEO.class);
        session.getTransaction().commit();
        List<ProductEO> list = query.getResultList();
        session.close();
        return list;

    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IProductDAO#addProduct(com.nagarro.pointofsaleapplication.models.
     * ProductEO)
     */
    @Override
    public ProductEO addProduct(final ProductEO product) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
        return product;
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.DAO.IProductDAO#getProductById(int)
     */
    @Override
    public ProductEO getProductById(final int id) {

        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        ProductEO product = null;
        try {

            TypedQuery<ProductEO> query = session.createNamedQuery(ProductEO.GET_PRODUCT_BY_ID, ProductEO.class)
                    .setParameter("id", id);
            product = query.getSingleResult();

        } catch (NoResultException exception) {
        }
        session.getTransaction().commit();
        session.close();
        return product;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.nagarro.pointofsaleapplication.DAO.IProductDAO#updateProductStock(com.nagarro.pointofsaleapplication.models.
     * ProductEO)
     */
    @Override
    public ProductEO updateProductStock(ProductEO product) {
        Session session = HibernateUtility.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        session.close();
        return product;

    }

}
