package com.example.kuybeer26092016.lionmonitoringdemo.models;

/**
 * Created by KuyBeer26092016 on 28/9/2559.
 */

public class Mis_history {
    private Integer mo_id,mc_id,mo_act,status;
    private String mc_name,mo_pram,start_datetime,end_datetime,mo_min,mo_max;

    public String getMo_min() {
        return mo_min;
    }

    public void setMo_max(String mo_max) {
        this.mo_max = mo_max;
    }

    public String getMo_max() {
        return mo_max;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getMo_id() {
        return mo_id;
    }

    public Integer getMc_id() {
        return mc_id;
    }

    public Integer getMo_act() {
        return mo_act;
    }

    public String getMc_name() {
        return mc_name;
    }

    public String getMo_pram() {
        return mo_pram;
    }

    public String getStart_datetime() {
        return start_datetime;
    }

    public String getEnd_datetime() {
        return end_datetime;
    }
}
