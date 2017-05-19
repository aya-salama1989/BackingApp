package com.baking.www.baking.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.R;
import com.baking.www.baking.adapters.IngredientsRecyclerAdapter;
import com.baking.www.baking.adapters.StepsRecyclerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailsFragment extends
        Fragment implements IngredientsRecyclerAdapter.OnIngredientClickListener, StepsRecyclerAdapter.OnStepClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RECIPE_KEY = "recipe";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Recipe mRecipe;
    private RecyclerView ingredientsRV, stepsRV;
    private View v;

    private OnFragmentInteractionListener mListener;
    private Steps mSteps;
    private Ingredients mIngredients;
    private IngredientsRecyclerAdapter ingredientsRecyclerAdapter;
    private StepsRecyclerAdapter stepsRecyclerAdapter;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    public static RecipeDetailsFragment newInstance(Recipe recipe) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(RECIPE_KEY, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(RECIPE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        mRecipe = getArguments().getParcelable(RECIPE_KEY);
        mIngredients = mRecipe.getIngredients();
        mSteps = mRecipe.getSteps();
        initViews();
        return v;
    }

    private void initViews() {
        LinearLayoutManager ingredientsLayoutManager = new LinearLayoutManager(getActivity());
        ingredientsRecyclerAdapter = new IngredientsRecyclerAdapter(this, mIngredients);
        stepsRecyclerAdapter = new StepsRecyclerAdapter(mSteps, this);
        LinearLayoutManager stepsLayoutManager = new LinearLayoutManager(getActivity());

        ingredientsRV = (RecyclerView) v.findViewById(R.id.rv_ingredients);
        ingredientsRV.setLayoutManager(ingredientsLayoutManager);
        ingredientsRV.setAdapter(ingredientsRecyclerAdapter);

        stepsRV = (RecyclerView) v.findViewById(R.id.rv_steps);
        stepsRV.setLayoutManager(stepsLayoutManager);
        stepsRV.setAdapter(stepsRecyclerAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
    public void onIngredientItemClick(int itemPosition) {

    }

    @Override
    public void onStepItemClick(int itemPosition) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
