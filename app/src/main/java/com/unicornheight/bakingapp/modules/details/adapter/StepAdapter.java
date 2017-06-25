package com.unicornheight.bakingapp.modules.details.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.mvp.model.CakesResponseSteps;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by deboajagbe on 6/25/17.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<CakesResponseSteps> mCakeList = new ArrayList<>();

    public StepAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.step_list, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bind(mCakeList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCakeList.size();
    }

    public void addCakes(List<CakesResponseSteps> cakes) {
        mCakeList.addAll(cakes);
        notifyDataSetChanged();
    }

    public void clearCakes() {
        mCakeList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.step_name) protected TextView mCakeTitle;
        String mIndex;

        private Context mContext;
        private CakesResponseSteps mCake;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(CakesResponseSteps cake) {
            mCake = cake;
            mCakeTitle.setText(getAdapterPosition() + ". " + cake.getShortDescription());
        }

        @Override
        public void onClick(View v) {
            if (mCakeClickListener != null) {
                mCakeClickListener.onClick(mCakeTitle, mCake, getAdapterPosition());
            }
        }
    }

    public void setCakeClickListener(OnCakeClickListener listener) {
        mCakeClickListener = listener;
    }

    private OnCakeClickListener mCakeClickListener;

    public interface OnCakeClickListener {

        void onClick(View v, CakesResponseSteps cake, int position);
    }
}
