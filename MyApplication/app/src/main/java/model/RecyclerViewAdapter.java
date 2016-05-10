package model;


import android.app.Activity;
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
import android.widget.Toast;

import com.example.julie.myapplication.R;
import com.example.julie.myapplication.ViewActivity;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> {

    private ArrayList<String> mDataset;
    private static Activity activity;



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


        public DataObjectHolder(final View itemView,Activity _activity, final RecyclerViewAdapter parent) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.schedule_name);
            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

            activity = _activity;


            itemView.setOnClickListener(this);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog("delete", label, parent, getAdapterPosition());
                }

            });
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialog("edit", label, parent, getAdapterPosition());
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
    private static void showDialog(String kind, final TextView tvname_schedule, final RecyclerViewAdapter rv, final int position){

        final String name_schedule = tvname_schedule.getText().toString();
        final AlertDialog.Builder dialog = new AlertDialog.Builder(activity);

        dialog.setCancelable(false);

        dialog.setPositiveButton(activity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        switch (kind) {
            case "delete":
                dialog.setTitle(activity.getResources().getString(R.string.yousure))
                        .setMessage(activity.getResources().getString(R.string.delete_schedule) + " '" + name_schedule + "' ?")
                        .setNegativeButton(activity.getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String ip = activity.getResources().getString(R.string.ip);
                                        HelperDB dbHelper = new HelperDB(activity.getBaseContext());
                                        if(dbHelper.isEditableSchedule(name_schedule)) {
                                            SharedPreferences loginPreferences = activity.getSharedPreferences("loginPrefs", activity.MODE_PRIVATE);
                                            String email = loginPreferences.getString("email", "");
                                            new RequestTaskDeleteSchedule(activity, activity.getBaseContext(), name_schedule, email).execute(ip + "/users/schedules/delete");
                                            dialog.dismiss();
                                        }
                                        else
                                            Toast.makeText(activity, activity.getResources().getString(R.string.not_deleting), Toast.LENGTH_LONG).show();
                                        //HelperDB dbHelper = new HelperDB(activity.getBaseContext());
                                        //dbHelper.deleteSchedule(name_schedule);
                                        //rv.deleteItem(position);


                                    }
                                });
                break;
            case "edit":
                final View linearlayout = activity.getLayoutInflater().inflate(R.layout.dialog_renameschedule, null);

                dialog.setView(linearlayout);

                dialog.setTitle(activity.getResources().getString(R.string.enter_new_name_schedule) + " '" + name_schedule + "':")
                        .setNegativeButton(activity.getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        HelperDB dbHelper = new HelperDB(activity.getBaseContext());
                                        TextView tv = (TextView) linearlayout.findViewById(R.id.etNewNameSchedule);
                                        String new_name_schedule = tv.getText().toString();
                                        if (!dbHelper.isNameScheduleExist(name_schedule)) {
                                            dbHelper.renameSchedule(new_name_schedule, name_schedule);
                                            tvname_schedule.setText(new_name_schedule);
                                        }
                                        dialog.dismiss();
                                    }
                                });
                break;

        }
        dialog.create();
        dialog.show();
    }

}