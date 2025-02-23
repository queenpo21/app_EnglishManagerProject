package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ProgramDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications_Second_Layer extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_second_layer);

        String message1 = getIntent().getStringExtra("classID");
        String message2 = getIntent().getStringExtra("idCertificate");

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataArrayList = new ArrayList<>();

        if (!message1.equals("")) {
            List<ExaminationDTO> listExam = ExaminationDAO.getInstance(Activity_Notifications_Second_Layer.this)
                    .SelectExamination(Activity_Notifications_Second_Layer.this,
                            "ID_CLASS = ? AND STATUS  = ?", new String[] {message1, "0"});
            List<ExamScoreDTO> lisExamScore = ExamScoreDAO.getInstance(Activity_Notifications_Second_Layer.this)
                    .SelectExamScore(Activity_Notifications_Second_Layer.this,
                            "ID_EXAM = ? AND STATUS = ?",
                            new String[] {listExam.get(0).getIdExam(), "0"});
            for (int i = 0; i < lisExamScore.size(); i++) {
                dataArrayList.add(lisExamScore.get(i));
            }

            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_score_manage_item, dataArrayList);
        }
        Log.d("Id certificate put: ", message2);

        if (!message2.equals("")) {


            List<ProgramDTO> listProgram = ProgramDAO.getInstance(Activity_Notifications_Second_Layer.this)
                            .SelectProgram(Activity_Notifications_Second_Layer.this,
                                    "ID_CERTIFICATE = ? AND STATUS = ?",
                                    new String[] {message2, "0"});
            for (int i = 0; i < listProgram.size(); i++) {
                dataArrayList.add(listProgram.get(0));
            }
            /*dataArrayList.add(new ProgramDTO("1", "1"
                    , "1", "1", "1"
                    , "1", "1", "1"
                    , "1", 10, "1", "1"));*/
            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_education_program_item, dataArrayList);
        }

        listView.setAdapter(listAdapter);
    }

    @Override

    public void onStart()  {
        super.onStart();

        String message1 = getIntent().getStringExtra("classID");
        String message2 = getIntent().getStringExtra("idCertificate");

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataArrayList = new ArrayList<>();

        if (!message1.equals("")) {
            List<ExaminationDTO> listExam = ExaminationDAO.getInstance(Activity_Notifications_Second_Layer.this)
                    .SelectExamination(Activity_Notifications_Second_Layer.this,
                            "ID_CLASS = ? AND STATUS  = ?", new String[] {message1, "0"});
            List<ExamScoreDTO> lisExamScore = ExamScoreDAO.getInstance(Activity_Notifications_Second_Layer.this)
                    .SelectExamScore(Activity_Notifications_Second_Layer.this,
                            "ID_EXAM = ? AND STATUS = ?",
                            new String[] {listExam.get(0).getIdExam(), "0"});
            for (int i = 0; i < lisExamScore.size(); i++) {
                dataArrayList.add(lisExamScore.get(i));
            }

            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_score_manage_item, dataArrayList);
        }
        Log.d("Id certificate put: ", message2);

        if (!message2.equals("")) {


            List<ProgramDTO> listProgram = ProgramDAO.getInstance(Activity_Notifications_Second_Layer.this)
                    .SelectProgram(Activity_Notifications_Second_Layer.this,
                            "ID_CERTIFICATE = ? AND STATUS = ?",
                            new String[] {message2, "0"});
            for (int i = 0; i < listProgram.size(); i++) {
                dataArrayList.add(listProgram.get(0));
            }
            /*dataArrayList.add(new ProgramDTO("1", "1"
                    , "1", "1", "1"
                    , "1", "1", "1"
                    , "1", 10, "1", "1"));*/
            listAdapter = new List_Adapter(Activity_Notifications_Second_Layer.this, R.layout.list_education_program_item, dataArrayList);
        }

        listView.setAdapter(listAdapter);

    }

}