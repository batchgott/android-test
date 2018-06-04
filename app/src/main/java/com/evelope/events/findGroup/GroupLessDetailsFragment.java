package com.evelope.events.findGroup;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
public class GroupLessDetailsFragment extends Fragment {

    private Activity mActivity;
    private Group currentGroup;
    private Event currentEvent;

    private TextView tv_header;
    private ImageView iv_picture;
    private TextView tv_event_name;
    private TextView tv_description;
    private ListView lv_participants;
    private Button btn_joinGroup;

    List<User> allUsersList;
    List<UserForList> allUsersForList;
    Long[] userID;
    String[] userName;
    Bitmap[] pictures;

    public GroupLessDetailsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group_less_details, container, false);

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                currentGroup= AppDatabase.getAppDatabase(mActivity).groupDao().getGroupByID(Long.parseLong(getArguments().getString("groupID"), 10));
                currentEvent=AppDatabase.getAppDatabase(mActivity).eventDao().getEventByID(currentGroup.getE_id());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tv_header=(TextView)view.findViewById(R.id.tv_group_details_header);
        tv_header.setText(currentGroup.getName());

        iv_picture=(ImageView)view.findViewById(R.id.iv_group_details_picture);
        GetPictureFromFile gpff=new GetPictureFromFile(mActivity,currentGroup);
        iv_picture.setImageBitmap(gpff.get());

        tv_event_name=(TextView) view.findViewById(R.id.tv_group_details_event_name);
        tv_event_name.setText(currentEvent.getName());

        tv_description=(TextView)view.findViewById(R.id.tv_group_details_description);
        tv_description.setText(currentGroup.getDescription());

        lv_participants=(ListView)view.findViewById(R.id.lv_participants);

        allUsersList=new ArrayList<>();
        allUsersForList=new ArrayList<>();
        setAdapterForLV();

        btn_joinGroup=(Button)view.findViewById(R.id.btn_joinGroup);
        btn_joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinGroup();
            }
        });

        Utility.setListViewHeightBasedOnChildren(lv_participants);

        return view;
    }

    private void joinGroup(){
        final Group_User gu=new Group_User();
        gu.setU_id(CurrentUser.get().getU_id());
        gu.setG_id(currentGroup.getG_id());

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase.getAppDatabase(mActivity).group_userDao().insertGroup_User(gu);
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ((MainActivity)getActivity()).setTabID(1);
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
        lv_participants.setAdapter(costumeUserListView);
    }

}
