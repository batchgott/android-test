package com.evelope.events.createGroup;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.write.createEntity.CreateEntityGroup;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.exceptions.CreateGroupInputException;
import com.evelope.events.tools.inputCheck.CreateGroupInputCheck;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateGroupFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Bitmap selectedImage;

    private Event currentEvent;
    private Activity mActivity;

    private TextView tv_createGroupHeader;
    private EditText et_groupName;
    private EditText et_groupDescription;
    private EditText et_groupMaxParticipants;
    private EditText et_groupMeetingPoint;
    private EditText et_groupMeetingTime;
    private Button btn_group_getPicture;
    private Button btn_createGroup;
    private ImageView iv_group_picture;
    private CheckBox cbx_drink;
    private CheckBox cbx_dance;
    private CheckBox cbx_sing;
    private CheckBox cbx_smoke;
    private CheckBox cbx_talk;
    private CheckBox cbx_play;

    CreateGroupInputCheck createGroupInputCheck;

    public CreateGroupFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_group, container, false);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db=AppDatabase.getAppDatabase(mActivity);
                Long eventID=Long.parseLong(getArguments().getString("eventID"),10);
                currentEvent=db.eventDao().getEventByID(eventID);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        selectedImage=null;
        tv_createGroupHeader=(TextView)view.findViewById(R.id.tv_createGroupHeader);
        tv_createGroupHeader.setText("Gruppe für \""+currentEvent.getName()+"\" erstellen");

        et_groupName=(EditText)view.findViewById(R.id.et_groupName);
        et_groupDescription=(EditText)view.findViewById(R.id.et_groupDescription);
        et_groupMaxParticipants=(EditText)view.findViewById(R.id.et_groupMaxParticipants);
        et_groupMeetingPoint=(EditText)view.findViewById(R.id.et_groupMeetingPoint);
        et_groupMeetingTime=(EditText)view.findViewById(R.id.et_groupMeetingTime);

        btn_group_getPicture=(Button)view.findViewById(R.id.btn_group_getPicture);
        btn_group_getPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFromAlbum();
            }
        });
        iv_group_picture=(ImageView)view.findViewById(R.id.iv_group_picture);

        btn_createGroup=(Button)view.findViewById(R.id.btn_createGroup);
        btn_createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveGroupToDatabase();
            }
        });
        cbx_drink=(CheckBox)view.findViewById(R.id.cbx_drink);
        cbx_dance=(CheckBox)view.findViewById(R.id.cbx_dance);
        cbx_sing=(CheckBox)view.findViewById(R.id.cbx_sing);
        cbx_smoke=(CheckBox)view.findViewById(R.id.cbx_smoke);
        cbx_talk=(CheckBox)view.findViewById(R.id.cbx_talk);
        cbx_play=(CheckBox)view.findViewById(R.id.cbx_play);


        return view;
    }


    private void saveGroupToDatabase(){
        Boolean createGroup=true;
        try {
        createGroupInputCheck=new CreateGroupInputCheck(et_groupName.getText().toString(),et_groupDescription.getText().toString(),et_groupMaxParticipants.getText().toString(),et_groupMeetingPoint.getText().toString(),et_groupMeetingTime.getText().toString(),selectedImage);
        if (!createGroupInputCheck.Check()) {
            createGroup=false;
            throw new CreateGroupInputException(createGroupInputCheck.getErrorMessage());
        }
        btn_createGroup.setEnabled(false);
        btn_group_getPicture.setEnabled(false);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                new CreateEntityGroup(et_groupName.getText().toString(),et_groupDescription.getText().toString(),et_groupMaxParticipants.getText().toString(),et_groupMeetingPoint.getText().toString(),et_groupMeetingTime.getText().toString(),currentEvent.getE_id(), CurrentUser.get().getU_id(),cbx_drink.isChecked(),cbx_dance.isChecked(),cbx_sing.isChecked(),cbx_smoke.isChecked(),cbx_talk.isChecked(),cbx_play.isChecked(),selectedImage,mActivity);
            }
        });
        t.start();
        t.join();
        btn_group_getPicture.setEnabled(true);
        btn_createGroup.setEnabled(true);
        } catch (CreateGroupInputException e) {
               Toast.makeText(mActivity,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (createGroup)
        ((MainActivity)getActivity()).setTabID(1);
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
                selectedImage = BitmapFactory.decodeStream(imageStream);
                iv_group_picture.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Etwas ist schiefgelaufen", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "Du hast kein Bild ausgewählt", Toast.LENGTH_LONG).show();
        }
    }
}
