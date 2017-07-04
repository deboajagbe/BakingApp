

package com.unicornheight.bakingapp.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    public static final String CAKE = "cake";

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showBackArrow();

        if (savedInstanceState == null) {
            // Create the detail player_fragment and add it to the activity
            // using a player_fragment transaction.
            Bundle arguments = new Bundle();
            Intent parentIntent = getIntent();

            if(parentIntent.hasExtra(DetailActivity.CAKE)){
                arguments.putSerializable(DetailActivity.CAKE, parentIntent.getSerializableExtra(DetailActivity.CAKE));
            }

            ListItemDetailFragment fragment = new ListItemDetailFragment();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
