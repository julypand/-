package com.example.julie.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import model.HelperDB;
import model.Lesson;

public class ClassesListActivity extends AppCompatActivity {
    TableLayout tableClasses;
    int day_id;
    static String name_schedule;
    SharedPreferences loginPreferences;
    static String[] week = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        name_schedule = loginPreferences.getString("name_schedule","");


        day_id = getIntent().getIntExtra("day_id", 0);

        tableClasses = (TableLayout) this.findViewById(R.id.tableClasses);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(day_id);

/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(ClassesListActivity.this, ViewActivity.class);//
        ClassesListActivity.this.finish();
        startActivity(intent);
    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "6";
        TextView today;
        int day_id;
        TableLayout tableClasses;
        Button btnLeft, btnRight;

        public PlaceholderFragment() {

        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_classes_list, container, false);
            day_id = getArguments().getInt(ARG_SECTION_NUMBER)-1;

            String day = week[day_id];
            today = (TextView) rootView.findViewById(R.id.tvDay);
            btnLeft = (Button) rootView.findViewById(R.id.btnleft);
            btnRight = (Button) rootView.findViewById(R.id.btnright);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day_right_id = (day_id + 1) % 6;
                    goDay(day_right_id);
                }
            });
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day_right_id = (day_id + 5) % 6;
                    goDay(day_right_id);
                }
            });

            today.setText(day);
            tableClasses = (TableLayout) rootView.findViewById(R.id.tableClasses);
            writeDaySchedule(day,name_schedule);
            return rootView;
        }
        public void goDay(int day_id){
            final Intent intent = new Intent(getActivity(), getActivity().getClass());
            intent.putExtra("day_id", day_id);
            startActivity(intent);
        }


        void writeDaySchedule(String day,String name_schedule){
            HelperDB dbHelper = new HelperDB(getActivity().getBaseContext(),"schedule",null,1);
            ArrayList<Lesson> lessonsOfDay = dbHelper.readScheduleOfDay(day,name_schedule);
            addLessons(lessonsOfDay);
        }

        void addLessons(ArrayList<Lesson> lessons) {
            for(Lesson les : lessons) {
                TableRow row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.Table));
                TextView timeText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView nameText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView roomText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView typeText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));

                timeText.setText(les.getTimeStart() + " - " + les.getTimeEnd());
                nameText.setText(les.getName());
                roomText.setText(les.getRoom());
                typeText.setText(les.getType());
                row.addView(timeText);
                row.addView(nameText);
                row.addView(roomText);
                row.addView(typeText);
                tableClasses.addView(row);
            }
        }
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getResources().getString(R.string.monday);
                case 1:
                    return getResources().getString(R.string.tuesday);
                case 3:
                    return getResources().getString(R.string.wednesday);
                case 4:
                    return getResources().getString(R.string.thursday);
                case 5:
                    return getResources().getString(R.string.friday);
                case 6:
                    return getResources().getString(R.string.saturday);
            }
            return null;
        }

    }

}
