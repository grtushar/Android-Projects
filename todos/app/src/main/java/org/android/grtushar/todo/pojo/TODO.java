package org.android.grtushar.todo.pojo;

import android.database.Cursor;

import org.android.grtushar.todo.db.TodoConstants;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Golam Rahman Tushar on 1/31/2018.
 */

public class TODO {
    private long id;
    private String title;
    private Calendar createTime;
    private int status;
    private int bookmarked;
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(int bookmarked) {
        this.bookmarked = bookmarked;
    }
    
    public TODO() {}

    public TODO(String title, Calendar createTime) {
        this.title = title;
        this.createTime = createTime;
    }

    public TODO(String title, Calendar createTime, int status, int bookmarked) {
        this.title = title;
        this.createTime = createTime;
        this.status = status;
        this.bookmarked = bookmarked;
    }

    @Override
    public String toString() {
        return "TODO{" +
                "title='" + title + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public static TODO getTodofromCursor(Cursor cursor){
        TODO todo = new TODO();
        todo.setId(cursor.getLong(cursor.getColumnIndex(TodoConstants.COLUMN_ID)));
        todo.setTitle(cursor.getString(cursor.getColumnIndex(TodoConstants.COLUMN_TITLE)));
        todo.setStatus(cursor.getInt(cursor.getColumnIndex(TodoConstants.COLUMN_STATUS)));
        todo.setBookmarked(cursor.getInt(cursor.getColumnIndex(TodoConstants.COLUMN_BOOKMARKED)));

        //get Calendar instance
        Calendar calendar = GregorianCalendar.getInstance();

        //set the calendar time to date created
        calendar.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(TodoConstants.COLUMN_CREATED_TIME)));
        todo.setCreateTime(calendar);

        return todo;
    }
}
