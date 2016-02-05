package model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class HelperDB extends SQLiteOpenHelper {

        public HelperDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int vers) {
                super(context, name, factory, vers);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE schedule ("
                        + "day text,"
                        + "name text,"
                        + "room text,"
                        + "stime text,"
                        + "etime text,"
                        + "type text"
                        + ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST schedule");
                onCreate(db);

        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
        }

        public void addToLocalDB(ArrayList<Lesson> lessons) {
                ContentValues cv = new ContentValues();
                SQLiteDatabase db = this.getWritableDatabase();
                for (Lesson l : lessons) {
                        cv.put("etime", l.getTimeEnd());
                        cv.put("stime", l.getTimeStart());
                        cv.put("room", l.getRoom());
                        cv.put("name", l.getName());
                        cv.put("day", l.getDay());
                        cv.put("type", l.getType());
                        db.insert("schedule", null, cv);
                }
        }

        public ArrayList<Lesson> readScheduleOfDay(String day) {
                ArrayList<Lesson> lessons = new ArrayList<>();
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor c = db.query("schedule", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                        int dayColIndex = c.getColumnIndex("day");
                        int nameColIndex = c.getColumnIndex("name");
                        int roomColIndex = c.getColumnIndex("room");
                        int timestartColIndex = c.getColumnIndex("stime");
                        int timeendColIndex = c.getColumnIndex("etime");
                        int typeColIndex = c.getColumnIndex("type");

                        do {
                                if (c.getString(dayColIndex).equals(day))
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
}


