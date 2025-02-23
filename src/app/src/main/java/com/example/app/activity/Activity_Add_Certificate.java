package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Certificate extends AppCompatActivity {
    EditText idCertificate, name, content;
    Button exitBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_certificate);
        String message = getIntent().getStringExtra("idCertificate");

    //    idCertificate = findViewById(R.id.idCertificate);
        name = findViewById(R.id.name);
        content = findViewById(R.id.content);

        if (!message.equals("")) {

        }

        exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        doneBtn = findViewById(R.id.done_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( name.getText().toString().equals("") || content.getText().toString().equals("")) {
                    Toast.makeText(Activity_Add_Certificate.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });

    }
}