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
 * @author chetanmahajan Employee entity creates table in the database with the given fields
 *
 */
@Entity
@Table(name = PointOfSaleApplicationConstants.EMPLOYEE_TABLE_NAME)
@NamedQueries({ @NamedQuery(name = EmployeeEO.GET_ALL_EMPLOYEES, query = "select employee from EmployeeEO employee"),
        @NamedQuery(name = EmployeeEO.GET_EMPLOYEE, query = "select employee from EmployeeEO employee where "
                + "username=:username and password=:password"),
        @NamedQuery(name = EmployeeEO.GET_EMPLOYEE_BY_ID, query = "select employee from EmployeeEO employee where "
                + "id=:id") })
public class EmployeeEO {

    public static final String GET_ALL_EMPLOYEES = "getAllEmployees";
    public static final String GET_EMPLOYEE = "getEmployeeByUsername";
    public static final String GET_EMPLOYEE_BY_ID = "getEmployeeById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String employeeCode;
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

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(final String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
