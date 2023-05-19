package com.restopos.models;

public class TabelDetails {
    private String Area;
    private String Sum;

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSum() {
        return Sum;
    }

    public void setSum(String sum) {
        Sum = sum;
    }

    public TabelDetails(String area, String sum) {
        Area = area;
        Sum = sum;
    }

    public TabelDetails() {
    }
}
