package com.evelope.events.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.introSlider.IntroActivity;
import com.evelope.events.tools.exceptions.LoginInputException;
import com.evelope.events.tools.inputCheck.LoginInputCheck;

import java.io.FileOutputStream;


public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_email, et_password;

    LoginInputCheck loginInputCheck;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email=(EditText)findViewById(R.id.edt_email);
        et_password=(EditText)findViewById(R.id.edt_password);

        btn_login=(Button)findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
            } else {
                Toast.makeText(this, extras.getString(Intent.EXTRA_TEXT), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, (String) savedInstanceState.getSerializable(Intent.EXTRA_TEXT), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(LoginActivity.this, IntroActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,"reglog");
        startActivity(intent);
    }

    private void goToMainActivity(){
        loginInputCheck=new LoginInputCheck(et_email.getText().toString(),et_password.getText().toString(),getApplicationContext());

        try {
            if (!loginInputCheck.Check())
                throw new LoginInputException(loginInputCheck.getErrorMessage());

            FileOutputStream fos;
            Boolean saved=false;
            try {
                fos = openFileOutput("currentUser", Context.MODE_PRIVATE);
                fos.write(loginInputCheck.getUser().getU_id().toString().getBytes());
                fos.close();
                saved=true;
            } catch (Exception e) {
                saved=false;
                Log.e("Login: ",Log.getStackTraceString(e));
                Toast.makeText(this, "Etwas ist fehlgeschlagen", Toast.LENGTH_LONG).show();
            }
            finally {
                if (saved)
                startActivity(new Intent(this, MainActivity.class));
            }
        }
        catch (LoginInputException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
