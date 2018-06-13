package com.shivansh.attendance;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodoDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView task;
    private TextView priority;
    private TextView deadline;
    private TextView description;
    DataAdapter mAdapter;
    private final static int LOADER_ID =1;
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    public TodoDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_detail, container, false);

        task = (TextView)view.findViewById(R.id.detail_task);
        Log.e(LOG_TAG,"Detail me");
        priority = (TextView)view.findViewById(R.id.detail_priority);
        deadline = (TextView)view.findViewById(R.id.detail_deadline);
        description = (TextView)view.findViewById(R.id.detail_description);
        mAdapter = new DataAdapter(getActivity(),null);

        getLoaderManager().initLoader(LOADER_ID, null, this);
//        Intent intent = getActivity().getIntent();
//        mForecastStr= intent.getDataString();
//
//        if (intent == null) {
//            return null;
//        }
//
//        ((TextView) view.findViewById(R.id.url)).setText(mForecastStr);
//        Log.e(LOG_TAG,"Hua");
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        String mForecastStr= intent.getDataString();
        Log.e(LOG_TAG,"URL : "+mForecastStr);

        String[] projection = {

                DataContract.SubjectData._ID,
                DataContract.SubjectData.TASK_NAME,
                DataContract.SubjectData.TASK_PRIORITY,
        DataContract.SubjectData.DEADLINE,
        DataContract.SubjectData.DESCRIPTION};

        return new CursorLoader(getActivity(),
                intent.getData(),
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if(cursor ==null)
            Log.e(LOG_TAG,"CURSOR NULL H BHAI");
        if (cursor != null && cursor.moveToFirst()) {

          String taskc = cursor.getString(1);
          Log.e(LOG_TAG,"Task in description : "+taskc);
          task.setText(taskc);

          String pp;
            int priorityc = cursor.getInt(2);
            switch (priorityc){
                case(DataContract.SubjectData.URGENT):
                    pp = "URGENT";
                    break;
                case(DataContract.SubjectData.NEUTRAL):
                    pp = "NEUTRAL";
                    break;
                default:
                    pp = "RELAX";
                    break;
            }
            priority.setText(pp);

            String deadc = cursor.getString(3);
            deadline.setText(deadc);

            String desc = cursor.getString(4);
            description.setText(desc);
            }
        }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
