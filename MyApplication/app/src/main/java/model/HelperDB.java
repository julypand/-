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
                        + "day text,"
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

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST schedule");
                db.execSQL("DROP TABLE IF EXIST lesson");
                onCreate(db);

        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
        }



        public void addToLocalDB(HashMap<String,ArrayList<Lesson>> schedule) {
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
                                cv.put("etime", l.getTimeEnd());
                                cv.put("stime", l.getTimeStart());
                                cv.put("room", l.getRoom());
                                cv.put("name", l.getName());
                                cv.put("day", l.getDay());
                                cv.put("type", l.getType());
                                db.insert("lesson", null, cv);
                        }

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


        public ArrayList<Lesson> readScheduleOfDay(String day, String schedule_name) {
                ArrayList<Lesson> lessons = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("lesson", null, null, null, null, null, null);
                int schedule_id = getIdSchedule(schedule_name);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex("id_schedule");
                        int dayColIndex = c.getColumnIndex("day");
                        int nameColIndex = c.getColumnIndex("name");
                        int roomColIndex = c.getColumnIndex("room");
                        int timestartColIndex = c.getColumnIndex("stime");
                        int timeendColIndex = c.getColumnIndex("etime");
                        int typeColIndex = c.getColumnIndex("type");

                        do {
                                if (c.getString(dayColIndex).equals(day) && c.getInt(idColIndex) == (schedule_id))
                                        lessons.add(new Lesson(c.getString(dayColIndex),
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


