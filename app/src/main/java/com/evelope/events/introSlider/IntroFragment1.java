package com.evelope.events.introSlider;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.evelope.events.R;


public class IntroFragment1 extends Fragment {

    private Context context = null; //TODO MAKE SETTER
    private String fileName;
    private final String FILE_NAME = "FILE_name";

    /**
     * Factory method for this fragment class. Constructs a new fragment.
     */
    public static IntroFragment1 create(String fileName, Context context) {
        IntroFragment1 fragment = new IntroFragment1();
        fragment.setFileName(fileName);
        fragment.setContext(context);
        return fragment;
    }

    public IntroFragment1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        fileName = getArguments().getString(FILE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_intro_fragment1, container, false);



                final VideoView video = (VideoView) rootView.findViewById(R.id.Videoview_IntroVideo);

                video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        video.requestFocus();
                        video.start();
                    }
                });
                video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        video.start();
                    }
                });

        String path = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.dummie_video;
                video.setVideoURI(Uri.parse(path));


        return rootView;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}