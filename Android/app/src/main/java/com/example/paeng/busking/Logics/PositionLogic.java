package com.example.paeng.busking.Logics;

public class PositionLogic {
    double lat;
    double lon;
    public PositionLogic(String target) {
        // 먼저 @ 의 인덱스를 찾는다 - 인덱스 값: 5
        int idx = target.indexOf("@");
        String tar1 = target.substring(0, idx);
        String tar2 = target.substring(idx+1);
        this.lat = Double.parseDouble(tar1);
        this.lon = Double.parseDouble(tar2);
    }

    public void load_data(double lat, double lon ) {
        lat = this.lat;
        lon = this.lon;
    }
}
