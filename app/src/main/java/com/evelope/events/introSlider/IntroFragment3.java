package com.evelope.events.introSlider;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.evelope.events.login.LoginActivity;
import com.evelope.events.register.RegisterActivity;
import com.evelope.events.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment3 extends Fragment {


    public IntroFragment3() {
        // Required empty public constructor
    }

    Button btn_Login,btn_Register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_intro_fragment3, container, false);

        btn_Login=view.findViewById(R.id.btn_login);
        btn_Register=view.findViewById(R.id.btn_register);

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToRegisterActivity();
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToLoginActivity();
            }
        });

        return view;
    }

    private void goToLoginActivity(){
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void goToRegisterActivity(){
        Intent intent=new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }

}
