package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.app.R;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class Activity_Add_Class extends AppCompatActivity {
    EditText classID, className, idTeacher, staffID;
    TextView startDate, endDate;
    Button exitBtn, doneBtn;
    String[] programIDItem;
    AutoCompleteTextView program;
    ArrayAdapter<String> roomAdapter, programAdapter;
    DatePickerDialog.OnDateSetListener startDt, endDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        String message = getIntent().getStringExtra("classID");

        List<ProgramDTO> listProgramAdapter = ProgramDAO.getInstance(Activity_Add_Class.this)
                .SelectProgram(Activity_Add_Class.this, "STATUS = ?",
                        new String[] {"0"});
        programIDItem = new String[listProgramAdapter.size()];
        for (int i = 0; i < listProgramAdapter.size(); i++) {
            programIDItem[i] = listProgramAdapter.get(i).getNameProgram();
        }

        program = findViewById(R.id.programID);
        programAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, programIDItem);
        program.setAdapter(programAdapter);
        program.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        startDate = findViewById(R.id.start_date);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Class.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        startDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                startDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        endDate = findViewById(R.id.end_date);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Class.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        endDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                endDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

      //  classID = findViewById(R.id.classID);
        className = findViewById(R.id.class_name);
        idTeacher = findViewById(R.id.idTeacher);
        staffID = findViewById(R.id.staffID);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        List<ClassDTO> listClass = ClassDAO.getInstance(Activity_Add_Class.this).selectClass(
                Activity_Add_Class.this, "ID_CLASS = ? AND STATUS = ?",
                new String[] {message, "0"});
        if (!message.equals("")) {

            Log.d("Id class send", listClass.get(0).toString());

            className.setText(listClass.get(0).getClassName());
            startDate.setText(listClass.get(0).getStartDate());
            endDate.setText(listClass.get(0).getEndDate());

            String idProgramText = listClass.get(0).getIdProgram();
            List<ProgramDTO> programArr = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                    Activity_Add_Class.this, "ID_PROGRAM = ? AND STATUS = ?",
                    new String[] {idProgramText, "0"});
            String idTeacherText = listClass.get(0).getIdTeacher();
            List<TeacherDTO> teacherArr = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                    Activity_Add_Class.this, "ID_TEACHER = ? AND STATUS = ?",
                    new String[] {idTeacherText, "0"});
            String idStaff = listClass.get(0).getIdStaff();
            List<StaffDTO> staffArr = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                    Activity_Add_Class.this, "ID_STAFF = ? AND STATUS = ?",
                    new String[] {idStaff, "0"});

            /*programID.setText(program.get(0).getNameProgram().toString());
            teacherName.setText(teacher.get(0).getFullName().toString());*/
            staffID.setText(staffArr.get(0).getFullName().toString());
            idTeacher.setText(teacherArr.get(0).getFullName());
            program.setText(programArr.get(0).getNameProgram());
            // toolbar.setTitle(className.getText().toString()  + classID.getText().toString());


            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (className.getText().toString().equals("")
                            || startDate.getText().toString().equals("") || endDate.getText().toString().equals("")
                            || staffID.getText().toString().equals("") || program.getText().toString().equals("")
                            || idTeacher.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Class.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

                        LocalDate date1 = LocalDate.parse(startDate.getText().toString(), formatter);
                        LocalDate date2 = LocalDate.parse(endDate.getText().toString(), formatter);
                        if (date1.isAfter(date2)) {
                            Toast.makeText(Activity_Add_Class.this, "Ngày kết thúc lớp học " +
                                    "phải sau ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin lớp học này không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                List<ProgramDTO> programSelect = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                                        Activity_Add_Class.this, "NAME = ? AND STATUS = ?",
                                        new String[] {program.getText().toString(), "0"});
                                List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                                        Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                        new String[] {idTeacher.getText().toString(), "0"});
                                List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                                        Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                        new String[] {staffID.getText().toString(), "0"});

                                String errorMessage1 = "";
                                String errorMessage2 = "";
                                boolean flag = true;
                                if (teacher.size() <= 0) {
                                    errorMessage1 = "sai tên giảng viên";
                                    flag = false;
                                }
                                if (staff.size() <= 0 )  {
                                    errorMessage2 = "sai tên nhân viên quản lý";
                                    flag = false;
                                }

                                if (!flag && !errorMessage1.equals("") && !errorMessage2.equals("")) {
                                    Toast.makeText(Activity_Add_Class.this, "Bạn đã nhập " +
                                            errorMessage1 + " và " + errorMessage2, Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (!flag) {
                                    Toast.makeText(Activity_Add_Class.this, "Bạn đã nhập " +
                                            errorMessage1 + errorMessage2, Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                ClassDTO classUpdate = new ClassDTO(message, className.getText().toString(),
                                        startDate.getText().toString(), endDate.getText().toString(),
                                        programSelect.get(0).getIdProgram(), teacher.get(0).getIdTeacher(), staff.get(0).getIdStaff(), "0");

                                try {
                                    int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).UpdateClass(
                                            Activity_Add_Class.this, classUpdate,"ID_CLASS = ? AND STATUS = ?",
                                            new String[] {message, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Class.this, "Sửa thông tin lớp học thành công",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d("Update class failed", "failed");
                                    }
                                } catch ( Exception e) {
                                    Log.d("Update class error: ", e.getMessage());
                                }
                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
            });

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn chưa hoàn thành chỉnh sửa, bạn có chắc chắn muốn thoát?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy", null);
                    builder.show();
                }
            });

        } else {
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Class.this);
                    builder.setTitle("Xác nhận")
                            .setMessage("Bạn chưa hoàn thành, bạn có chắc chắn muốn thoát?");
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
                    if (className.getText().toString().equals("")
                            || startDate.getText().toString().equals("") || idTeacher.getText().toString().equals("")
                            || endDate.getText().toString().equals("") || program.getText().toString().equals("")
                            || staffID.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Class.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");

                        LocalDate date1 = LocalDate.parse(startDate.getText().toString(), formatter);
                        LocalDate date2 = LocalDate.parse(endDate.getText().toString(), formatter);
                        if (date1.isAfter(date2)) {
                            Toast.makeText(Activity_Add_Class.this, "Ngày kết thúc lớp học " +
                                    "phải sau ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<ProgramDTO> programSelect = ProgramDAO.getInstance(Activity_Add_Class.this).SelectProgram(
                                Activity_Add_Class.this, "NAME = ? AND STATUS = ?",
                                new String[] {program.getText().toString(), "0"});
                        List<TeacherDTO> teacher = TeacherDAO.getInstance(Activity_Add_Class.this).SelectTeacher(
                                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                new String[] {idTeacher.getText().toString(), "0"});
                        List<StaffDTO> staff = StaffDAO.getInstance(Activity_Add_Class.this).SelectStaffVer2(
                                Activity_Add_Class.this, "FULLNAME = ? AND STATUS = ?",
                                new String[] {staffID.getText().toString(), "0"});

                        String errorMessage1 = "";
                        String errorMessage2 = "";
                        boolean flag = true;
                        if (teacher.size() <= 0) {
                            errorMessage1 = "sai tên giảng viên";
                            flag = false;
                        }
                        if (staff.size() <= 0 )  {
                            errorMessage2 = "sai tên nhân viên quản lý";
                            flag = false;
                        }

                        if (!flag && !errorMessage1.equals("") && !errorMessage2.equals("")) {
                            Toast.makeText(Activity_Add_Class.this, "Bạn đã nhập " +
                                    errorMessage1 + " và " + errorMessage2, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (!flag) {
                            Toast.makeText(Activity_Add_Class.this, "Bạn đã nhập " +
                                   errorMessage1 + errorMessage2, Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ClassDTO classNew = new ClassDTO(message, className.getText().toString(),
                                startDate.getText().toString(), endDate.getText().toString(),
                                programSelect.get(0).getIdProgram(), teacher.get(0).getIdTeacher(),
                                staff.get(0).getIdStaff(), "0");

                        try {
                            int rowEffect = ClassDAO.getInstance(Activity_Add_Class.this).InsertClass(
                                    Activity_Add_Class.this, classNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Class.this, "Thêm lớp học mới thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new class failed", "failed");
                        }

                        String idClassJustAdd = "";
                        List<ClassDTO> classJustAdd = ClassDAO.getInstance(Activity_Add_Class.this)
                                .selectClass(Activity_Add_Class.this,
                                        "NAME = ? AND START_DATE = ? AND END_DATE = ? AND STATUS = ?",
                                        new String[] {classNew.getClassName(),
                                        classNew.getStartDate(), classNew.getEndDate(), "0"});
                        if (classJustAdd.size() > 0) {
                            idClassJustAdd = classJustAdd.get(0).getClassID();
                        }

                        ExaminationDTO examination = new ExaminationDTO(null,
                                "Kỳ thi tổng két cuối khóa", "Chuẩn format quốc tế",
                                endDate.getText().toString(), idClassJustAdd, "B1.11");
                        int rowEffectAddExam = ExaminationDAO.getInstance(Activity_Add_Class.this)
                                        .InsertExamination(Activity_Add_Class.this, examination);
                        if (rowEffectAddExam > 0) {
                            Log.d("Insert new exam", "success");
                        } else {
                            Log.d("Insert new exam", "failed");
                        }

                        className.setText("");
                        startDate.setText("");
                        endDate.setText("");
                        idTeacher.setText("");
                        staffID.setText("");
                    }
                }
            });

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.normal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}