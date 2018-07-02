package org.android.grtushar.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MiddleActivity extends AppCompatActivity {

    TextView message;
    Button goToDoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);

        message = findViewById(R.id.middleActivityTextView);
        goToDoButton = findViewById(R.id.goToDoButton);

        message.setText("Welcome " + getIntent().getStringExtra("name") + "!");

        goToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiddleActivity.this, LastActivity.class);
                startActivity(intent);
            }
        });
    }
}
