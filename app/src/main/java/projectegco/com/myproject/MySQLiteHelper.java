package projectegco.com.myproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by dell pc on 21/12/2559.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_RESULTS = "results"; //table name is used
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_IMGNAME = "imageName";
    public static final String COLUMN_TIMESTAMP = "dateTime";

    private static final String DATABASE_NAME = "resultCheck.db"; //database name file SQLite
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_RESULTS + "(" + COLUMN_ID + " integer primary key autoincrement, "+
            COLUMN_IMGNAME + " text not null,"+ COLUMN_MESSAGE + " text not null,"
            + COLUMN_TIMESTAMP + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE); //execute SQL
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //if update program not always onCreate
        Log.w(MySQLiteHelper.class.getName(),                                  //delete old one add new
                "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
        onCreate(db);
    }
}
