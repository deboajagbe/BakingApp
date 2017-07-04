package com.unicornheight.bakingapp.modules.player;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.unicornheight.bakingapp.R;
import com.unicornheight.bakingapp.mvp.model.CakesResponseSteps;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by deboajagbe on 6/25/17.
 */

public class PlayerFragment extends Fragment {

    @Bind(R.id.playerView) SimpleExoPlayerView mPlayerView;
    @Bind(R.id.full_detail) TextView mDetails;
    @Bind(R.id.image_temp) ImageView mImageView;
    private CakesResponseSteps steps;
    private String cakeVideoUrl;
    private String cakeDetail;
    private String thumbUrl;
    public SimpleExoPlayer mExoPlayer;


    public PlayerFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // Load the content specified by the player_fragment
            // arguments. In a real-world scenario, use a Loader
            steps = (CakesResponseSteps) getArguments().getSerializable(CakePlayer.CAKE);
            cakeVideoUrl = steps.getVideoURL();
            cakeDetail = steps.getDescription();
            thumbUrl = steps.getThumbnailURL();
            getActivity().setTitle(steps.getShortDescription());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.player_fragment, container, false);
        ButterKnife.bind(this, rootView);
        if (cakeVideoUrl != null && !cakeVideoUrl.equals("") && !cakeVideoUrl.isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(cakeVideoUrl));
        }else if(thumbUrl != null && !thumbUrl.equals("") && !thumbUrl.isEmpty()){
            mPlayerView.setVisibility(View.VISIBLE);
            initializePlayer(Uri.parse(thumbUrl));
        }else {
            mImageView.setImageResource(R.drawable.temp);
        }
        mDetails.setText(cakeDetail);

        return rootView;
    }

    public void initializePlayer(Uri mediaUri) {
        if (mediaUri != null) {
            if (mExoPlayer == null) {
                // Create an instance of the ExoPlayer.
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);


                // Prepare the MediaSource.
                String userAgent = Util.getUserAgent(getContext(), "Steps :");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.seekTo(0);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
    }

}