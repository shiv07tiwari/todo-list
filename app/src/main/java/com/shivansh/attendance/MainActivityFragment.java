package com.shivansh.attendance;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import android.content.Context;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static ArrayAdapter<String> mToastAdapter;
    private final String LOG_TAG = MainActivity.class.getSimpleName();



    public static final int COLUMN_TASK = 1;
    private ListView subject;
    private final int COLUMN_PRIORITY = 2;
    private final int COLUMN_DEADLINE = 3;

   private ArrayList<String> places = new ArrayList<String>();
   //places.add("Hello");

    private TextView brain;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

        brain = (TextView) rootview.findViewById(R.id.brain);
        Log.e(LOG_TAG,"Total : "+brain);
        mToastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.subject_grid,
                R.id.one_subject,
                places
        );
        subject = (ListView) rootview.findViewById(R.id.subject_list);
        subject.setAdapter(mToastAdapter);
        return rootview;
    }
    @Override
    public void onResume() {
        Log.e(LOG_TAG,"OnResumeCalled");
        displayDatabaseInfo();
        super.onResume();
    }
    void clearlist()
    {
        subject.setAdapter(null);
    }

 void displayDatabaseInfo() {

    String[] projection = {
            DataContract.SubjectData._ID,
            DataContract.SubjectData.TASK_NAME,
            DataContract.SubjectData.TASK_PRIORITY,
            DataContract.SubjectData.DEADLINE
    };

    Cursor cursor = getActivity().getContentResolver().query(
            DataContract.SubjectData.CONTENT_URI,
            projection,
            null,
            null,
            null
    );
    try {
        cursor.moveToPosition(0);
        for(int i=1;i<=cursor.getCount();i++)
        {
            String task = cursor.getString(COLUMN_TASK);
            Log.e(LOG_TAG,"Task : "+task);
            int priority = cursor.getInt(COLUMN_PRIORITY);
            String deadline = cursor.getString(COLUMN_DEADLINE);
            Log.e(LOG_TAG,"Deadline : "+deadline);
            String finaldata = i+": Task : "+task+" Priority : "+priority+" Deadline : "+deadline;
            places.add(finaldata);
            cursor.moveToNext();
        }



        brain.setText("Number of Tasks to be performed : " + cursor.getCount());
    } finally {
        cursor.close();
    }
}
}
