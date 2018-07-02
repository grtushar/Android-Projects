package org.android.grtushar.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.android.grtushar.todo.db.DataBaseHelper;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText nameEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        Log.d("onCreate", "inside on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.getWritableDatabase();

        textView = findViewById(R.id.showTextView);
        nameEditText = findViewById(R.id.nameEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString = nameEditText.getText().toString();

                Intent intent = new Intent(MainActivity.this, MiddleActivity.class);
                intent.putExtra("name", nameString);
                startActivity(intent);
                //nameEditText.setText("");
            }
        });

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //Toast.makeText(getBaseContext(), "on main activity result", Toast.LENGTH_SHORT).show();
//        super.onActivityResult(requestCode, resultCode, data);
//        textView.setText("");
//        Toast.makeText(getBaseContext(), "on main activity result", Toast.LENGTH_SHORT).show();
//        Log.d("onResult", "inside on activity result");
//    }

    @Override
    protected void onResume() {
        super.onResume();
        nameEditText.setText("");
    }
}
