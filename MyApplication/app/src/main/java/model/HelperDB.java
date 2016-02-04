package model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperDB extends SQLiteOpenHelper{

        public HelperDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int vers) {
            super(context, name, factory,vers);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE schedule ("
                    + "day text,"
                    + "name text,"
                    + "room text,"
                    + "stime text,"
                    + "etime text"
                    + ");");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXIST schedule");
                onCreate(db);

        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
                onUpgrade(db,oldVersion,newVersion);
        }

}
