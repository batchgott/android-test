package com.evelope.events.findGroup;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.Group;
import com.evelope.events.efragment;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListGroupsFragment extends Fragment {

    private Activity mActivity;
    private Boolean cat1,cat2,cat3,cat4,cat5,cat6;
    private Event currentEvent;
    private TextView tv_noGroupsFound;
    private Button btn_noGroupsFound;

    ListView lvGroups;
    List<Group> allGroupList;
    List<Group> cat1List;
    List<Group> cat2List;
    List<Group> cat3List;
    List<Group> cat4List;
    List<Group> cat5List;
    List<Group> cat6List;
    List<GroupForList> allGroupListForLV;
    Long[] groupID;
    String[] groupName;
    String[] groupDescription;
    Bitmap[] pictures;
    String[] eventName;
    Long[] eventID;
    Long[] adminID;


    public ListGroupsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_groups, container, false);
        cat1=getArguments().getBoolean("cat1");
        cat2=getArguments().getBoolean("cat2");
        cat3=getArguments().getBoolean("cat3");
        cat4=getArguments().getBoolean("cat4");
        cat5=getArguments().getBoolean("cat5");
        cat6=getArguments().getBoolean("cat6");

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

        lvGroups=(ListView)view.findViewById(R.id.groupList);
        allGroupList=new ArrayList<>();
        allGroupListForLV=new ArrayList<>();
        setAdapterForLV();

        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long groupID=Long.parseLong((String) parent.getAdapter().getItem(position),10);
                ((MainActivity)getActivity()).selectOtherFragment(efragment.GROUP_LESS_DETAILS_FRAGMENT,groupID);
            }
        });

        btn_noGroupsFound=(Button)view.findViewById(R.id.btn_noGroupsFound);
        btn_noGroupsFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).selectOtherFragment(efragment.FIND_GROUP_FRAGMENT,currentEvent.getE_id());
            }
        });

        tv_noGroupsFound=(TextView)view.findViewById(R.id.tv_noGroupsFound);

        if (lvGroups.getCount()==0){
            btn_noGroupsFound.setVisibility(View.VISIBLE);
            tv_noGroupsFound.setVisibility(View.VISIBLE);
        }
        else {
            btn_noGroupsFound.setVisibility(View.GONE);
            tv_noGroupsFound.setVisibility(View.GONE);
        }

        return view;
    }

    private void setAdapterForLV(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db=AppDatabase.getAppDatabase(getActivity());
                if (cat1)
                    cat1List=db.groupDao().getGroupForCategoryIDandEventID(0l,currentEvent.getE_id());
                else
                    cat1List=new ArrayList<>();
                if (cat2)
                    cat2List=db.groupDao().getGroupForCategoryIDandEventID(1l,currentEvent.getE_id());
                else
                    cat2List=new ArrayList<>();
                if (cat3)
                    cat3List=db.groupDao().getGroupForCategoryIDandEventID(2l,currentEvent.getE_id());
                else
                    cat3List=new ArrayList<>();
                if (cat4)
                    cat4List=db.groupDao().getGroupForCategoryIDandEventID(3l,currentEvent.getE_id());
                else
                    cat4List=new ArrayList<>();
                if (cat5)
                    cat5List=db.groupDao().getGroupForCategoryIDandEventID(4l,currentEvent.getE_id());
                else
                    cat5List=new ArrayList<>();
                if (cat6)
                    cat6List=db.groupDao().getGroupForCategoryIDandEventID(5l,currentEvent.getE_id());
                else
                    cat6List=new ArrayList<>();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Group g:cat1List
             ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }
        for (Group g:cat2List
                ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }
        for (Group g:cat3List
                ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }
        for (Group g:cat4List
                ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }
        for (Group g:cat5List
                ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }
        for (Group g:cat6List
                ) {
            if (!allGroupList.contains(g))
                allGroupList.add(g);
        }

        if (allGroupList!=null && allGroupList.size()!=0){
            allGroupListForLV=new ArrayList<>();
            for (Group g : allGroupList){
                allGroupListForLV.add(new GroupForList(g,getActivity()));
            }
            groupID=new Long[allGroupListForLV.size()];
            groupName=new String[allGroupListForLV.size()];
            groupDescription=new String[allGroupListForLV.size()];
            pictures=new Bitmap[allGroupListForLV.size()];
            eventID=new Long[allGroupListForLV.size()];
            eventName=new String[allGroupListForLV.size()];
            adminID=new Long[allGroupListForLV.size()];
            for (int i=0;i<allGroupListForLV.size();i++){
                groupID[i]=allGroupListForLV.get(i).getGroup().getG_id();
                groupName[i]=allGroupListForLV.get(i).getGroup().getName();
                groupDescription[i]=allGroupListForLV.get(i).getGroup().getDescription();
                pictures[i]=allGroupListForLV.get(i).getBitmap();
                eventID[i]=allGroupListForLV.get(i).getEventID();
                eventName[i]=allGroupListForLV.get(i).getEventName();
                adminID[i]=allGroupListForLV.get(i).getGroup().getAdmin_id();
            }
            com.evelope.events.findGroup.CostumeGroupListView costumeGroupListView=new com.evelope.events.findGroup.CostumeGroupListView(getActivity(),groupID,groupName,groupDescription,pictures,eventID,eventName,adminID);
            lvGroups.setAdapter(costumeGroupListView);
        }
    }

}
