package com.baking.www.baking.utilities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.baking.www.baking.R;

/**
 * Created by Dell on 01/06/2017.
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.main_frag_holder);
        setContentView(frameLayout);
    }
}

