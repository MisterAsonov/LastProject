package com.example.lastproject;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpTabFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;


    EditText ETemail, ETname, ETlastname, ETcode, ETpassword;
    Button btn_signup;
    float v = 0;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

            mAuth = FirebaseAuth.getInstance();

            ETemail = view.findViewById(R.id.signup_email);
            ETname = view.findViewById(R.id.signup_name);
            ETlastname = view.findViewById(R.id.signup_lastName);
            ETcode = view.findViewById(R.id.ind_code);
            ETpassword = view.findViewById(R.id.signup_password);

            btn_signup = view.findViewById(R.id.btn_signup);
            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    registerUser();

                }

            });

            return view;
        }

        private void registerUser(){

            String email = ETemail.getText().toString().trim();
            String name = ETname.getText().toString().trim();
            String lastname = ETlastname.getText().toString().trim();
            String code = ETcode.getText().toString().trim();
            String password = ETpassword.getText().toString().trim();



            if(email.isEmpty()){
                ETemail.setError("Email is required!");
                ETemail.requestFocus();
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                ETemail.setError("Please provide valid email!");
                ETemail.requestFocus();
                return;
            }

            if(name.isEmpty()){
                ETname.setError("Name is required!");
                ETname.requestFocus();
                return;
            }
            if(lastname.isEmpty()){
                ETlastname.setError("Last Name is required!");
                ETlastname.requestFocus();
                return;
            }

            if(code.isEmpty()){
                ETcode.setError("Code is required!");
                ETcode.requestFocus();
                return;
            }


            if(password.isEmpty()){
                ETpassword.setError("Password is required!");
                ETpassword.requestFocus();
                return;
            }

            if(password.length() < 6){
                ETpassword.setError("Min password length should be 6 characters!");
                ETpassword.requestFocus();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                User user = new User(email, name, lastname, code);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(),"User has been registered successfully!", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getContext(),"Faild to register! Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(getContext(),"Faild to register the user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


