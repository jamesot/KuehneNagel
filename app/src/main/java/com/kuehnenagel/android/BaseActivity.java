package com.kuehnenagel.android;

/**
 * Created by stephineosoro on 27/09/2016.
 */


        import android.support.annotation.ColorRes;
        import android.support.annotation.DimenRes;
        import android.support.annotation.IntegerRes;
        import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public int dimen(@DimenRes int resId) {
        return (int) getResources().getDimension(resId);
    }

    public int color(@ColorRes int resId) {
        return getResources().getColor(resId);
    }

    public int integer(@IntegerRes int resId) {
        return getResources().getInteger(resId);
    }
}