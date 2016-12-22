package projectegco.com.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ArrayAdapter<Photo> photoArrayAdapter;
    private DataSource dataSource;
    protected List<Photo> data = new ArrayList<>();
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        setTitle("Photo");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button

        FloatingActionButton fabcam = (FloatingActionButton) findViewById(R.id.fabcam);
        FloatingActionButton fabok = (FloatingActionButton) findViewById(R.id.fabok);

        if (!hasCamera()){
            fabcam.setEnabled(false);
        }

        //Set Listview
        dataSource = new DataSource(this);
        dataSource.open();
        data = dataSource.getAllResults();
        photoArrayAdapter = new CustomAdapter(this,0,data);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(photoArrayAdapter); //push data in adapter into listview
    }

    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void launchCamera(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //get photo
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        photoView = (ImageView)findViewById(R.id.photoView);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
//            Bundle extras = data.getExtras();
//            Bitmap bitmap = (Bitmap)extras.get("data");
//            photoView.setImageBitmap(bitmap);
//        }
//    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            System.out.println("xxfinalfile: "+finalFile);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        System.out.println("xxpath: "+Uri.parse(path));
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
}
