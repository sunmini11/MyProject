package projectegco.com.myproject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.io.File;

public class ZoomPhotoActivity extends AppCompatActivity {

    protected static final String ImagePath = "path";
    protected static final String ImageName = "imagename";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zoom_photo);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button

        final String getImgPath = getIntent().getStringExtra(ImagePath);
//        final String getImgName = getIntent().getStringExtra(ImageName);
        setTitle("Zoom Photo");



        SubsamplingScaleImageView imageView = (SubsamplingScaleImageView)findViewById(R.id.imageView);
//        imageView.setImage(ImageSource.resource(R.drawable.egco));
        imageView.setImage(ImageSource.uri(getImgPath));
//        System.out.print("imgpath zoom: "+ImagePath);
        Toast.makeText(ZoomPhotoActivity.this, ""+getImgPath, Toast.LENGTH_SHORT).show();

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
