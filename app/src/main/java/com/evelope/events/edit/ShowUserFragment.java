package com.evelope.events.edit;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.User;
import com.evelope.events.efragment;
import com.evelope.events.login.LoginActivity;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.GetPictureFromFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowUserFragment extends Fragment {


    FloatingActionButton fab;
    ImageView iv_proiflePicture;
    TextView tv_name;
    TextView tv_email;
    TextView tv_description;
    TextView tv_phonenumber;
    Button btn_logout;


    public ShowUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_show_user, container, false);

        fab=view.findViewById(R.id.float_edit);
        iv_proiflePicture=view.findViewById(R.id.iv_image);
        tv_name=view.findViewById(R.id.tv_name);
        tv_email=view.findViewById(R.id.tv_email);
        tv_description=view.findViewById(R.id.tv_description);
        tv_phonenumber=view.findViewById(R.id.tv_phonenumber);
        btn_logout=view.findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentUser.Logout();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUser();
            }
        });

        setUserAttributes();

        return view;
    }

    private void editUser(){
        ((MainActivity)getActivity()).selectOtherFragment(efragment.EDIT_USER_FRAGMENT);
    }

    private void setUserAttributes(){
        User user= CurrentUser.get();

        tv_name.setText(user.getFirstName().toString()+" "+user.getLastName().toString());
        tv_email.setText(user.getEmail().toString());
        tv_description.setText(user.getDescription().toString());
        tv_phonenumber.setText(user.getPhoneNumber().toString());

        GetPictureFromFile getPictureFromFile= new GetPictureFromFile(getActivity(),user);
        iv_proiflePicture.setImageBitmap(getPictureFromFile.get());

    }
}
