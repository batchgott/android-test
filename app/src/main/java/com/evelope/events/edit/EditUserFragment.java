package com.evelope.events.edit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.User;
import com.evelope.events.database.write.updateEntity.UpdateEntityUser;
import com.evelope.events.efragment;
import com.evelope.events.login.LoginActivity;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.GetPictureFromFile;
import com.evelope.events.tools.exceptions.UpdateUserInputException;
import com.evelope.events.tools.inputCheck.UpdateUserInputCheck;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;

    ImageView iv_proiflePicture;
    TextView tv_email;
    EditText edt_firstName;
    EditText edt_lastName;
    EditText edt_description;
    EditText edt_phonenumber;
    EditText edt_oldPassword;
    EditText edt_newPassword;
    EditText edt_newPasswordRepeate;
    Button btn_save;
    Button btn_changePicture;
    Bitmap picture;
    private Activity mActivity;

    public EditUserFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_user, container, false);

        iv_proiflePicture=view.findViewById(R.id.iv_image);
        tv_email=view.findViewById(R.id.tv_email);
        edt_firstName=view.findViewById(R.id.edt_firstName);
        edt_lastName=view.findViewById(R.id.edt_lastName);
        edt_description=view.findViewById(R.id.edt_description);
        edt_phonenumber=view.findViewById(R.id.edt_phonenumber);
        edt_oldPassword=view.findViewById(R.id.edt_oldPassword);
        edt_newPassword=view.findViewById(R.id.edt_newPassword);
        edt_newPasswordRepeate=view.findViewById(R.id.edt_newPasswordRepeate);
        btn_save=view.findViewById(R.id.btn_save);
        btn_changePicture=view.findViewById(R.id.btn_changePicture);

        btn_save.setEnabled(true);
        btn_changePicture.setEnabled(true);

        btn_changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromAlbum();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUser();
            }
        });

        fillEditTexts();

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    private void updateUser(){
        try {
            UpdateUserInputCheck updateUserInputCheck = new UpdateUserInputCheck(edt_firstName.getText().toString(), edt_lastName.getText().toString(), edt_description.getText().toString(), edt_phonenumber.getText().toString(), picture, edt_oldPassword.getText().toString(), edt_newPassword.getText().toString(), edt_newPasswordRepeate.getText().toString());
            if (!updateUserInputCheck.Check())
                throw new UpdateUserInputException(updateUserInputCheck.getErrorMessage());
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_save.setEnabled(false);
                    btn_changePicture.setEnabled(false);
                    new UpdateEntityUser(CurrentUser.get(),edt_firstName.getText().toString(),edt_lastName.getText().toString(),edt_description.getText().toString(),CurrentUser.get().getEmail().toString(),edt_phonenumber.getText().toString(),edt_newPassword.getText().toString(),picture,mActivity);
                    btn_save.setEnabled(true);
                    btn_changePicture.setEnabled(true);
                }
            }, 100);
            ((MainActivity)getActivity()).selectOtherFragment(efragment.SHOW_USER_FRAGMENT);
            CurrentUser.setNull();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }
        catch(UpdateUserInputException e) {
            Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void fillEditTexts(){
        User user= CurrentUser.get();
        tv_email.setText(user.getEmail().toString());

        GetPictureFromFile getPictureFromFile= new GetPictureFromFile(getActivity(),user);
        iv_proiflePicture.setImageBitmap(getPictureFromFile.get());
        picture=getPictureFromFile.get();

        edt_firstName.setText(user.getFirstName().toString());
        edt_lastName.setText(user.getLastName().toString());
        edt_description.setText(user.getDescription().toString());
        edt_phonenumber.setText(user.getPhoneNumber().toString());
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
                final InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                picture = BitmapFactory.decodeStream(imageStream);
                iv_proiflePicture.setImageBitmap(picture);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Etwas ist schiefgelaufen", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "Du hast kein Bild ausgewählt", Toast.LENGTH_LONG).show();
        }
    }

}
