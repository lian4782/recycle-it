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

public class RecyclePage extends AppCompatActivity implements RecyclerViewInterface{
    boolean update = false;
    BottomNavigationView bottomNav;
    ArrayList<recycleItem> ItemsList;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    recycleItem r;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_page);

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
                        Intent i1 = new Intent(RecyclePage.this, Chat.class);
                        i1.putExtra("from RecyclePage", true);
                        startActivity(i1);
                        break;

                    case R.id.action_ranks:
                        Intent i3 = new Intent(RecyclePage.this, Rating.class);
                        i3.putExtra("from RecyclePage", true);
                        startActivity(i3);
                        break;

                    case R.id.action_trash:
                        break;

                    case R.id.action_profile:
                        Intent i2 = new Intent(RecyclePage.this, profile.class);
                        i2.putExtra("from RecyclePage", true);
                        startActivity(i2);
                        break;
                }
                return true;
            }
        });

        ItemsList = new ArrayList<recycleItem>();
        recyclerView = findViewById(R.id.rw1);
        view_list_of_items();



    }

            public void view_list_of_items() {
                RecyclerViewAdapter1 adapter = new RecyclerViewAdapter1(RecyclePage.this, ItemsList, this);
                databaseReference = firebaseDatabase.getReference("Recycle Items");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                            if (singleSnapshot.getValue() != null) {
                                r = new recycleItem(singleSnapshot.child("name").getValue().toString()
                                        , Integer.parseInt(singleSnapshot.child("value").getValue().toString())
                                        , singleSnapshot.child("kindOfBin").getValue().toString()
                                        , singleSnapshot.child("picname").getValue().toString());
                                ItemsList.add(r);
                            }
                        }
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RecyclePage.this));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


    @Override
    public void onItemClick(int position) {
        update = false;
        SharedPreferences sharedPreferences;
        sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE);
        String email = (sharedPreferences.getString("Email","default"));

        if (ItemsList.get(position).getKindOfBin().equals("פח כתום")) {
            Toast.makeText(RecyclePage.this, "email", Toast.LENGTH_LONG).show();
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("Users");



            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        User u = singleSnapshot.getValue(User.class);
                        u = new User(singleSnapshot.child("key").getValue().toString()
                                ,singleSnapshot.child("name").getValue().toString()
                                , singleSnapshot.child("email").getValue().toString()
                                ,singleSnapshot.child("password").getValue().toString()
                                ,singleSnapshot.child("blueBin").getValue(recyclebin.class)
                                ,singleSnapshot.child("orangeBin").getValue(recyclebin.class)
                                ,singleSnapshot.child("purpleBin").getValue(recyclebin.class)
                                ,singleSnapshot.child("allbin").getValue(recyclebin.class)
                        );

                    if (u.getEmail().equals(email) && update == false) {

                            Toast.makeText(RecyclePage.this, email + " ", Toast.LENGTH_LONG).show();
                            u.getOrangeBin().updateBin(ItemsList.get(position).getValue());
                            databaseReference.child(u.getKey()).setValue(u);
                            update = true;

                    }

                }

             }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(RecyclePage.this,
                            " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }



    }
}



