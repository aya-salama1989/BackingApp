package com.baking.www.baking.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baking.www.baking.DataFetchers.dataModels.Step;
import com.baking.www.baking.DataFetchers.dataModels.Steps;
import com.baking.www.baking.R;

/**
 * Created by Dell on 16/05/2017.
 */

public class StepsRecyclerAdapter extends RecyclerView.Adapter<StepsRecyclerAdapter.StepsViewHolder> {

    private Context context;
    private Steps steps;
    private OnStepClickListener onStepClickListener;
    private StepsViewHolder stepsViewHolder;

    public StepsRecyclerAdapter(Steps steps, OnStepClickListener onStepClickListener) {
        this.steps = steps;
        this.onStepClickListener = onStepClickListener;
        setHasStableIds(true);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_recipe_step, parent, false);
        stepsViewHolder = new StepsViewHolder(v);
        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        stepsViewHolder.setData(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public interface OnStepClickListener {
        void onStepItemClick(int itemPosition);
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView stepTitle;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepTitle = (TextView) itemView.findViewById(R.id.tv_step);
            itemView.setOnClickListener(this);
        }

        public void setData(Step step) {
            stepTitle.setText(step.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            onStepClickListener.onStepItemClick(getAdapterPosition());
        }
    }
}
