package com.digitalDreams.millionaire_game;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class WrongAnswerDialog extends Dialog {
    Context context;
    RewardedVideoAd  mRewardedVideoAd;
    MediaPlayer mMediaPlayer;
    ImageView cancel_icon;
    public WrongAnswerDialog(@NonNull Context context, RewardedVideoAd rewardedVideoAd, MediaPlayer mMediaPlayer) {

        super(context);
        this.context = context;
        this.mRewardedVideoAd = rewardedVideoAd;
        this.mMediaPlayer = mMediaPlayer;

    }

    RelativeLayout continue_btn,close_dialog;
    RelativeLayout give_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_answer_layout);
        continue_btn = findViewById(R.id.play_again);
        give_up = findViewById(R.id.give_up);
        close_dialog =findViewById(R.id.close_dialog);
        cancel_icon =  findViewById(R.id.cancel_icon);
        close_dialog.setVisibility(View.GONE);





        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("language","en");
        int endcolor = sharedPreferences.getInt("end_color",context.getResources().getColor(R.color.purple_dark));
        int startColor = sharedPreferences.getInt("start_color",context.getResources().getColor(R.color.purple_500));
        String game_level = sharedPreferences.getString("game_level","1");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        RelativeLayout bg = findViewById(R.id.rootview);

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {startColor,endcolor});

        bg.setBackgroundDrawable(gd);

        cancel_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();



                Intent intent = new Intent(getContext(), FailureActivity.class);
                context.startActivity(intent);

            }
        });

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();



                Intent intent = new Intent(getContext(), FailureActivity.class);
                context.startActivity(intent);

            }
        });

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.isOnline(context)) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.pause();
                    }
                    if (mRewardedVideoAd != null) {
                        mRewardedVideoAd.show();
                    }
                    dismiss();
                }
//                else if(mRewardedVideoAd != null && mRewardedVideoAd.isLoaded()){
//                    if (mMediaPlayer != null) {
//                        mMediaPlayer.pause();
//                    }
//
//                        mRewardedVideoAd.show();
//
//                    dismiss();
//                }
                else {
                    Toast.makeText(context,"Connect to internet and try again",Toast.LENGTH_LONG).show();

                }


            }
        });
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                if( mMediaPlayer != null){
                    mMediaPlayer.start();
                }

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(context,"Error: Loading video Ad failed, Please connect to the internet",Toast.LENGTH_LONG).show();

                dismiss();

                Intent intent = new Intent(getContext(), FailureActivity.class);
                    context.startActivity(intent);



            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        give_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();

                Intent intent = new Intent(getContext(), FailureActivity.class);
                context.startActivity(intent);



            }
        });
    }
}
