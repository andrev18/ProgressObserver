package com.demo.qucoapps.progressobserversample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements ProgressView {

    View pb;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb);

        simulateApiCall();
    }

    @Override
    public void show() {
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        pb.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }

    private void simulateApiCall(){
        Observable.just("Hello World")
                .delay(4, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ProgressObserver<String>(MainActivity.this) {
                    @Override
                    public void doOnSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);

                    }

                    @Override
                    public void doOnNext(@NonNull String s) {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void doOnError(@NonNull Throwable e) {

                    }

                    @Override
                    public void doOnComplete() {

                    }
                });
    }
}
