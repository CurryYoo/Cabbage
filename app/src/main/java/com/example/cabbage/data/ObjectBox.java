package com.example.cabbage.data;

import android.content.Context;

import io.objectbox.BoxStore;

/**
 * Author: xiemugan
 * Date: 2020/6/21
 * Description:
 **/
public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
    }

    public static BoxStore get() { return boxStore; }
}
