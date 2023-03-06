package com.digitalDreams.millionaire_game;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

}
