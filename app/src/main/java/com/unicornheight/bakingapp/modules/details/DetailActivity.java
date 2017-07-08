package com.unicornheight.bakingapp.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    public static final String CAKE = "cake";
    ListItemDetailFragment fragment;
    public boolean IsFavorite;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showBackArrow();

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Intent parentIntent = getIntent();

            if(parentIntent.hasExtra(DetailActivity.CAKE)){
                arguments.putSerializable(DetailActivity.CAKE, parentIntent.getSerializableExtra(DetailActivity.CAKE));
            }

            fragment = new ListItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.listitem_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail;
    }

    public void checkResult(boolean ans) {
        if (ans) {
            IsFavorite = true;
        } else {
            IsFavorite = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.add_favorite:
                IsFavorite = !IsFavorite;
                Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                invalidateOptionsMenu();
                return true;

            case R.id.remove_favorite:
                IsFavorite = !IsFavorite;
                Toast.makeText(this, "Added as Favorite", Toast.LENGTH_SHORT).show();
                fragment.addFavorite();
                invalidateOptionsMenu();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (IsFavorite) {
            menu.findItem(R.id.remove_favorite).setVisible(false);
            menu.findItem(R.id.add_favorite).setVisible(true);
        } else {
            menu.findItem(R.id.remove_favorite).setVisible(true);
            menu.findItem(R.id.add_favorite).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
