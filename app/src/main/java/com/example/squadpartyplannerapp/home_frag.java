package com.example.squadpartyplannerapp;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.squadpartyplannerapp.ListAdapters.Fragment_pager_adapter;
import com.google.android.material.tabs.TabLayout;

public class home_frag extends Fragment {
    ViewPager viewPager;
    Fragment_pager_adapter fragment_pager_adapter;
    TabLayout tabLayout;

    public home_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_frag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tabLayout);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");
        setFragmentPagerAdapter();
    }

    public void setFragmentPagerAdapter() {
        fragment_pager_adapter = new Fragment_pager_adapter(getChildFragmentManager());
        fragment_pager_adapter.AddFragment(new upcoming_frag(),"");
        fragment_pager_adapter.AddFragment(new completed_frag(),"");
        fragment_pager_adapter.AddFragment(new cancelled_frag(),"");
        viewPager.setAdapter(fragment_pager_adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Upcoming");
        tabLayout.getTabAt(1).setText("Completed");
        tabLayout.getTabAt(2).setText("Cancelled");
    }


}
