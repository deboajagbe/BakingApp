

package com.unicornheight.bakingapp.modules.home;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.base.BaseActivity;
import com.unicornheight.bakingapp.di.components.DaggerCakeComponent;
import com.unicornheight.bakingapp.di.module.CakeModule;
import com.unicornheight.bakingapp.modules.details.DetailActivity;
import com.unicornheight.bakingapp.modules.details.ListItemDetailFragment;
import com.unicornheight.bakingapp.modules.home.adapter.CakeAdapter;
import com.unicornheight.bakingapp.modules.player.PlayerFragment;
import com.unicornheight.bakingapp.mvp.model.Cake;
import com.unicornheight.bakingapp.mvp.presenter.CakePresenter;
import com.unicornheight.bakingapp.mvp.view.MainView;
import com.unicornheight.bakingapp.utilities.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements MainView {

    @Bind(R.id.cake_list) protected RecyclerView mCakeList;
    @Inject protected CakePresenter mPresenter;
    private CakeAdapter mCakeAdapter;
    private boolean mTwoPane;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        if (findViewById(R.id.listitem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        initializeList();
        loadCakes();
    }

    private void loadCakes() {
        if(NetworkUtils.isNetAvailable(this)) {
            mPresenter.getCakes();
        } else {
            mPresenter.getCakesFromDatabase();
        }
    }

    private void initializeList() {
        mCakeList.setHasFixedSize(true);
        mCakeList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCakeAdapter = new CakeAdapter(getLayoutInflater());
        mCakeAdapter.setCakeClickListener(mCakeClickListener);
        mCakeList.setAdapter(mCakeAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reload:
                loadCakes();
                return true;
            case R.id.action_about:
                showAbout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAbout() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("Developed by Debo Ajagbe on 24/05/2017. \n\nGet the code and follow me on github!")
                .setCancelable(true)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Get Code", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://github.com/deboajagbe"));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    @Override
    protected void resolveDaggerDependency() {
        DaggerCakeComponent.builder()
        .applicationComponent(getApplicationComponent())
        .cakeModule(new CakeModule(this))
        .build().inject(this);
    }

    @Override
    public void onCakeLoaded(List<Cake> cakes) {
        mCakeAdapter.addCakes(cakes);
    }

    @Override
    public void onShowDialog(String message) {
        showDialog(message);
    }

    @Override
    public void onHideDialog() {
        hideDialog();
    }

    @Override
    public void onShowToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClearItems() {
        mCakeAdapter.clearCakes();
    }

    private CakeAdapter.OnCakeClickListener mCakeClickListener = new CakeAdapter.OnCakeClickListener() {
        @Override
        public void onClick(View v, Cake cake, int position) {
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(DetailActivity.CAKE, cake);
                ListItemDetailFragment fragment = new ListItemDetailFragment();
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.listitem_detail_container, fragment)
                        .commit();
            } else {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(DetailActivity.CAKE, cake);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, v, "cakeImageAnimation");
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        }

    };
}
