package com.baking.www.baking.fragments;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baking.www.baking.DataFetchers.dataModels.Step;
import com.baking.www.baking.R;
import com.baking.www.baking.utilities.Logging;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dell on 28/05/2017.
 */

public class StepDetailsChildFragment extends Fragment {

    private static final String ARG_STEP_DATA = "step";
    private static final String ARG_FRAGMENT_POSITION = "fragment_position";
    @BindView(R.id.playerView)
    SimpleExoPlayerView simpleExoPlayerView;
    @BindView(R.id.tv_step_description)
    TextView stepTV;
    @BindView(R.id.no_video_holder)
    TextView emptyVideoHolder;

    @BindView(R.id.imageView)
    ImageView imageView;

    private View v;
    private SimpleExoPlayer mExoPlayer;

    public static Fragment newInstance(int position, Step step) {
        StepDetailsChildFragment f = new StepDetailsChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_POSITION, position);
        args.putParcelable(ARG_STEP_DATA, step);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_step_details, container, false);
        ButterKnife.bind(this, v);
        initViews();
        return v;
    }

    private void initViews() {
        Step step = getArguments().getParcelable(ARG_STEP_DATA);
        Logging.log(step.getVideoURL());
        if (step.getVideoURL() != null & !step.getVideoURL().isEmpty()) {
            initializePlayer(Uri.parse(step.getVideoURL()));
            simpleExoPlayerView.setVisibility(View.VISIBLE);
            emptyVideoHolder.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        } else if (step.getThumbnailURL() != null & !step.getThumbnailURL().isEmpty()) {
            Picasso.with(getActivity()).load(step.getThumbnailURL())
                    .error(R.drawable.img_placeholder)
                    .placeholder(R.drawable.img_placeholder).into(imageView);
            simpleExoPlayerView.setVisibility(View.INVISIBLE);
            emptyVideoHolder.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            simpleExoPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(),
                    R.drawable.img_placeholder));
            simpleExoPlayerView.setVisibility(View.INVISIBLE);
            emptyVideoHolder.setVisibility(View.VISIBLE);
        }
        stepTV.setText(step.getDescription());
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "Backing");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mExoPlayer != null)
            releasePlayer();

    }

    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }
}
