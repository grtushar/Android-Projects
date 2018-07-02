package org.android.grtushar.todo.db;

/**
 * Created by Golam Rahman Tushar on 2/6/2018.
 */

import android.content.*;
import android.database.Cursor;
import android.net.Uri;

import org.android.grtushar.todo.pojo.TODO;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO {
    private Context mContext;
    private static TodoDAO todoDAOInstance = null;

    public static TodoDAO getTodoDAOInstance(Context context) {
        if(todoDAOInstance == null) {
            todoDAOInstance = new TodoDAO(context);
        }

        return todoDAOInstance;
    }

    private TodoDAO(Context context) {
        this.mContext = context;
    }

    public long insert(TODO todo) {
        ContentValues values = new ContentValues();
        values.put(TodoConstants.COLUMN_TITLE, todo.getTitle());
        values.put(TodoConstants.COLUMN_STATUS, todo.getStatus());
        values.put(TodoConstants.COLUMN_CREATED_TIME, System.currentTimeMillis());
        values.put(TodoConstants.COLUMN_BOOKMARKED, System.currentTimeMillis());
        Uri result = mContext.getContentResolver().insert(TodoConstants.CONTENT_URI, values);
        long id = Long.parseLong(result.getLastPathSegment());
        return id;
    }

    public TODO getTodo(Long id) {
        TODO todo;
        Cursor cursor = mContext.getContentResolver().query(TodoConstants.CONTENT_URI,
                TodoConstants.COLUMNS, TodoConstants.COLUMN_ID + " = " + id, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            todo = TODO.getTodofromCursor(cursor);
            return todo;
        }
        return null;
    }

    public List<TODO> getAllTodos() {
        List<TODO> todos = new ArrayList<TODO>();
        Cursor cursor = mContext.getContentResolver().query(TodoConstants.CONTENT_URI, TodoConstants.COLUMNS, null, null, null);
        if (cursor != null){
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                todos.add(TODO.getTodofromCursor(cursor));
                cursor.moveToNext();
            }
            cursor.close();
        }
        return todos;
    }

    public void delete(TODO todo) {
        mContext.getContentResolver().delete(
                TodoConstants.CONTENT_URI, TodoConstants.COLUMN_ID + "=" + todo.getId(), null);
    }
}
