package projectegco.com.myproject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TakePhotoActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayAdapter<Photo> photoArrayAdapter;
    private PhotoDataSource photoDataSource;
    protected List<Photo> data = new ArrayList<>();
    public String absolutePath;
    String currentDateTime;
    protected static final String selectedSubject = "subject";
    protected static final String idselectedSubject = "subjectid";
    TextView subTextView;
    Button deleteButton;
    CheckBox checkBox;
    ListView listView;
    ImageView photoView;
    Photo photo1;

    boolean isSelectAll = true;
    public static boolean flag = false;
    CheckBox checkboxAll;
    ArrayList<Integer> msgMultiSelected;

    String filename;
    Uri imageUri;
    String imageurl;
    Bitmap thumbnail;

    ContentValues values;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        setTitle("Photo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button

        final String getSubject = getIntent().getStringExtra(selectedSubject);
        final String getSubjectID = getIntent().getStringExtra(idselectedSubject);

        subTextView = (TextView) findViewById(R.id.subjectTxt);
        subTextView.setText(getSubject);

        FloatingActionButton fabcam = (FloatingActionButton) findViewById(R.id.fabcam);
        FloatingActionButton fabsend = (FloatingActionButton) findViewById(R.id.fabok);

        if (!hasCamera()) {
            fabcam.setEnabled(false);
        }

        //Set Listview
        photoDataSource = new PhotoDataSource(this);
        photoDataSource.open();
        data = photoDataSource.getAllPhotos(getSubjectID);
        photoArrayAdapter = new CustomAdapter(this, 0, data);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(photoArrayAdapter); //push data in adapter into listview
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemChecked(4, true);

        //Select All checkbox
        checkboxAll = (CheckBox) findViewById(R.id.checkBox2);
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean check = listView.isItemChecked(0);
                for (int i = 0; i <= data.size(); i++) {
                    listView.setItemChecked(i, !check);
                    System.out.println("xxcheck " + " " + i + !check);
                }
            }
        });

        //Delete selected checkbox
        deleteButton = (Button) findViewById(R.id.delBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoActivity.this); // where dialog appear
                builder.setMessage("Do you want to delete the photos?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    // when ans = yes do this
                    public void onClick(DialogInterface dialogInterface, int i) {

                        photoDataSource.open();

                        for (int j = 0; j < data.size(); j++) {
                            System.out.println(j + "xxxxcheck = " + data.get(j).isSelected());
                            if (data.get(j).isSelected()) {
                                deleteButton.setEnabled(true);
                                photo1 = photoArrayAdapter.getItem(j);
                                photoDataSource.deleteResult(data.get(j));
                            }
                        }
                        data = photoDataSource.getAllPhotos(getSubjectID);
                        photoArrayAdapter = new CustomAdapter(TakePhotoActivity.this, 0, data);
                        listView.setAdapter(photoArrayAdapter);
                        Toast.makeText(TakePhotoActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        onPause();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create();
                builder.show();
            }
        });

    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchCamera(View view) {
//        int i = 0;
//        filename = Environment.getExternalStorageDirectory().getPath() + "/storage/sdcard0/ProjectPhoto/img_"+i+".jpg";
//        imageUri = Uri.fromFile(new File(filename));
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

    }

    //get photo
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap)extras.get("data");

        System.out.println("printfilename22 "+imageUri);
        try {
                thumbnail = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), imageUri);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                imgView.setImageBitmap(thumbnail);
                imageurl = getRealPathFromURI(imageUri);
            System.out.println("printfilename222 "+imageurl);

        } catch (Exception e) {
                e.printStackTrace();
            }

            String getSubject = getIntent().getStringExtra(selectedSubject);
            String getSubjectID = getIntent().getStringExtra(idselectedSubject);

//            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

//
//            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            absolutePath = finalFile.getAbsolutePath();

        System.out.println("imgpath: "+absolutePath+" real: "+imageurl);


        //Get Current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
            currentDateTime = dateFormat.format(new Date());

            // put value into Photo table
//            photoDataSource.open();
//            photo1 = photoDataSource.createPhoto(imageurl, getSubjectID, currentDateTime,absolutePath);
//            photoArrayAdapter.add(photo1);
//            photoArrayAdapter.notifyDataSetChanged();

//            System.out.println("xxfinalfile: "+finalFile);
//            System.out.println("xxab: "+absolutePath);
//            System.out.println("xxdate: "+currentDateTime);
//            System.out.println("xxsubid: "+getSubjectID);
//            System.out.println("xxsub: "+getSubject);
//            System.out.println("xxpath: "+tempUri);

//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        System.out.println("xxpathparse: " + Uri.parse(path));
        System.out.println("xxpath..: " + path);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
//        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//        cursor.moveToFirst();
//        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//        System.out.println("xxidx: " + cursor.getString(idx));
//        return cursor.getString(idx);

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        photoDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        photoDataSource.close();
        super.onPause();
    }
}
