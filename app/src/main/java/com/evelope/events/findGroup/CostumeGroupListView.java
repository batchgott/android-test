package com.evelope.events.findGroup;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.Categorie;
import com.evelope.events.tools.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class CostumeGroupListView extends ArrayAdapter<String> {

    private Long[] groupID;
    private String[] groupName;
    private String[] groupDescription;
    private Bitmap[] pictures;
    private Long[] eventID;
    private Long[] adminID;
    private String[] eventName;


    public CostumeGroupListView(Activity context, Long[] groupID, String[] groupName, String[] groupDescription, Bitmap[] pictures, Long[] eventID, String[] eventName, Long[] adminID) {
        super(context, R.layout.layout_groups_listview, groupName);
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.pictures = pictures;
        this.eventID = eventID;
        this.eventName = eventName;
        this.adminID = adminID;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return groupID[position].toString();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.layout_groups_listview, null, true);
        }
        final ViewHolder viewHolder = new ViewHolder();
        viewHolder.relativeLayout_groups_listview = (RelativeLayout) convertView.findViewById(R.id.relativelayout_groups_listview);
        viewHolder.eventID = eventID[position];
        viewHolder.groupID = groupID[position];
        viewHolder.ivGroupPicture = (ImageView) convertView.findViewById(R.id.iv_group_picture);
        viewHolder.tv_eventName = (TextView) convertView.findViewById(R.id.tv_event_name);
        viewHolder.tv_groupDescription = (TextView) convertView.findViewById(R.id.tv_group_description);
        viewHolder.tv_groupName = (TextView) convertView.findViewById(R.id.tv_group_name);
        viewHolder.ivC1 = (ImageView) convertView.findViewById(R.id.ivCategorie1);
        viewHolder.ivC2 = (ImageView) convertView.findViewById(R.id.ivCategorie2);
        viewHolder.ivC3 = (ImageView) convertView.findViewById(R.id.ivCategorie3);
        viewHolder.ivC4 = (ImageView) convertView.findViewById(R.id.ivCategorie4);
        viewHolder.ivC5 = (ImageView) convertView.findViewById(R.id.ivCategorie5);
        viewHolder.ivC6 = (ImageView) convertView.findViewById(R.id.ivCategorie6);
        viewHolder.relativeLayout_groups_listview.setBackgroundResource(R.drawable.bb_bottom_bar_top_shadow);
        List<Categorie> categorieList = new ArrayList<>();
        //region set Categorie Pictures
         categorieList=AppDatabase.getAppDatabase(getContext()).categorieDao().getCategoriesForGroupID(groupID[position]);
         Integer count=1;
            for (Categorie c : categorieList) {
                ImageView concreteView=null;
                if(count==1) {
                     concreteView=viewHolder.ivC1;
                }
                if(count==2) {
                     concreteView=viewHolder.ivC2;
                }
                if(count==3) {
                     concreteView=viewHolder.ivC3;
                }
                if(count==4) {
                     concreteView=viewHolder.ivC4;
                }
                if(count==5) {
                     concreteView=viewHolder.ivC5;
                }
                if(count==6) {
                     concreteView=viewHolder.ivC6;
                }
                if (c.getName().equals("drink")) {
                    concreteView.setImageResource(R.drawable.icon_drink_without_padding);
                    count++;
                }
                if (c.getName().equals("dance")) {
                    concreteView.setImageResource(R.drawable.icon_dance_without_padding);
                    count++;
                }
                if (c.getName().equals("sing")) {
                    concreteView.setImageResource(R.drawable.icon_sing_without_padding);
                    count++;
                }
                if (c.getName().equals("smoke")) {
                    concreteView.setImageResource(R.drawable.icon_smoking_without_padding);
                    count++;
                }
                if (c.getName().equals("talk")) {
                    concreteView.setImageResource(R.drawable.icon_talk_without_padding);
                    count++;
                }
                if (c.getName().equals("play")) {
                    concreteView.setImageResource(R.drawable.icon_play_without_padding);
                    count++;
                }

            }

        //endregion
        viewHolder.tv_groupName.setText(groupName[position]);
        viewHolder.tv_eventName.setVisibility(View.GONE);
        viewHolder.tv_groupDescription.setText(groupDescription[position]);
        viewHolder.ivGroupPicture.setImageBitmap(pictures[position]);
        return convertView;
    }

    class ViewHolder {
        RelativeLayout relativeLayout_groups_listview;
        Long groupID;
        Long eventID;
        TextView tv_eventName;
        TextView tv_groupName;
        TextView tv_groupDescription;
        ImageView ivGroupPicture;
        ImageView ivC1, ivC2, ivC3, ivC4, ivC5, ivC6;
        Boolean categoriesAlreadySet=false;
    }
}
