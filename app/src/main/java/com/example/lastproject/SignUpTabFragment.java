package com.example.lastproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SignUpTabFragment extends Fragment {
    EditText email, name, lastname, code, password;
    Button btn_signup;
    float v = 0;

    public class LoginTabFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            ViewGroup view = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);



            return view;
        }
    }
}
/**email = view.findViewById(R.id.signup_email);
 name = view.findViewById(R.id.signup_name);
 lastname = view.findViewById(R.id.signup_lastName);
 code = view.findViewById(R.id.ind_code);
 password = view.findViewById(R.id.signup_password);
 btn_signup = view.findViewById(R.id.btn_signup);

 email.setTranslationX(800);
 name.setTranslationX(800);
 lastname.setTranslationX(800);
 code.setTranslationX(800);
 password.setTranslationX(800);
 btn_signup.setTranslationX(800);

 email.setAlpha(v);
 name.setAlpha(v);
 lastname.setAlpha(v);
 code.setAlpha(v);
 password.setAlpha(v);
 btn_signup.setAlpha(v);

 email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
 name.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
 lastname.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
 code.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
 password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
 btn_signup.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(900).start();
 *
 */
