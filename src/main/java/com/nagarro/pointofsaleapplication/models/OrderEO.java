package com.nagarro.pointofsaleapplication.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;

/**
 * @author chetanmahajan order entity creates orders table in the db with the given fields
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.ORDER_TABLE_NAME)
@NamedQueries({
        @NamedQuery(name = OrderEO.GET_ORDERS_BY_CUSTID, query = "select order from OrderEO order where "
                + "employee_id=:empid and customer_id=:custid"),
        @NamedQuery(name = OrderEO.GET_ORDERS_BY_EMPID, query = "select order from OrderEO order where "
                + "employee_id=:empid  order by orderDate desc"),
        @NamedQuery(name = OrderEO.GET_ORDERS_BY_EMPID_STATUS, query = "select order from OrderEO order where "
                + "employee_id=:empid and status=:status order by orderDate desc"),
        @NamedQuery(name = OrderEO.GET_ORDER_BY_ID, query = "select order from OrderEO order where " + "id=:id"),
        @NamedQuery(name = OrderEO.GET_ORDERS_FOR_TRANSACTION, query = "select order from OrderEO order where "
                + "employee_id=:id and modeId=:mode and status=:status and date=:date order by orderDate asc"),
        @NamedQuery(name = OrderEO.GET_ORDERS_FOR_TRANSACTION_FOR_REPORTS, query = "select order from OrderEO order where "
                + "employee_id=:id and modeId=:mode and status=:status and orderDate between :startDate and :endingDate"
                + " order by orderDate asc") })
public class OrderEO {

    public static final String GET_ORDERS_BY_CUSTID = "getOrdersByCustId";
    public static final String GET_ORDERS_BY_EMPID = "getOrdersByEmpId";
    public static final String GET_ORDERS_BY_EMPID_STATUS = "getOrdersByEmpIdAndStatus";
    public static final String GET_ORDER_BY_ID = "getOrderById";
    public static final String GET_ORDERS_FOR_TRANSACTION = "getOrdersForTransaction";
    public static final String GET_ORDERS_FOR_TRANSACTION_FOR_REPORTS = "getTransactionsForReports";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private CustomerEO customer;
    @ManyToOne
    private EmployeeEO employee;
    @Column
    private String status;
    @Column
    private int modeId;
    @Column
    private Date orderDate;
    @Column
    private float amount;
    @Column
    private float paidAmount;
    @Column
    private String date;
    @Column
    private String time;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderDetailEO> orderdetails;

    public List<OrderDetailEO> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(final List<OrderDetailEO> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public CustomerEO getCustomer() {
        return customer;
    }

    public void setCustomer(final CustomerEO customer) {
        this.customer = customer;
    }

    public EmployeeEO getEmployee() {
        return employee;
    }

    public void setEmployee(final EmployeeEO employee) {
        this.employee = employee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public int getModeId() {
        return modeId;
    }

    public void setModeId(final int modeId) {
        this.modeId = modeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final Date orderDate) {
        this.orderDate = orderDate;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(final float amount) {
        this.amount = amount;
    }

    public float getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(final float paidAmount) {
        this.paidAmount = paidAmount;
    }

}
