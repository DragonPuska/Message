package com.example.message;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.message.databinding.ActivityMessageUp2Binding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MessageUp extends AppCompatActivity implements View.OnClickListener {

    private ActivityMessageUp2Binding binding;
    public EditText mEditText;
    public RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Window window;
    List<StateMessage> stateAdapterMessages = new ArrayList<>();
    View arrow_push;
    Context context = this;
    DatabaseReference myRef;
    List<String> values;
    StateAdapterMessage adapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageUp2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        Bundle arguments = intent.getExtras();
        myRef = database.getReference(arguments.get("database").toString());
        arrow_push = binding.arrowPush;
        arrow_push.setOnClickListener(this);
        mEditText = binding.messageField;
        recyclerView = binding.recyclerMessage;
        recyclerView.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar(binding.appBarMessage.toolMessageUps);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        Toolbar toolBar = binding.appBarMessage.toolMessageUps;//toolBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
         public void onClick(View v) {
                MessageUp.super.onBackPressed();
            }
        });
        int profileImage = Integer.valueOf(arguments.get("profileImage").toString());
        TextView name = findViewById(R.id.name);
        name.setText(arguments.get("name").toString());
        ImageView image = findViewById(R.id.imageViewss);
        image.setImageResource(profileImage);
        adapter = new StateAdapterMessage(this,stateAdapterMessages);
        recyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                values = Arrays.asList(value.split("-"));
                stateAdapterMessages.add(new StateMessage(values.get(0),values.get(1),values.get(2)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.swipeMessage.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                binding.swipeMessage.setRefreshing(false);
            }
        });
    }


    @Override
    public void onClick(View v) {
                Log.d("recyler","recycler");
                if (!mEditText.getText().toString().isEmpty()) {
                myRef.push().setValue(mEditText.getText().toString() + "-" + dateFormat.format(new Date()) + "-" + BaseLogic.nick);
                mEditText.setText("");
                Log.d("adapter", stateAdapterMessages.toString()); }
        }
    }


