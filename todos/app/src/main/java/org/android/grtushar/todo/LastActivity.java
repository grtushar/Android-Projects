package org.android.grtushar.todo;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.*;
import android.widget.TextView;
import android.widget.Toast;

import org.android.grtushar.todo.db.DataBaseHelper;
import org.android.grtushar.todo.db.SampleTODOs;
import org.android.grtushar.todo.db.TodoDAO;
import org.android.grtushar.todo.pojo.TODO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LastActivity extends AppCompatActivity {

    EditText addToEditText;
    Button addToButton;
    ListView toDoListView;

    private ArrayList<TODO> toDos;

    BaseAdapter adapter;
    ArrayAdapter<TODO> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.getWritableDatabase();

        //initDb();
        initializeAll();
    }

    private void initializeAll() {
        addToEditText = findViewById(R.id.addToDoEditText);
        addToButton = findViewById(R.id.addToDoButton);
        toDoListView = findViewById(R.id.toDoListView);
        toDos = new ArrayList<TODO>(TodoDAO.getTodoDAOInstance(getBaseContext()).getAllTodos());

        adapter = new BaseAdapter() {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public int getCount() {
                return toDos.size();
            }

            @Override
            public Object getItem(int position) {
                return toDos.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                if(view == null) {
                    view = inflater.inflate(R.layout.todolayout, null);
                }
                TextView todoTitle = view.findViewById(R.id.titleTextView),
                         createDate = view.findViewById(R.id.createDateTextView);

                todoTitle.setText(toDos.get(position).getTitle());
                Calendar calendar = toDos.get(position).getCreateTime();
                createDate.setText(android.text.format.DateFormat.format("dd-MM-yyyy HH:mm:ss a", calendar));

                return view;
            }
        };

        //arrayAdapter = new ArrayAdapter<TODO>(this, R.layout.todolayout, toDos);
        toDoListView.setAdapter(adapter);
        registerForContextMenu(toDoListView);

        //onscreen done button to add todo_item
        addToEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_NEXT) {
                    addTodo();
                }
                return false;
            }
        });

        // <editor-fold desc="addToButtonOnClickListener">
        addToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
        // </editor-fold>

//        toDoListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
//                openDelDialogBox(position);
//                return false;
//            }
//        });
        //

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.toDoListView) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle("Options");
            String[] menuItems = {"Edit", "Delete", "Bookmark", "Mark As Complete"};
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
        //Toast.makeText(getBaseContext(), "hello from on create context menu!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int optionNo = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int itemPosition = info.position;
        if(optionNo == 1) {
            TodoDAO.getTodoDAOInstance(getApplicationContext()).delete(toDos.get(itemPosition));
            toDos.remove(itemPosition);
            adapter.notifyDataSetChanged();

            Toast.makeText(getBaseContext(), "Todo Removed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "Under Construction!", Toast.LENGTH_SHORT).show();
        }
        return true;
        //return super.onContextItemSelected(item);
    }

    private void openDelDialogBox(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LastActivity.this);

        builder.setCancelable(true);
        builder.setTitle("Alert!");
        builder.setMessage("Are you sure you want to delete the TODO item?");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TodoDAO.getTodoDAOInstance(getApplicationContext()).delete(toDos.get(position));
                toDos.remove(position);
                adapter.notifyDataSetChanged();

                Toast.makeText(getBaseContext(), "Todo Removed!", Toast.LENGTH_SHORT).show();

            }
        });

        builder.show();
    }
    private void initDb() {
        ArrayList<TODO> sampleTodos = (ArrayList<TODO>) SampleTODOs.getSampleTODOs();

        for(TODO todo: sampleTodos) {
            Long temp = TodoDAO.getTodoDAOInstance(this).insert(todo);
            System.out.println("Temp: " + temp);
        }
    }
    private void addTodo() {
        String title = addToEditText.getText().toString();
        //check if string has only white spaces;
        if(title.trim().length() == 0) {
            Toast.makeText(getBaseContext(), "Write Something!", Toast.LENGTH_SHORT).show();
            return;
        }

        Calendar calendar = GregorianCalendar.getInstance();

        TODO todo = new TODO(title, calendar, 0, 0);
        toDos.add(todo);
        TodoDAO.getTodoDAOInstance(getApplicationContext()).insert(todo);
        adapter.notifyDataSetChanged();
        addToEditText.setText("");
//                hide virtual keyboard
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(addToEditText.getWindowToken(), 0);
        Toast.makeText(getBaseContext(), "Todo Added!", Toast.LENGTH_SHORT).show();
    }
}
