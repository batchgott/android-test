package com.evelope.events.groups;


import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Group;
import com.evelope.events.database.User;
import com.evelope.events.efragment;
import com.evelope.events.tools.GetPictureFromFile;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupMemberDetailsFragment extends Fragment {

    Activity mActivity;
    User currentUser;
    Group currentGroup;
    ImageView iv_image;
    TextView tv_name;
    TextView tv_email;
    TextView tv_description;
    TextView tv_phonenumber;
    ImageButton ib_close;

    public GroupMemberDetailsFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_group_member_details, container, false);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                currentUser= AppDatabase.getAppDatabase(mActivity).userDao().getUserByID(Long.parseLong(getArguments().getString("userID"), 10));
                currentGroup=AppDatabase.getAppDatabase(mActivity).groupDao().getGroupByID(Long.parseLong(getArguments().getString("groupID"),10));
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        iv_image=(ImageView)view.findViewById(R.id.iv_image);
        GetPictureFromFile gpff=new GetPictureFromFile(mActivity,currentUser);
        iv_image.setImageBitmap(gpff.get());

        tv_name=(TextView)view.findViewById(R.id.tv_name);
        tv_name.setText(currentUser.getFirstName()+" "+currentUser.getLastName());

        tv_email=(TextView)view.findViewById(R.id.tv_email);
        tv_email.setText(currentUser.getEmail());

        tv_description=(TextView)view.findViewById(R.id.tv_description);
        tv_description.setText(currentUser.getEmail());

        tv_phonenumber=(TextView)view.findViewById(R.id.tv_phonenumber);
        tv_phonenumber.setText(currentUser.getPhoneNumber());

        ib_close=(ImageButton)view.findViewById(R.id.ib_close);
        ib_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).selectOtherFragment(efragment.GROUP_DETAILS_FRAGMENT,currentGroup.getG_id());
            }
        });

        return view;
    }

}
