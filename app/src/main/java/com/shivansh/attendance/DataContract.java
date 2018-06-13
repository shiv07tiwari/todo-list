package com.shivansh.attendance;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.shivansh.attendance.DataContract.SubjectData.CONTENT_URI;

/**
 * Created by Dell on 10-06-2018.
 */

public class DataContract {



    public static final String CONTENT_AUTHORITY = "com.shivansh.attendance";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_TASK = "tododata";

    public static final class SubjectData implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();

        public static final String TABLE_NAME = "tododata";
        public static final String TASK_NAME = "task";
        public static final String TASK_PRIORITY = "priority";
        public static final String DEADLINE ="deadline" ;
        public static final String DESCRIPTION = "description";

        public static final int URGENT =1;
        public static final int NEUTRAL =2;
        public static final int CHILL =3;
    }
    public static Uri buildforonetodo(int index){
        String indexstr = String.valueOf(index);
        return CONTENT_URI.buildUpon().appendPath(indexstr).build();
    }
    public static Uri buildReturnInsertUri(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}
