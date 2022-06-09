package com.example.lastproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    EditText ETemail, ETpassword;
    Button btn_login;
    CheckBox saveme;
    ImageView visible, invisible;
    TextView btn_forgot_pas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        ETemail = view.findViewById(R.id.login_email);
        ETpassword = view.findViewById(R.id.login_password);
        saveme = view.findViewById(R.id.stayin);
        visible = view.findViewById(R.id.visible_password);
        invisible = view.findViewById(R.id.invisible_password);
        btn_forgot_pas = view.findViewById(R.id.tv_forgot_pass);

        btn_forgot_pas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * מחלקה למפנה למסך עם שיחזור סיסמה
                 */
                Intent intent = new Intent(getActivity(), forgot_password.class);
                startActivity(intent);
            }
        });

        visible.setVisibility(View.VISIBLE);
        invisible.setVisibility(View.GONE);

        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמשנה את הראות סיסמה בתוך סדה הסיסמה
             */
            public void onClick(View view) {
                ETpassword.setInputType(InputType.TYPE_CLASS_TEXT);
                visible.setVisibility(View.GONE);
                invisible.setVisibility(View.VISIBLE);
            }
        });

        invisible.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * מחלקה שמשנה את הראות סיסמה בתוך סדה הסיסמה
             */
            public void onClick(View view) {
                ETpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                visible.setVisibility(View.VISIBLE);
                invisible.setVisibility(View.GONE);
            }
        });



        sp = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = sp.edit();

        if(sp.contains("Email")){
            ETemail.setText(sp.getString("Email",""));
        }

        if(sp.contains("Password")){
            ETpassword.setText(sp.getString("Password",""));
        }

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(userLogin()) {

                   Intent intent = new Intent(getActivity(), SplashScreen.class);
                   intent.putExtra("email", ETemail.getText().toString());
                   intent.putExtra("password", ETpassword.getText().toString());
                   startActivity(intent);
               }


            }
        });

        return view;
    }

    private boolean userLogin(){
/**
 * מחלקה שמחזירה true כאשר משתמש קיים במסד נתונים וכל סדות מתאימות לדרישות אחרת מחזירה false
 */
        boolean isChecked = saveme.isChecked();
        String email = ETemail.getText().toString().trim();
        String password = ETpassword.getText().toString().trim();

        if(email.isEmpty()){
            ETemail.setError("Email is required!");
            ETemail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ETemail.setError("Please provide valid email!");
            ETemail.requestFocus();
            return false;
        }

        if(password.isEmpty()){
            ETpassword.setError("Password is required!");
            ETpassword.requestFocus();
            return false;
        }

        if(password.length() < 6){
            ETpassword.setError("Min password length should be 6 characters!");
            ETpassword.requestFocus();
            return false;
        }


        if(isChecked){
            saveUserData();
        }

        return true;
    }

    /**
     * חלקה שומר שם המשתמש וסיסמה של משתמש וזיכרון פנימי בטלפון
     */
    private void saveUserData() {
        String email = ETemail.getText().toString();
        String password = ETpassword.getText().toString();

        editor.putString("Email", email);
        editor.apply();
        editor.putString("Password", password);
        editor.apply();

    }

}

