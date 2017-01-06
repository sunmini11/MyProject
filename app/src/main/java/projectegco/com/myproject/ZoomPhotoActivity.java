package projectegco.com.myproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ZoomPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(new ImageViewZoom(this));
        setContentView(new ImageViewZoom(this));
    }
}
