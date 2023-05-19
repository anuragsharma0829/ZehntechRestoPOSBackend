package com.restopos.models;

import javax.persistence.*;

@Entity
@Table(name = "Guest_info")
public class GuestInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int G_id;
    private int adults;
    private int childs;
    private int  table_num;
    private String area;
    private String waiter_name;
    private int table_id;

    public GuestInfo() {
    }

    public GuestInfo(int g_id, int adults, int childs, int table_num, String area, String waiter_name,int table_id) {
        G_id = g_id;
        this.adults = adults;
        this.childs = childs;
        this.table_num = table_num;
        this.area = area;
        this.waiter_name = waiter_name;
        this.table_id=table_id;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getG_id() {
        return G_id;
    }

    public void setG_id(int g_id) {
        G_id = g_id;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChilds() {
        return childs;
    }

    public void setChilds(int childs) {
        this.childs = childs;
    }

    public int getTable_num() {
        return table_num;
    }

    public void setTable_num(int table_num) {
        this.table_num = table_num;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

}
