package com.digitalDreams.millionaire_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

public class UserDetails extends AppCompatActivity {
    EditText usernameEdt;
    CardView avatarContainer1,avatarContainer2,avatarContainer3,avatarContainer4;
    GridLayout gridLayout;
    String username="",avatar="",country="",flag="";
    Spinner spinner;
    CountryAdapter countryAdapter;
    ArrayList countries = new ArrayList();
    ArrayList flags = new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        addCountries();


        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setTitle("");
        usernameEdt = findViewById(R.id.username);
        SharedPreferences sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        usernameEdt.setText(username);
        Button continueBtn = findViewById(R.id.continueBtn);
        spinner = findViewById(R.id.country);
        countryAdapter = new CountryAdapter(this,countries,flags);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        gridLayout = findViewById(R.id.grid);
        for (int a=0;a<gridLayout.getChildCount();a++){
            CardView card = (CardView) gridLayout.getChildAt(a);
            int b = a;
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    avatar= String.valueOf(b+1);
                    selectAvatar(card);
                }
            });
        }
        if(type!=null&&type.equals("edit")){
            selectAvatar();
        }


        /////////////////////////

     //   ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,countries);
        spinner.setAdapter(countryAdapter);


        //////////////////////////
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                country = selectedItem;
                flag = flags.get(i).toString();
                //Log.i("Flag",flags.get(i).toString());



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void selectAvatar(CardView cardView){
        for (int a=0;a<gridLayout.getChildCount();a++){
            CardView card = (CardView) gridLayout.getChildAt(a);
            card.setCardBackgroundColor(getResources().getColor(R.color.color5));
        }
        cardView.setCardBackgroundColor(getResources().getColor(R.color.green));

    }

    private void selectAvatar(){
        SharedPreferences sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
        String avatar = sharedPreferences.getString("avatar","1");

        for (int a=0;a<gridLayout.getChildCount();a++){
            if(a+1==Integer.parseInt(avatar)) {
                CardView card = (CardView) gridLayout.getChildAt(a);
                card.setCardBackgroundColor(getResources().getColor(R.color.green));
            }
        }
    }

    private void validateInput(){
        username = usernameEdt.getText().toString();
        if(username.isEmpty()){
            Toast.makeText(UserDetails.this,"Player name can't be empty",Toast.LENGTH_SHORT).show();
        }else if(country == "" || country == "Select country"){
            Toast.makeText(UserDetails.this,"Select your country",Toast.LENGTH_SHORT).show();


        }else if(avatar.isEmpty()){
            Toast.makeText(UserDetails.this,"Select an avatar",Toast.LENGTH_SHORT).show();

        }else {
            SharedPreferences sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",username);
            editor.putString("avatar",avatar);
            editor.putString("country",country);
            editor.putString("country_flag",flag);
            editor.putString("game_level","1");
            editor.putString("current_play_level","1");
            editor.putBoolean("isFirstTime",true);

            editor.apply();
            Log.i("lentiiiii3", String.valueOf(Utils.IS_DONE_INSERTING));

            Intent intent;
            if(Utils.IS_DONE_INSERTING) {
                Log.i("lentiiiii3", "Dashboard");

                intent = new Intent(UserDetails.this, Dashboard.class);
            }else{
                Log.i("lentiiiii3", "Welcome");
                intent = new Intent(UserDetails.this, WelcomeActivity.class);

            }
            startActivity(intent);
            finish();

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    public  void addCountries(){
       // Log.i("llllll","flag");
        try{
            String json = readRawTextFile(R.raw.country_json);
            JSONArray jsonArray = new JSONArray(json);
            //Iterator<String> iterator = jsonObject.keys();

            //Log.i("llllll","flag");
            for(int j =0; j < jsonArray.length();j++){
                JSONObject obj1 = jsonArray.getJSONObject(j);
                String name = obj1.getString("name");
                String flag = obj1.getString("image");
                countries.add(name);
                flags.add(flag);
            }

//            while (iterator.hasNext()){
//                String key = iterator.next();
//                JSONObject obj1 = jsonObject.getJSONObject(key);
//                String name = obj1.getString("name");
//                String flag = obj1.getString("image");
//                countries.add(name);
//                flags.add(flag);
//
//                Log.i("llllll",flag);
//
//               /// countryAdapter.notifyDataSetChanged();
//            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    String[] countries4ii = new String[]{"Select country","Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas",
            "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
            "Bosnia and Herzegowina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam",
            "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China, People's republic of", "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
            "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire",
            "Croatia (Hrvatska)", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
            "East Timor", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia",
            "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "France Metropolitan", "French Guiana",
            "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar",
            "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
            "Heard and Mc Donald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",
            "Indonesia", "Iran (Islamic Republic of)", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
            "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kosovo", "Kuwait",
            "Kyrgyzstan", "Lao, People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libyan Arab Jamahiriya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia, The Former Yugoslav Republic of", "Madagascar",
            "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius",
            "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montserrat",
            "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands",
            "Norway", "Oman", "Pakistan", "Palau", "Panama", "Papua New Guinea", "Paraguay", "Palestine", "Peru", "Philippines", "Pitcairn",
            "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda",
            "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino",
            "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Seychelles", "Sierra Leone", "Singapore",
            "Slovakia (Slovak Republic)", "Slovenia", "Solomon Islands", "Somalia", "South Africa",
            "South Georgia and the South Sandwich Islands", "Spain", "Sri Lanka", "St. Helena", "St. Pierre and Miquelon",
            "Sudan", "Suriname", "Svalbard and Jan Mayen Islands", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic",
            "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Tibet", "Togo", "Tokelau", "Tonga",
            "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine",
            "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay",
            "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "Virgin Islands (British)", "Virgin Islands (U.S.)",
            "Wallis and Futuna Islands", "Western Sahara", "Yemen", "Yugoslavia", "Zambia", "Zimbabwe"};


    private String readRawTextFile( int resId) throws IOException {
        InputStream is = getResources().openRawResource(resId);
        Writer writer = new StringWriter();
        char[] buffer = new char[10024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }

        String jsonString = writer.toString();
        return jsonString;
    }
}