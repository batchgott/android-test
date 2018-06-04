package com.evelope.events.groups;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Event;
import com.evelope.events.database.Group;
import com.evelope.events.database.Group_User;
import com.evelope.events.database.User;
import com.evelope.events.efragment;
import com.evelope.events.tools.CurrentUser;
import com.evelope.events.tools.GetPictureFromFile;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupDetailsFragment extends Fragment {

    Activity mActivity;
    private Long groupID;
    private Group currentGroup;
    private Event currentEvent;
    Integer amountParticipants;
    Boolean admin;

    TextView tv_group_name;
    ImageView iv_group_picture;
    TextView tv_event_name;
    TextView tv_group_description;
    TextView tv_group_participants;
    TextView tv_group_max_participants;
    TextView tv_group_meeting_place;
    TextView tv_group_meeting_time;
    ListView lvParticipants;
    Button btn_leaveGroup;
    List<User> allUsersList;
    List<UserForList> allUsersForList;
    Long[] userID;
    String[] userName;
    Bitmap[] pictures;

    public GroupDetailsFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group_details, container, false);
        groupID = Long.parseLong(getArguments().getString("groupID"), 10);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                currentGroup= AppDatabase.getAppDatabase(mActivity).groupDao().getGroupByID(groupID);
                currentEvent=AppDatabase.getAppDatabase(mActivity).eventDao().getEventByID(currentGroup.getE_id());
                amountParticipants=AppDatabase.getAppDatabase(mActivity).group_userDao().countParticipantsForGroupID(groupID);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tv_group_name=(TextView)view.findViewById(R.id.tv_group_details_header);
        tv_group_name.setText(currentGroup.getName());

        iv_group_picture=(ImageView)view.findViewById(R.id.iv_group_details_picture);
        GetPictureFromFile gpff=new GetPictureFromFile(mActivity,currentGroup);
        iv_group_picture.setImageBitmap(gpff.get());

        tv_event_name=(TextView)view.findViewById(R.id.tv_group_details_event_name);
        tv_event_name.setText("Gruppe für "+currentEvent.getName());

        tv_group_description=(TextView)view.findViewById(R.id.tv_group_details_description);
        tv_group_description.setText(currentGroup.getDescription());

        tv_group_participants=(TextView)view.findViewById(R.id.tv_group_details_participants);
        tv_group_participants.setText(amountParticipants.toString());

        tv_group_max_participants=(TextView)view.findViewById(R.id.tv_group_details_max_participants);
        tv_group_max_participants.setText(currentGroup.getMax_participants().toString());

        tv_group_meeting_place=(TextView)view.findViewById(R.id.tv_group_details_meeting_point);
        tv_group_meeting_place.setText(currentGroup.getMeeting_point());

        tv_group_meeting_time=(TextView)view.findViewById(R.id.tv_group_details_meeting_time);
        tv_group_meeting_time.setText(currentGroup.getMeeting_time());

        lvParticipants=(ListView)view.findViewById(R.id.lv_participants);
        allUsersList=new ArrayList<>();
        allUsersForList=new ArrayList<>();
        setAdapterForLV();

        lvParticipants.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long groupMemberID=Long.parseLong((String) parent.getAdapter().getItem(position),10);
                ((MainActivity)getActivity()).selectOtherFragment(efragment.GROUP_MEMBER_DETAILS_FRAGMENT,groupMemberID,currentGroup.getG_id());
            }
        });

        btn_leaveGroup=(Button)view.findViewById(R.id.btn_leaveGroup);
        btn_leaveGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity)
                        .setMessage("Wollen Sie die Gruppe wirklich verlassen?")
                        .setCancelable(false)
                        .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                leaveGroup();
                            }
                        })
                        .setNegativeButton("Nein", null)
                        .show();
            }
        });

        Utility.setListViewHeightBasedOnChildren(lvParticipants);

        return view;
    }

    public void leaveGroup() {
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                Group_User gu=new Group_User();
                gu.setU_id(CurrentUser.get().getU_id());
                gu.setG_id(currentGroup.getG_id());
                AppDatabase.getAppDatabase(mActivity).group_userDao().deleteUserFromGroup(gu);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ((MainActivity)getActivity()).selectOtherFragment(efragment.GROUPS_FRAGMENT);
    }

    private void setAdapterForLV() {


        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                if (AppDatabase.getAppDatabase(mActivity).userDao().getUserForGroupID(currentGroup.getG_id())!=null)
                allUsersList=AppDatabase.getAppDatabase(mActivity).userDao().getUserForGroupID(currentGroup.getG_id());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (allUsersList!=null &&allUsersList.size()!=0)
        {
            for (User u :allUsersList){
                allUsersForList.add(new UserForList(u,mActivity));
            }
            userID=new Long[allUsersForList.size()];
            userName=new String[allUsersForList.size()];
            pictures=new Bitmap[allUsersForList.size()];
            for (int i=0; i<allUsersForList.size();i++){
                userID[i]=allUsersForList.get(i).getUser().getU_id();
                userName[i]=allUsersForList.get(i).getUser().getFirstName()+" "+allUsersForList.get(i).getUser().getLastName();
                pictures[i]=allUsersForList.get(i).getBitmap();
            }
        }
        CostumeUserListView costumeUserListView=new CostumeUserListView(mActivity, userID, userName, pictures);
        lvParticipants.setAdapter(costumeUserListView);
    }

}
