package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;

public class Activity_Add_Program extends AppCompatActivity {
    EditText idProgram, programName, inputScore, outputScore, content, speak, write, read, listen, tuitionFees, certificate, studyPeriod;
    Button exitBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_program);
        
        idProgram = findViewById(R.id.idProgram);
        programName = findViewById(R.id.name);
        inputScore = findViewById(R.id.inputScore);
        outputScore = findViewById(R.id.outputScore);
        content = findViewById(R.id.content);
        speak = findViewById(R.id.speaking);
        write = findViewById(R.id.writing);
        listen = findViewById(R.id.listening);
        read = findViewById(R.id.reading);
        studyPeriod = findViewById(R.id.studyPeriod);
        tuitionFees = findViewById(R.id.tuitionFees);
        certificate = findViewById(R.id.certificate);

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
                if (idProgram.getText().toString().equals("") || programName.getText().toString().equals("")
                        || inputScore.getText().toString().equals("") || outputScore.getText().toString().equals("")
                        || content.getText().toString().equals("") || speak.getText().toString().equals("")
                        || write.getText().toString().equals("") || listen.getText().toString().equals("")
                        || read.getText().toString().equals("") || tuitionFees.getText().toString().equals("")
                        || studyPeriod.getText().toString().equals("") || certificate.toString().equals("")) {
                    Toast.makeText(Activity_Add_Program.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }
        });
    }
}