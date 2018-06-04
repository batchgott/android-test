package com.evelope.events;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.evelope.events.createEvent.CreateEventFragment;
import com.evelope.events.createGroup.CreateGroupFragment;
import com.evelope.events.edit.EditUserFragment;
import com.evelope.events.edit.ShowUserFragment;
import com.evelope.events.events.EventDetailsFragment;
import com.evelope.events.events.EventsFragment;
import com.evelope.events.findGroup.FindGroupFragment;
import com.evelope.events.findGroup.GroupLessDetailsFragment;
import com.evelope.events.findGroup.ListGroupsFragment;
import com.evelope.events.groups.GroupDetailsFragment;
import com.evelope.events.groups.GroupMemberDetailsFragment;
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
    FindGroupFragment fragmentFindGroup;
    ListGroupsFragment fragmentListGroups;
    GroupMemberDetailsFragment fragmentGroupMemberDetails;
    GroupLessDetailsFragment fragmentGroupLessDetails;

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
        if (fragment==efragment.GROUP_LESS_DETAILS_FRAGMENT) {
            fragmentGroupLessDetails =new GroupLessDetailsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("groupID",id.toString());
            fragmentGroupLessDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroupLessDetails);
            transaction.commit();
            currentFragment=efragment.GROUP_LESS_DETAILS_FRAGMENT;
        }
        if (fragment==efragment.GROUP_MEMBER_DETAILS_FRAGMENT) {
            fragmentGroupMemberDetails =new GroupMemberDetailsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("userID",id.toString());
            fragmentGroupMemberDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroupMemberDetails);
            transaction.commit();
            currentFragment=efragment.GROUP_MEMBER_DETAILS_FRAGMENT;
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
        if (fragment==efragment.FIND_GROUP_FRAGMENT) {
            fragmentFindGroup =new FindGroupFragment();
            Bundle bundl=new Bundle();
            bundl.putString("eventID",id.toString());
            fragmentFindGroup.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentFindGroup);
            transaction.commit();
            currentFragment=efragment.FIND_GROUP_FRAGMENT;
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
            currentFragment=efragment.EVENTS_FRAGMENT;
        }
        else if (currentFragment==efragment.EDIT_USER_FRAGMENT){
            fragmentShowUser=new ShowUserFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentShowUser);
            transaction.commit();
            currentFragment=efragment.SHOW_USER_FRAGMENT;
        }
        else if (currentFragment==efragment.EVENT_DETAILS_FRAGMENT){
            fragmentEvents=new EventsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("page","0");
            fragmentEvents.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
            currentFragment=efragment.EVENTS_FRAGMENT;
        }
        else if (currentFragment==efragment.GROUP_MEMBER_DETAILS_FRAGMENT){
            fragmentGroups=new GroupsFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroups);
            transaction.commit();
            currentFragment=efragment.GROUPS_FRAGMENT;
        }
        else if (currentFragment==efragment.GROUP_DETAILS_FRAGMENT){
            fragmentGroups=new GroupsFragment();
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroups);
            transaction.commit();
            currentFragment=efragment.GROUPS_FRAGMENT;
        }
        else if ((currentFragment==efragment.EVENTS_FRAGMENT||currentFragment==efragment.GROUPS_FRAGMENT||currentFragment==efragment.CREATE_EVENTS_FRAGMENT||currentFragment==efragment.SHOW_USER_FRAGMENT)) {
        if (!pressedBackOnce) {
                pressedBackOnce = true;
                Toast.makeText(getApplicationContext(), "Nochmal drücken um App zu beenden", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pressedBackOnce = false;
                    }
                }, 3000);
            } else {
                finish();
                System.exit(0);
            }
        }
        else{
            fragmentEvents=new EventsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("page","0");
            fragmentEvents.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentEvents);
            transaction.commit();
            currentFragment=efragment.EVENTS_FRAGMENT;
        }
    }
    public void selectOtherFragment(efragment fragment,Long id,Boolean cat1,Boolean cat2, Boolean cat3, Boolean cat4, Boolean cat5, Boolean cat6){
        if (fragment==efragment.LIST_GROUPS_FRAGMENT) {
            fragmentListGroups =new ListGroupsFragment();
            Bundle bundl=new Bundle();
            bundl.putString("eventID",id.toString());
            bundl.putBoolean("cat1",cat1);
            bundl.putBoolean("cat2",cat2);
            bundl.putBoolean("cat3",cat3);
            bundl.putBoolean("cat4",cat4);
            bundl.putBoolean("cat5",cat5);
            bundl.putBoolean("cat6",cat6);
            fragmentListGroups.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentListGroups);
            transaction.commit();
            currentFragment=efragment.LIST_GROUPS_FRAGMENT;
        }
        else{
            Log.e("Wrong Fragment","Deppat?");
        }
    }

    public void selectOtherFragment(efragment fragment,Long userID,Long groupID) {
        if (fragment == efragment.GROUP_MEMBER_DETAILS_FRAGMENT) {
            fragmentGroupMemberDetails = new GroupMemberDetailsFragment();
            Bundle bundl = new Bundle();
            bundl.putString("userID", userID.toString());
            bundl.putString("groupID",groupID.toString());
            fragmentGroupMemberDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroupMemberDetails);
            transaction.commit();
            currentFragment = efragment.GROUP_MEMBER_DETAILS_FRAGMENT;
        } else {
            Log.e("Wrong Fragment", "Deppat?");
        }
    }

    public void selectOtherFragment(efragment fragment,Long userID,Boolean isAdmin) {
        if (fragment == efragment.GROUP_DETAILS_FRAGMENT) {
            fragmentGroupDetails = new GroupDetailsFragment();
            Bundle bundl = new Bundle();
            bundl.putString("groupID", userID.toString());
            bundl.putBoolean("isAdmin",isAdmin);
            fragmentGroupDetails.setArguments(bundl);
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.contentContainer, fragmentGroupDetails);
            transaction.commit();
            currentFragment = efragment.GROUP_DETAILS_FRAGMENT;
        } else {
            Log.e("Wrong Fragment", "Deppat?");
        }
    }

    public void setTabID(int id){
        bottomBar.selectTabAtPosition(id);
    }

}
