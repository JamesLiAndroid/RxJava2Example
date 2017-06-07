package com.shiliukeji.xc_lsy.rxjavalearning;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.Cloth;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.Clothes;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.DaggerTestComponent;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.Shoe;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.TestComponent;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.TestModule;
import com.shiliukeji.xc_lsy.rxjavalearning.pdagger.WhiteCloth;

import javax.inject.Inject;
import javax.inject.Named;

public class DaggerTestActivity extends AppCompatActivity {

    private TextView tvDagger;
    private TextView tvRB;
    private TextView tvSingle;


    @Inject
    Cloth cloth;

    @Inject
    @Named("blue")
    Cloth clothBlue;

    @Inject
    @Named("red")
    Cloth clothRed;

    /**
     * 使用自定义注解注入内容
     */
    @Inject
    @WhiteCloth
    Cloth clothWhite;

    /**
     * 当Component在所拥有的Module类中找不到依赖需求方需要类型的提供方法时,
     * Dagger2就会检查该需要类型的有没有用@Inject声明的构造方法,有则用该构造方法创建一个.
     */
    @Inject
    Shoe shoe;

    /**
     * 依赖于其它依赖类的案例
     */
    @Inject
    Clothes clothes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);

        tvDagger = (TextView) findViewById(R.id.tv_dagger_data);
        tvRB = (TextView) findViewById(R.id.tv_rb);
        tvSingle = (TextView) findViewById(R.id.tv_is_equal);

        // 完成注入！
        TestComponent component = DaggerTestComponent.builder().testModule(new TestModule()).build();
        component.inject(DaggerTestActivity.this);

        tvDagger.setText("我现在有"+cloth.getColor()+"物料," + shoe.getShoe() + "鞋子, "+clothes);
        tvRB.setText(clothRed + "::" + clothBlue+"::"+clothWhite);

        tvSingle.setText("两个类是否Equal？" + clothes.getCloth().equals(cloth));
    }
}
