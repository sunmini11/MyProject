package projectegco.com.myproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dell pc on 21/12/2559.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_SUBJECT ="subject"; //table name is used
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT_NAME = "subjectname";
    public static final String COLUMN_SUBJECT_ID = "subjectid";

    public static final String TABLE_PHOTO = "photo";
    public static final String COLUMN_SUBJECTPH_ID = "subjectid";
    public static final String COLUMN_IDPH = "_idph";
    public static final String COLUMN_IMGPATH = "imagePath";
    public static final String COLUMN_TIMESTAMP = "dateTime";
    public static final String COLUMN_THUMBPATH = "thumbnailpath";

    private static final String DATABASE_NAME = "PhotoDatabase.db"; //database name file SQLite
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE_SUBJECT = "create table "
            + TABLE_SUBJECT + "(" + COLUMN_ID + " integer primary key autoincrement, "+COLUMN_SUBJECT_NAME + " text not null, " +
            COLUMN_SUBJECT_ID + " text not null);";

    private static final String DATABASE_CREATE_PHOTO = "create table "
            + TABLE_PHOTO + "(" + COLUMN_IDPH + " integer primary key autoincrement, "+
            COLUMN_IMGPATH + " text not null, "+ COLUMN_SUBJECTPH_ID + " text not null, "
            + COLUMN_TIMESTAMP + " text not null, " + COLUMN_THUMBPATH + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_SUBJECT);
        database.execSQL(DATABASE_CREATE_PHOTO); //execute SQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //if update program not always onCreate
        Log.w(MySQLiteHelper.class.getName(),                                  //delete old one add new
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTO);
        onCreate(db);
    }
}
