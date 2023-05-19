package com.restopos.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordertable")
public class OrderTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer order_id;
    private Integer table_id;
    private String  wiater_name;
    private Long total_payment;
    private Date created_date;
    private int table_no;

    public int getTable_no() {
        return table_no;
    }

    public void setTable_no(int table_no) {
        this.table_no = table_no;
    }

    public Long getTotal_payment() {
        return total_payment;
    }

    public void setTotal_payment(Long total_payment) {
        this.total_payment = total_payment;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getTable_id() {
        return table_id;
    }

    public void setTable_id(Integer table_id) {
        this.table_id = table_id;
    }

    public String getWiater_name() {
        return wiater_name;
    }

    public void setWiater_name(String wiater_name) {
        this.wiater_name = wiater_name;
    }




    public OrderTable(Integer order_id, Integer table_id, String wiater_name, Long total_payment,Date created_date, int table_no) {
        this.order_id = order_id;
        this.table_id = table_id;
        this.wiater_name = wiater_name;
        this.total_payment=total_payment;
        this.created_date=created_date;
        this.table_no=table_no;

    }

    public OrderTable() {
    }
}
