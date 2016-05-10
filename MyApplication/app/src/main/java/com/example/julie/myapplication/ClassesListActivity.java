package com.example.julie.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import model.HelperDB;
import model.Lesson;

public class ClassesListActivity extends AppCompatActivity {
    TableLayout tableClasses;
    int day_id;
    static String name_schedule;
    SharedPreferences loginPreferences;
    HelperDB dbHelper;
    static ArrayList<String> week;

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes_list);
        dbHelper = new HelperDB(getApplicationContext());
        week = dbHelper.getWeek();


        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        name_schedule = loginPreferences.getString("name_schedule", "");


        day_id = getIntent().getIntExtra("day_id", 0);

        tableClasses = (TableLayout) this.findViewById(R.id.tableClasses);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(day_id);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.isEditableSchedule(name_schedule)) {
                    int day_id = mViewPager.getCurrentItem();
                    Intent intent = new Intent(ClassesListActivity.this, NewClassActivity.class);
                    intent.putExtra("day_id", day_id);
                    ClassesListActivity.this.finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getBaseContext(), ClassesListActivity.this.getResources().getString(R.string.not_editable), Toast.LENGTH_LONG).show();
                }
            }
        });
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
        HelperDB helperDB;

        public PlaceholderFragment() {}

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

            String day = week.get(day_id);
            today = (TextView) rootView.findViewById(R.id.tvDay);
            btnLeft = (Button) rootView.findViewById(R.id.btnleft);
            btnRight = (Button) rootView.findViewById(R.id.btnright);
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day_right_id = (day_id + 1) % 6;
                    mViewPager.setCurrentItem(day_right_id);
                }
            });
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day_right_id = (day_id + 5) % 6;
                    mViewPager.setCurrentItem(day_right_id);
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
            helperDB = new HelperDB(getActivity().getApplicationContext());
            ArrayList<Lesson> lessonsOfDay = helperDB.readScheduleOfDay(day_id,name_schedule);
            Collections.sort(lessonsOfDay, new Comparator<Lesson>() {
                @Override
                public int compare(Lesson lhs, Lesson rhs) {
                    return lhs.getTimeStart().compareTo(rhs.getTimeStart());
                }
            });
            addLessons(lessonsOfDay);
        }

        void addLessons(ArrayList<Lesson> lessons) {
            for(final Lesson les : lessons) {
                TableRow row = new TableRow(new ContextThemeWrapper(getActivity(), R.style.Table));
                TextView timeText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView nameText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView roomText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                TextView typeText = new TextView(new ContextThemeWrapper(getActivity(), R.style.CellSchedule));
                timeText.setText(les.convert(les.getTimeStart()) + " - " + les.convert(les.getTimeEnd()));
                nameText.setText(les.getName());
                roomText.setText(les.getRoom());
                typeText.setText(les.getType());
                row.addView(timeText);
                row.addView(nameText);
                row.addView(roomText);
                row.addView(typeText);
                row.setClickable(true);
                final int id = les.getId();
                row.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if(helperDB.isEditableSchedule(name_schedule)) {
                            final Intent intentEditClass = new Intent(getActivity(), EditClassActivity.class);
                            intentEditClass.putExtra("class_id", id);
                            intentEditClass.putExtra("day_id", day_id);
                            getActivity().finish();
                            startActivity(intentEditClass);
                            return true;
                        }
                        Toast.makeText(getActivity().getBaseContext(), getActivity().getResources().getString(R.string.not_editable), Toast.LENGTH_LONG).show();
                        return false;
                    }

                });
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
                    return week.get(0);
                case 1:
                    return week.get(1);
                case 3:
                    return week.get(2);
                case 4:
                    return week.get(3);
                case 5:
                    return week.get(4);
                case 6:
                    return week.get(5);
            }
            return null;
        }

    }

}
