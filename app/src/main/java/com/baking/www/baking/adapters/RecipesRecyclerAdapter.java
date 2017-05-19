package com.baking.www.baking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.www.baking.DataFetchers.dataModels.Recipe;
import com.baking.www.baking.DataFetchers.dataModels.Recipes;
import com.baking.www.baking.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Dell on 10/05/2017.
 */

public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesRecyclerAdapter.RecipesViewHolder> {
    private OnRecipeItemSelected onRecipeItemSelected;
    private Recipes recipes;
    private RecipesViewHolder recipesViewHolder;
    private Context context;

    public RecipesRecyclerAdapter(OnRecipeItemSelected onRecipeItemSelected, Recipes recipes) {
        this.onRecipeItemSelected = onRecipeItemSelected;
        this.recipes = recipes;
        setHasStableIds(true);

    }

    @Override
    public RecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recipe, parent, false);
        recipesViewHolder = new RecipesViewHolder(view);
        return recipesViewHolder;
    }

    @Override
    public void onBindViewHolder(RecipesViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        recipesViewHolder.setDatat(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }


    public interface OnRecipeItemSelected {
        void onItemSelected(int itemPosition);
    }

    class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView recipeImage;
        private TextView recipeName, servingsNum, ingrediengtsNum, stepsNum;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            recipeImage = (ImageView) itemView.findViewById(R.id.img_recipe);
            recipeName = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            servingsNum = (TextView) itemView.findViewById(R.id.tv_servings);
            ingrediengtsNum = (TextView) itemView.findViewById(R.id.tv_ingredients);
            stepsNum = (TextView) itemView.findViewById(R.id.tv_steps);

            itemView.setOnClickListener(this);
        }

        public void setDatat(Recipe recipe) {
            if (!recipe.getImage().isEmpty())
                Picasso.with(context).load(recipe.getImage())
                        .placeholder(R.drawable.img_placeholder).error(R.drawable.img_placeholder).into(recipeImage);

            recipeName.setText(recipe.getName());
            servingsNum.setText(context.getString(R.string.servings_holder, recipe.getServings()));
            ingrediengtsNum.setText(context.getString(R.string.ingredients_holder, recipe.getIngredients().size()));
            stepsNum.setText(context.getString(R.string.steps_holder, recipe.getSteps().size()));
        }

        @Override
        public void onClick(View v) {
            onRecipeItemSelected.onItemSelected(getAdapterPosition());
        }
    }
}
