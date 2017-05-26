package com.baking.www.baking.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    private View v;

    public StepsRecyclerAdapter(Steps steps, OnStepClickListener onStepClickListener) {
        this.steps = steps;
        this.onStepClickListener = onStepClickListener;
        setHasStableIds(true);
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType) {
            case VIEW_TYPES.Header:
                v = inflater.inflate(R.layout.item_steps_header, parent, false);
                break;
            case VIEW_TYPES.Normal:
                v = inflater.inflate(R.layout.item_recipe_step, parent, false);
                break;
            default:
        }
        stepsViewHolder = new StepsViewHolder(v);

        return stepsViewHolder;
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        if (position == 0) {
            stepsViewHolder.setHeader();
        } else {
            stepsViewHolder.setData(steps.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return VIEW_TYPES.Header;
        else
            return VIEW_TYPES.Normal;
    }

    public interface OnStepClickListener {
        void onStepItemClick(int itemPosition);
    }

    class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        TextView stepTitle;
        TextView headerTxtVue;

        public StepsViewHolder(View itemView) {
            super(itemView);
            stepTitle = (TextView) itemView.findViewById(R.id.tv_step);
            headerTxtVue = (TextView) itemView.findViewById(R.id.tv_step_header);
            itemView.setOnClickListener(this);
        }

        public void setData(Step step) {
            stepTitle.setText(step.getShortDescription());
        }

        public void setHeader() {
            if (headerTxtVue != null)
                headerTxtVue.setText(context.getString(R.string.ingredients));
        }

        @Override
        public void onClick(View v) {
            onStepClickListener.onStepItemClick(getAdapterPosition());
            if (v.isEnabled()) {
                v.setBackgroundColor(Color.parseColor("#3F51B5"));
            } else {
                v.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(Color.parseColor("#3F51B5"));
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(Color.WHITE);
            }
            return false;
        }
    }

    private class VIEW_TYPES {
        public static final int Header = 1;
        public static final int Normal = 2;
//        public static final int Footer = 3;
    }
}
