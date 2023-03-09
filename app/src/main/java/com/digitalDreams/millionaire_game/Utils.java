package com.digitalDreams.millionaire_game;

import static com.digitalDreams.millionaire_game.FailureActivity.getDeviceId;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static boolean IS_DONE_INSERTING =false;
    public static int NUMBER_OF_INSERT = 0;


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

    public static void sendScoreToSever(Context context,String score, Map<String,String> userDetails,String modeValue) {
        String url = context.getResources().getString(R.string.base_url)+"/post_score.php";
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
                Log.i("ooooop",score);
                param.put("score", score);
                param.put("username", userDetails.get("username"));
                param.put("country", userDetails.get("country"));
                param.put("country_json", country_json.toString());
                param.put("country_flag", userDetails.get("country_flag"));
                param.put("avatar",  getAvatar(context));
                param.put("device_id",getDeviceId(context));
                param.put("game_type","millionaire");
                param.put("mode",modeValue);
                Log.i("praram", String.valueOf(param));
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public static String getAvatar(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String avatar = sharedPreferences.getString("avatar","");
        return avatar;
    }

}
