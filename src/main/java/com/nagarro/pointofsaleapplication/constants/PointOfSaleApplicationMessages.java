package com.nagarro.pointofsaleapplication.constants;

public class PointOfSaleApplicationMessages {

    /**
     * successfull addition of cash message
     */
    public static final String SUCCESSFULL_ADDED_CASH_MESSAGE = "successfully added cash into cash drawer";
    /**
     * error on adding cash to cash drawer
     */
    public static final String ERROR_ADDED_CASH_MESSAGE = "Error adding cash as no employee found to whom this"
            + " drawer belongs";

    /**
     * successfully update cash drawer cash message
     */
    public static final String SUCCESSFULL_UPDATE_CASH_MESSAGE = "successfully update cash of cash drawer";
    /**
     * error updating cash drawer cash message
     */
    public static final String ERROR_UPDATING_CASH_MESSAGE = "Error updating cash drawer's as no cash drawer found "
            + "for the passed employee id";
    /**
     * employee not found error message
     */
    public static final String EMPLOYEE_NOT_FOUND_MESSAGE = "employee not found for respective employee id";
    /**
     * product stock empty message
     */
    public static final String PRODUCT_STOCK_EMPTY_MESSAGE = " stock is less than demanded";
    /**
     * customer not found error message
     */
    public static final String CUSTOMER_NOT_FOUND_MESSAGE = "employee not found for respective customer id";
    /**
     * successful addition of order message
     */
    public static final String SUCCESSFULLY_ADDED_ORDER_MESSAGE = "successfully added order message";
    /**
     * Internal server error message
     */
    public static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    /**
     * successfully fetched orders message
     */
    public static final String SUCCESSFULLY_FETCHED_ORDERS_MESSAGE = "Successfully fetched orders";
    /**
     * fetching orders error message
     */
    public static final String ERROR_FETCHING_ORDERS_MESSAGE = "No order found for respective employee id";

    /**
     * successfully delete order message
     */
    public static final String SUCCESSFULLY_DELETED_ORDER_MESSAGE = "Successfully deleted order with id ";
    /**
     * error deleting order with given id message
     */
    public static final String ERROR_DELETING_ORDER_MESSAGE = "No order found with id ";
    /**
     * successfully get customers message
     */
    public static final String SUCCESFULLY_FETCHED_CUSTOMERS = "Successfully get customers";
    /**
     * No customers found message
     */
    public static final String NO_CUSTOMER_FOUND = "No customer found";
    /**
     * successfully fetched cash drawer records
     */
    public static final String SUCCESFULLY_FETCHED_DRAWER = "Successfully fetched cash drawer entries";
    /**
     * wrong api key message
     */
    public static final String WRONG_API_KEY_MESSAGE = "Wrong Api key";
    /**
     * error adding message
     */
    public static final String ERROR_ADDING_CUSTOMER_MESSAGE = "Error on adding customer";
    /**
     * no employee found message
     */
    public static final String NO_EMPLOYEE_FOUND_MESSAGE = "No employee found";

    /**
     * error adding employee message
     */
    public static final String ERROR_ADDING_EMPLOYEE_MESSAGE = "Error adding employee";
    /**
     * invalid credentials message
     */
    public static final String INVALID_CREDENTIALS_MESSAGE = "Invalid username or password";
    /**
     * no cash in the cash drawer message
     */
    public static final String NO_CASH_MESSAGE = "No cash drawer entry yet";
    /**
     * error adding product message
     */
    public static final String ERROR_ADDING_PRODUCT_MESSAGE = "Error adding product";

}
