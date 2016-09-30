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
    public Pram mo_pram;
    public class Pram{
        private String pram_1,pram_2,pram_3,pram_4;

        public String getPram_3() {
            return pram_3;
        }

        public String getPram_4() {
            return pram_4;
        }

        public String getPram_1() {
            return pram_1;
        }

        public String getPram_2() {
            return pram_2;
        }
    }
    public Min mo_min;
    public class Min{
        private String min_1,min_2,min_3,min_4;

        public String getMin_1() {
            return min_1;
        }

        public String getMin_2() {
            return min_2;
        }

        public String getMin_3() {
            return min_3;
        }

        public String getMin_4() {
            return min_4;
        }
    }
    public Max mo_max;
    public class Max{
        private String max_1,max_2,max_3,max_4;

        public String getMax_1() {
            return max_1;
        }

        public String getMax_2() {
            return max_2;
        }

        public String getMax_3() {
            return max_3;
        }

        public String getMax_4() {
            return max_4;
        }
    }
    public Unit mo_unit;
    public class Unit{
        public String unit_1,unit_2,unit_3,unit_4;

        public String getUnit_1() {
            return unit_1;
        }

        public String getUnit_2() {
            return unit_2;
        }

        public String getUnit_3() {
            return unit_3;
        }

        public String getUnit_4() {
            return unit_4;
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

    public Pram getMo_pram() {
        return mo_pram;
    }

    public Min getMo_min() {
        return mo_min;
    }

    public Unit getMo_unit() {
        return mo_unit;
    }

    public Max getMo_max() {
        return mo_max;
    }
}
