package com.demo.qucoapps.progressobserversample;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by avlad on 24.10.2017.
 */

public abstract class ProgressObserver<TYPE> implements Observer<TYPE> {

    private final WeakReference<ProgressView> progressViewWeakReference;

    public ProgressObserver(ProgressView progressView) {
        this.progressViewWeakReference = new WeakReference<ProgressView>(progressView);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        doOnSubscribe(d);
        ProgressView progressView = progressViewWeakReference.get();

        if (progressView != null) {
            progressView.show();
        }

    }

    @Override
    public void onNext(@NonNull TYPE type) {
        doOnNext(type);
        ProgressView progressView = progressViewWeakReference.get();

        if (progressView != null) {
            progressView.show();
        }

    }

    @Override
    public void onError(@NonNull Throwable e) {
        doOnError(e);
        ProgressView progressView = progressViewWeakReference.get();

        if (progressView != null) {
            progressView.show();
        }

    }

    @Override
    public void onComplete() {
        doOnComplete();

        ProgressView progressView = progressViewWeakReference.get();

        if (progressView != null) {
            progressView.hide();
        }

    }


    public abstract void doOnSubscribe(@NonNull Disposable d);


    public abstract void doOnNext(@NonNull TYPE type);


    public abstract void doOnError(@NonNull Throwable e);

    public abstract void doOnComplete();


}
