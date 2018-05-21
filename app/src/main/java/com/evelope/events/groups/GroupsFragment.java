package com.evelope.events.groups;


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
import com.evelope.events.database.Group;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment {

    ListView lvGroups;
    List<Group> allGroupList;
    List<GroupForList> allGroupListForLV;
    Long[] groupID;
    String[] groupName;
    String[] groupDescription;
    Bitmap[] pictures;
    String[] eventName;
    Long[] eventID;
    Long[] adminID;

    public GroupsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_groups, container, false);
        lvGroups=(ListView)view.findViewById(R.id.groupList);
        allGroupList=new ArrayList<>();
        allGroupListForLV=new ArrayList<>();
        setAdapterForLV();
        lvGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long groupIDforFragment=Long.parseLong((String) parent.getAdapter().getItem(position),10);
                ((MainActivity)getActivity()).selectOtherFragment(efragment.GROUP_DETAILS_FRAGMENT,groupIDforFragment);
            }
        });
        return view;
    }

    private void setAdapterForLV(){
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase db=AppDatabase.getAppDatabase(getActivity());
                if (db.groupDao().getGroupsByUserID(CurrentUser.get().getU_id())!=null)
                    allGroupList=db.groupDao().getGroupsByUserID(CurrentUser.get().getU_id());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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
            CostumeGroupListView costumeGroupListView=new CostumeGroupListView(getActivity(),groupID,groupName,groupDescription,pictures,eventID,eventName,adminID);
            lvGroups.setAdapter(costumeGroupListView);
        }
    }

}
