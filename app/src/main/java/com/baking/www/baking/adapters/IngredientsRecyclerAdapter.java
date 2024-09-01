package com.baking.www.baking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.baking.www.baking.DataFetchers.dataModels.Ingredient;
import com.baking.www.baking.DataFetchers.dataModels.Ingredients;
import com.baking.www.baking.R;

/**
 * Created by Dell on 16/05/2017.
 */

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.IngredientsViewHolder>{
    private Context context;
    private OnIngredientClickListener onIngredientClickListener;
    private Ingredients ingredients;
    private IngredientsViewHolder ingredientsViewHolder;

    public IngredientsRecyclerAdapter(OnIngredientClickListener onIngredientClickListener, Ingredients ingredients) {
        this.onIngredientClickListener = onIngredientClickListener;
        this.ingredients = ingredients;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.item_recipe_ingredient, parent, false);
        ingredientsViewHolder = new IngredientsViewHolder(v);
        return ingredientsViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        ingredientsViewHolder.setData(ingredients.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public interface OnIngredientClickListener{
        void onIngredientItemClick(int itemPosition);
    }

    class IngredientsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView ingredientTxtVue;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ingredientTxtVue = (TextView) itemView.findViewById(R.id.tv_ingredient);
        }

        private void setData(Ingredient ingredient){
            ingredientTxtVue.setText(context.getString(R.string.ingredient_holder,
                    ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredient()));
        }

        @Override
        public void onClick(View v) {
            onIngredientClickListener.onIngredientItemClick(getAdapterPosition());
        }
    }
}
