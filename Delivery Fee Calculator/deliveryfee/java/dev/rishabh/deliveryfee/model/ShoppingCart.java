/**
 * This is an Model/Entity Class used to handle Request Input.
 * 
 * @author Rishabh Garg
 */
package dev.rishabh.deliveryfee.model;

public class ShoppingCart {

    private double cart_value;
    private int delivery_distance;
    private int amount_of_items;
    private String time;
    private double delivery_fee;

    /**
     * These are getters and setters for private data variables.
     */
    public double getCart_value() {
        return this.cart_value;
    }

    public void setCart_value(double cart_value) {
        this.cart_value = cart_value;
    }

    public int getDelivery_distance() {
        return this.delivery_distance;
    }

    public void setDelivery_distance(int delivery_distance) {
        this.delivery_distance = delivery_distance;
    }

    public int getAmount_of_items() {
        return this.amount_of_items;
    }

    public void setAmount_of_items(int amount_of_items) {
        this.amount_of_items = amount_of_items;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getDelivery_fee() {
        return this.delivery_fee;
    }

    public void setDelivery_fee(double delivery_fee) {
        this.delivery_fee = delivery_fee;
    }

    @Override
    public String toString() {
        return "{" +
            " cart_value='" + getCart_value() + "'" +
            ", delivery_distance='" + getDelivery_distance() + "'" +
            ", amount_of_items='" + getAmount_of_items() + "'" +
            ", time='" + getTime() + "'" +
            ", delivery_fee='" + getDelivery_fee() + "'" +
            "}";
    }
}