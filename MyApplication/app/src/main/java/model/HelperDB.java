package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelperDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "schedule";

    private static final String TABLE_LESSON = "lesson";
    private static final String TABLE_DAY = "day";
    private static final String TABLE_SCHEDULE = "schedule";

    private static final String KEY_ID = "id";
    private static final String KEY_SCH_ID = "schedule_id";
    private static final String KEY_DAY_ID = "day_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ROOM = "room";
    private static final String KEY_TYPE = "type";
    private static final String KEY_ST_TIME = "stime";
    private static final String KEY_E_TIME = "etime";
    private static final String KEY_EDIT = "iseditable";


        public HelperDB(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE " + TABLE_LESSON + " ( "
                        + KEY_ID + " integer, "
                        + KEY_SCH_ID + " integer, "
                        + KEY_DAY_ID + " integer, "
                        + KEY_NAME + " text, "
                        + KEY_ROOM + " text, "
                        + KEY_ST_TIME + " text, "
                        + KEY_E_TIME + " text, "
                        + KEY_TYPE + " text"
                        + ");");

                db.execSQL("CREATE TABLE " + TABLE_SCHEDULE + " ( "
                        + KEY_ID + " integer, "
                        + KEY_NAME + " text, "
                        + KEY_EDIT + " integer"
                        + ");");

                db.execSQL("CREATE TABLE " + TABLE_DAY + " ( "
                        + KEY_ID + " integer, "
                        + KEY_NAME + " text "
                        + ");");



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST " + TABLE_LESSON);
                db.execSQL("DROP TABLE IF EXIST " + TABLE_SCHEDULE);
                db.execSQL("DROP TABLE IF EXIST " + TABLE_DAY);

                onCreate(db);

        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
        }



        public void addToLocalDB(User user){
                List<Schedule> schedules = user.getSchedules();
                List<String> week = user.week();
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();


                for (Schedule schedule: schedules) {
                        String name_schedule = schedule.getName();
                        int schedule_id = schedule.getId();
                        int isEditable  = schedule.getIsEditable()? 1:0;
                        String query = "INSERT INTO " + TABLE_SCHEDULE + " (" +KEY_ID +"," + KEY_NAME + ","+ KEY_EDIT + ") VALUES ("+ schedule_id+ ",'"+ name_schedule + "'," + isEditable + ")";
                        db.execSQL(query);

                        List<Lesson> lessons = schedule.getLessons();
                        for (Lesson l : lessons) {
                                cv.put(KEY_ID, l.getId());
                                cv.put(KEY_SCH_ID, schedule_id);
                                cv.put(KEY_E_TIME, l.convert(l.getTimeEnd()));
                                cv.put(KEY_ST_TIME, l.convert(l.getTimeStart()));
                                cv.put(KEY_ROOM, l.getRoom());
                                cv.put(KEY_NAME, l.getName());
                                cv.put(KEY_DAY_ID, l.getDay());
                                cv.put(KEY_TYPE, l.getType());
                                db.insert(TABLE_LESSON, null, cv);
                        }

                }
            for(int i = 0; i < week.size(); i++){
                String query = "INSERT INTO " + TABLE_DAY +"("+KEY_ID+","+KEY_NAME+") VALUES ('"+ i + "\',\'" + week.get(i) +  "\')";
                db.execSQL(query);
            }


        }
        public int getIdSchedule(String name){
                int id = 0;
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query(TABLE_SCHEDULE, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex(KEY_ID);
                        int nameColIndex = c.getColumnIndex(KEY_NAME);
                        do {
                                if (c.getString(nameColIndex).equals(name)) {
                                        id = c.getInt(idColIndex);
                                }

                        } while (c.moveToNext());
                        c.close();
                }
                return id;

        }

        public ArrayList<Schedule> getSchedules(){
                ArrayList<Schedule> schedules = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query(TABLE_SCHEDULE, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int nameColIndex = c.getColumnIndex(KEY_NAME);
                        int isEditableIndex = c.getColumnIndex(KEY_EDIT);
                        do {
                                String name = c.getString(nameColIndex);
                                boolean isEditable = c.getInt(isEditableIndex) != 0;
                                schedules.add(new Schedule(name,isEditable));

                        } while (c.moveToNext());
                        c.close();
                }
                return schedules;
        }
        public ArrayList<String> getWeek(){
                ArrayList<String> week = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query(TABLE_DAY, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int dayColIndex = c.getColumnIndex(KEY_NAME);
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
                Cursor c = db.query(TABLE_LESSON, null, null, null, null, null, null);
                int schedule_id = getIdSchedule(schedule_name);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex(KEY_ID);
                        int idScheduleColIndex = c.getColumnIndex(KEY_SCH_ID);
                        int dayColIndex = c.getColumnIndex(KEY_DAY_ID);
                        int nameColIndex = c.getColumnIndex(KEY_NAME);
                        int roomColIndex = c.getColumnIndex(KEY_ROOM);
                        int timestartColIndex = c.getColumnIndex(KEY_ST_TIME);
                        int timeendColIndex = c.getColumnIndex(KEY_E_TIME);
                        int typeColIndex = c.getColumnIndex(KEY_TYPE);

                        do {
                            int day_id = c.getInt(dayColIndex);
                            int schedule= c.getInt(idScheduleColIndex);
                                if (c.getInt(dayColIndex) == (day) && c.getInt(idScheduleColIndex) == (schedule_id))
                                        lessons.add(new Lesson(c.getInt(idColIndex),c.getInt(dayColIndex),
                                                c.getString(nameColIndex),
                                                schedule_name,
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
                Cursor c = db.query(TABLE_DAY, null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex(KEY_ID);
                        int nameColIndex = c.getColumnIndex(KEY_NAME);

                        do {
                                if (c.getInt(idColIndex) == (day_id))
                                        name = c.getString(nameColIndex);
                        } while (c.moveToNext());
                        c.close();
                }
                 return name;

        }
        public void addLesson(Lesson l, String name_schedule){
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues cv = new ContentValues();
            int schedule_id = getIdSchedule(name_schedule);

            cv.put(KEY_ID, l.getId());
            cv.put(KEY_SCH_ID, schedule_id);
            cv.put(KEY_E_TIME, l.convert(l.getTimeEnd()));
            cv.put(KEY_ST_TIME, l.convert(l.getTimeStart()));
            cv.put(KEY_ROOM, l.getRoom());
            cv.put(KEY_NAME, l.getName());
            cv.put(KEY_DAY_ID, l.getDay());
            cv.put(KEY_TYPE, l.getType());
            db.insert(TABLE_LESSON, null, cv);

        }
        public void addSchedule(Schedule schedule){
            SQLiteDatabase db = this.getWritableDatabase();
            String name = schedule.getName();
            int id = schedule.getId();
            int isEditable = schedule.getIsEditable()?1:0;
            String query = "INSERT INTO " + TABLE_SCHEDULE + "(" + KEY_ID+ "," + KEY_NAME +"," + KEY_EDIT+ ") VALUES (" +id + ",'" + name + "', "+ isEditable +")";
            db.execSQL(query);
        }

        public void deleteSchedule(String name_schedule){
                SQLiteDatabase db = this.getWritableDatabase();
                int id_schedule = getIdSchedule(name_schedule);
                deleteLessons(id_schedule);
                db.execSQL("DELETE FROM " + TABLE_SCHEDULE + " WHERE " + KEY_ID + " = " + id_schedule);
        }
        public void deleteLessons(int id_schedule){
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM " + TABLE_LESSON + " WHERE "+ KEY_SCH_ID + " = " + id_schedule);

        }
        public void clear(){
                SQLiteDatabase db = this.getWritableDatabase();
                db.execSQL("DELETE FROM "+ TABLE_SCHEDULE);
                db.execSQL("DELETE FROM " + TABLE_LESSON);
                db.execSQL("DELETE FROM "+ TABLE_DAY);

        }


        public boolean isNameScheduleExist(String name){
                SQLiteDatabase db = this.getReadableDatabase();
                String query = "SELECT * FROM " + TABLE_SCHEDULE +" WHERE " + KEY_NAME + " = '" + name + "'";
                Cursor c = db.rawQuery(query,null);
                if (c.moveToFirst()){
                        return true;
                }
                return false;
        }
        public void renameSchedule(String new_name,String name){
                SQLiteDatabase db = this.getWritableDatabase();
                int id = getIdSchedule(name);
                db.execSQL("UPDATE " + TABLE_SCHEDULE + " SET "+ KEY_NAME + " = '" +  new_name + "' WHERE "+ KEY_ID + " = " + id );
        }
    public boolean isEditableSchedule(String name){
        boolean isEditable = false;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_SCHEDULE, null, null , null, null, null, null);
        if (c.moveToFirst()) {
            int isEditableColIndex = c.getColumnIndex(KEY_EDIT);
            int nameColIndex = c.getColumnIndex(KEY_NAME);
            do {
                String nameschedule = c.getString(nameColIndex);
                if (c.getString(nameColIndex).equals(name)) {
                    isEditable = c.getInt(isEditableColIndex) != 0;
                    break;
                }
            } while (c.moveToNext());
            c.close();
        }
        return isEditable;
    }
}


