package com.evelope.events.createEvent;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.evelope.events.database.write.createEntity.CreateEntityEvent;
import com.evelope.events.database.write.createEntity.CreateEntityUser_Event;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.exceptions.CreateEventInputException;
import com.evelope.events.R;
import com.evelope.events.tools.inputCheck.CreateEventInputCheck;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    String imgDecodableString;
    Bitmap selectedImage;


    EditText et_eventName,et_eventDescription,et_eventLocation;
    ImageView img_event_picture;

    Button btn_getImage;
    Button btn_createEvent;
    CheckBox cbx_joinEvent;
    Button btn_startDate;
    Button btn_endDate;


    DatePickerFragment startFragment, endFragment;

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
        btn_startDate=(Button) view.findViewById(R.id.btn_chose_start_date);
        btn_endDate=(Button) view.findViewById(R.id.btn_chose_end_date);

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

        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartDatePickerDialog();
            }
        });

        btn_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndDatePickerDialog();
            }
        });


        btn_createEvent.setEnabled(true);
        btn_getImage.setEnabled(true);

        return view;
    }


    private void showStartDatePickerDialog() {
        startFragment= new DatePickerFragment();
        startFragment.show(getActivity().getFragmentManager(),"Start Date Picker");
    }

    private void showEndDatePickerDialog() {
        endFragment= new DatePickerFragment();
        endFragment.show(getActivity().getFragmentManager(),"End Date Picker");
    }



    private void saveEventToDatabase(){
        try {
            createEventInputCheck = new CreateEventInputCheck(et_eventName.getText().toString(), et_eventDescription.getText().toString(), et_eventLocation.getText().toString(), startFragment.getSelectedDay()+"."+startFragment.getSelectedMonth()+"."+startFragment.getSelectedYear(), endFragment.getSelectedDay()+"."+endFragment.getSelectedMonth()+"."+endFragment.getSelectedYear(), selectedImage);
            if (!createEventInputCheck.Check())
                throw new CreateEventInputException(createEventInputCheck.getErrorMessage());
            btn_createEvent.setEnabled(false);
            btn_getImage.setEnabled(false);
            Thread t=new Thread(new Runnable() {
                @Override
                public void run() {
                    CreateEntityEvent createEntityEvent =new CreateEntityEvent(et_eventName.getText().toString(), et_eventDescription.getText().toString(), et_eventLocation.getText().toString(),
                            startFragment.getSelectedDay()+"."+startFragment.getSelectedMonth()+"."+startFragment.getSelectedYear(), endFragment.getSelectedDay()+"."+endFragment.getSelectedMonth()+"."+endFragment.getSelectedYear(), selectedImage, getActivity());
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


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        int selectedYear;
        int selectedMonth;
        int selectedDay;

        public int getSelectedYear() {
            return selectedYear;
        }

        public int getSelectedMonth() {
            return selectedMonth;
        }

        public int getSelectedDay() {
            return selectedDay;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year =c.get(Calendar.YEAR);
            int month=c.get(Calendar.MONTH);
            int day=c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(),this, year, month,day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            selectedDay=dayOfMonth;
            selectedMonth=month;
            selectedYear=year;
        }
    }

}
