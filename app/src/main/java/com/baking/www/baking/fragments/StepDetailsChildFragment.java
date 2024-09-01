package com.baking.www.baking.fragments;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.baking.www.baking.DataFetchers.dataModels.Step;
import com.baking.www.baking.R;
import com.baking.www.baking.utilities.Logging;
import com.squareup.picasso.Picasso;


/**
 * Created by Dell on 28/05/2017.
 */

@UnstableApi
public class StepDetailsChildFragment extends Fragment {

    private static final String ARG_STEP_DATA = "step";
    private static final String ARG_FRAGMENT_POSITION = "fragment_position";

    private View v;
    private ExoPlayer player;
    private PlayerView playerView;
    public static Fragment newInstance(int position, Step step) {
        StepDetailsChildFragment f = new StepDetailsChildFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_POSITION, position);
        args.putParcelable(ARG_STEP_DATA, step);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.content_step_details, container, false);
        initViews();
        return v;
    }

    private void initViews() {
        playerView = v.findViewById(R.id.playerView);
        TextView stepTV = v.findViewById(R.id.tv_step_description);
        TextView emptyVideoHolder = v.findViewById(R.id.no_video_holder);
        ImageView imageView = v.findViewById(R.id.imageView);

        Step step = getArguments().getParcelable(ARG_STEP_DATA);

        Logging.log(step.getVideoURL());
        if (step.getVideoURL() != null & !step.getVideoURL().isEmpty()) {
            initializePlayer(Uri.parse(step.getVideoURL()));
            playerView.setVisibility(View.VISIBLE);
            emptyVideoHolder.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        } else if (step.getThumbnailURL() != null & !step.getThumbnailURL().isEmpty()) {
            Picasso.with(getActivity()).load(step.getThumbnailURL())
                    .error(R.drawable.img_placeholder)
                    .placeholder(R.drawable.img_placeholder).into(imageView);
            playerView.setVisibility(View.INVISIBLE);
            emptyVideoHolder.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            playerView.setDefaultArtwork(ContextCompat.getDrawable(getContext(), R.drawable.img_placeholder));
            playerView.setVisibility(View.INVISIBLE);
            emptyVideoHolder.setVisibility(View.VISIBLE);
        }
        stepTV.setText(step.getDescription());
    }

    private void initializePlayer(Uri mediaUri) {
        player = new ExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);
        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(mediaUri);
        // Set the media item to be played.
        player.setMediaItem(mediaItem);
        // Prepare the player.
        player.prepare();
        // Start the playback.
        player.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if (player == null) return;
        player.stop();
        player.release();
    }
}