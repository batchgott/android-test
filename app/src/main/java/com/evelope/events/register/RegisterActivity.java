package com.evelope.events.register;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.evelope.events.R;
import com.evelope.events.database.write.createEntity.CreateEntityUser;
import com.evelope.events.login.LoginActivity;
import com.evelope.events.tools.exceptions.RegisterInputException;
import com.evelope.events.tools.inputCheck.RegisterInputCheck;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Bitmap selectedImage;

    ImageView img_user_picture;

    Button btn_getImage;

    EditText et_firstName,et_lastName,et_email,et_password,et_passwordConfirm,et_description,et_phoneNumber,userName;
    Button btn_register;
    RegisterInputCheck registerInputCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_firstName=(EditText)findViewById(R.id.edt_firstName);
        et_lastName=(EditText)findViewById(R.id.edt_lastName);
        et_email=(EditText)findViewById(R.id.edt_email);
        et_password=(EditText)findViewById(R.id.edt_password);
        et_passwordConfirm=(EditText)findViewById(R.id.edt_passwordConfirm);
        et_description=(EditText)findViewById(R.id.edt_description);
        et_phoneNumber=(EditText)findViewById(R.id.edt_phoneNumber);

        img_user_picture=(ImageView)findViewById(R.id.img_user_picture);

        btn_getImage=(Button)findViewById(R.id.btn_user_getPicture);
        selectedImage=null;

        btn_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromAlbum();
            }
        });

        btn_register=(Button)findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });

        btn_getImage.setEnabled(true);
        btn_register.setEnabled(true);
    }

    private void registerUser(){
        try {
            registerInputCheck=new RegisterInputCheck(et_firstName.getText().toString(),et_lastName.getText().toString(),et_email.getText().toString(),et_password.getText().toString(),et_passwordConfirm.getText().toString(),et_description.getText().toString(),et_phoneNumber.getText().toString(),selectedImage);
            if (!registerInputCheck.Check())
                throw new  RegisterInputException(registerInputCheck.getErrorMessage());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_getImage.setEnabled(false);
                    btn_register.setEnabled(false);
                    new CreateEntityUser(et_firstName.getText().toString(),et_lastName.getText().toString(),et_description.getText().toString(),et_email.getText().toString(),et_phoneNumber.getText().toString(),et_password.getText().toString(),selectedImage,RegisterActivity.this);
                }
            }, 100);
//            Thread t=new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    btn_getImage.setEnabled(false);
//                    btn_register.setEnabled(false);
//                    new CreateEntityUser(et_firstName.getText().toString(),et_lastName.getText().toString(),et_description.getText().toString(),et_email.getText().toString(),et_phoneNumber.getText().toString(),et_password.getText().toString(),selectedImage,RegisterActivity.this);
//                }
//            });
//            t.start();
//            try
//            {
//                t.join();
//            }
//            catch(InterruptedException ex)
//            {
//            }
            Intent intent =new Intent(RegisterActivity.this,LoginActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT,"Der Benutzer "+et_firstName.getText().toString()+" "+et_lastName.getText().toString()+" wurde erstellt");
            startActivity(intent);
        }
        catch (RegisterInputException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getImageFromAlbum(){
        try{
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }catch(Exception exp){
            Log.i("Error",exp.toString());
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                img_user_picture.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Etwas ist schiefgelaufen", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Du hast kein Bild ausgewählt", Toast.LENGTH_LONG).show();
        }
    }
}
