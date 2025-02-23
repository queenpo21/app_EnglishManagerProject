package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.model.PotentialStudentDTO;

import java.time.LocalDate;
import java.util.List;

public class Activity_Add_Potential_Student extends AppCompatActivity {
    EditText studentID, studentName, phoneNumber, address, level, appointmentNumber;
    Button doneBtn, exitBtn;
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> genderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_potential_student);

        gender = findViewById(R.id.gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        gender.setAdapter(genderAdapter);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        // studentID = findViewById(R.id.idStudent);
        studentName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        level = findViewById(R.id.level);
        address = findViewById(R.id.address);
        appointmentNumber = findViewById(R.id.appointmentNumber);
        doneBtn = findViewById(R.id.done_btn);

        String message = getIntent().getStringExtra("studentID");
        final String idStudent = message;
        PotentialStudentDTO potentialStudent = null;
        Log.d("Id student put:", idStudent);
        if (!message.equals("")) {

            Log.d("Put message: ", message);

            List<PotentialStudentDTO> listPotentialStudent = PotentialStudentDAO.getInstance(
                    Activity_Add_Potential_Student.this).SelectStudent(Activity_Add_Potential_Student.this,
                    "ID_STUDENT = ?", new String[] {message});

            gender.setText(listPotentialStudent.get(0).getGender());
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);

            potentialStudent = listPotentialStudent.get(0);
            if (listPotentialStudent.size() != 0) {
                studentName.setText(potentialStudent.getStudentName());
                address.setText(potentialStudent.getAddress());
                phoneNumber.setText(potentialStudent.getPhoneNumber());
               // gender.setText(potentialStudent.getGender());
                level.setText(potentialStudent.getLevel());
                appointmentNumber.setText(potentialStudent.getAppointmentNumber());
                Log.d("Potential Student ID: ", message);
            }

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean acceptSwitch = true;    //Đúng thì mới trả về Fragment_Setting

                    if (address.getText().equals("")
                            || level.getText().equals("") || appointmentNumber.getText().equals("")
                            || studentName.getText().equals("") || gender.getText().equals("") || phoneNumber.getText().equals("")) {
                        acceptSwitch = false;
                    }
                    Log.d("Shift update student: ", "ok");

                    if (acceptSwitch) {

                        if (!isInteger(appointmentNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Potential_Student.this,
                                    "Số lượng cuộc hẹn phải là số nguyên dương!",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!Activity_Add_Official_Student.isValidPhoneNumber(phoneNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Potential_Student.this, "Định dạng số điện thoại " +
                                    "chưa chính xác!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            if (!idStudent.equals("") || !idStudent.equals(null)) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Potential_Student.this);
                                builder.setTitle("Thông báo")
                                        .setMessage("Bạn có chắn chắn muốn chỉnh sửa thông tin của học viên không?");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        int rowEffect = PotentialStudentDAO.getInstance(Activity_Add_Potential_Student.this).updatePotentialStudent(
                                                Activity_Add_Potential_Student.this, new PotentialStudentDTO(
                                                        idStudent,
                                                        studentName.getText().toString(),
                                                        phoneNumber.getText().toString(),
                                                        gender.getText().toString(),
                                                        address.getText().toString(),
                                                        level.getText().toString(),
                                                        appointmentNumber.getText().toString()), "ID_STUDENT = ?", new String[]{idStudent});
                                        Log.d("Update potential student: ", new PotentialStudentDTO(
                                                idStudent,
                                                studentName.getText().toString(),
                                                phoneNumber.getText().toString(),
                                                gender.getText().toString(),
                                                address.getText().toString(),
                                                level.getText().toString(),
                                                appointmentNumber.getText().toString()).toString());
                                        if (rowEffect > 0) {
                                            Toast.makeText(Activity_Add_Potential_Student.this,
                                                    "Sửa thông tin học viên tiềm năng thành công", Toast.LENGTH_SHORT).show();
                                            Log.d("Add potential student:", "success");
                                        }else {
                                            Log.d("Add potential student:", "fail");
                                        }
                                    }
                                });
                                builder.setNegativeButton("Hủy", null);
                                builder.show();
                            }
                        } catch (Exception e) {
                            Log.d("Add potential student error:", "fail");
                        }
                    }
                    else Toast.makeText(Activity_Add_Potential_Student.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (studentName.getText().toString().equals("") || address.getText().toString().equals("") ||
                            level.getText().toString().equals("") || appointmentNumber.getText().toString().equals("") ||
                            gender.getText().toString().equals("") || phoneNumber.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Potential_Student.this, "Hãy nhập đầy đủ thông tin",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isInteger(appointmentNumber.getText().toString())) {
                        Toast.makeText(Activity_Add_Potential_Student.this,
                                "Số lượng cuộc hẹn phải là số nguyên dương!",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!Activity_Add_Official_Student.isValidPhoneNumber(phoneNumber.getText().toString())) {
                        Toast.makeText(Activity_Add_Potential_Student.this, "Định dạng số điện thoại " +
                                "chưa chính xác!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Log.d("Add new potential student", "Ok");
                    Log.d("Create dialog", "Ok");
                    int rowEffect = PotentialStudentDAO.getInstance(Activity_Add_Potential_Student.this).InsertPotentialStudent(
                            Activity_Add_Potential_Student.this, new PotentialStudentDTO(
                                    idStudent,
                                    studentName.getText().toString(),
                                    phoneNumber.getText().toString(),
                                    gender.getText().toString(),
                                    address.getText().toString(),
                                    level.getText().toString(),
                                    appointmentNumber.getText().toString())
                    );

                    if (rowEffect > 0) {
                        Toast.makeText(Activity_Add_Potential_Student.this,
                                "Thêm học viên tiềm năng mới thành công", Toast.LENGTH_SHORT).show();
                        Log.d("Add potential student:", "success");

                        studentName.setText(null);
                        address.setText(null);
                        phoneNumber.setText(null);
                        gender.setText(null);
                        level.setText(null);
                        appointmentNumber.setText(null);

                    }else {
                        Log.d("Add potential student:", "fail");
                    }
                    //finish();
                }

            });
        }

        exitBtn = findViewById(R.id.exit_btn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public static boolean isInteger(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}