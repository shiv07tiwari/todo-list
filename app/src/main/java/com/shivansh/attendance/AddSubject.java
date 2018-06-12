package com.shivansh.attendance;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSubject extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private EditText mTaskEditText;
    private String task;
    private String deadline;
    private EditText mDeadlineEditText;

    /** EditText field to enter the pet's gender */

    private int mPriority = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mTaskEditText = (EditText) findViewById(R.id.todo_task);
        mDeadlineEditText = (EditText) findViewById(R.id.todo_deadline);

        Spinner mPriorityspinner = (Spinner) findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mPriorityspinner.setAdapter(adapter);

        mPriorityspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Urgent")) {
                        mPriority = DataContract.SubjectData.URGENT;
                    } else if (selection.equals("Neutral")) {
                        mPriority = DataContract.SubjectData.NEUTRAL; // Female
                    } else if (selection.equals("Relaxed")) {
                        mPriority = DataContract.SubjectData.CHILL;
                    } else {
                        mPriority = 0;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                mPriority=0;
            }
        });

    }

    public void InsertData(){

        Editable taskeditable = mTaskEditText.getText();
        task = taskeditable.toString();
        Log.e(LOG_TAG,"kya bhara :" +task);

        Editable deadedittable = mDeadlineEditText.getText();
        deadline = deadedittable.toString();


        ContentValues todovalues = new ContentValues();
        todovalues.put(DataContract.SubjectData.TASK_NAME, task);
        todovalues.put(DataContract.SubjectData.TASK_PRIORITY,mPriority);
        todovalues.put(DataContract.SubjectData.DEADLINE,deadline);

        Uri returnuri = getContentResolver().insert(DataContract.SubjectData.CONTENT_URI,todovalues);
        Log.e(LOG_TAG,"ID : "+ returnuri);
        Toast.makeText(this, "ITEM added with new row uri :"+ returnuri ,Toast.LENGTH_LONG).show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adddata, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done) {
            InsertData();
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
