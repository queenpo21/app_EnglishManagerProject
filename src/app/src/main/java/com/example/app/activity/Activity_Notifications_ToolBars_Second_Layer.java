package com.example.app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications_ToolBars_Second_Layer extends AppCompatActivity {
    private String message1, message2, message3;
    Toolbar toolbar;
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    ArrayList<Object> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_tool_bars_second_layer);

        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_btn);

        dataArrayList = new ArrayList<>();
        list = new ArrayList<>();

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setAdapter(listAdapter);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onStart() {
        super.onStart();
        //listAdapter.clear();
        message1 = getIntent().getStringExtra("classID");
        message2 = getIntent().getStringExtra("classIDtoViewSchedule");
        message3 = getIntent().getStringExtra("idClass");
        String message7 = getIntent().getStringExtra("idSchedule");

        dataArrayList = new ArrayList<>();
        Log.d("Message found: ", message1 + "  " + message2);

        if (!message1.equals("")) {

            toolbar.setTitle("Danh sách học viên");
            String message = getIntent().getStringExtra("classID");
            List<TeachingDTO> listTeaching = new ArrayList<>();

            try {
                listTeaching = TeachingDAO.getInstance(
                        Activity_Notifications_ToolBars_Second_Layer.this).SelectTeaching(
                        Activity_Notifications_ToolBars_Second_Layer.this,
                        "ID_CLASS = ? AND STATUS = ?", new String[]{message, "0"});
                Log.d("Get list student error", listTeaching.toString());
            } catch (Exception e) {
                Log.d("Get list student error", e.getMessage());
            }

            List<OfficialStudentDTO> listStudent = new ArrayList<>();
            for (int i = 0; i < listTeaching.size(); i++) {
                try {
                    List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                            Activity_Notifications_ToolBars_Second_Layer.this).SelectStudentVer2(
                            Activity_Notifications_ToolBars_Second_Layer.this,
                            "ID_STUDENT = ? AND STATUS = ?",
                            new String[]{listTeaching.get(i).getIdStudent(), "0"});
                    Log.d("Get student exists", student.toString());
                    listStudent.add(student.get(0));

                } catch (Exception e) {
                    Log.d("Get list student in each class", e.getMessage());
                }
            }
            //  Log.d("Get list student error", listStudent.toString());

            for (int i = 0; i < listStudent.size(); i++) {
                dataArrayList.add(listStudent.get(i));
            }

            // dataArrayList.add(new OfficialStudentDTO("1","1","1","1","1","1",1));
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_offfical_student_item, dataArrayList);
        }
        Log.d("Id_Class found:", message2);
        if (!message2.equals("")) {
            toolbar.setTitle("Lịch học");

            List<ScheduleDTO> listSchedule = ScheduleDAO.getInstance(Activity_Notifications_ToolBars_Second_Layer.this)
                    .SelectSchedule(Activity_Notifications_ToolBars_Second_Layer.this,
                            "ID_CLASS = ? AND STATUS = ?",
                            new String[] {message2, "0"});
            for (int i = 0; i < listSchedule.size(); i++) {
                dataArrayList.add(listSchedule.get(i));
            }

            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_schedule_manage_item, dataArrayList);
        }

        if (!message3.equals("")) {
            toolbar.setTitle("Danh sách điểm của học viên theo lớp");

            List<TeachingDTO> listTeaching = TeachingDAO.getInstance(Activity_Notifications_ToolBars_Second_Layer.this)
                    .SelectTeaching(Activity_Notifications_ToolBars_Second_Layer.this,
                            "ID_CLASS = ? AND STATUS = ?",
                            new String[] {message3, "0"});

            List<ExaminationDTO>exam = ExaminationDAO.getInstance(Activity_Notifications_ToolBars_Second_Layer.this)
                    .SelectExamination(Activity_Notifications_ToolBars_Second_Layer.this,
                            "ID_CLASS = ? AND STATUS = ?", new String[] {message3, "0"});
            if (exam.size() > 0) {
                List<ExamScoreDTO> listExam = ExamScoreDAO.getInstance(Activity_Notifications_ToolBars_Second_Layer.this)
                        .SelectExamScore(Activity_Notifications_ToolBars_Second_Layer.this,
                                "ID_EXAM = ? AND STATUS = ?",
                                new String[] {exam.get(0).getIdExam(), "0"});
                for (int i = 0; i < listExam.size(); i++) {
                    dataArrayList.add(listExam.get(i));
                }
            }
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this,
                    R.layout.list_score_manage_item, dataArrayList);

        }


        /*if (!message1.equals("")) {
            //toolbar.setTitle("Chi tiết lớp học");
            dataArrayList.add(new ExamScoreDTO("1","1","1","1","1","1","1"));
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_score_manage_item, dataArrayList);
        }

        if (!message2.equals("")) {
            dataArrayList.add(new ProgramDTO("1", "1"
                    , "1", "1", "1"
                    , "1", "1", "1"
                    , "1", 10, "1", "1"));
            listAdapter = new List_Adapter(Activity_Notifications_ToolBars_Second_Layer.this, R.layout.list_education_program_item, dataArrayList);
        }*/
        listView.setAdapter(listAdapter);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        /*Intent addOffical = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Official_Student.class);
        addOffical.putExtra("studentID", "");
        startActivity(addOffical);*/
        if (id == R.id.add) {
            if (!message1.equals("")) {
                Intent addOffical = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Official_Student.class);
                addOffical.putExtra("studentID", "");
                startActivity(addOffical);
            }
            if (!message2.equals("")) {
                Intent addSchedule = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Schedule.class);
                addSchedule.putExtra("idSchedule", "");
                startActivity(addSchedule);
            }
            if (!message3.equals("")) {
                Intent addScore = new Intent(Activity_Notifications_ToolBars_Second_Layer.this, Activity_Add_Exam_Score.class);
                addScore.putExtra("idStudent", "");
                startActivity(addScore);
            }
        }

        if (id == R.id.search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Search here");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filterList(newText);
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterList(String text) {
        list.clear();
        for (int i = 0; i < dataArrayList.size(); i++) {
            Object item = dataArrayList.get(i);
            if (item instanceof ScheduleDTO) {
                ScheduleDTO scheduleDTO = (ScheduleDTO) item;
                if (scheduleDTO.getIdClass().toLowerCase().contains(text.toLowerCase()))
                    list.add(scheduleDTO);
            }

            if (item instanceof OfficialStudentDTO) {
                OfficialStudentDTO officialStudentDTO = (OfficialStudentDTO) item;
                if (officialStudentDTO.getFullName().toLowerCase().contains(text.toLowerCase()))
                    list.add(officialStudentDTO);
            }

            if (item instanceof ExamScoreDTO) {
                ExamScoreDTO examScoreDTO = (ExamScoreDTO) item;
                if (examScoreDTO.getIdStudent().toLowerCase().contains(text.toLowerCase()))
                    list.add(examScoreDTO);
            }
        }
        if (list.isEmpty())
            Toast.makeText(this, "Không tìm thấy dữ liệu nào", Toast.LENGTH_SHORT).show();
        else {
            listAdapter.setFilterList(list);
            //listAdapter.notifyDataSetChanged();
        }
    }
}