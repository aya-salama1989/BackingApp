package com.baking.www.baking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.Fetchers.RecipesDataFetcher;
import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Recipes;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.RecipesRecyclerAdapter;
import com.baking.www.baking.utilities.Logging;

import static com.baking.www.baking.utilities.InternetConnectivity.checkOnlineState;


public class MainFragment extends Fragment implements RecipesRecyclerAdapter.OnRecipeItemSelected,
        RecipesDataFetcher.RecipesFetcherDataListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private RecipesDataFetcher recipesDataFetcher;


    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;
    private RecipesRecyclerAdapter recipesRecyclerAdapter;
    private Recipes mRecipes;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        mRecipes = new Recipes();
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_recipes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recipesRecyclerAdapter = new RecipesRecyclerAdapter(this, mRecipes);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipesRecyclerAdapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Recipe recipe) {
        if (mListener != null) {
            mListener.onFragmentInteraction(recipe);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(int itemPosition) {
        mListener.onFragmentInteraction(mRecipes.get(itemPosition));
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mRecipes.isEmpty()){
            getData();
        }
    }

    @Override
    public void onConnectionFailure() {
        if (!checkOnlineState(getActivity())) {
            Logging.shortToast(getActivity(), getString(R.string.internet_error));
        } else {
            Logging.shortToast(getActivity(), getString(R.string.server_error));
        }
    }

    private void getData() {
        recipesDataFetcher = new RecipesDataFetcher(getActivity(), this);
        recipesDataFetcher.getRecipes();
    }

    @Override
    public void onConnectionDone(Recipes recipes) {
        mRecipes.addAll(recipes);
        recipesRecyclerAdapter.notifyDataSetChanged();
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Recipe recipe);
    }
}
