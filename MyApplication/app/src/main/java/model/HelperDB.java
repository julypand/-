package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class HelperDB extends SQLiteOpenHelper {

        public HelperDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int vers) {
                super(context, name, factory, vers);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE lesson ("
                        + "id_schedule integer,"
                        + "day_id integer,"
                        + "name text,"
                        + "room text,"
                        + "stime text,"
                        + "etime text,"
                        + "type text"
                        + ");");

                db.execSQL("CREATE TABLE schedule ("
                        + "id integer primary key autoincrement,"
                        + "name text"
                        + ");");

                db.execSQL("CREATE TABLE day ("
                        + "id integer,"
                        + "name text"
                        + ");");


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST schedule");
                db.execSQL("DROP TABLE IF EXIST lesson");
                db.execSQL("DROP TABLE IF EXIST day");

                onCreate(db);

        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
        }



        public void addToLocalDB(User user){
                HashMap<String,ArrayList<Lesson>> schedule = user.getSchedules();
                ArrayList<String> week = user.getWeek();
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();


                for (HashMap.Entry<String, ArrayList<Lesson>> entry : schedule.entrySet()) {
                        String name_schedule = entry.getKey();
                        String query = "INSERT INTO schedule (name) VALUES ('" + name_schedule + "\')";
                        db.execSQL(query);

                        int schedule_id = getIdSchedule(name_schedule);
                        ArrayList<Lesson> lessons = entry.getValue();
                        for (Lesson l : lessons) {
                                cv.put("id_schedule", schedule_id);
                                cv.put("etime", l.convert(l.getTimeEnd()));
                                cv.put("stime", l.convert(l.getTimeStart()));
                                cv.put("room", l.getRoom());
                                cv.put("name", l.getName());
                                cv.put("day_id", l.getDay());
                                cv.put("type", l.getType());
                                db.insert("lesson", null, cv);
                        }

                }
            for(int i = 0; i < week.size(); i++){
                String query = "INSERT INTO day (id, name) VALUES ('"+ i + "\',\'" + week.get(i) +  "\')";
                db.execSQL(query);
            }


        }
        public int getIdSchedule(String name){
                int id = 0;
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("schedule", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex("id");
                        int nameColIndex = c.getColumnIndex("name");
                        do {
                                if (c.getString(nameColIndex).equals(name)) {
                                        id = c.getInt(idColIndex);
                                }

                        } while (c.moveToNext());
                        c.close();
                }
                return id;

        }

        public ArrayList<String> getNameSchedules(){
                ArrayList<String> names = new ArrayList<>();

                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("schedule", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int nameColIndex = c.getColumnIndex("name");
                        do {
                                String name = c.getString(nameColIndex);
                                names.add(name);

                        } while (c.moveToNext());
                        c.close();
                }
                return names;
        }
        public ArrayList<String> getWeek(){
                ArrayList<String> week = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("day", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int dayColIndex = c.getColumnIndex("name");
                        do {
                                week.add(c.getString(dayColIndex));
                        } while (c.moveToNext());
                        c.close();
                }

                return week;
        }

        public ArrayList<Lesson> readScheduleOfDay(int day, String schedule_name) {
                ArrayList<Lesson> lessons = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("lesson", null, null, null, null, null, null);
                int schedule_id = getIdSchedule(schedule_name);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex("id");
                        int idScheduleColIndex = c.getColumnIndex("id_schedule");
                        int dayColIndex = c.getColumnIndex("day_id");
                        int nameColIndex = c.getColumnIndex("name");
                        int roomColIndex = c.getColumnIndex("room");
                        int timestartColIndex = c.getColumnIndex("stime");
                        int timeendColIndex = c.getColumnIndex("etime");
                        int typeColIndex = c.getColumnIndex("type");

                        do {
                            int day_id = c.getInt(dayColIndex);
                            int schedule= c.getInt(idScheduleColIndex);
                                if (c.getInt(dayColIndex) == (day) && c.getInt(idScheduleColIndex) == (schedule_id))
                                        lessons.add(new Lesson(1,c.getInt(dayColIndex),
                                                c.getString(nameColIndex),
                                                c.getString(roomColIndex),
                                                c.getString(timestartColIndex),
                                                c.getString(timeendColIndex),
                                                c.getString(typeColIndex)
                                        ));
                        } while (c.moveToNext());
                        c.close();
                }
                return lessons;

        }
        public String getNameDay(int day_id){
                String name= "";
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("day", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex("id");
                        int nameColIndex = c.getColumnIndex("name");

                        do {
                                if (c.getInt(idColIndex) == (day_id))
                                        name = c.getString(nameColIndex);
                        } while (c.moveToNext());
                        c.close();
                }
                 return name;

        }
        public void addLesson(Lesson lesson, String name_schedule, int day_id){
                SQLiteDatabase db = this.getReadableDatabase();
                int id_schedule = getIdSchedule(name_schedule);
                String query = "INSERT INTO lesson (id_schedule,day_id,name,room,stime,etime,type) " +
                        "                               VALUES ('" + id_schedule + " ',' "
                                                                        + day_id + "','"  + lesson.getName() +"','"
                                                                                + lesson.getRoom()  + "','"
                                                                                        +lesson.getTimeStart() + "','"
                                                                                                +lesson.getTimeEnd() + "','"
                                                                                                        +lesson.getType() + "\')";


                db.execSQL(query);

        }
        public void addSchedule(String name){
            SQLiteDatabase db = this.getReadableDatabase();
            String query = "INSERT INTO schedule (name) VALUES ('" + name + "\')";
            db.execSQL(query);
        }

        public void deleteSchedule(String name_schedule){
                SQLiteDatabase db = this.getWritableDatabase();
                int id_schedule = getIdSchedule(name_schedule);
                deleteLessons(id_schedule);
                db.execSQL("DELETE FROM schedule WHERE id = " + id_schedule);
        }
        public void deleteLessons(int id_schedule){
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM lesson WHERE id_schedule = " + id_schedule);

        }
        public void clear(){
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM schedule");
                db.execSQL("DELETE FROM lesson");

                db.execSQL("DELETE FROM day");
        }


        public boolean isNameScheduleExist(String name){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "SELECT FROM schedule WHERE name = '" + name;
                String q = "SELECT id FROM schedule " + "WHERE name = '" + name + "' LIMIT 1";
                Cursor c = db.rawQuery(q,null);
                if (!c.moveToFirst()){
                        return true;
                }
                return false;
        }
        public void renameSchedule(String new_name,String name){
                SQLiteDatabase db = this.getWritableDatabase();
                int id = getIdSchedule(name);
                db.execSQL("UPDATE schedule SET name = '" +  new_name + "' WHERE id = " + id );
        }
}


