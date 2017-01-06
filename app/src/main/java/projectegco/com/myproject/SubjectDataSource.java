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
public class SubjectDataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbhelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_SUBJECT_NAME, MySQLiteHelper.COLUMN_SUBJECT_ID};

    public SubjectDataSource(Context context){
        dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public Subject createSubject(String subject_name,String subject_id){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_SUBJECT_NAME, subject_name);
        values.put(MySQLiteHelper.COLUMN_SUBJECT_ID, subject_id);
        open();

        long insertID = database.insert(MySQLiteHelper.TABLE_SUBJECT, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SUBJECT, allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        Subject subject = cursorToSubject(cursor);
        cursor.close();
        return subject;
    }


    public List<Subject> getAllSubjects(){
        List<Subject> results = new ArrayList<Subject>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_SUBJECT, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Subject subject = cursorToSubject(cursor);
            results.add(subject);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public Subject cursorToSubject(Cursor cursor){ //set value to Subject
        Subject subject = new Subject(cursor.getLong(0),cursor.getString(1),cursor.getString(2));
        System.out.println("getsubject: "+cursor.getLong(0)+cursor.getString(1)+cursor.getString(2));
        return subject;
    }

    //find selected subject
//    public Cursor findSubject(String un) {
//        Cursor cursor = database.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_SUBJECT
//                + " WHERE " + MySQLiteHelper.COLUMN_SUBJECT_NAME + "=" + "'" + un + "'", null);
//        return cursor;
//    }
}
