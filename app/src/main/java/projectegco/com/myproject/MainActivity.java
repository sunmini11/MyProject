package projectegco.com.myproject;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button submitBtn;
    private static Spinner subjectSpinner;
    private Button takePhoto;
    private Button result;
    private Button cancel;

    String selectedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Menu");
        dialog.setContentView(R.layout.menu_dialog);

        subjectSpinner = (Spinner) findViewById(R.id.subSpinner);
        submitBtn = (Button) findViewById(R.id.submitButton);
        takePhoto = (Button) dialog.findViewById(R.id.takePhotoBtn);
        result = (Button) dialog.findViewById(R.id.resultBtn);
        cancel = (Button) dialog.findViewById(R.id.cancelBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedSubject = subjectSpinner.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, "Subject: " + selectedSubject,
                        Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                intent.putExtra(TakePhotoActivity.selectedSubject,selectedSubject);
                startActivity(intent);
            }
        });

        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ResultExcelActivity.class);
                startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
