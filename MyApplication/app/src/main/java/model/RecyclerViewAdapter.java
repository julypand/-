package model;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.julie.myapplication.R;
import com.example.julie.myapplication.ViewActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter
        .DataObjectHolder> {

    private ArrayList<String> mDataset;
    private static Activity activity;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;


        public DataObjectHolder(View itemView,Activity _activity) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            activity = _activity;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ViewActivity.class);
            SharedPreferences loginPreferences = activity.getSharedPreferences("loginPrefs", activity.MODE_PRIVATE);
            SharedPreferences.Editor loginPrefEditor = loginPreferences.edit();
            loginPrefEditor.putString("name_schedule", label.getText().toString());
            loginPrefEditor.commit();

            intent.putExtra("name_schedule", label.getText());
            activity.finish();
            activity.startActivity(intent);
        }
    }



    public RecyclerViewAdapter(ArrayList<String> myDataset, Activity _activity) {
        mDataset = myDataset;
        activity = _activity;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view,activity);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.label.setText(mDataset.get(position));
    }

    public void addItem(String dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}