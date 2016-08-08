package com.example.dell.asnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.dell.asnavigation.R;
import com.example.dell.asnavigation.FloatingHintEditText;
/**
 * Created by dell on 7/31/2016.
 */
public class AddNew extends ActionBarActivity  {
    FloatingHintEditText hospitalname,fordc,doctorname,result;
    Button submit;
    String hosp_name,for_c,doc_name,result_s;
    DbHelper dbHelper;
    private Toolbar mToolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        // item.setVisible(false);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
         dbHelper=new DbHelper(this);
        dbHelper.onCreate();
        hospitalname=(FloatingHintEditText)findViewById(R.id.hopitalname);
        fordc=(FloatingHintEditText)findViewById(R.id.fordc);
        doctorname=(FloatingHintEditText)findViewById(R.id.docname);
        result=(FloatingHintEditText)findViewById(R.id.result);
        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hosp_name=hospitalname.getText().toString();
                for_c=fordc.getText().toString();
                doc_name=doctorname.getText().toString();
                result_s=result.getText().toString();
                /*if(hosp_name.trim().equals("")&& for_c.trim().equals("")&&doc_name.trim().equals("")&&result_s.trim().equals(""))
                {
                    hospitalname.setError("Field is Required");
                    fordc.setError("Field is Required");
                    doctorname.setError("Field is Required");
                    result.setError("Field is Required");
                }
                else {*/
           if(!hosp_name.trim().equals("")&&!for_c.trim().equals("")&&!doc_name.trim().equals("")&&!result_s.trim().equals("")) {
               if (dbHelper.insertProfileevent(hosp_name, for_c, doc_name, result_s)) {
                   Toast.makeText(getApplicationContext(), "Successfully inserted", Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(AddNew.this, PatientHistory.class);
                   startActivity(intent);
                   finish();
               }
           }
                      else
           {
               hospitalname.setError("Enter the hospital name");
               fordc.setError("Enter the Value");
               doctorname.setError("Enter the Doctor name");
               result.setError("Enter the Result value");
         //  Toast.makeText(AddNew.this,"Enter Details",Toast.LENGTH_LONG).show();//}
           }
            }
        });

    }
}
