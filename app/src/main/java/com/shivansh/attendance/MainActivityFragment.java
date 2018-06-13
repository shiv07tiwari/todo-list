package com.shivansh.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
   // public static ArrayAdapter<String> mToastAdapter;
    private DataAdapter mAdapter;
    private final static int LOADER_ID =2;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView brain;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_main, container, false);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        mAdapter = new DataAdapter(getActivity(), null);

        brain = (TextView) rootview.findViewById(R.id.brain);

        ListView listView = (ListView)rootview.findViewById(R.id.subject_list); //list view dhunda
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // CursorAdapter returns a cursor at the correct position for getItem(), or null
                // if it cannot seek to that position.
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position+1);
                if (cursor != null) {

                    Intent intent = new Intent(getActivity(), TodoDetail.class)
                            .setData(DataContract.buildforonetodo(position+1));
                    startActivity(intent);
                }
            }
        });

        return rootview;
    }
    @Override
    public void onResume() {
        Log.e(LOG_TAG,"OnResumeCalled");
       displayDatabaseInfo();
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

         String[] projection = {
                // In this case the id needs to be fully qualified with a table name, since
                // the content provider joins the location & weather tables in the background
                // (both have an _id column)
                // On the one hand, that's annoying.  On the other, you can search the weather table
                // using the location set by the user, which is only in the Location table.
                // So the convenience is worth it.
                 DataContract.SubjectData._ID,
                 DataContract.SubjectData.TASK_NAME,
                 DataContract.SubjectData.TASK_PRIORITY};
         Log.e(LOG_TAG,"Original : "+DataContract.SubjectData.CONTENT_URI);
        return new CursorLoader(getActivity(),
                DataContract.SubjectData.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    void displayDatabaseInfo() {

        Cursor cursor = getActivity().getContentResolver().query(
                DataContract.SubjectData.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        Log.e(LOG_TAG,"Total items : "+cursor.getCount());
        try {
            brain.setText("Number of Tasks to be performed : " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

}
