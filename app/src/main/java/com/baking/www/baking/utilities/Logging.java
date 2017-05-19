package com.baking.www.baking.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


final public class Logging {

    static Toast toast;

    private Logging() {

    }

    public static void log(String s) {
        Log.d("TAG", s);
    }

    public static void shortToast(Context context, String s) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void longToast(Context context, String s) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.show();

    }
}