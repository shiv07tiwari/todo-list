package com.shivansh.attendance;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Dell on 13-06-2018.
 */

public class DataAdapter extends CursorAdapter {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    public DataAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_todo, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.e(LOG_TAG,"ID : "+cursor.getInt(0));
        String todo_item = cursor.getString(1);
        TextView task = (TextView) view.findViewById(R.id.text_todo_task);
        task.setText(todo_item);

        String priority;
        int prio = cursor.getInt(2);
        switch (prio){
            case(DataContract.SubjectData.URGENT):
                priority = "URGENT";
                break;
            case(DataContract.SubjectData.NEUTRAL):
                priority = "NEUTRAL";
                break;
            default:
                priority = "RELAX";
                break;
        }
        TextView task_priority = (TextView) view.findViewById(R.id.text_todo_priority);
        task_priority.setText(priority);
    }
}
