package com.github.dmstocking.putitonthelist.uitl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidLog implements Log {

    @Inject
    public AndroidLog() {
    }

    @Override
    public void v(String tag, String msg) {
        android.util.Log.v(tag, msg);
    }

    @Override
    public void v(String tag, String msg, Throwable tr) {
        android.util.Log.v(tag, msg, tr);
    }

    @Override
    public void d(String tag, String msg) {
        android.util.Log.d(tag, msg);
    }

    @Override
    public void d(String tag, String msg, Throwable tr) {
        android.util.Log.d(tag, msg, tr);
    }

    @Override
    public void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

    @Override
    public void i(String tag, String msg, Throwable tr) {
        android.util.Log.i(tag, msg, tr);
    }

    @Override
    public void w(String tag, String msg) {
        android.util.Log.w(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        android.util.Log.w(tag, msg, tr);
    }

    @Override
    public void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        android.util.Log.e(tag, msg, tr);
    }

    @Override
    public void wtf(String tag, String msg) {
        android.util.Log.wtf(tag, msg);
    }

    @Override
    public void wtf(String tag, String msg, Throwable tr) {
        android.util.Log.wtf(tag, msg, tr);
    }
}
