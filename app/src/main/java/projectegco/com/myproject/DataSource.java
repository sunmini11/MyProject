package projectegco.com.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 21/12/2559.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbhelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_IMGPATH, MySQLiteHelper.COLUMN_SUBJECT, MySQLiteHelper.COLUMN_TIMESTAMP};

    public DataSource(Context context){
        dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Photo createPhoto(String imgpath,String subject,String timestamp){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_IMGPATH, imgpath);
        values.put(MySQLiteHelper.COLUMN_SUBJECT, subject);
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp);
        open();
        long insertID = database.insert(MySQLiteHelper.TABLE_PHOTO, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PHOTO, allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        Photo photo = cursorToPhoto(cursor);
        cursor.close();
        return photo;
    }

    public void deleteResult(Photo results){
        long id = results.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PHOTO, MySQLiteHelper.COLUMN_ID + "=" + id,null);
    }

    public List<Photo> getAllResults(){
        List<Photo> results = new ArrayList<Photo>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PHOTO, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Photo photo = cursorToPhoto(cursor);
            results.add(photo);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public Photo cursorToPhoto(Cursor cursor){ //set value to Photo
        Photo photo = new Photo(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return photo;
    }
}
