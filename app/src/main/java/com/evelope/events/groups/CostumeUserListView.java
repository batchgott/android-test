package com.evelope.events.groups;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.evelope.events.MainActivity;
import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Group_User;

class CostumeUserListView extends ArrayAdapter<String>{

    private Long[] userID;
    private String[] userName;
    private Bitmap[] pictures;
    private Boolean isAdmin;
    private Long groupID;


    public CostumeUserListView(Activity context,Long[] userID,String[] userName, Bitmap[] pictures,Boolean isAdmin,Long groupID) {
        super(context, R.layout.layout_participants_listview, userName);
        this.userID=userID;
        this.userName=userName;
        this.pictures=pictures;
        this.isAdmin=isAdmin;
        this.groupID=groupID;
    }

    @Nullable
    @Override
    public String getItem(int position){
        return userID[position].toString();
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        ViewHolder mainViewHolder=null;
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.layout_participants_listview,null,true);
        }
        final ViewHolder viewHolder=new ViewHolder();
        viewHolder.userID=userID[position];
        viewHolder.ivUserPicture=(ImageView)convertView.findViewById(R.id.iv_user_picture);
        viewHolder.ivUserPicture.setImageBitmap(pictures[position]);
        viewHolder.tvUserName=(TextView)convertView.findViewById(R.id.tv_user_name);
        viewHolder.tvUserName.setText(userName[position]);
        viewHolder.linearLayout_participants=(LinearLayout)convertView.findViewById(R.id.linearLayout_participants);
        viewHolder.btn_deleteUser=(ImageButton)convertView.findViewById(R.id.btn_deleteUser);
        if (isAdmin)
            viewHolder.btn_deleteUser.setVisibility(View.VISIBLE);
        else
            viewHolder.btn_deleteUser.setVisibility(View.GONE);

        viewHolder.btn_deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Group_User gu=new Group_User();
                        gu.setG_id(groupID);
                        gu.setU_id(userID[position]);
                        AppDatabase.getAppDatabase(getContext()).group_userDao().deleteUserFromGroup(gu);
                    }
                });
                t.start();
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                viewHolder.linearLayout_participants.setVisibility(View.GONE);
            }
        });
        return convertView;
    }

    class ViewHolder{
        Long userID;
        TextView tvUserName;
        ImageView ivUserPicture;
        ImageButton btn_deleteUser;
        LinearLayout linearLayout_participants;
    }
}
