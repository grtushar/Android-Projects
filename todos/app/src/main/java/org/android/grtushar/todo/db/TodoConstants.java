package org.android.grtushar.todo.db;

import android.net.Uri;

import android.content.*;

/**
 * Created by Golam Rahman Tushar on 2/4/2018.
 */

public class TodoConstants {
    //Variables
    public static final String TODO_TABLE = "todos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_BOOKMARKED = "bookmarked";
    public static final String COLUMN_CREATED_TIME = "created_time";

    public static final String BASE_PATH_TODO = "todos";
    private static final String AUTHORITY = "org.android.grtushar.todo.data.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH_TODO);
    public static final int ITEM = 100;
    public static final int ITEMS = 101;
    public static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH_TODO, ITEMS);
        URI_MATCHER.addURI(AUTHORITY, BASE_PATH_TODO + "/#", ITEM);

    }

    public static final String[] COLUMNS = {
            COLUMN_ID,
            COLUMN_TITLE,
            COLUMN_STATUS,
            COLUMN_BOOKMARKED,
            COLUMN_CREATED_TIME
    };

    //Commands
    public static final String CREATE_TABLE_TODO = "create table "
            + TODO_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_STATUS + " text not null, "
            + COLUMN_BOOKMARKED + " integer not null, "
            + COLUMN_CREATED_TIME + " integer not null " + ")";
}
