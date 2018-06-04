package com.nagarro.pointofsaleapplication.dto;

/**
 * @author transaction reports of particular employee
 */
public class Transaction {

    private int orderId;
    private String date;
    private String time;
    private float amount;
    private float startBalance;
    private float endingBalance;

    public Transaction() {

    }

    public Transaction(final int orderId, final String date, final String time, final float amount,
            final float startBalance, final float endingBalance) {
        this.orderId = orderId;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.startBalance = startBalance;
        this.endingBalance = endingBalance;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(final int orderId) {
        this.orderId = orderId;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(final float amount) {
        this.amount = amount;
    }

    public float getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(final float startBalance) {
        this.startBalance = startBalance;
    }

    public float getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(final float endingBalance) {
        this.endingBalance = endingBalance;
    }

}
