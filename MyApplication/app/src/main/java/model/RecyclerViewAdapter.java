package model;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.julie.myapplication.R;
import com.example.julie.myapplication.ViewActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<String> mDataset;
    private static Activity activity;
    private LayoutInflater layoutInflater;

    public RecyclerViewAdapter(Context context){
        layoutInflater = LayoutInflater.from(context);
        mDataset = new ArrayList<String>();
    }


    public RecyclerViewAdapter(ArrayList<String> myDataset, Activity _activity) {
        mDataset = myDataset;
        activity = _activity;

    }

    @Override
    public RecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_item, parent, false);

        return new DataObjectHolder(view,activity,this);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.DataObjectHolder holder, int position) {
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




    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        Button btnEdit,btnDelete;
        private RecyclerViewAdapter parent;


        public DataObjectHolder(final View itemView,Activity _activity, final RecyclerViewAdapter parent) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.schedule_name);
            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            activity = _activity;
            this.parent = parent;


            itemView.setOnClickListener(this);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle(activity.getResources().getString(R.string.yousure))
                            .setMessage(activity.getResources().getString(R.string.delete_schedule) + " '" + label.getText() + "' ?")
                                    .setCancelable(false)
                                    .setNegativeButton(activity.getResources().getString(R.string.ok),
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    HelperDB dbHelper = new HelperDB(activity.getBaseContext(),"schedule",null,1);
                                                    String name_schedule = label.getText().toString();
                                                    dbHelper.deleteSchedule(name_schedule);
                                                    parent.deleteItem(getAdapterPosition());
                                                    dialog.dismiss();

                                                }
                                            })
                                    .setPositiveButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                    builder.create();
                    builder.show();
                }
            });

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

}