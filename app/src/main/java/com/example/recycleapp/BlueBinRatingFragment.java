package com.example.recycleapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlueBinRatingFragment extends Fragment {
    RecyclerView recyclerView;
    UserForRating u;
    ArrayList<UserForRating> UsersList;

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_blue_bin_rating, container, false);
        super.onCreate(savedInstanceState);

        UsersList = new ArrayList<UserForRating>();
        ArrayList<UserForRating> NewUsersList = new ArrayList<UserForRating>();

        recyclerView = root.findViewById(R.id.rwBlueBin);
        RecyclerViewRatingAdapter adapter = new RecyclerViewRatingAdapter(this.getContext(), NewUsersList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot singleSnapshot : snapshot.getChildren()) {
                    if (singleSnapshot.getValue() != null) {
                        u = new UserForRating(singleSnapshot.child("name").getValue().toString()
                                , singleSnapshot.child("blueBin").getValue(recyclebin.class))
                        ;
                        UsersList.add(u);
                        //Toast.makeText(profile.this,  singleSnapshot.child("orangeBin").child("cnt").getValue()+" ", Toast.LENGTH_LONG).show();

                    }
                }

                UserForRating UsersArray[]=new UserForRating[UsersList.size()];
                for(int i=0;i<UsersArray.length;i++){
                    UsersArray[i]=UsersList.get(i);
                }

                for (int i = 0; i < UsersArray.length - 1; i++)
                    for (int j = 0; j < UsersArray.length - i - 1; j++)
                        if (UsersArray[j].getBin().getPoints() < UsersArray[j + 1].getBin().getPoints()) {
                            // swap arr[j+1] and arr[j]
                            UserForRating temp = new UserForRating(UsersArray[j].getName(),UsersArray[j].getBin());
                            UsersArray[j] = UsersArray[j + 1];
                            UsersArray[j + 1] = temp;
                        }

                for(int i=0;i<UsersArray.length;i++){
                    NewUsersList.add(UsersArray[i]);
                }
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
    }
}