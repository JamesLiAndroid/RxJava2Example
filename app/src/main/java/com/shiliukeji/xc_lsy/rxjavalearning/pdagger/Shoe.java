package com.shiliukeji.xc_lsy.rxjavalearning.pdagger;

import javax.inject.Inject;

/**
 * 需要注入的实体类
 * Created by XC_LSY on 2017/6/6.
 */

public class Shoe {
    private String shoe = "6666";

    @Inject
    public Shoe() {

    }

    public String getShoe() {
        return shoe;
    }

    public void setShoe(String shoe) {
        this.shoe = shoe;
    }

    @Override
    public String toString() {
        return shoe + "鞋子";
    }
}
