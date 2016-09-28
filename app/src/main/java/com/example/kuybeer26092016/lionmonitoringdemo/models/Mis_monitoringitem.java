package com.example.kuybeer26092016.lionmonitoringdemo.models;

/**
 * Created by KuyBeer26092016 on 27/9/2559.
 */

public class Mis_monitoringitem {
    private String mc_name;
    private String mc_id;
    public Act mo_act;
    public class Act{
        private String act_1,act_2,act_3,act_4;

        public String getAct_3() {
            return act_3;
        }

        public String getAct_4() {
            return act_4;
        }

        public String getAct_1() {
            return act_1;
        }

        public String getAct_2() {
            return act_2;
        }
    }
    public String getMc_name() {
        return mc_name;
    }

    public String getMc_id() {
        return mc_id;
    }

    public Act getMo_act() {
        return mo_act;
    }
}
