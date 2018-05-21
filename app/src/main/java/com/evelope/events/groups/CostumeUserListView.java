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
import android.widget.ImageView;
import android.widget.TextView;

import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;

class CostumeUserListView extends ArrayAdapter<String>{

    private Long[] userID;
    private String[] userName;
    private Bitmap[] pictures;
    private Activity context;


    public CostumeUserListView(Activity context,Long[] userID,String[] userName, Bitmap[] pictures) {
        super(context, R.layout.layout_participants_listview, userName);
        this.context=context;
        this.userID=userID;
        this.userName=userName;
        this.pictures=pictures;
    }

    @Nullable
    @Override
    public String getItem(int position){
        return userID[position].toString();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        return convertView;
    }

    class ViewHolder{
        Long userID;
        TextView tvUserName;
        ImageView ivUserPicture;
    }
}
