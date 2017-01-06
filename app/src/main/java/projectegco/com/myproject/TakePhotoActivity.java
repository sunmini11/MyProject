package projectegco.com.myproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayAdapter<Photo> photoArrayAdapter;
    private PhotoDataSource photoDataSource;
    protected List<Photo> data = new ArrayList<>();
    public static String absolutePath = "path";
    String currentDateTime;
    protected static final String selectedSubject = "subject";
    protected static final String idselectedSubject = "subjectid";
    TextView subTextView;
    Button deleteButton;
    CheckBox checkBox;
    ListView listView;
    Photo getFromPhoto;

    boolean isSelectAll = true;
    public static boolean flag = false;
    CheckBox checkboxAll;
    ArrayList<Integer> msgMultiSelected;



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
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(photoArrayAdapter); //push data in adapter into listview

        //Click to zoom photo
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
//                Toast.makeText(TakePhotoActivity.this, "image"+i, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(TakePhotoActivity.this,ZoomPhotoActivity.class);
//                intent.putExtra(ImageViewZoom.absolutePath,absolutePath);
//                startActivity(intent);
                checkBox = (CheckBox) view.getTag(R.id.checkBox);
//                if (photoArrayAdapter.getCount() > 0) {
//
//                    final Photo p = photoArrayAdapter.getItem(i);
//                    photoDataSource.deleteResult(p); // delete in database
//                    view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
//                        @Override
//                        public void run() {
//                            photoArrayAdapter.remove(p); // delete in listviewlist.remove(item);
//                            view.setAlpha(1);
//                        }
//                    });
//                }



//                if (checkboxAll.isChecked()){
//                    System.out.println("hey3");
//                    for ( int j=0; j< listView.getCount(); j++ ) {
//                        System.out.println("hey");
//                        listView.setItemChecked(j, true);
//                    }
//                }else {
//                    System.out.println("hey3");
//                    for ( int j=0; j< listView.getCount(); j++ ) {
//                        System.out.println("hey1");
//                        listView.setItemChecked(j, false);
//                    }
//                }



            }
        });

//        //Select All checkbox
        checkboxAll = (CheckBox)findViewById(R.id.checkBox2);
        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkboxAll.isChecked())
                {
//                    checkBox.setChecked(true);
                    // check all list items
                    System.out.println("xxx check");
                    for ( int i=0; i < listView.getChildCount(); i++) {
                        listView.setItemChecked(i, true);
                    }
                }
                else if(!checkboxAll.isChecked())
                {
                    //  unselect all list items
//                   checkBox.setChecked(false);
                    System.out.println("xxx uncheck");
                }

            }
        });

        //Check checkbox and push delete button
        deleteButton = (Button)findViewById(R.id.delBtn);
        for (int k = 0; k < data.size(); k++) {
            if(data.get(k).isSelected()){
                deleteButton.isEnabled();
            }
        }

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgMultiSelected = new ArrayList<Integer>();
                AlertDialog.Builder builder = new AlertDialog.Builder(TakePhotoActivity.this); // where dialog appear
                builder.setMessage("Do you want to delete the items?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    // when ans = yes do this
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for (int j = 0; j < data.size(); j++) {
                            System.out.println(j + "xxxxcheck = " + data.get(j).isSelected());
                            if (data.get(j).isSelected()) {
                                getFromPhoto = photoArrayAdapter.getItem(j);
                                photoDataSource.deleteResult(getFromPhoto);
                                photoArrayAdapter.remove(data.get(j));

                                Toast.makeText(TakePhotoActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create();
                builder.show();
            }
        });

    }


    private String isCheckedorNot(CheckBox checkbox){
        if(checkbox.isChecked())
            return "is checked";
        else
            return "is not checked";
    }


    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //get photo
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            String getSubject = getIntent().getStringExtra(selectedSubject);
            String getSubjectID = getIntent().getStringExtra(idselectedSubject);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
            absolutePath = finalFile.getAbsolutePath();

            //Get Current time
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");
            currentDateTime = dateFormat.format(new Date());

            // put value into Photo table
            photoDataSource.open();
            Photo photo1 = photoDataSource.createPhoto(absolutePath,getSubjectID,currentDateTime);
            photoArrayAdapter.add(photo1);
            photoArrayAdapter.notifyDataSetChanged();

            System.out.println("xxfinalfile: "+finalFile);
            System.out.println("xxab: "+absolutePath);
            System.out.println("xxdate: "+currentDateTime);
            System.out.println("xxsubid: "+getSubjectID);
            System.out.println("xxsub: "+getSubject);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        //System.out.println("xxpath: "+Uri.parse(path));
        return Uri.parse(path);

    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        System.out.println("xxidx: "+cursor.getString(idx));
        return cursor.getString(idx);
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
