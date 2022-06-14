package com.example.recycleapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.Executor;

public class SignupTabFragment extends Fragment implements View.OnClickListener {
    EditText Name;
    EditText Email;
    EditText Pass;
    EditText CPass;
    Button signup;
    float v=0;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    ProgressDialog p;
    DatabaseReference myref=firebaseDatabase.getReference("Users").push();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);
        Name=root.findViewById(R.id.name);
        Email=root.findViewById(R.id.mail);
        Pass=root.findViewById(R.id.pass);
        CPass=root.findViewById(R.id.Cpass);
        signup=root.findViewById(R.id.signup);

        Name.setTranslationY(800);
        Email.setTranslationY(800);
        Pass.setTranslationY(800);
        CPass.setTranslationY(800);
        signup.setTranslationY(800);

        firebaseAuth = FirebaseAuth.getInstance();
        signup.setOnClickListener(this);

        Name.setAlpha(v);
        Email.setAlpha(v);
        Pass.setAlpha(v);
        CPass.setAlpha(v);
        signup.setAlpha(v);

        Name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
        Pass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        CPass.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
        signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();

        // Inflate the layout for this fragment
        return root;

    }

    public void createUser(){
        p= new ProgressDialog(getContext());
        p.setMessage("Registration...");
        p.show();
        if(isValidate())
//יצירת פרופיל משתמש לפי אימייל וסיסמה
            firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString
                    (), Pass.getText().toString()).addOnCompleteListener( getActivity(),
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull
                                                       Task<AuthResult> task) {
                            if (task.isSuccessful()){
//יצירת אובייקט מסוג User
                                User u = new
                                        User(myref.getKey(), Name.getText().toString(), Email.getText().toString(), Pass.getText().toString());
                                myref.setValue(u);
                                p.dismiss();
                                Toast.makeText(getActivity(),"ההרשמה הצליחה", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getActivity(),
                            e.getMessage(), Toast.LENGTH_LONG).show();
                    p.dismiss();
                }
            });
    }



    //בדיקה האם האימייל עומד בהגדרות הנדרשות – הגדרות המופיעות
// באנדרואיד ואם אורך הסיסמה לפחות 6 תווים
    public boolean isValidate(){
        if (!
                Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()
        ){
            Email.setError("Invalid email");
            Email.setFocusable(true);
            return false;
        }
        else if (Pass.getText().toString().length()<6){
            Pass.setError("password length at least 6 characters");
            Pass.setFocusable(true);
            return false;
        }
        else if (! ( (Pass.getText().toString() ).equals( CPass.getText().toString() )) ){
            CPass.setError("הסיסמאות שהזנת אינן זהות");
            CPass.setFocusable(true);
            return false;
        }
        return true;
    }



    @Override
    public void onClick(View view) {
        if(view==signup){
            createUser();

        }

    }

}
