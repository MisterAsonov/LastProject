package com.example.lastproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class LoginTabFragment extends Fragment {

    private FirebaseAuth mAuth;

    EditText ETemail, ETpassword;
    Button btn_login;
    float v = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        ETemail = view.findViewById(R.id.login_email);
        ETpassword = view.findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();

            }
        });

        return view;
    }

    private void userLogin(){

        String email = ETemail.getText().toString().trim();
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

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(),"Faild to login! Please chek your email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

}

