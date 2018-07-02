package org.android.grtushar.todo.db;

import org.android.grtushar.todo.pojo.TODO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Golam Rahman Tushar on 2/5/2018.
 */

public class SampleTODOs {
    public static List<TODO> getSampleTODOs() {
        List<TODO> sampleTodos = new ArrayList<TODO>();
        //Date date = new Date();
        Calendar calendar = GregorianCalendar.getInstance();

        //create the dummy TODO
        TODO todo1 = new TODO();
        todo1.setTitle("DisneyLand Trip");
        todo1.setBookmarked(1);
        todo1.setCreateTime(calendar);
        todo1.setStatus(0);

        TODO todo2 = new TODO();
        todo2.setTitle("DisneyLand Trip");
        todo2.setBookmarked(1);
        todo2.setCreateTime(calendar);
        todo2.setStatus(0);

        TODO todo3 = new TODO();
        todo3.setTitle("DisneyLand Trip");
        todo3.setBookmarked(1);
        todo3.setCreateTime(calendar);
        todo3.setStatus(0);

        TODO todo4 = new TODO();
        todo4.setTitle("DisneyLand Trip");
        todo4.setBookmarked(1);
        todo4.setCreateTime(calendar);
        todo4.setStatus(0);

        TODO todo5 = new TODO();
        todo5.setTitle("DisneyLand Trip");
        todo5.setBookmarked(1);
        todo5.setCreateTime(calendar);
        todo5.setStatus(0);

        //add TODOs to the list
        sampleTodos.add(todo1);
        sampleTodos.add(todo2);
        sampleTodos.add(todo3);
        sampleTodos.add(todo4);
        sampleTodos.add(todo5);

        return sampleTodos;
    }
}
