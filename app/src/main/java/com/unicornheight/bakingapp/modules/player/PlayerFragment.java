package com.unicornheight.bakingapp.modules.player;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.unicornheight.bakingapp.helper.ImageHandler;
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
        mDetails.setText(cakeDetail);
        initializePlayer();
        return rootView;
    }

    public void initializePlayer() {
        Uri mediaUri = null;
        if (cakeVideoUrl != null && !cakeVideoUrl.equals("") && !cakeVideoUrl.isEmpty()) {
            mPlayerView.setVisibility(View.VISIBLE);
            mediaUri = Uri.parse(cakeVideoUrl);
        }else if(thumbUrl != null && !thumbUrl.equals("") && !thumbUrl.isEmpty()){
            Glide.with(getActivity()).load(thumbUrl)
                    .asBitmap()
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.temp)
                    .into((mImageView));
        }else {
            mImageView.setImageResource(R.drawable.temp);
        }
        if (mediaUri != null) {
            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);

                String userAgent = Util.getUserAgent(getContext(), "Steps :");
                MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.seekTo(0);
                mExoPlayer.setPlayWhenReady(false);
            }
        }
    }

}