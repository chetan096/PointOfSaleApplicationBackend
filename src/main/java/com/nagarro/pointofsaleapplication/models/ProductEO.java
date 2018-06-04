package com.nagarro.pointofsaleapplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;

/**
 * @author chetanmahajan product entity creates products table in the db with the given fields
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.PRODUCT_TABLE_NAME)
@NamedQueries({ @NamedQuery(name = ProductEO.GET_ALL_PRODUCTS, query = "select product from ProductEO product "),
        @NamedQuery(name = ProductEO.GET_PRODUCT_BY_ID, query = "select product from ProductEO product where "
                + "id=:id") })
public class ProductEO {

    public static final String GET_ALL_PRODUCTS = "getAllproducts";

    public static final String GET_PRODUCT_BY_ID = "getProductById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String productCode;
    @Column
    private String productName;
    @Column
    private String description;
    @Column
    private float price;
    @Column
    private long quantity;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(final String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(final String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

}
