package com.evelope.events.events;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.User_Event;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.GetPictureFromFile;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

    Activity mActivity;
    private Long eventID;
    private Event currentEvent;
    Boolean subscribed;

    private TextView tv_eventName;
    private ImageView iv_eventImage;
    private TextView tv_eventTime;
    private TextView tv_location;
    private TextView tv_participants;
    private TextView tv_description;
    private ImageButton ib_close;
    private ImageButton ib_subscribe;
    private Button btn_createGroup;
    private Button btn_findGroup;
    private LinearLayout linearLayout_find_create_group;

    public EventDetailsFragment() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        eventID = Long.parseLong(getArguments().getString("eventID"), 10);
        ib_subscribe=(ImageButton)view.findViewById(R.id.ib_subscribe);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db = AppDatabase.getAppDatabase(mActivity);
                currentEvent = db.eventDao().getEventByID(eventID);

                if (db.user_eventDao().User_EventExists(CurrentUser.get().getU_id(),currentEvent.getE_id())==1){
                    subscribed=true;
                    ib_subscribe.setImageResource(R.drawable.ic_star_full);
                }
                else{
                    subscribed=false;
                    ib_subscribe.setImageResource(R.drawable.ic_star_empty);
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tv_eventName = (TextView) view.findViewById(R.id.tv_eventHeader);
        tv_eventName.setText(currentEvent.getName());

        iv_eventImage = (ImageView) view.findViewById(R.id.iv_eventImage);
        GetPictureFromFile gpff = new GetPictureFromFile(mActivity, currentEvent);
        iv_eventImage.setImageBitmap(gpff.get());

        tv_eventTime = (TextView) view.findViewById(R.id.tv_eventTime);
        tv_eventTime.setText(currentEvent.getStartDate().toString() + " - " + currentEvent.getEndDate().toString());

        tv_location = (TextView) view.findViewById(R.id.tv_location);
        tv_location.setText(currentEvent.getLocation().toString());

        tv_participants = (TextView) view.findViewById(R.id.tv_participants);
        tv_participants.setText(currentEvent.getParticipants().toString());

        tv_description = (TextView) view.findViewById(R.id.tv_description);
        tv_description.setText(currentEvent.getDescription().toString());

        ib_close=(ImageButton)view.findViewById(R.id.ib_close);
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribed)
                    ((MainActivity)getActivity()).selectOtherFragment(efragment.EVENTS_FRAGMENT,0);
                else
                    ((MainActivity)getActivity()).selectOtherFragment(efragment.EVENTS_FRAGMENT,1);
            }
        });


        ib_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (subscribed){
                    subscribed=false;
                    ib_subscribe.setImageResource(R.drawable.ic_star_empty);
                    linearLayout_find_create_group.setVisibility(View.GONE);
                }
                else {
                    subscribed=true;
                    ib_subscribe.setImageResource(R.drawable.ic_star_full);
                    linearLayout_find_create_group.setVisibility(View.VISIBLE);
                }


                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase db=AppDatabase.getAppDatabase(mActivity);
                        User_Event ue=new User_Event();
                        ue.setE_id(currentEvent.getE_id());
                        ue.setU_id(CurrentUser.get().getU_id());
                        if (subscribed)
                            db.user_eventDao().insertUser_Event(ue);
                        else
                            db.user_eventDao().deleteUser_Event(currentEvent.getE_id(),CurrentUser.get().getU_id());
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        //TODO Button sind ausgegraut wenn User bereits Mitglied einer Gruppe ist
        btn_createGroup=(Button)view.findViewById(R.id.btn_createGroup);
        btn_findGroup=(Button)view.findViewById(R.id.btn_findGroup);
        linearLayout_find_create_group=(LinearLayout) view.findViewById(R.id.linearlayout_find_create_groups);
        if (subscribed)
            linearLayout_find_create_group.setVisibility(View.VISIBLE);
        else
            linearLayout_find_create_group.setVisibility(View.GONE);

        btn_createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateGroupFragment();
            }
        });


        return view;
    }

    private void openCreateGroupFragment(){
        ((MainActivity)getActivity()).selectOtherFragment(efragment.CREATE_GROUP_FRAGMENT,currentEvent.getE_id());
    }

}
