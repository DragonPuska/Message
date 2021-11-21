package com.example.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySuccessRegisterBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        bind = intent.getExtras();

        code = binding.editTextTextPersonName;
        reg = new Register();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
        MainActivity = new Intent(this,MainActivity.class);
        Sender tlsSender = new Sender("semengaponov05@gmail.com","SemensemenA12");
        tlsSender.send("CODE", reg.Code, "unnamed", bind.get("Email").toString().trim());
        Button success = binding.button2;
        success.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(code.getText().toString().equalsIgnoreCase(reg.Code)){
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