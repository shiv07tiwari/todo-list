package com.shivansh.attendance;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Dell on 12-06-2018.
 */

public class DataProvider extends ContentProvider {


    private DatabaseHelper mHelper;
    public static final int ALL_TODO = 100;
    public static final int ONE_TOTO = 101;
    @Override
    public boolean onCreate() {
        mHelper = new DatabaseHelper(getContext());
        return true;
    }

    public static UriMatcher buildUriMatcher()
    {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DataContract.CONTENT_AUTHORITY;

        matcher.addURI(authority,DataContract.PATH_TASK,ALL_TODO);
        matcher.addURI(authority,DataContract.PATH_TASK+"/#",ONE_TOTO);
        return matcher;
    }

    @Override
    public Cursor query(Uri uri,String[] projection, String selection, String[] selectionArgs, String s1) {

        Cursor retCursor = null;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int match = buildUriMatcher().match(uri);
        switch(match){
            case ALL_TODO:
                retCursor = db.query(DataContract.SubjectData.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        s1);
                break;
            case ONE_TOTO:
                selection = DataContract.SubjectData._ID+"=?";
                selectionArgs= new String[]{String.valueOf(ContentUris.parseId(uri))};
                retCursor = db.query(DataContract.SubjectData.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,
                        s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri,ContentValues contentValues) {

        SQLiteDatabase db = mHelper.getWritableDatabase();
        final int match = buildUriMatcher().match(uri);
        Uri returnuri = null;
        switch(match){
            case(ALL_TODO):
                long _id = db.insert(DataContract.SubjectData.TABLE_NAME,null, contentValues);
                if ( _id > 0 )
                    returnuri = DataContract.buildReturnInsertUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnuri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int rowsdeleted;
        final int match = buildUriMatcher().match(uri);
        switch (match){
            case(ALL_TODO):
                rowsdeleted = db.delete(DataContract.SubjectData.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknowen URI : " + uri);
        }
        if(rowsdeleted!=0)
             getContext().getContentResolver().notifyChange(uri,null);

        return rowsdeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
