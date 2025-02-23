package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ScheduleDTO;

import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Add_Schedule extends AppCompatActivity {
    TextView startDate, endDate;
    DatePickerDialog.OnDateSetListener startDt, endDt;
    Button doneBtn, exitBtn;
    String[] dayOfWeekItem = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
    String[] classTimeItem = {"7h00 - 9h00", "9h00 - 11h00", "13h00 - 15h00", "15h00 - 17h00", "17h00 - 19h00", "19h00 - 21h00"};
   // String[] idClassroomItem = {"Huhu", "Chào Loan"};
    ArrayList<String> idClassroomItem = new ArrayList<>();
    AutoCompleteTextView dayOfWeek, classTime, idClassroom;
    ArrayAdapter<String> dayOfWeekAdapter, classTimeAdapter, idClassroomAdapter;
    String dayOfWeekText, classTimeText, idClassroomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        String message = getIntent().getStringExtra("idSchedule");
        dayOfWeek = findViewById(R.id.dayOfWeek);
        idClassroom = findViewById(R.id.idClassroom);
        classTime = findViewById(R.id.classTime);
        doneBtn = findViewById(R.id.done_btn);
        exitBtn = findViewById(R.id.exit_btn);

        dayOfWeekAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, dayOfWeekItem);
        dayOfWeek.setAdapter(dayOfWeekAdapter);
        dayOfWeekText = dayOfWeek.getText().toString();
        dayOfWeek.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               dayOfWeekText = parent.getItemAtPosition(position).toString();
               Log.d("Day of week: ", dayOfWeekText);
            }
        });

        classTimeAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, classTimeItem);
        classTime.setAdapter(classTimeAdapter);
        classTimeText = classTime.getText().toString();
        classTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classTimeText = parent.getItemAtPosition(position).toString();
                Log.d("Class time: ", classTimeText);
            }
        });

        List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_Add_Schedule.this)
                .SelectClassroom(Activity_Add_Schedule.this, "STATUS = ?",
                        new String[] {"0"});
        for (int i = 0; i < listClassroom.size(); i++) {
            idClassroomItem.add(listClassroom.get(i).getName());
        }

        idClassroomText = idClassroom.getText().toString();
        idClassroomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, idClassroomItem);
        idClassroom.setAdapter(idClassroomAdapter);
        idClassroom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idClassroomText = parent.getItemAtPosition(position).toString();
                Log.d("Id classroom: ", idClassroomText);
            }
        });

        if(!message.equals("")) {

            List<ScheduleDTO> lisSchedule = ScheduleDAO.getInstance(Activity_Add_Schedule.this)
                    .SelectSchedule(Activity_Add_Schedule.this,
                            "ID_SCHEDULE = ? AND STATUS = ?",
                            new String[] {message, "0"});

            if (Integer.parseInt(lisSchedule.get(0).getDayOfWeek()) > 7) {
                dayOfWeek.setText("Chủ nhật");
            } else {
                dayOfWeek.setText("Thứ " + lisSchedule.get(0).getDayOfWeek());
            }
            dayOfWeekAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, dayOfWeekItem);
            dayOfWeek.setAdapter(dayOfWeekAdapter);

            classTime.setText(lisSchedule.get(0).getStartTime() + "h00 - " +
                    lisSchedule.get(0).getEndTime() + "h00");
            classTimeAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, classTimeItem);
            classTime.setAdapter(classTimeAdapter);

            List<ClassroomDTO> classroomSelect = ClassroomDAO.getInstance(Activity_Add_Schedule.this)
                    .SelectClassroom(Activity_Add_Schedule.this,
                            "ID_CLASSROOM = ? AND STATUS = ?",
                            new String[] {lisSchedule.get(0).getIdClassroom(), "0"});

            idClassroom.setText(classroomSelect.get(0).getName());
            idClassroomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, idClassroomItem);
            idClassroom.setAdapter(idClassroomAdapter);

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Schedule.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn có chắc chắn muốn thoát?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
            });
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dayOfWeek.getText().equals("") || idClassroom.getText().equals("")
                        || classTime.getText().equals("")) {
                        Toast.makeText(Activity_Add_Schedule.this, "Hãy nhập đầy đủ thông tin!",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Schedule.this);
                        builder.setTitle("Thông báo")
                                .setMessage("Bạn có chắn chắn muốn chỉnh sửa thời khóa biểu của học viên không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Get day of week
                                /*int firstDigitIndex = dayOfWeek.getText().toString().indexOf("\\d");
                                if (firstDigitIndex!= -1 && !dayOfWeek.getText().toString().isEmpty()) {
                                    dayOfWeekText = dayOfWeek.getText().toString().substring(0, firstDigitIndex);
                                } else {
                                    dayOfWeekText = "8";
                                }*/

                                Pattern pattern = Pattern.compile("\\d+");
                                Matcher matcher = pattern.matcher(dayOfWeek.getText().toString());

                                if (matcher.find() && !dayOfWeek.getText().toString().isEmpty()) {
                                    // Lấy chỉ số của chữ số đầu tiên
                                    dayOfWeekText = matcher.group();
                                } else {
                                    dayOfWeekText = "8";
                                }

                                Log.d("Day of week text: ", dayOfWeekText);
                                Log.d("Day of week text: ", dayOfWeekText);
                                // Get time of class
                                classTimeText = classTime.getText().toString();
                                String[] parts = classTimeText.split("-");
                                String startHourStr = ""; // Lấy số giờ từ phần đầu
                                String endHourStr = "";

                                if (parts[0].length() == 6) {
                                    startHourStr = parts[0].substring(0,2);
                                } else {
                                   startHourStr = parts[0].substring(0,1);
                                }
                                if (parts[1].length() == 6) {
                                    endHourStr = parts[1].substring(1,3);
                                } else {
                                    endHourStr = parts[1].substring(1,2);
                                }

                                idClassroomText = idClassroom.getText().toString();
                                // Get id classroom
                                List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_Add_Schedule.this)
                                        .SelectClassroom(Activity_Add_Schedule.this,
                                                "NAME = ? AND STATUS = ? ",
                                                new String[] {idClassroomText, "0"});
                                Log.d("Classroom id text: ", idClassroomText);

                                ScheduleDTO scheduleUpdate = new ScheduleDTO(message, dayOfWeekText,
                                        startHourStr,endHourStr, lisSchedule.get(0).getIdClass(), listClassroom.get(0).getIdRoom());

                                try {
                                    int rowEffect = ScheduleDAO.getInstance(Activity_Add_Schedule.this)
                                            .UpdateSchedule(Activity_Add_Schedule.this, scheduleUpdate,
                                                    "ID_SCHEDULE = ? AND STATUS = ?",
                                                    new String[] {message, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Schedule.this, "Sửa thời khóa biểu thành công!",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Schedule.this, "Sửa thời khóa biểu thất bại!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } catch(Exception e) {
                                    Log.d("Update schedule error: ", e.getMessage());
                                }
                            }
                        });

                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
            });
        } else {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Schedule.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn có chắc chắn muốn thoát?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
            });
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dayOfWeek.getText().equals("") || classTime.getText().toString().equals("")
                    || idClassroom.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Schedule.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                        Log.d("Id class in list adapter", List_Adapter.idClassClick);
                    } else {
                        /*int firstDigitIndex = dayOfWeek.getText().toString().indexOf("\\d"); // "\\d" là biểu thức chính quy cho một chữ số
                        if (firstDigitIndex!= -1) {
                            dayOfWeekText = dayOfWeek.getText().toString().substring(0, firstDigitIndex);
                        } else {
                            dayOfWeekText = "8";
                        }*/

                        Pattern pattern = Pattern.compile("\\d+");
                        Matcher matcher = pattern.matcher(dayOfWeek.getText().toString());

                        if (matcher.find() && !dayOfWeek.getText().toString().isEmpty()) {
                            // Lấy chỉ số của chữ số đầu tiên
                            dayOfWeekText = matcher.group();
                        } else {
                            dayOfWeekText = "8";
                        }

                        Log.d("Day of week text: ", dayOfWeekText);

                        // Get time of class
                        String[] parts = classTimeText.split("-");
                        String startHourStr = parts[0].substring(0, 2); // Lấy số giờ từ phần đầu
                        String endHourStr = parts[1].substring(0, 2);

                        if (parts[0].length() == 6) {
                            startHourStr = parts[0].substring(0,2);
                        } else {
                            startHourStr = parts[0].substring(0,1);
                        }
                        if (parts[1].length() == 6) {
                            endHourStr = parts[1].substring(1,3);
                        } else {
                            endHourStr = parts[1].substring(1,2);
                        }

                        Log.d("Time hour: ", startHourStr + "  " + endHourStr);

                        // Get id classroom
                        List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_Add_Schedule.this)
                                .SelectClassroom(Activity_Add_Schedule.this,
                                        "NAME = ? AND STATUS = ? ",
                                        new String[] {idClassroomText, "0"});

                        List<ScheduleDTO> checkScheduleOfClassExist = ScheduleDAO.getInstance(Activity_Add_Schedule.this)
                                .SelectSchedule(Activity_Add_Schedule.this,
                                        "DAY_OF_WEEK = ? AND START_TIME = ? AND END_TIME = ? AND ID_CLASS = ? " +
                                                "AND ID_CLASSROOM = ? AND STATUS = ?",
                                        new String[] {dayOfWeekText,startHourStr,endHourStr,
                                                List_Adapter.idClassClick, listClassroom.get(0).getIdRoom(), "0" });
                        Log.d("Add new schedule and check: ", checkScheduleOfClassExist.toString());
                        if (checkScheduleOfClassExist.size() > 0) {
                            Toast.makeText(Activity_Add_Schedule.this, "Lịch học này của " +
                                    "lớp đã tồn tại!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<ScheduleDTO> checkScheduleOfOtherClassExist = ScheduleDAO.getInstance(Activity_Add_Schedule.this)
                                .SelectSchedule(Activity_Add_Schedule.this,
                                        "DAY_OF_WEEK = ? AND START_TIME = ? AND END_TIME = ? " +
                                                "AND ID_CLASSROOM = ? AND STATUS = ?",
                                        new String[] {dayOfWeekText,startHourStr,endHourStr,
                                                listClassroom.get(0).getIdRoom(), "0" });
                        if (checkScheduleOfOtherClassExist.size() > 0) {
                            Toast.makeText(Activity_Add_Schedule.this, "Lịch học này đã trùng với một " +
                                    "lớp khác!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ScheduleDTO scheduleNew = new ScheduleDTO(message, dayOfWeekText,
                                startHourStr,endHourStr, List_Adapter.idClassClick, listClassroom.get(0).getIdRoom());

                        try {
                            int rowEffect = ScheduleDAO.getInstance(Activity_Add_Schedule.this)
                                    .InsertSchedule(Activity_Add_Schedule.this, scheduleNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Schedule.this, "Thêm lịch học mới " +
                                        "cho lớp thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Schedule.this, "Thêm lịch học mới " +
                                        "cho lớp thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new schedule error: ", e.getMessage());
                        }

                    }
                }
            });
        }
    }
}