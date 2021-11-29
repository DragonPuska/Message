package com.example.message;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.message.databinding.ActivityBaseLogicBinding;
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

public class BaseLogic extends AppCompatActivity {
    Window window;
    ActionBarDrawerToggle mDrawerToggle;
    ActionBarDrawerToggle toggle;
    private ActivityBaseLogicBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    Intent message_up;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference("nicknames");
    StateAdapter adapter;
    List<String> valueCheck = new ArrayList<String>();
    RecyclerView recyclerView;
    List<String> values = new ArrayList<String>();
    static String nick;
    Context context = this;
    String database;
    String last_message = null;
    List<String> local_database = new ArrayList<String>();
    Date dateNow = new Date();
    ArrayList<State> states = new ArrayList<State>();
    SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binding date with layout
        binding = ActivityBaseLogicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Intilaze ToolBar
        message_up = new Intent(this, MessageUp.class);
        setSupportActionBar(binding.appBarMain.toolbar);
        intilazeRecyclerView();
    }

    public void intilazeRecyclerView() {
        //Intilaze RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void messageup(int profileImage, String name) {
        Log.d("Koll", String.valueOf(profileImage));
        message_up.putExtra("profileImage", profileImage);
        message_up.putExtra("name", name);
        if (name.length() > nick.length()) database = name + nick;
        else database = nick + name;
        message_up.putExtra("database", database);
        startActivity(message_up);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setDatabase();
        binding.swipeRefresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                binding.swipeRefresher.setRefreshing(false);
            }
        });
        adapter = new StateAdapter(context, states, new StateAdapter.OnItemClick() {
            @Override
            public void OnItemClick(State states) {
                messageup(states.getProfileResource(), states.getTitle());
            }
        });
    }

    private void setDatabase() {
        Intent intent = getIntent();
        Bundle bnd = intent.getExtras();
        int mass = 0;
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String value = snapshot.getValue(String.class);
                values = Arrays.asList(value.split("-"));
                Log.d("array_message", values.toString());
                if (!valueCheck.contains(values.get(0)) && !values.contains(bnd.get("Email").toString())) {
                    states.add(new State(values.get(0), "", R.drawable.p, formatForDateNow.format(dateNow), ""));
                    recyclerView.setAdapter(adapter);
                    valueCheck.add(values.get(0));
                }
                if (values.contains(bnd.get("Email").toString())) {
                    nick = values.get(0);
                }
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        valueCheck = new ArrayList<String>();
    }

    private static long back_pressed;

    Intent ik;
    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            ik = new Intent(Intent.ACTION_MAIN);
            ik.addCategory(Intent.CATEGORY_HOME);
            ik.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(ik);
        }
        else
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        back_pressed = System.currentTimeMillis();
    }
}