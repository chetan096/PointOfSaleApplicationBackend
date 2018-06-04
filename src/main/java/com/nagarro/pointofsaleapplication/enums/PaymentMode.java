package com.nagarro.pointofsaleapplication.enums;

/**
 * @author chetanmahajan
 * enums for payment methods
 */
public enum PaymentMode {

    NONE(0, "None"), CASH(1, "cash"), CARD(2, "Card");

    private int value;
    private String name;

    PaymentMode(final int value, final String name) {
        this.value = value;
        this.setName(name);
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public static String getName(final int value) {
        PaymentMode mode = null;
        for (PaymentMode item : PaymentMode.values()) {
            if (item.getValue() == value) {
                mode = item;
                break;
            }
        }
        return mode.getName();
    }

}