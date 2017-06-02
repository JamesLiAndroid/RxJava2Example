package com.shiliukeji.xc_lsy.rxjavalearning;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvContent;
    private Button btnHelloWorld;
    private Button btnEasyCode;
    private Button btnMap;
    private Button btnFlatMap;
    private Button btnFilter;
    private Button btnErrorHandler;
    private Button btnThreadSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        tvContent = (TextView) findViewById(R.id.tv_content);
        btnHelloWorld = (Button) findViewById(R.id.btn_hello);
        btnHelloWorld.setOnClickListener(this);
        btnEasyCode = (Button) findViewById(R.id.btn_easy_code);
        btnEasyCode.setOnClickListener(this);
        btnMap = (Button) findViewById(R.id.btn_map);
        btnMap.setOnClickListener(this);
        btnFlatMap = (Button) findViewById(R.id.btn_flat_map);
        btnFlatMap.setOnClickListener(this);
        btnFilter = (Button) findViewById(R.id.btn_filter);
        btnFilter.setOnClickListener(this);
        btnErrorHandler = (Button) findViewById(R.id.btn_error_handler);
        btnErrorHandler.setOnClickListener(this);
        btnThreadSwitch = (Button) findViewById(R.id.btn_thread_switch);
        btnThreadSwitch.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hello:
                helloWorld();
                break;
            case R.id.btn_easy_code:
                esayCode();
                break;
            case R.id.btn_map:
                map();
                break;
            case R.id.btn_flat_map:
                flatMap();
                break;
            case R.id.btn_filter:
                filter();
                break;
            case R.id.btn_error_handler:
                errorHandler();
                break;
            case R.id.btn_thread_switch:
                threadSwitch();
                break;
        }
    }

    private void threadSwitch() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("将在5s以后显示");
                SystemClock.sleep(5000);
                e.onNext("Android Best 10 Tips");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void errorHandler() {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("error:" + (1 / 0));
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("TAG","OnNext!");
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                        Log.d("TAG", "ERROR!");
                        Toast.makeText(MainActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG", "Complete!");
                    }
                });
    }

    private void filter() {
        Flowable.fromArray(1,2,3,4,5,68,21,32)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 33;
                    }
                })
                .take(2)
                /**
                 * doOnNext 允许我们在每次输出一个元素之前做一些额外的事情。
                 */
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("TAG", "显示："+integer+"添加");
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("TAG", ""+integer);
                        Toast.makeText(MainActivity.this, "::" + integer, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void flatMap() {
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        list.add(2);
        list.add(3);

        /**
         * FlatMap实际上解决的是Observable嵌套问题，通过在flatMap中进行操作，
         * 将一种Observable转化为另一种Obervable，
         * 将转化后的Observable交给Subscriber处理
         */
        Flowable.just(list)
                .flatMap(new Function<List<Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(List<Integer> integers) throws Exception {
                        return Flowable.fromIterable(integers);
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("TAG", "integer:"+integer);
                        Toast.makeText(MainActivity.this, ""+integer, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void map() {
        Flowable.just("mapMulti")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("TAG", "accept!");
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void esayCode() {
        Flowable.just("EasyWay").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("TAG", "EasyMode");
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void helloWorld() {
        // 创建Flowable
        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("Hello world RxJava2!");
                e.onComplete();
            }
        }, BackpressureStrategy.BUFFER);

        // 创建Subscriber
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                Log.d("TAG", "onSubscribe!");
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(String s) {
                Log.d("TAG", "onNext!");
                tvContent.setText(s);
            }

            @Override
            public void onError(Throwable t) {
                Log.d("TAG", "onError!");
            }

            @Override
            public void onComplete() {
                Log.d("TAG", "onComplete!");
                Toast.makeText(MainActivity.this, "onCompleted!", Toast.LENGTH_LONG).show();
            }
        };
        // 组装，执行！
        flowable.subscribe(subscriber);
    }
}
