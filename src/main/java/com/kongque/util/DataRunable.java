package com.kongque.util;

import java.util.function.Consumer;

public class DataRunable<T> implements  Runnable {


    private T t;

    private Consumer<T> consumer;


    public DataRunable(){
    }

    public DataRunable(T t,Consumer<T> c){
        this();
        this.t=t;this.consumer=c;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    @Override
    public void run() {
        consumer.accept(t);
    }
}
