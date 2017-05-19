package com.baking.www.baking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.fragments.MainFragment;

public class MainActivity extends AppCompatActivity
        implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setToolBar() {

    }

    private void initViews() {
        setToolBar();
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frag_holder, mainFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Recipe recipe) {

        if (recipe != null) {
            Intent intent = new Intent(this, RecipeDetailsActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }

    }
}
