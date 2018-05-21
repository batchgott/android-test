package com.evelope.events.events;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.evelope.events.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {
    FragmentStatePagerAdapter adapterViewPager;
    ViewPager vpPager;
    View view;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_event, container, false);
        vpPager = view.findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        Integer page=Integer.parseInt(getArguments().getString("page"),10);
        vpPager.setCurrentItem(page);
        return view;
    }



    public static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return MyEventsFragment.newInstance(0, "Meine Events");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return AllEventsFragment.newInstance(1, "Alle Events");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0)
                return "Meine Events";
            else
                return "Alle Events";
        }

    }


}
