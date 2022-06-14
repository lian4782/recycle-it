package com.example.recycleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profile extends AppCompatActivity {
    SharedPreferences sp;
    BottomNavigationView bottomNav;
    FirebaseAuth firebaseAuth;


    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    User u;
    recyclebin bluebin, purplebin, orangebin, allbin;
    TextView BlueBinCnt,OrangeBinCnt,PurpleBinCnt,AllBinCnt;
    TextView BlueBinPoints,OrangeBinPoints,PurpleBinPoints,AllBinPoints, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BlueBinCnt=findViewById(R.id.BlueBinCnt);
        BlueBinPoints=findViewById(R.id.BlueBinPoints);
        OrangeBinCnt=findViewById(R.id.OrangeBinCnt);
        OrangeBinPoints=findViewById(R.id.OrangeBinPoints);
        PurpleBinCnt=findViewById(R.id.PurpleBinCnt);
        PurpleBinPoints=findViewById(R.id.PurpleBinPoints);
        AllBinCnt=findViewById(R.id.AllBinCnt);
        AllBinPoints=findViewById(R.id.AllBinPoints);
        username=findViewById(R.id.username);

        sp=getSharedPreferences("users",0);
        firebaseAuth = FirebaseAuth.getInstance();
        bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Menu menu = (Menu) bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_chat:
                        Intent i1 = new Intent(profile.this, Chat.class);
                        i1.putExtra("from Profile", true);
                        startActivity(i1);
                        break;

                    case R.id.action_ranks:
                        Intent i2 = new Intent(profile.this, Rating.class);
                        i2.putExtra("from Profile", true);
                        startActivity(i2);
                        break;

                    case R.id.action_trash:
                        Intent i3 = new Intent(profile.this, RecyclePage.class);
                        i3.putExtra("from Profile", true);
                        startActivity(i3);
                        break;

                    case R.id.action_profile:
                        break;
                }
                return true;
            }
        });

        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE);
        String email = (sharedPreferences.getString("Email","default"));

    databaseReference = firebaseDatabase.getReference("Users");
                databaseReference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            for (DataSnapshot singleSnapshot : snapshot.getChildren()) {

                    if (singleSnapshot.getValue() != null) {
                        u = singleSnapshot.getValue(User.class);
                        if (u.getEmail().equals(email)) {


                            bluebin=singleSnapshot.child("blueBin").getValue(recyclebin.class);
                            purplebin=singleSnapshot.child("purpleBin").getValue(recyclebin.class);
                            orangebin=singleSnapshot.child("orangeBin").getValue(recyclebin.class);
                            allbin=singleSnapshot.child("allbin").getValue(recyclebin.class);

                            BlueBinPoints.setText("נק' שצברתי:"+ bluebin.getPoints());
                            BlueBinCnt.setText("נאספו "+ bluebin.getCnt()+" פריטים");
                            OrangeBinPoints.setText("נק' שצברתי:"+ orangebin.getPoints());
                            OrangeBinCnt.setText("נאספו "+ orangebin.getCnt()+" פריטים");
                            PurpleBinPoints.setText("נק' שצברתי:"+ purplebin.getPoints());
                            PurpleBinCnt.setText("נאספו "+ purplebin.getCnt()+" פריטיפ");
                            AllBinPoints.setText("נק' שצברתי: "+ allbin.getPoints());
                            AllBinCnt.setText("נאספו "+allbin.getCnt() +" פריטים");
                            username.setText(singleSnapshot.child("name").getValue().toString());

                            Toast.makeText(profile.this, orangebin.getPoints()+" ", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });


}
public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.after_login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();

        if(id==R.id.logout)
        {
            if (sp.getBoolean("isChecked", false))
            {
                SharedPreferences.Editor editor = sp.edit();
                editor.putBoolean("isChecked", false);
                editor.commit();
            }
            firebaseAuth.signOut();
            finish();
            Intent go = new Intent(this, LoginActivity.class);
            go.putExtra("from Profile", true);
            startActivity(go);
        }
        return true;
    }

}

