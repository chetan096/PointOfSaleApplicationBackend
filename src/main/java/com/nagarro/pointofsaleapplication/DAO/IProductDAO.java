package com.nagarro.pointofsaleapplication.DAO;

import java.util.List;

import com.nagarro.pointofsaleapplication.models.ProductEO;

/**
 * @author chetanmahajan interface specifying product related operations
 */
public interface IProductDAO {

    /**
     * @return get all products from the db
     */
    List<ProductEO> getProducts();

    /**
     * @param product
     *            to be added to the product table
     * @return product after successfully adding
     */
    ProductEO addProduct(ProductEO product);

    /**
     * @param id
     *            product's id
     * @return product with the same id
     */
    ProductEO getProductById(final int id);

    /**
     * @param product
     *            whose stock is to be updated
     * @return
     */
    ProductEO updateProductStock(ProductEO product);

}
