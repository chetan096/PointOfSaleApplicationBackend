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
 * @author chetanmahajan Customer entity creates table in the database with the given fields
 *
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.CUSTOMER_TABLE_NAME)
@NamedQueries({ @NamedQuery(name = CustomerEO.GET_ALL_CUSTOMERS, query = "select customer from CustomerEO customer"),
        @NamedQuery(name = CustomerEO.GET_CUSTOMER_BY_ID, query = "select customer from CustomerEO customer where "
                + "id=:id"),
        @NamedQuery(name = CustomerEO.GET_ALL_CUSTOMERS_BY_QUERY_STRING, query = "select customer from CustomerEO"
                + " customer where customerCode like :input or username like :input or email like"
                + " :input or phone_no like :input") })

public class CustomerEO {

    public static final String GET_ALL_CUSTOMERS = "getAllCustomers";

    public static final String GET_CUSTOMER_BY_ID = "getCustomerById";

    public static final String GET_ALL_CUSTOMERS_BY_QUERY_STRING = "getCustomersByQueryString";

    @Id
    // create autoincrement primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String customerCode;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;
    @Column
    private String password;

    @Column(unique = true)
    private long phone_no;

    @Column
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(final String customerCode) {
        this.customerCode = customerCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(final long phone_no) {
        this.phone_no = phone_no;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

}
