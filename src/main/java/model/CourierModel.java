package model;

import lombok.Data;

@Data

public class CourierModel {
    private String login;
    private String password;
    private String firstName;

    public CourierModel(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }
}