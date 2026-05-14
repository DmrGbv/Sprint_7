package model;

import java.util.List;

public class OrderModel {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private int phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public OrderModel(String firstName, String lastName, String address, int metroStation,
                      int phone, int rentTime, String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public List<String> getColor() { return color; }
    public void setColor(List<String> color) { this.color = color; }
}
