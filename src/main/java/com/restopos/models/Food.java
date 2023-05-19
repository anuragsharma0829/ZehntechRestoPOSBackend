package com.restopos.models;

import javax.persistence.*;


@Entity
@Table(name = "Foods",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "food_name")
        })
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int food_id;

    private String food_name;
    private String food_type;
    private int rate;
    private String food_icon;


    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getFood_icon() {
        return food_icon;
    }

    public void setFood_icon(String food_icon) {
        this.food_icon = food_icon;
    }


    public Food() {
    }

    public Food(int food_id, String food_name, String food_type, int rate, String food_icon) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.food_type = food_type;
        this.rate = rate;
        this.food_icon = food_icon;
    }
}
