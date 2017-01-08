package projectegco.com.myproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 23/12/2559.
 */
public class PhotoDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbhelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_IDPH, MySQLiteHelper.COLUMN_IMGPATH,
            MySQLiteHelper.COLUMN_SUBJECTPH_ID, MySQLiteHelper.COLUMN_TIMESTAMP,MySQLiteHelper.COLUMN_THUMBPATH};

    public PhotoDataSource(Context context){
        dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Photo createPhoto(String imgpath,String subjectid,String timestamp,String thumbnail){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_IMGPATH, imgpath);
        values.put(MySQLiteHelper.COLUMN_SUBJECTPH_ID, subjectid);
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp);
        values.put(MySQLiteHelper.COLUMN_THUMBPATH, thumbnail);
        open();

        long insertID = database.insert(MySQLiteHelper.TABLE_PHOTO, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PHOTO, allColumns, MySQLiteHelper.COLUMN_IDPH + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        Photo photo = cursorToPhoto(cursor);
        cursor.close();
        return photo;
    }

    public void deleteResult(Photo results){
        long id = results.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_PHOTO, MySQLiteHelper.COLUMN_IDPH + "=" + id,null);
    }

    public List<Photo> getAllPhotos(String subId){
        List<Photo> ph = new ArrayList<Photo>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_PHOTO
                + " WHERE " + MySQLiteHelper.COLUMN_SUBJECTPH_ID + "=" + "'" + subId + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Photo photo = cursorToPhoto(cursor);
            ph.add(photo);
            cursor.moveToNext();
        }
        cursor.close();
        return ph;
    }

    public Photo cursorToPhoto(Cursor cursor){ //set value to Photo
        Photo photo = new Photo(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4));
        System.out.println("getphoto: "+cursor.getLong(0)+cursor.getString(1)+cursor.getString(2)+cursor.getString(3)+cursor.getString(4));
        return photo;
    }
}
