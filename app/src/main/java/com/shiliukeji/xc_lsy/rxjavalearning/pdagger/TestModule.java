package com.shiliukeji.xc_lsy.rxjavalearning.pdagger;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * 提供Cloth对象
 * Created by XC_LSY on 2017/6/6.
 */

@Module
public class TestModule {

    // @Singleton 注解
    @PerActivity
    @Provides
    public Cloth getCloth() {
        Cloth cloth = new Cloth();
        cloth.setColor("RED");
        return cloth;
    }

    @Provides
    @Named("red")
    public Cloth getClothRed() {
        Cloth cloth = new Cloth();
        cloth.setColor("红色");
        return cloth;
    }

    @Provides
    @Named("blue")
    public Cloth getClothBlue() {
        Cloth cloth = new Cloth();
        cloth.setColor("蓝色");
        return cloth;
    }


    @Provides
    @WhiteCloth
    public Cloth getClothWhite() {
        Cloth cloth = new Cloth();
        cloth.setColor("白色");
        return cloth;
    }
    // @Singleton 注解
    @PerActivity
    @Provides
    public Clothes getClothes(Cloth cloth) {
        return new Clothes(cloth);
    }
}
