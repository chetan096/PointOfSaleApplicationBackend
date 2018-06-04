package com.nagarro.pointofsaleapplication.models;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.nagarro.pointofsaleapplication.constants.PointOfSaleApplicationConstants;

/**
 * @author chetanmahajan cash drawer entity creates cash drawer table in the db with the given fields
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.CASH_DRAWER_TABLE_NAME)
@NamedQueries({
        @NamedQuery(name = CashDrawerEO.GET_DRAWER_BY_ID, query = "select drawer from CashDrawerEO drawer where "
                + "employee_id=:id and date=:date"),
        @NamedQuery(name = CashDrawerEO.GET_DRAWER_ALL_ENTRIES, query = "select drawer from CashDrawerEO drawer where "
                + "employee_id=:id"),
        @NamedQuery(name = CashDrawerEO.GET_DRAWER_ALL_ENTRIES_FOR_GIVEN_DATES, query = "select drawer from CashDrawerEO drawer where "
                + "employee_id=:id and updatedAt between :startDate and :endingDate") })
public class CashDrawerEO {

    public static final String GET_DRAWER_BY_ID = "getDrawerById";
    public static final String GET_DRAWER_ALL_ENTRIES = "getAllEntries";
    public static final String GET_DRAWER_ALL_ENTRIES_FOR_GIVEN_DATES = "getDrawerEntriesForGivenDate";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private float startBalance;
    @Column
    private float currentBalance;
    @Column
    private Date updatedAt;
    @OneToOne
    private EmployeeEO employee;

    @Column
    private String date;

    @Column
    private String lastUpdationTime;

    public String getLastUpdationTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(PointOfSaleApplicationConstants.TIME_FORMAT);
        this.lastUpdationTime = formatter.format(this.updatedAt);
        return this.lastUpdationTime;
    }

    public void setLastUpdationTime(final String lastUpdationTime) {
        this.lastUpdationTime = lastUpdationTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public float getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(final float startBalance) {
        this.startBalance = startBalance;
    }

    public float getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(final float currentBalance) {
        this.currentBalance = currentBalance;
    }

    public void setUpdatedAt(final Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public EmployeeEO getEmployee() {
        return employee;
    }

    public void setEmployee(final EmployeeEO employee) {
        this.employee = employee;
    }

}
