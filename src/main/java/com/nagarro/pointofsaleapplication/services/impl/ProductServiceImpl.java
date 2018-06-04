package com.nagarro.pointofsaleapplication.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.pointofsaleapplication.DAO.IProductDAO;
import com.nagarro.pointofsaleapplication.models.ProductEO;
import com.nagarro.pointofsaleapplication.services.IProductService;

/**
 * @author chetanmahajan implementation of interface providing product related services
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDAO productDao;

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IProductService#getProducts()
     */
    @Override
    public List<ProductEO> getProducts() {
        return productDao.getProducts();
    }

    /*
     * (non-Javadoc)
     * @see
     * com.nagarro.pointofsaleapplication.services.IProductService#addProduct(com.nagarro.pointofsaleapplication.models.
     * ProductEO)
     */
    @Override
    public ProductEO addProduct(final ProductEO product) {
        return productDao.addProduct(product);
    }

    /*
     * (non-Javadoc)
     * @see com.nagarro.pointofsaleapplication.services.IProductService#getProductById(int)
     */
    @Override
    public ProductEO getProductById(final int id) {
        return productDao.getProductById(id);
    }

}
