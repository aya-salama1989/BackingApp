package com.baking.www.baking.DataFetchers.Fetchers;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baking.www.baking.R;
import com.baking.www.baking.utilities.Logging;


/**
 * Created by hp on 5/27/2016.
 */
public class BaseDataFetcher {

    protected String BaseURL;
    protected Context mContext;
    protected BaseDataFetcherListener mListener;
    private RequestQueue mRequestQueue;

    public BaseDataFetcher(Context context, BaseDataFetcherListener mListener) {
        this.mContext = context;
        BaseURL = mContext.getResources().getString(R.string.base_url);
        this.mListener = mListener;
    }

    public BaseDataFetcher(Context context) {
        this.mContext = context;
        BaseURL = mContext.getResources().getString(R.string.base_url);

    }

    public RequestQueue getReQ() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            Logging.log("Volley Error: " + volleyError.toString() + " Error Message: "
                    + volleyError.getMessage() + " Error cause: " + volleyError.getCause());
            if (mListener != null) {
                mListener.onConnectionFailure();
            }
        }
    };


    public void retryPolicy(JsonObjectRequest jsonObjReq) {
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void retryPolicy(JsonArrayRequest jsonArrReq) {
        jsonArrReq.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void retryPolicy(StringRequest stringRequest) {
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}
