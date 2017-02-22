package projectegco.com.myproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private Button submitBtn;
    private static Spinner subjectSpinner;
    private Button takePhoto;
    private Button result;
    private Button cancel;

    String selectedSubject;
    String idselectedSubject;
    SubjectDataSource subjectDataSource = new SubjectDataSource(this);
    Subject subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

//        ActionBar mActionBar = getSupportActionBar();
//        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009688")));

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle("Please Choose");
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
                idselectedSubject = getResources().getStringArray(R.array.subject_arrays_value)[subjectSpinner.getSelectedItemPosition()];

                Toast.makeText(MainActivity.this, "Subject: " + selectedSubject,
                        Toast.LENGTH_SHORT).show();
                System.out.println("subject: "+selectedSubject+" "+idselectedSubject);

                MyCommand myCommand = new MyCommand(getApplicationContext());
                String url = "http://192.168.13.112/upload/subjectname.php";
//                                String url = "http://"+ipAddress+"/upload/upload.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), "Error while uploading subject volley error subject", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Error while uploading subject volley error subject. Please try again.");
                        builder.setNegativeButton("Close",null);
                        builder.create();
                        builder.show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        //Add data to be send to php server
                        params.put("subjectname", selectedSubject);
                        System.out.println("sub12"+selectedSubject);
                        return params;
                    }
                };
                myCommand.add(stringRequest);
                myCommand.execute();

                subjectDataSource.open();
                subject = subjectDataSource.createSubject(selectedSubject,idselectedSubject);
                subjectDataSource.open();
             //   System.out.println("xxxx: "+subject.getId()+subject.getSubjectId()+subject.getSubjectName());



                dialog.show();
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
                intent.putExtra(TakePhotoActivity.selectedSubject,selectedSubject);
                intent.putExtra(TakePhotoActivity.idselectedSubject,idselectedSubject);



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

    @Override
    protected void onResume() {
        subjectDataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        subjectDataSource.close();
        super.onPause();
    }

}
