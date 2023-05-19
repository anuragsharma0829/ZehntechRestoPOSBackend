package com.restopos.models;

//import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Tables")
public class Tables {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int table_no;
    private String Area;
    private String of_sitting;
    private Boolean Table_status;
    private String waiter_name;

    private String createdBy;
    private Date created_date;




    public Tables(int id, int table_no, String area, String of_sitting, Boolean table_status,String createdBy,Date created_date,String waiter_name) {
        this.id = id;
        this.table_no = table_no;
        Area = area;
        this.of_sitting = of_sitting;
        Table_status = table_status;
        this.createdBy=createdBy;
        this.created_date=created_date;
        this.waiter_name=waiter_name;

    }
//
    public Tables(int i, String ball, String s, boolean b, String john) {
    }
//
    public int getId() {
        return id;
    }

    public String getWaiter_name() {
        return waiter_name;
    }

    public void setWaiter_name(String waiter_name) {
        this.waiter_name = waiter_name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Tables() {
    }

//    public Tables getId() {
//        return id;
//    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getOf_sitting() {
        return of_sitting;
    }

    public void setOf_sitting(String of_sitting) {
        this.of_sitting = of_sitting;
    }

    public Boolean getTable_status() {
        return Table_status;
    }

    public void setTable_status(Boolean table_status) {
        Table_status = table_status;
    }



}

