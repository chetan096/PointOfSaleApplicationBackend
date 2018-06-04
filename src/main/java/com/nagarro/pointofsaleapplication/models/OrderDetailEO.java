package com.nagarro.pointofsaleapplication.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;

/**
 * @author chetanmahajan order detail entity create order detail table in the db with the given fields
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.ORDER_DETAIL_TABLE_NAME)
public class OrderDetailEO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private OrderEO order;

    @ManyToOne
    private ProductEO product;

    @Column
    private long quantity;

    @Column
    private float price;

    @Transient
    private int productId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    // to break the cycle between order and order detail
    @JsonIgnore
    public OrderEO getOrder() {
        return order;
    }

    public void setOrder(final OrderEO order) {
        this.order = order;
    }

    public ProductEO getProduct() {
        return product;
    }

    public void setProduct(final ProductEO product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

}
