package com.example.message;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Window window;
    public FirebaseAuth mAuth;
    Button btn;
    Intent MainActivity;
    Intent BaseLogic;
    private EditText Email;
    private EditText Password;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_up);
        intilaze();
    }

    public void intilaze() {
        Email = findViewById(R.id.message_field);
        Password = findViewById(R.id.Password);
        MainActivity = new Intent(this, MainActivity.class);
        BaseLogic = new Intent(this, BaseLogic.class);
    }

    public void signInWithEmailAndPassword(String Email, String Password) {
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(BaseLogic);
                    Log.d("tusk_auth", "successful");
                } else {
                    startActivity(MainActivity);
                    Log.d("tusk_auth", "failure");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public void onClick(View v) {
        if (!(Email.getText().toString().isEmpty()) && !(Password.getText().toString().isEmpty())) {
            Log.d("field", Email.getText().toString() + " " + Password.getText().toString());
            BaseLogic.putExtra("Email", Email.getText().toString());
            signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString());
        }
        Email.setText("");
        Password.setText("");
    }
}