package com.example.message;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText Email;
    EditText Password;
    EditText Nickname;
    Button btn;
    Intent intent;
    ImageView image;
    Window window;
    TextView btnT;
    FirebaseAnalytics fas;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intilaze();
        ui_mode();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void ui_mode(){
        switch(getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK){
            case Configuration.UI_MODE_NIGHT_NO:
                Log.d("theme","0");
                image.setBackgroundTintList(getResources().getColorStateList(R.color.back_white));
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                Log.d("theme","1");
                image.setBackgroundTintList(getResources().getColorStateList(R.color.back_black));
                break;
        }
    }

    public void intilaze(){
        fas = FirebaseAnalytics.getInstance(this);
        image = findViewById(R.id.imageView2);
        intent = new Intent(this, Login.class);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.purple_700));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Intent intent = new Intent(this , Login.class);
                startActivity(intent);
                break;
            case R.id.textView7:
                Intent intent2 = new Intent(this ,Register.class);
                startActivity(intent2);
                break;
        }

    }

}