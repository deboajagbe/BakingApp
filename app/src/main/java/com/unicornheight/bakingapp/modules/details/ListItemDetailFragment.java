package com.unicornheight.bakingapp.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.modules.details.adapter.StepAdapter;
import com.unicornheight.bakingapp.modules.player.CakePlayer;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.model.CakesResponseIngredients;
import com.unicornheight.bakingapp.mvp.model.CakesResponseSteps;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by deboajagbe on 6/25/17.
 */

public class ListItemDetailFragment extends Fragment {


    //this should call the recipe data so you can send data
    private StepAdapter mStepAdapter;
    @Bind(R.id.cakeTitle) protected TextView mCakeTitle;
    @Bind(R.id.step_list) protected RecyclerView mStepList;
    Cake cake;
    private int cake_id;
    private String cakeName;
    private String image_url;
    private List<CakesResponseIngredients> Ingredients;
    private List<CakesResponseSteps> steps;

    /**
     * Mandatory empty constructor for the player_fragment manager to instantiate the
     * player_fragment (e.g. upon screen orientation changes).
     */
    public ListItemDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Load the content specified by the player_fragment
            // arguments. In a real-world scenario, use a Loader
            cake = (Cake) getArguments().getSerializable(DetailActivity.CAKE);
            cake_id = cake.getId();
            cakeName = cake.getName();
            image_url = cake.getImage();
            Ingredients = cake.getIngredients();
            steps = cake.getSteps();

            getActivity().setTitle(cakeName);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cake_details, container, false);
        ButterKnife.bind(this, rootView);
        initializeList();

        if (Ingredients != null) {
            for (CakesResponseIngredients ingredients :  Ingredients) {
                String cakeIngredient = ingredients.getIngredient();
                String cakeQuantity = String.valueOf(ingredients.getQuantity());
                String cakeMeasure = String.valueOf(ingredients.getMeasure());
                mCakeTitle.append("- " + cakeIngredient + " " + "(" + cakeQuantity + " " + cakeMeasure + ")" + "\n");
            }
        }
        mStepAdapter.addCakes(steps);
        return rootView;
        }


    private void initializeList() {
        mStepList.setHasFixedSize(true);
        mStepList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mStepAdapter = new StepAdapter(getActivity().getLayoutInflater());
        mStepAdapter.setCakeClickListener(mCakeClickListener);
        mStepList.setAdapter(mStepAdapter);
    }
        private StepAdapter.OnCakeClickListener mCakeClickListener = new StepAdapter.OnCakeClickListener() {
        @Override
        public void onClick(View v, CakesResponseSteps cake, int position) {
            Intent intent = new Intent(getContext(), CakePlayer.class);
            intent.putExtra(CakePlayer.CAKE, cake);
            startActivity(intent);
        }
    };


}
