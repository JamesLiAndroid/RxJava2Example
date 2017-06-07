package com.shiliukeji.xc_lsy.rxjavalearning.pdagger;

/**
 * 需要注入的实体类
 * Created by XC_LSY on 2017/6/6.
 */

public class Cloth {
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "布料";
    }
}
