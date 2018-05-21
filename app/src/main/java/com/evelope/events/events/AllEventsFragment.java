package com.evelope.events.events;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllEventsFragment extends Fragment {

    ListView lvEvents;
    List<Event> allEventList;
    List<EventForList> allEventsListForLV;
    Long[] eventID;
    String[] eventName;
    String[] eventDescription;
    Bitmap[] pictures;


    private String title;
    private int page;


    public static AllEventsFragment newInstance(int page, String title) {
        AllEventsFragment fragmentSecond = new AllEventsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentSecond.setArguments(args);
        return fragmentSecond;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_events, container, false);
        lvEvents=(ListView)view.findViewById(R.id.eventList);
        allEventList =new ArrayList<>();
        allEventsListForLV=new ArrayList<>();
        setAdapterForLV();

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long eventIDforFragment=Long.parseLong((String) parent.getAdapter().getItem(position),10);
                ((MainActivity)getActivity()).selectOtherFragment(efragment.EVENT_DETAILS_FRAGMENT,eventIDforFragment);
            }
        });

        return view;
    }

    //TODO auf Datenbank wird nur zugegriffen, wenn es wirklich einen neuen Eintrag gibt
    public void setAdapterForLV(){

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db= AppDatabase.getAppDatabase(getActivity());
                if (db.eventDao().getAllEventsExceptOf(db.eventDao().getEventIDsByUserID(CurrentUser.get().getU_id()))!=null)
                allEventList =db.eventDao().getAllEventsExceptOf(db.eventDao().getEventIDsByUserID(CurrentUser.get().getU_id()));
            }
        });
        t.start();
        try
        {
            t.join();
        }
        catch(InterruptedException ex)
        {
        }
        if (allEventList !=null&& allEventList.size()!=0) {
            allEventsListForLV=new ArrayList<>();
            for (Event e : allEventList) {
                allEventsListForLV.add(new EventForList(e, getActivity()));
            }
            eventID = new Long[allEventsListForLV.size()];
            eventName = new String[allEventsListForLV.size()];
            eventDescription = new String[allEventsListForLV.size()];
            pictures = new Bitmap[allEventsListForLV.size()];
            for (int i = 0; i < allEventsListForLV.size(); i++) {
                eventID[i] = allEventsListForLV.get(i).getEvent().getE_id();
                eventName[i] = allEventsListForLV.get(i).getEvent().getName();
                eventDescription[i] = allEventsListForLV.get(i).getEvent().getDescription();
                pictures[i] = allEventsListForLV.get(i).getBitmap();
            }


            CostumeEventListView costumeEventListView = new CostumeEventListView(this.getActivity(), eventName, eventDescription, pictures, eventID,false);
            lvEvents.setAdapter(costumeEventListView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapterForLV();
    }

    @Override
    public void onStart() {
        super.onStart();
        setAdapterForLV();
    }

}
