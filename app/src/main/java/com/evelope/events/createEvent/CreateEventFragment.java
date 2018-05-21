package com.evelope.events.createEvent;


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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.evelope.events.MainActivity;
import com.evelope.events.database.write.createEntity.CreateEntityEvent;
import com.evelope.events.database.write.createEntity.CreateEntityUser_Event;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.exceptions.CreateEventInputException;
import com.evelope.events.R;
import com.evelope.events.tools.inputCheck.CreateEventInputCheck;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Bitmap selectedImage;


    EditText et_eventName,et_eventDescription,et_eventLocation,et_eventStartDate,et_eventEndDate;
    ImageView img_event_picture;

    Button btn_getImage;
    Button btn_createEvent;
    CheckBox cbx_joinEvent;

    CreateEventInputCheck createEventInputCheck;

    public CreateEventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_event, container, false);

        selectedImage=null;

        et_eventName=(EditText)view.findViewById(R.id.edit_event_name);
        et_eventDescription=(EditText)view.findViewById(R.id.edit_event_description);
        et_eventLocation=(EditText)view.findViewById(R.id.edit_event_location);
        et_eventStartDate=(EditText)view.findViewById(R.id.edit_event_startDate);
        et_eventEndDate=(EditText)view.findViewById(R.id.edit_event_endDate);

        btn_getImage=(Button)view.findViewById(R.id.btn_event_getPicture);
        btn_createEvent=(Button)view.findViewById(R.id.btn_createEvent);
        cbx_joinEvent=(CheckBox)view.findViewById(R.id.cbx_joinEvent);

        img_event_picture=(ImageView)view.findViewById(R.id.img_event_picture);

        btn_getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getImageFromAlbum();
            }
        });
        btn_createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveEventToDatabase();
            }
        });

        btn_createEvent.setEnabled(true);
        btn_getImage.setEnabled(true);

        return view;
    }

    private void saveEventToDatabase(){
        try {
            createEventInputCheck = new CreateEventInputCheck(et_eventName.getText().toString(), et_eventDescription.getText().toString(), et_eventLocation.getText().toString(), et_eventStartDate.getText().toString(), et_eventEndDate.getText().toString(), selectedImage);
            if (!createEventInputCheck.Check())
                throw new CreateEventInputException(createEventInputCheck.getErrorMessage());
            btn_createEvent.setEnabled(false);
            btn_getImage.setEnabled(false);
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    CreateEntityEvent createEntityEvent =new CreateEntityEvent(et_eventName.getText().toString(), et_eventDescription.getText().toString(), et_eventLocation.getText().toString(),
                            et_eventStartDate.getText().toString(), et_eventEndDate.getText().toString(), selectedImage, getActivity());
                    if (cbx_joinEvent.isChecked()==true)
                        new CreateEntityUser_Event(CurrentUser.get().getU_id(), createEntityEvent.getEventID(),getActivity());
                }
            });
            t.start();
            t.join();
            btn_getImage.setEnabled(true);
            btn_createEvent.setEnabled(true);
        }
        catch (CreateEventInputException e)
        {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error beim erstellen des Events", Toast.LENGTH_LONG).show();
        }
        //((MainActivity)getActivity()).selectOtherFragment(efragment.EVENTS_FRAGMENT);
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
                img_event_picture.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Etwas ist schiefgelaufen", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "Du hast kein Bild ausgewählt", Toast.LENGTH_LONG).show();
        }
    }

}
