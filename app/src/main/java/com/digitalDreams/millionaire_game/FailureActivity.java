package com.digitalDreams.millionaire_game;

import static com.digitalDreams.millionaire_game.GameActivity2.continueGame;
import static com.digitalDreams.millionaire_game.GameActivity2.hasOldWinningAmount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class FailureActivity extends AppCompatActivity {
    long time;
    String countdownTime="9000";
    public RewardedVideoAd mRewardedVideoAd;
    private boolean clicked = false;
    public static InterstitialAd interstitialAd;
    String modeValue = "";
    TextView replay_level;
    int animationCount = 1;
    RelativeLayout hex;
    TextView failureTxt;
    TextView noThankBtn;
    RelativeLayout continueBtn;
    RelativeLayout r;
    CountDownTimer countDownTimer;

    RelativeLayout new_games;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure);

        replay_level = findViewById(R.id.replay);
        hex = findViewById(R.id.hex);
        failureTxt = findViewById(R.id.failureTxt);
        noThankBtn = findViewById(R.id.no_thanks);
        continueBtn = findViewById(R.id.continue_game);
        r = findViewById(R.id.btn_forAnim);
        new_games = findViewById(R.id.new_games);
        new_games.setVisibility(View.GONE);

        continueBtn.setVisibility(View.GONE);
        failureTxt.setVisibility(View.GONE);
        new MyAnimation(hex);
        // hex.setVisibility(View.GONE);
        noThankBtn.setVisibility(View.GONE);
        r.setVisibility(View.GONE);

        animateWebContainer(replay_level);

        Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadding);
        continueBtn.startAnimation(aniFade);
        r.startAnimation(aniFade);

        loadInterstialAd();


        // mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);

        //loadVideoAd();

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String languageCode = sharedPreferences.getString("language", "en");
        int endcolor = sharedPreferences.getInt("end_color", getResources().getColor(R.color.purple_dark));
        int startColor = sharedPreferences.getInt("start_color", getResources().getColor(R.color.purple_500));
        int cardBackground = sharedPreferences.getInt("card_background", 0x219ebc);

        String mode = sharedPreferences.getString("game_mode", "0");
        //TextView modeTxt = findViewById(R.id.mode);
        if (mode.equals("0")) {
            //  modeTxt.setText("Mode: Normal");
            modeValue = "normal";
        } else {
            // modeTxt.setText("Mode: Hard");
            modeValue = "hard";
        }
        checkScore();
        new MyAnimation(r);
        LinearLayout rootview = findViewById(R.id.rootview);

        new Particles(this, rootview, R.layout.image_xml, 20);
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{startColor, endcolor});

        rootview.setBackgroundDrawable(gd);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE);

        new_games.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(interstitialAd.isLoaded()){

                    interstitialAd.show();
                }else{
                    Intent i = new Intent(FailureActivity.this, CountDownActivity.class);
                    // startActivity(i);

                    startActivity(i);
                    finish();
                }

                interstitialAd.setAdListener(new AdListener(){
                    @Override
                    public void onAdClosed() {
                        Intent i = new Intent(FailureActivity.this, CountDownActivity.class);
                        // startActivity(i);

                        startActivity(i);
                        finish();
                        super.onAdClosed();
                    }
                });


                        // Do something after 5s = 5000ms

            }
        });


        noThankBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showInterstitial();

                if (CountDownActivity.mMediaPlayer != null) {
                    CountDownActivity.mMediaPlayer.stop();
                }

                Intent intent = new Intent(FailureActivity.this, PlayDetailsActivity.class);

                // intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                boolean hasOldWinningAmount = getIntent().getBooleanExtra("hasOldWinningAmount", false);

                    if (hasOldWinningAmount) {
                        intent.putExtra("hasOldWinningAmount", true);

                    }

                intent.putExtra("noThanks", true);

                startActivity(intent);
                finish();


//                final Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startActivity(intent);
//                        finish();
//                        // Do something after 5s = 5000ms
//                        //buttons[inew][jnew].setBackgroundColor(Color.BLACK);
//                    }
//                }, 7000);

            }
        });

        TextView countDownTxt = findViewById(R.id.count_down_text);
        time = Long.parseLong(countdownTime);
        countDownTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long l) {
                int sec = (int) (l / 1000);
                int seconds = (sec % 3600) % 60;
                countDownTxt.setText("" + seconds);
            }

            @Override
            public void onFinish() {
            }
        };

        countDownTimer.start();


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Loadeddd", "onClick");
//                GameActivity2.continueGame=true;
//                finish();

                if (!isNetworkConnected()) {
                    startActivity(new Intent(FailureActivity.this, PlayDetailsActivity.class));
                    finish();
                    Toast.makeText(FailureActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();
                }


                clicked = true;

                if (CountDownActivity.mRewardedVideoAd == null) {
                    showInterstitial();

                } else if (!CountDownActivity.mRewardedVideoAd.isLoaded()) {
                    showInterstitial();
                    Log.i("mRewardedVideoAd", "Not LOaded");
                    CountDownActivity.mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());
                } else {
                    Log.i("mRewardedVideoAd", "LOaded");
                    CountDownActivity.mRewardedVideoAd.show();

                }

            }
        });
        try{
        if (GameActivity2.mRewardedVideoAd == null) {
            mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
                @Override
                public void onRewardedVideoAdLoaded() {
                    if (clicked) {
                        showRewardedVideo();
                    }
                }

                @Override
                public void onRewardedVideoAdOpened() {

                }

                @Override
                public void onRewardedVideoStarted() {

                }

                @Override
                public void onRewardedVideoAdClosed() {
                    continueGame = true;
                    finish();
                }

                @Override
                public void onRewarded(com.google.android.gms.ads.reward.RewardItem rewardItem) {

                }

                @Override
                public void onRewardedVideoAdLeftApplication() {

                }

                @Override
                public void onRewardedVideoAdFailedToLoad(int i) {

                }

                @Override
                public void onRewardedVideoCompleted() {
                    continueGame = true;
                    finish();
                }

            });
        } else {
            GameActivity2.mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
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
                    CountDownActivity.mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());

                    continueGame = true;
                    finish();
                }

                @Override
                public void onRewarded(com.google.android.gms.ads.reward.RewardItem rewardItem) {

                }

                @Override
                public void onRewardedVideoAdLeftApplication() {

                }

                @Override
                public void onRewardedVideoAdFailedToLoad(int i) {

                }

                @Override
                public void onRewardedVideoCompleted() {
                    CountDownActivity.mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());

                    continueGame = true;
                    finish();
                }

            });
        }
    }catch(Exception e){
            e.printStackTrace();
        }

        loadInterstialAd();
        if(GameActivity2.mRewardedVideoAd==null) {
            loadVideoAd();
        }

        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                Intent intent = new Intent(FailureActivity.this,PlayDetailsActivity.class);
                startActivity(intent);
                finish();
            }

//            @Override
//            public void onAdFailedToLoad(LoadAdError loadAdError) {
//                super.onAdFailedToLoad(loadAdError);
//                Intent intent = new Intent(FailureActivity.this,PlayDetailsActivity.class);
//                startActivity(intent);
//                finish();
//            }
        });
    }

    private void animateWebContainer(View view){
        AlphaAnimation fadeIn=new AlphaAnimation(0,1);
        final AnimationSet set = new AnimationSet(false);
        Animation inFromLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);

        set.addAnimation(inFromLeft);
        set.addAnimation(fadeIn);
        set.setDuration(1000);
        set.setStartOffset(100);
        view.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationCount++;

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(animationCount == 2){
                    failureTxt.setVisibility(View.VISIBLE);
                    animateWebContainer(failureTxt);


                }else if(animationCount == 3){

                    continueBtn.setVisibility(View.VISIBLE);
                    r.setVisibility(View.VISIBLE);
                    animateWebContainer(r);
                    animateWebContainer(continueBtn);


                }else if(animationCount == 5){

                    new_games.setVisibility(View.VISIBLE);
                    //r.setVisibility(View.VISIBLE);
                   // animateWebContainer(new_games);
                    animateMobileContainer(new_games);


                }else if(animationCount == 6){
                    noThankBtn.setVisibility(View.VISIBLE);
                   // animateWebContainer(noThankBtn);




                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }


    private void animateMobileContainer(View view){
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(2000);

//        AlphaAnimation fadeIn=new AlphaAnimation(0,1);
//        final AnimationSet set = new AnimationSet(false);
//        Animation inFromRigth = new TranslateAnimation(
//                Animation.RELATIVE_TO_PARENT, 1.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f,
//                Animation.RELATIVE_TO_PARENT, 0.0f);

//        set.addAnimation(inFromRigth);
//        set.addAnimation(fadeIn);
//        set.setDuration(1000);
//        set.setStartOffset(100);
        view.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animationCount++;

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                noThankBtn.setVisibility(View.VISIBLE);
                animateWebContainer(noThankBtn);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//        ExitDialog dialog = new ExitDialog(this);
//        dialog.show();
//        Window window = dialog.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onResume() {
//        if(GameActivity2.mRewardedVideoAd==null) {
//            mRewardedVideoAd.resume(this);
//        }else {
//           GameActivity2. mRewardedVideoAd.resume(this);
//
//        }


        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       try{
           if(GameActivity2.mRewardedVideoAd==null) {
               mRewardedVideoAd.pause(this);
           }else {
               GameActivity2.mRewardedVideoAd.pause(this);

           }

           if( CountDownActivity.mMediaPlayer!=null){
               CountDownActivity.mMediaPlayer.pause();
           }
       }catch (Exception e){}

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(GameActivity2.mRewardedVideoAd==null) {
//            mRewardedVideoAd.destroy(this);
//        }else {
//            GameActivity2.mRewardedVideoAd.destroy(this);
//        }
    }

    private void loadVideoAd() {
        // Load a reward based video ad
       if(GameActivity2.mRewardedVideoAd != null){
           GameActivity2.loadVideoAd();
       }
      ///  GameActivity2.mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());
      //  mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());
    }

    public void showRewardedVideo() {

        if (mRewardedVideoAd.isLoaded()) {
            if(CountDownActivity.mMediaPlayer!=null) {
                CountDownActivity.mMediaPlayer.stop();
            }
            mRewardedVideoAd.show();

        }
    }

    public void showRewardedVideo2() {
        ///GameActivity2.mRewardedVideoAd.loadAd("ca-app-pub-4696224049420135/7768937909", new AdRequest.Builder().build());

        if (GameActivity2.mRewardedVideoAd.isLoaded()) {
            if(CountDownActivity.mMediaPlayer!=null) {
                CountDownActivity.mMediaPlayer.stop();
            }
            GameActivity2.mRewardedVideoAd.show();

        }
    }


    private void loadInterstialAd(){
        interstitialAd = new InterstitialAd(this) ;
        interstitialAd.setAdUnitId (getResources().getString(R.string.interstitial_adunit) ) ;
        interstitialAd.loadAd(new AdRequest.Builder().addTestDevice("9D16E23BB90EF4BFA204300CCDCCF264").build());
       // mInterstitialAd.loadAd(adRequest);

//        interstitialAd.setAdListener(new AdListener() {
//            public void onAdLoaded() {
//                showInterstitial();
//            }
//        });
    }

    private void checkScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        String highscore = sharedPreferences.getString("high_score", "0");
        String username = sharedPreferences.getString("username", "");
        String country = sharedPreferences.getString("country", "");
        String country_flag = sharedPreferences.getString("country_flag", "");
        String oldAmountWon = sharedPreferences.getString("amountWon", "");
        int h = Integer.parseInt(highscore);
        String score = GameActivity2.amountWon;
        ///score = score.substring(1);
        score = score.replace(",", "");
        int s = Integer.parseInt(score);


        if(hasOldWinningAmount){

            String amountWon = GameActivity2.amountWon.replace("$","").replace(",","");
            oldAmountWon = oldAmountWon.replace("$","").replace(",","");
            s = Integer.parseInt(amountWon) + Integer.parseInt(oldAmountWon);



        }


        if (s > h) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("high_score", String.valueOf(s));
            editor.apply();


        }
        Map userDetails = new HashMap();
        userDetails.put("username",username);
        userDetails.put("country",country);
        userDetails.put("country_flag",country_flag);



       try{
           sendScoreToSever(String.valueOf(s),  userDetails);
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void sendScoreToSever(String score,Map<String,String> userDetails) {
        String url = getResources().getString(R.string.base_url)+"/post_score.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response", "response " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String, String> param = new HashMap<>();
                JSONObject country_json = new JSONObject();
               try{
                   country_json.put("name",userDetails.get("country"));
                   country_json.put("url", userDetails.get("country_flag"));
               }catch (Exception e){
                   e.printStackTrace();
               }
                param.put("score", score);
                param.put("username", userDetails.get("username"));
                param.put("country", userDetails.get("country"));
                param.put("country_json", country_json.toString());
                param.put("country_flag", userDetails.get("country_flag"));
                param.put("avatar",  getAvatar());
                param.put("device_id",getDeviceId(FailureActivity.this));
                param.put("game_type","millionaire");
                param.put("mode",modeValue);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private String getAvatar(){
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        String avatar = sharedPreferences.getString("avatar","");
        return avatar;
    }

    public static String getDeviceId(Context context) {

        String id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return id;
    }


    private void showInterstitial() {
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();

        }

        loadInterstialAd();
    }



    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadInterstialAd();
    }
}