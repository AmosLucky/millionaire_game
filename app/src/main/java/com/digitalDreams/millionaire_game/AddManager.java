//package com.digitalDreams.millionaire_game;
//import android.app.Activity;
//import android.content.Context;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//
//import com.google.android.gms.ads.AdError;
//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.FullScreenContentCallback;
//import com.google.android.gms.ads.LoadAdError;
//import com.google.android.gms.ads.MobileAds;
//import com.google.android.gms.ads.OnUserEarnedRewardListener;
//import com.google.android.gms.ads.initialization.InitializationStatus;
//import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
//import com.google.android.gms.ads.interstitial.InterstitialAd;
//import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
//import com.google.android.gms.ads.rewarded.RewardItem;
//import com.google.android.gms.ads.rewarded.RewardedAd;
//import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
//import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
//
//public class AddManager {
//    public InterstitialAd mInterstitialAd;
//    public  RewardedAd rewardedAd;
//    Context context;
//    Activity activity;
//
//    AddManager(Context context, Activity activity, InterstitialAd interstitialAd,RewardedAd rewardedAd){
//        this.context = context;
//        this.activity = activity;
//        this.mInterstitialAd = interstitialAd;
//        this.rewardedAd = rewardedAd;
//
//    }
//
//
//
//    public  void initInterstitialAd(){
//        MobileAds.initialize(context, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {}
//        });
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        InterstitialAd.load(context,context.getResources().getString(R.string.interstitial_adunit), adRequest,
//                new InterstitialAdLoadCallback() {
//                    @Override
//                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
//                        // The mInterstitialAd reference will be null until
//                        // an ad is loaded.
//                        mInterstitialAd = interstitialAd;
//                        Log.i("InterstitialAdd", "onAdLoaded");
//                    }
//
//                    @Override
//                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                        // Handle the error
//                        Log.d("InterstitialAdd", loadAdError.toString());
//                        mInterstitialAd = null;
//                    }
//                });
//
//
//        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//            @Override
//            public void onAdClicked() {
//                // Called when a click is recorded for an ad.
//                Log.d("Interstial", "Ad was clicked.");
//            }
//
//            @Override
//            public void onAdDismissedFullScreenContent() {
//                // Called when ad is dismissed.
//                // Set the ad reference to null so you don't show the ad a second time.
//                Log.d("Interstial", "Ad dismissed fullscreen content.");
//                mInterstitialAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                // Called when ad fails to show.
//                Log.e("Interstial", "Ad failed to show fullscreen content.");
//                mInterstitialAd = null;
//            }
//
//            @Override
//            public void onAdImpression() {
//                // Called when an impression is recorded for an ad.
//                Log.d("Interstial", "Ad recorded an impression.");
//            }
//
//            @Override
//            public void onAdShowedFullScreenContent() {
//                // Called when ad is shown.
//                Log.d("Interstial", "Ad showed fullscreen content.");
//            }
//        });
//    }
//
//
//
//public  void showInterstitialAd(){
//    if (mInterstitialAd != null) {
//        mInterstitialAd.show(activity);
//    } else {
//        Log.d("TAG", "The interstitial ad wasn't ready yet.");
//    }
//
//}
//
//public  void  initRewardedVideo(){
//    AdRequest adRequest = new AdRequest.Builder().build();
//    RewardedAd.load(context, "ca-app-pub-4696224049420135/7768937909",
//            adRequest, new RewardedAdLoadCallback() {
//                @Override
//                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
//                    // Handle the error.
//                    Log.d("Admob", loadAdError.toString());
//                    rewardedAd = null;
//                }
//
//                @Override
//                public void onAdLoaded(@NonNull RewardedAd ad) {
//                    rewardedAd = ad;
//                    Log.d("rewardedAd", "Ad was loaded.");
//                }
//            });
//
//
//    RewardedAd.load(context, "ca-app-pub-3940256099942544/5354046379",
//            new AdRequest.Builder().build(),  new RewardedAdLoadCallback() {
//                @Override
//                public void onAdLoaded(RewardedAd ad) {
//                    Log.d("Rewarded", "Ad was loaded.");
//                    rewardedAd = ad;
//                    ServerSideVerificationOptions options = new ServerSideVerificationOptions
//                            .Builder()
//                            .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
//                            .build();
//                    rewardedAd.setServerSideVerificationOptions(options);
//                }
//                @Override
//                public void onAdFailedToLoad(LoadAdError loadAdError) {
//                    Log.d("Rewarded", loadAdError.toString());
//                    rewardedAd = null;
//                }
//            });
//
//    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//        @Override
//        public void onAdClicked() {
//            // Called when a click is recorded for an ad.
//            Log.d("REwarded", "Ad was clicked.");
//        }
//
//        @Override
//        public void onAdDismissedFullScreenContent() {
//            // Called when ad is dismissed.
//            // Set the ad reference to null so you don't show the ad a second time.
//            Log.d("REwarded", "Ad dismissed fullscreen content.");
//            rewardedAd = null;
//        }
//
//        @Override
//        public void onAdFailedToShowFullScreenContent(AdError adError) {
//            // Called when ad fails to show.
//            Log.e("REwarded", "Ad failed to show fullscreen content.");
//            rewardedAd = null;
//        }
//
//        @Override
//        public void onAdImpression() {
//            // Called when an impression is recorded for an ad.
//            Log.d("REwarded", "Ad recorded an impression.");
//        }
//
//        @Override
//        public void onAdShowedFullScreenContent() {
//            // Called when ad is shown.
//            Log.d("REwarded", "Ad showed fullscreen content.");
//        }
//    });
//}
//
//public void showRewardAd(){
//    if (rewardedAd != null) {
//        //Activity activityContext = MainActivity.this;
//        rewardedAd.show(activity, new OnUserEarnedRewardListener() {
//
//            @Override
//            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
//                // Handle the reward.
//                Log.d("rewarded", "The user earned the reward.");
//                int rewardAmount = rewardItem.getAmount();
//                String rewardType = rewardItem.getType();
//            }
//        });
//    } else {
//        Log.d("rewarded", "The rewarded ad wasn't ready yet.");
//    }
//}
//
//
//
//}
