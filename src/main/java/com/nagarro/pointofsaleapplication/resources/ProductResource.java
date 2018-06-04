package com.nagarro.pointofsaleapplication.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationMessages;
import com.nagarro.pointofsaleapplication.dto.MyError;
import com.nagarro.pointofsaleapplication.models.ProductEO;
import com.nagarro.pointofsaleapplication.services.IProductService;

/**
 * @author chetanmahajan product resource for getting data related to product
 */
@RestController
@CrossOrigin
public class ProductResource {

    @Autowired
    private IProductService productService;

    /**
     * @param key
     *            api key
     * @return list of products
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity listAllProducts(@RequestHeader("apikey")
    final String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        List<ProductEO> products = productService.getProducts();
        if (products.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProductEO>>(products, HttpStatus.OK);
    }

    /**
     * @param product
     *            to be added to the product table
     * @param key
     *            api key
     * @return added product
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value = "/products", method = RequestMethod.POST)
    public ResponseEntity addNewProduct(@RequestBody ProductEO product, @RequestHeader("apikey") String key) {
        long apikey = Long.parseLong(key);
        if (apikey != PointOfSaleApplicationConstants.API_KEY) {
            MyError error = new MyError(PointOfSaleApplicationMessages.WRONG_API_KEY_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.FORBIDDEN);
        }
        ProductEO addedProduct = productService.addProduct(product);
        if (addedProduct == null) {
            MyError error = new MyError(PointOfSaleApplicationMessages.ERROR_ADDING_PRODUCT_MESSAGE);
            return new ResponseEntity<MyError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductEO>(addedProduct, HttpStatus.CREATED);
    }
}
