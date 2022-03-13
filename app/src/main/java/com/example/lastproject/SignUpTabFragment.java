package com.example.lastproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class SignUpTabFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;

    String who;
    Spinner spinner;
    EditText ETemail, ETname, ETlastname, ETpassword, ETrefereal_link;
    ImageView qr_scanner;
    Button btn_signup;
    public static int SighAppTabRequestCode = 989;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);

            mAuth = FirebaseAuth.getInstance();


            ETemail = view.findViewById(R.id.signup_email);
            ETname = view.findViewById(R.id.signup_name);
            ETlastname = view.findViewById(R.id.signup_lastName);
            spinner = view.findViewById(R.id.who);
            ETpassword = view.findViewById(R.id.signup_password);
            ETrefereal_link = view.findViewById(R.id.referal_link);
            qr_scanner = view.findViewById(R.id.qr_scanner);

            Intent intent = getActivity().getIntent();
            ETrefereal_link.setText(intent.getStringExtra("referal_link"));

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getActivity(),
                    R.array.who,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    who = adapterView.getItemAtPosition(i).toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btn_signup = view.findViewById(R.id.btn_signup);
            btn_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    registerUser();

                }

            });

            qr_scanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), qr_scanner.class);

                    startActivityForResult(intent, SighAppTabRequestCode );

                }
            });

            return view;
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == SighAppTabRequestCode) {

           if(data != null && data.hasExtra("ref_link"))
            ETrefereal_link.setText( data.getStringExtra("ref_link"));

        }

    }

    private void registerUser(){

            String email = ETemail.getText().toString().trim();
            String name = ETname.getText().toString().trim();
            String lastname = ETlastname.getText().toString().trim();
            String password = ETpassword.getText().toString().trim();
            String referal_link = ETrefereal_link.getText().toString().trim();



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

                                String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String referal_link = ETrefereal_link.getText().toString().trim();

                                if(who.equals("Teacher") && referal_link.isEmpty())
                                    referal_link = id;
                                User user = new User(email, name, lastname, who, referal_link,id,"");

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(id)
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
                                if(!referal_link.isEmpty()){
                                    PersonInGroup person = new PersonInGroup(id);
                                        FirebaseDatabase.getInstance().getReference("Groups")
                                        .child(referal_link).push()
                                        .setValue(person).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getContext(),"Successfuly added to group", Toast.LENGTH_SHORT).show();

                                        }else{
                                            Toast.makeText(getContext(),"Faild to add! Try again!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            }else{
                                Toast.makeText(getContext(),"Faild to register the user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }


