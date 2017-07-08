package com.unicornheight.bakingapp.modules.details;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.unicornheight.bakingapp.modules.player.PlayerFragment;
import com.unicornheight.bakingapp.modules.widget.CakeWidgetProvider;
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


    private StepAdapter mStepAdapter;
    @Bind(R.id.cakeTitle)
    protected TextView mCakeTitle;
    @Bind(R.id.step_list)
    protected RecyclerView mStepList;
    Cake cake;
    private int cake_id;
    private String cakeName;
    private String image_url;
    private List<CakesResponseIngredients> Ingredients;
    private List<CakesResponseSteps> steps;
    private boolean mTwoPane;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private String setWidgetIngredient;


    public ListItemDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
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
        checkFavorite();
        if (rootView.findViewById(R.id.player_container) != null) {
            mTwoPane = true;
        }

        if (Ingredients != null) {
            for (CakesResponseIngredients ingredients : Ingredients) {
                String cakeIngredient = ingredients.getIngredient();
                String cakeQuantity = String.valueOf(ingredients.getQuantity());
                String cakeMeasure = String.valueOf(ingredients.getMeasure());
                setWidgetIngredient += ("- " + cakeIngredient + " " + "(" + cakeQuantity + " " + cakeMeasure + ")") + "\n";
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

    public void addFavorite() {
        editor = getActivity().getSharedPreferences(getString(R.string.pref_name), 0).edit();
        editor.putString(getString(R.string.pref_key), cakeName);
        editor.putString(getString(R.string.pref_ingredient), setWidgetIngredient);
        editor.apply();

        Intent intent = new Intent(getContext(), CakeWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{R.xml.cake_widget_info});
        getContext().sendBroadcast(intent);
    }

    public void checkFavorite() {
        sharedPreferences = getActivity().getSharedPreferences(getString(R.string.pref_name), 0);
        if (sharedPreferences.contains(getString(R.string.pref_key))) {
            String cakeDetail = sharedPreferences.getString(getString(R.string.pref_key), "");
            if (cakeDetail.equals(cakeName)) {
                ((DetailActivity) getActivity()).checkResult(true);
            }
        } else {
            //set favourite to first cake Recipe as default
            addFavorite();
        }
    }

    private StepAdapter.OnCakeClickListener mCakeClickListener = new StepAdapter.OnCakeClickListener() {
        @Override
        public void onClick(View v, CakesResponseSteps cake, int position) {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(CakePlayer.CAKE, cake);
                PlayerFragment fragment = new PlayerFragment();
                fragment.setArguments(arguments);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.player_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(getContext(), CakePlayer.class);
                intent.putExtra(CakePlayer.CAKE, cake);
                startActivity(intent);
            }
        }
    };

}