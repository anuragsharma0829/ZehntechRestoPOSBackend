package com.restopos.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "Tempdata")
public class Temptable<T> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer temp_id;

    private Integer t_teble_id;
    private String t_foodname;
    private String t_fooftype;
    private String t_qty;
    private Integer t_rate;
    private Integer order_id;

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getTemp_id() {
        return temp_id;
    }

    public void setTemp_id(Integer temp_id) {
        this.temp_id = temp_id;
    }





    public Integer getT_teble_id() {
        return t_teble_id;
    }

    public void setT_teble_id(Integer t_teble_id) {
        this.t_teble_id = t_teble_id;
    }

    public String getT_foodname() {
        return t_foodname;
    }

    public void setT_foodname(String t_foodname) {
        this.t_foodname = t_foodname;
    }

    public String getT_fooftype() {
        return t_fooftype;
    }

    public void setT_fooftype(String t_fooftype) {
        this.t_fooftype = t_fooftype;
    }

    public String getT_qty() {
        return t_qty;
    }

    public void setT_qty(String t_qty) {
        this.t_qty = t_qty;
    }

    public Integer getT_rate() {
        return t_rate;
    }

    public void setT_rate(Integer t_rate) {
        this.t_rate = t_rate;
    }

    public Temptable(Integer temp_id, Integer t_oredrid, Integer t_teble_id, String t_foodname, String t_fooftype, String t_qty, Integer t_rate) {
        this.temp_id = temp_id;

        this.t_teble_id = t_teble_id;
        this.t_foodname = t_foodname;
        this.t_fooftype = t_fooftype;
        this.t_qty = t_qty;
        this.t_rate = t_rate;
    }

    public Temptable() {
    }
}
