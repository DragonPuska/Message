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

import com.example.message.databinding.ActivitySuccessRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SuccessRegister extends AppCompatActivity implements View.OnClickListener {

    ActivitySuccessRegisterBinding binding;
    EditText code;
    Register reg;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Intent MainActivity;
    Bundle bind;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySuccessRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        bind = intent.getExtras();
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        code = binding.editTextTextPersonName;
        reg = new Register();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        MainActivity = new Intent(this,MainActivity.class);
        Log.d("code",bind.get("Code").toString() );
        Sender tlsSender = new Sender("semengaponov05@gmail.com","SemensemenA123
");
        tlsSender.send("CODE", bind.get("Code").toString() , "unnamed", bind.get("Email").toString().trim());
        Button success = binding.button2;
        success.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(code.getText().toString().equalsIgnoreCase(bind.get("Code").toString() )){
            reg.push(Register.nick, bind.get("Email").toString());
            Log.d("gotovo",bind.get("Email").toString() + bind.get("Password").toString() + Register.nick);
            mAuth.createUserWithEmailAndPassword(bind.get("Email").toString(),bind.get("Password").toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(MainActivity); Log.d("task","createUserWithEmailandPassword::success");
                            }else{startActivity(MainActivity); Log.d("task","createUserWithEmailandPassword::error");}
                        }
                    });
        }
    }
}
