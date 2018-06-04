package com.evelope.events.findGroup;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.efragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindGroupFragment extends Fragment {


    private Activity mActivity;
    private Event currentEvent;

    private CheckBox cbx_drink;
    private CheckBox cbx_dance;
    private CheckBox cbx_sing;
    private CheckBox cbx_smoke;
    private CheckBox cbx_talk;
    private CheckBox cbx_play;

    private TextView tv_banner;
    private Button btn_search;
    private Button btn_back;


    public FindGroupFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find_group, container, false);

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
        cbx_drink=(CheckBox)view.findViewById(R.id.cbx_drink);
        cbx_dance=(CheckBox)view.findViewById(R.id.cbx_dance);
        cbx_sing=(CheckBox)view.findViewById(R.id.cbx_sing);
        cbx_smoke=(CheckBox)view.findViewById(R.id.cbx_smoke);
        cbx_talk=(CheckBox)view.findViewById(R.id.cbx_talk);
        cbx_play=(CheckBox)view.findViewById(R.id.cbx_play);

        tv_banner=(TextView)view.findViewById(R.id.tv_findGroupHeader);
        tv_banner.setText("Welche Kategorien sind Ihnen für das Event '"+currentEvent.getName()+"' wichtig?");

        btn_search=(Button)view.findViewById(R.id.btn_searchGroup);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListGroupsFragment();
            }
        });

        btn_back=(Button)view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).selectOtherFragment(efragment.EVENT_DETAILS_FRAGMENT,currentEvent.getE_id());
            }
        });

        return view;

    }

    private void openListGroupsFragment(){
        ((MainActivity)getActivity()).selectOtherFragment(efragment.LIST_GROUPS_FRAGMENT,currentEvent.getE_id(),cbx_drink.isChecked(),
                cbx_dance.isChecked(), cbx_sing.isChecked(),cbx_smoke.isChecked(),cbx_talk.isChecked(),cbx_play.isChecked());
    }
}
