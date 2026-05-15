package model;

import lombok.Data;

@Data

public class CancelOrderModel {
    private int track;

    public CancelOrderModel(int track) {
        this.track = track;
    }
}
