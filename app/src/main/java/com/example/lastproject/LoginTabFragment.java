package com.example.lastproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    EditText email, password;
    Button btn_login;
    float v = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);


        return view;
    }
}
/**email = view.findViewById(R.id.login_email);
 password = view.findViewById(R.id.login_password);
 btn_login = view.findViewById(R.id.btn_login);

 email.setTranslationX(800);
 password.setTranslationX(800);
 btn_login.setTranslationX(800);

 email.setAlpha(v);
 password.setAlpha(v);
 btn_login.setAlpha(v);

 email.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();
 password.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(500).start();
 btn_login.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
 *
 */
