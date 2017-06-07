package com.shiliukeji.xc_lsy.rxjavalearning.pdagger;

/**
 * Created by XC_LSY on 2017/6/6.
 */

public class Clothes {
    private Cloth cloth;

    public Clothes(Cloth cloth) {
        this.cloth = cloth;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    @Override
    public String toString() {
        return cloth.getColor() + "大衣！";
    }
}

