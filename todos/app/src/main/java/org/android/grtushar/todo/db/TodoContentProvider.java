package org.android.grtushar.todo.db;

/**
 * Created by Golam Rahman Tushar on 2/5/2018.
 */

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

public class TodoContentProvider extends ContentProvider {
    private DataBaseHelper dbHelper;
    @Override
    public boolean onCreate() {
        dbHelper = new DataBaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TodoConstants.TODO_TABLE);
        checkColumns(projection);
        System.out.println("uri: " + uri);

        int type = TodoConstants.URI_MATCHER.match(uri);
        switch (type){
            case TodoConstants.ITEMS:
                //there not to do if the query is for the table
                break;
            case TodoConstants.ITEM:
                queryBuilder.appendWhere(TodoConstants.COLUMN_ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int type = TodoConstants.URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Long id;

        switch (type) {
            case TodoConstants.ITEMS:
                id = db.insert(TodoConstants.TODO_TABLE, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(TodoConstants.BASE_PATH_TODO + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int type = TodoConstants.URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows;
        switch (type) {
            case TodoConstants.ITEMS:
                affectedRows = db.delete(TodoConstants.TODO_TABLE, selection, selectionArgs);
                break;

            case TodoConstants.ITEM:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.delete(TodoConstants.TODO_TABLE, TodoConstants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.delete(TodoConstants.TODO_TABLE, TodoConstants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int type = TodoConstants.URI_MATCHER.match(uri);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows;
        switch (type) {
            case TodoConstants.ITEMS:
                affectedRows = db.update(TodoConstants.TODO_TABLE, contentValues, selection, selectionArgs);
                break;

            case TodoConstants.ITEM:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    affectedRows = db.update(TodoConstants.TODO_TABLE, contentValues, TodoConstants.COLUMN_ID + "=" + id, null);
                } else {
                    affectedRows = db.update(TodoConstants.TODO_TABLE, contentValues, TodoConstants.COLUMN_ID + "=" + id + " and " + selection, selectionArgs);
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affectedRows;
    }

    //Check the validity of the requested projection
    private void checkColumns(String[] projection) {
        if (projection != null) {
            HashSet<String> request = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> available = new HashSet<String>(Arrays.asList(TodoConstants.COLUMNS));
            if (!available.containsAll(request)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
