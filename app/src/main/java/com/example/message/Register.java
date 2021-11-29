package com.example.message;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText Password;
    private EditText Email;
    private EditText NickName;
    public String Email_Value = "";
    public String Password_Value = "";
    public String Code = "";
    public static String nick = "";
    public String email_up = "adsa";
    static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = firebaseDatabase.getReference("nicknames");
    private Button btn;
    Intent MainActivity;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Password = findViewById(R.id.Password);
        Email = findViewById(R.id.message_field);
        NickName = findViewById(R.id.Nickname);
        btn = findViewById(R.id.button);
        btn.setOnClickListener(this);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        MainActivity = new Intent(this,MainActivity.class);
        Code = code();
    }

     public void push(String nickname, String email){
         myRef.push().setValue(nickname + "-" + email);
     }

     public String code(){
        int i = 0;
        StringBuilder str = new StringBuilder();
        while (i < 4) {
            Random r = new Random();
            char cc = (char) (r.nextInt(26) + 'a');
            str.append(cc);
            i++;
        }
        return str.toString().toUpperCase();
     }

//    public void createUserWithEmailandPassword(){
//        mAuth.createUserWithEmailAndPassword(Email_Value,Password_Value)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                       if(task.isSuccessful()){
//                            Log.d("task","createUserWithEmailandPassword::success");
//                       }else{startActivity(MainActivity);}
//                    }
//                });
//    }

    @Override
    public void onClick(View v) {
        if (!(Email.getText().toString().isEmpty()) && !(Password.getText().toString().isEmpty())) {
            Password_Value = Password.getText().toString();
            Email_Value = Email.getText().toString();
            nick = NickName.getText().toString();
            Intent intent = new Intent(this,SuccessRegister.class);
            intent.putExtra("Email",Email_Value);
            intent.putExtra("Password",Password_Value);
            intent.putExtra("Code",Code);
            startActivity(intent);
        }
    }
}