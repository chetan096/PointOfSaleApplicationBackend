package com.nagarro.pointofsaleapplication.services;

import java.util.List;

import com.nagarro.pointofsaleapplication.models.ProductEO;

/**
 * @author chetanmahajan interface for product related services
 */
public interface IProductService {

    /**
     * 
     * @return all products from the products table
     */
    List<ProductEO> getProducts();

    /**
     * @param product
     *            to be added to the product table
     * @return the same after successfully adding
     */
    ProductEO addProduct(ProductEO product);

    /**
     * @param id
     *            of the product
     * @return product from the database
     */
    ProductEO getProductById(final int id);

}
