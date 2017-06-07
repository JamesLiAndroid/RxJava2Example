package com.shiliukeji.xc_lsy.rxjavalearning.pdagger;

import com.shiliukeji.xc_lsy.rxjavalearning.DaggerTestActivity;

import dagger.Component;

/**
 * 注入的接口方法
 * Created by XC_LSY on 2017/6/6.
 */
// @Singleton 注解
@PerActivity
@Component(modules = TestModule.class)
public interface TestComponent {
    void inject(DaggerTestActivity activity);
}
