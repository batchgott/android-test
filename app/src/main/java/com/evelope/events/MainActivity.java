package com.evelope.events;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.evelope.events.createEvent.CreateEventFragment;
import com.evelope.events.createGroup.CreateGroupFragment;
import com.evelope.events.edit.EditUserFragment;
import com.evelope.events.edit.ShowUserFragment;
import com.evelope.events.events.EventDetailsFragment;
import com.evelope.events.events.EventsFragment;
import com.evelope.events.groups.GroupDetailsFragment;
import com.evelope.events.groups.GroupsFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


public class MainActivity extends AppCompatActivity {

    EventsFragment fragmentEvents;
    GroupsFragment fragmentGroups;
    CreateEventFragment fragmentCreateEvents;
    ShowUserFragment fragmentShowUser;
    EditUserFragment fragmentEditUser;
    EventDetailsFragment fragmentEventDetails;
    CreateGroupFragment fragmentCreateGroup;
    GroupDetailsFragment fragmentGroupDetails;
    FragmentTransaction transaction;

    BottomBar bottomBar;
    Boolean pressedBackOnce;
    efragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pressedBackOnce=false;

        bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_e) {
                    fragmentEvents=new EventsFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundl=new Bundle();
                    bundl.putString("page","0");
                    fragmentEvents.setArguments(bundl);
                    transaction.replace(R.id.contentContainer, fragmentEvents);
                    transaction.commit();
                    currentFragment=efragment.EVENTS_FRAGMENT;
                }
                if (tabId == R.id.tab_group) {
                    fragmentGroups=new GroupsFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, fragmentGroups);
                    transaction.commit();
                    currentFragment=efragment.GROUPS_FRAGMENT;
                }
                if (tabId == R.id.tab_createEvent) {
                    fragmentCreateEvents=new CreateEventFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, fragmentCreateEvents);
                    transaction.commit();
                    currentFragment=efragment.CREATE_EVENTS_FRAGMENT;
                }
                if (tabId == R.id.tab_settings) {
                    fragmentShowUser=new ShowUserFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.contentContainer, fragmentShowUser);
                    transaction.commit();
                    currentFragment=efragment.SHOW_USER_FRAGMENT;
                }
            }
        });
    }
    public void selectOtherFragment(efragment fragment){
        if (fragment==efragment.EVENTS_FRAGMENT){
            fragmentEvents=new EventsFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            Bundle bundl=new Bundle();
            bundl.putString("page","0");
            fragmentEvents.setArguments(bundl);
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
            currentFragment=efragment.EVENTS_FRAGMENT;
        }
        if (fragment==efragment.EDIT_USER_FRAGMENT) {
            fragmentEditUser=new EditUserFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEditUser);
            transaction.commit();
            currentFragment=efragment.EDIT_USER_FRAGMENT;
        }
        if (fragment==efragment.SHOW_USER_FRAGMENT) {
            fragmentShowUser=new ShowUserFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentShowUser);
            transaction.commit();
            currentFragment=efragment.SHOW_USER_FRAGMENT;
        }
        if (fragment==efragment.GROUPS_FRAGMENT) {
            fragmentGroups=new GroupsFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroups);
            transaction.commit();
            currentFragment=efragment.GROUPS_FRAGMENT;
        }
    }
    public void selectOtherFragment(efragment fragment,Long id){
        if (fragment==efragment.EVENT_DETAILS_FRAGMENT) {
            fragmentEventDetails =new EventDetailsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("eventID",id.toString());
            fragmentEventDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEventDetails);
            transaction.commit();
            currentFragment=efragment.EVENT_DETAILS_FRAGMENT;
        }
        if (fragment==efragment.CREATE_GROUP_FRAGMENT) {
            fragmentCreateGroup =new CreateGroupFragment();
            Bundle bundl=new Bundle();
            bundl.putString("eventID",id.toString());
            fragmentCreateGroup.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentCreateGroup);
            transaction.commit();
            currentFragment=efragment.CREATE_GROUP_FRAGMENT;
        }
        if (fragment==efragment.GROUP_DETAILS_FRAGMENT) {
            fragmentGroupDetails =new GroupDetailsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("groupID",id.toString());
            fragmentGroupDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroupDetails);
            transaction.commit();
            currentFragment=efragment.GROUP_DETAILS_FRAGMENT;
        }
    }
    public void selectOtherFragment(efragment fragment,Integer decideWhichPage){
        if (fragment==efragment.EVENTS_FRAGMENT) {
            fragmentEvents =new EventsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("page",decideWhichPage.toString());
            fragmentEvents.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
            currentFragment=efragment.EVENTS_FRAGMENT;
        }
    }


    //TODO Wenn in EditUserFragment, zurückgehen auf ShowUserFragment
    @Override
    public void onBackPressed() {
        if (currentFragment==efragment.CREATE_GROUP_FRAGMENT){
            fragmentEvents=new EventsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("page","0");
            fragmentEvents.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
        }
        else if (currentFragment==efragment.EDIT_USER_FRAGMENT){
            fragmentShowUser=new ShowUserFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentShowUser);
            transaction.commit();
        }
        else if (currentFragment==efragment.EVENT_DETAILS_FRAGMENT){
            fragmentEvents=new EventsFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
        }
        else if (!pressedBackOnce){
            pressedBackOnce=true;
            Toast.makeText(getApplicationContext(),"Nochmal drücken um App zu beenden",Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pressedBackOnce=false;
                }
            },3000);
        }
        else {
            finish();
            System.exit(0);
        }

    }

    public void setTabID(int id){
        bottomBar.selectTabAtPosition(id);
    }

}
