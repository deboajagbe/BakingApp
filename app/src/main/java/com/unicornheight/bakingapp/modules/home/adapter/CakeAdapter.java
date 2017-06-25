
package com.unicornheight.bakingapp.modules.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.helper.ImageHandler;
import com.unicornheight.bakingapp.mvp.model.Cake;

import org.eclipse.core.internal.resources.Resource;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CakeAdapter extends RecyclerView.Adapter<CakeAdapter.Holder> {

    private LayoutInflater mLayoutInflater;
    private List<Cake> mCakeList = new ArrayList<>();

    public CakeAdapter(LayoutInflater inflater) {
        mLayoutInflater = inflater;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.list_item_layout, parent, false);
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

    public void addCakes(List<Cake> cakes) {
        mCakeList.addAll(cakes);
        notifyDataSetChanged();
    }

    public void clearCakes() {
        mCakeList.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.cake_icon) protected ImageView mCakeIcon;
        @Bind(R.id.textview_title) protected TextView mCakeTitle;
        @Bind(R.id.textview_preview_description) protected TextView mCakePreviewDescription;

        private Context mContext;
        private Cake mCake;

        public Holder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void bind(Cake cake) {
            mCake = cake;
            mCakeTitle.setText(cake.getName());
            mCakePreviewDescription.setText("Servings: " + cake.getServings());

            if(cake.getImage() != null && !cake.getImage().isEmpty() && !cake.getImage().equals("")) {
                Glide.with(mContext).load(cake.getImage())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .placeholder(R.drawable.empty)
//                    .error(R.drawable.empty)
                        .into(new ImageHandler(mCakeIcon));

            }else{
                mCakeIcon.setImageResource(R.drawable.empty);
            }
        }

        @Override
        public void onClick(View v) {
            if (mCakeClickListener != null) {
                mCakeClickListener.onClick(mCakeIcon, mCake, getAdapterPosition());
            }
        }
    }

    public void setCakeClickListener(OnCakeClickListener listener) {
        mCakeClickListener = listener;
    }

    private OnCakeClickListener mCakeClickListener;

    public interface OnCakeClickListener {

        void onClick(View v, Cake cake, int position);
    }
}
