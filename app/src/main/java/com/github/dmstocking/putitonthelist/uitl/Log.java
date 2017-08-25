package com.github.dmstocking.putitonthelist.uitl;

public interface Log {

    void v(String tag, String msg);

    void v(String tag, String msg, Throwable tr);

    void d(String tag, String msg);

    void d(String tag, String msg, Throwable tr);

    void i(String tag, String msg);

    void i(String tag, String msg, Throwable tr);

    void w(String tag, String msg);

    void w(String tag, String msg, Throwable tr);

    void e(String tag, String msg);

    void e(String tag, String msg, Throwable tr);

    void wtf(String tag, String msg);

    void wtf(String tag, String msg, Throwable tr);
}
