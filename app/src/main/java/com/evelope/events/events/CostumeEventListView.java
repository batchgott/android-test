package com.evelope.events.events;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.evelope.events.R;
import com.evelope.events.database.AppDatabase;
import com.evelope.events.database.User_Event;
import com.evelope.events.database.write.createEntity.CreateEntityUser_Event;
import com.evelope.events.database.write.deleteEntity.DeleteEntityUser_Event;
import com.evelope.events.tools.CurrentUser;


public class CostumeEventListView extends ArrayAdapter<String> {

    private Long[] eventID;
    private String[] eventName;
    private String[] eventDescription;
    private Bitmap[] eventPictures;
    private Activity context;
    private Boolean belongsToUser;


    public CostumeEventListView(Activity context,String[] eventName,String[] eventDescription,Bitmap[] eventPictures,Long[] eventID,Boolean belongsToUser) {
        super(context, R.layout.layout_events_listview,eventName);
        this.context=context;
        this.eventName=eventName;
        this.eventDescription=eventDescription;
        this.eventPictures=eventPictures;
        this.eventID=eventID;
        this.belongsToUser=belongsToUser;
    }

    @Nullable
    @Override
    public String getItem(int position){
        return eventID[position].toString();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mainViewHolder=null;
        if (convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.layout_events_listview,null,true);
        }
            final ViewHolder viewHolder=new ViewHolder();
            if (!belongsToUser)
                viewHolder.subscribed=false;
            else
                viewHolder.subscribed=true;
            viewHolder.eventID=eventID[position];
            viewHolder.btn_star=(ImageButton)convertView.findViewById(R.id.btn_subscribeEvent);
            if (!belongsToUser)
                viewHolder.btn_star.setImageResource(R.drawable.ic_star_empty);
            else
                viewHolder.btn_star.setImageResource(R.drawable.ic_star_full);
            viewHolder.ivEventLogo=(ImageView) convertView.findViewById(R.id.eventLogo);
            viewHolder.ivEventLogo.setImageBitmap(eventPictures[position]);
            viewHolder.tvEventName=(TextView)convertView.findViewById(R.id.tvEventName);
            viewHolder.tvEventDescription=(TextView)convertView.findViewById(R.id.tvEventDescription);
            viewHolder.tvEventDescription.setText(eventDescription[position]);
            viewHolder.tvEventName.setText(eventName[position]);
            viewHolder.btn_star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (!viewHolder.subscribed) {
                            viewHolder.btn_star.setImageResource(R.drawable.ic_star_full);
                            viewHolder.subscribed=true;
                        }
                        else {
                            viewHolder.btn_star.setImageResource(R.drawable.ic_star_empty);
                            viewHolder.subscribed=false;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User_Event ue=new User_Event();
                                ue.setU_id(CurrentUser.get().getU_id());
                                ue.setE_id(viewHolder.eventID);
                                if (viewHolder.subscribed)
                                    new CreateEntityUser_Event(CurrentUser.get().getU_id(),viewHolder.eventID,getContext());
                                else
                                    new DeleteEntityUser_Event(CurrentUser.get().getU_id(),viewHolder.eventID,getContext());
                            }
                        }).start();
                }
            });
        return convertView;
    }



    class ViewHolder {
        Long eventID;
        TextView tvEventName;
        TextView tvEventDescription;
        ImageView ivEventLogo;
        Boolean subscribed;
        ImageButton btn_star;

//        public ViewHolder(View v) {
//            tvEventName=v.findViewById(R.id.tvEventName);
//            tvEventDescription=v.findViewById(R.id.tvEventDescription);
//            ivEventLogo=v.findViewById(R.id.eventLogo);
//            btn_star=v.findViewById(R.id.btn_subscribeEvent);
//            subscribed=false;
//        }
    }
}
