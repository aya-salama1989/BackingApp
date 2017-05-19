package com.baking.www.baking.utilities;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectivity {
    //TODO-1 : check reviewers recommendations,
    //TODO-2 : check permissions,
    //TODO-3 : check efresh logic
    public static boolean checkOnlineState(Context context) {
        ConnectivityManager CManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo NInfo = CManager.getActiveNetworkInfo();
        if (NInfo != null && NInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
