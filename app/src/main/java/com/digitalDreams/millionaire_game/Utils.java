package com.digitalDreams.millionaire_game;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;

public class Utils {
    public static boolean IS_DONE_INSERTING =false;


    public static String addCommaToNumber(double number){
        String pattern = "#,###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);

    }
    public static String addCommaToNumber(int number){
        String pattern = "#,###,###.###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(number);

    }

    public static String addDollarSign(String number){

        return "$"+number;

    }
    public static String addCommaAndDollarSign(double number){

        return addDollarSign(addCommaToNumber(number));

    }

    public static String prettyCount(Integer number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    public  static boolean isOnline(Context context){
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mgr.getActiveNetworkInfo();

        if (netInfo != null) {
            if (netInfo.isConnected()) {
                // Internet Available
                return  true;
            }else {
                //No internet
                return false;
            }
        } else {
            //No internet
            return false;
        }
    }

    public static void  greenBlink(View btn, Context context){
      int dark =   R.drawable.dark_play;
      int light = R.drawable.playbtn_bg;
        MediaPlayer.create(context, R.raw.play).start();


        btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {



                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    btn.setBackgroundResource(dark);
                    //newGameBtn.startAnimation( new AlphaAnimation(1F, 0.7F));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    btn.setBackgroundResource(light);
                    //newGameBtn.startAnimation( new AlphaAnimation(1F, 1F));
                }


                return false;
            }
        });

        //btn.performClick();
    }

    public static void  darkBlueBlink(View btn,Context context){

        int dark =   R.drawable.ic_hex_2;
        int light = R.drawable.ic_hexnow;
        MediaPlayer.create(context, R.raw.others).start();


        btn.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {



                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    btn.setBackgroundResource(dark);
                    //newGameBtn.startAnimation( new AlphaAnimation(1F, 0.7F));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)
                {
                    btn.setBackgroundResource(light);
                    //newGameBtn.startAnimation( new AlphaAnimation(1F, 1F));
                }


                return false;
            }
        });

        //btn.performClick();
    }

    public static void pressSound(Context context){
        MediaPlayer.create(context, R.raw.others).start();
    }

}
