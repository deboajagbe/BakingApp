package com.unicornheight.bakingapp.modules.player;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.base.BaseActivity;


public class CakePlayer extends BaseActivity {

    public static final String CAKE = "cake";
    PlayerFragment fragment;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        showBackArrow();

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Intent parentIntent = getIntent();
            fragment = new PlayerFragment();

            if(parentIntent.hasExtra(CakePlayer.CAKE)){
                arguments.putSerializable(CakePlayer.CAKE, parentIntent.getSerializableExtra(CakePlayer.CAKE));
            }

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.player_container, fragment)
                    .commit();
        }

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_cake_player;
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


    @Override
    protected void onStart() {
        super.onStart();
        if (fragment.mExoPlayer == null) {
            fragment.initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (fragment.mExoPlayer == null) {
            fragment.initializePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fragment.mExoPlayer != null) {
            releasePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fragment.mExoPlayer != null) {
            releasePlayer();
        }
    }

    public void releasePlayer() {
        fragment.mExoPlayer.stop();
        fragment.mExoPlayer.release();
        fragment.mExoPlayer = null;
    }
}
