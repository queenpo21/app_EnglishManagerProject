package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

import java.util.Calendar;
import java.util.List;

public class Activity_Add_Staff extends AppCompatActivity {
    EditText address, phoneNumber, salary;
    TextView fullName;
    TextView birthday;
    Button exitBtn, doneBtn;
    String[] genderItem = {"Nam", "Nữ"};
    String[] typeItem = {"Giáo viên", "Nhân viên ghi danh", "Nhân viên học vụ", "Quản lý"};
    AutoCompleteTextView gender, type;
    ArrayAdapter<String> genderAdapter, typeAdapter;
    DatePickerDialog.OnDateSetListener birthDt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        
        String message1 = getIntent().getStringExtra("idStaff");
        String message2 = getIntent().getStringExtra("idTeacher");

        gender = findViewById(R.id.gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        gender.setAdapter(genderAdapter);
        doneBtn = findViewById(R.id.doneBtn);
        exitBtn = findViewById(R.id.exitBtn);
        salary = findViewById(R.id.salary);
        LinearLayout passwordLayout = findViewById(R.id.layoutPassword);
        passwordLayout.setVisibility(View.GONE);
        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        type = findViewById(R.id.type);
        typeAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, typeItem);
        type.setAdapter(typeAdapter);
        type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });


        birthday = findViewById(R.id.birthday);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Staff.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        birthDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        birthDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                birthday.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        fullName = findViewById(R.id.fullName);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phoneNumber);

        if (message1.equals("") && message2.equals("")) {

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Staff.this);
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
                    boolean acceptSwitch = true;    //Đúng thì mới trả về Fragment_Setting

                    if (fullName.getText().equals("") || type.getText().toString().equals("")
                            || address.getText().equals("") || phoneNumber.getText().equals("")
                            || birthday.getText().equals("") || salary.getText().equals("")) {
                        Toast.makeText(Activity_Add_Staff.this, "Hãy nhập" +
                                " đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Integer.parseInt(salary.getText().toString()) <= 0) {
                        Toast.makeText(Activity_Add_Staff.this, "Tiền lương âm là không hợp lệ",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!isInteger(salary.getText().toString())) {
                        Toast.makeText(Activity_Add_Staff.this, "Tiền lương phải là một số nguyên dương!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!Activity_Add_Official_Student.isValidPhoneNumber(phoneNumber.getText().toString())) {
                        Toast.makeText(Activity_Add_Staff.this, "Định dạng số điện thoại " +
                                "chưa chính xác!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Staff.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn có chắn chắn muốn chỉnh sửa thông tin của học viên không?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (type.getText().toString().equals("Giáo viên")) {

                                TeacherDTO teacherDTO = new TeacherDTO(null, fullName.getText().toString(),
                                        address.getText().toString(), phoneNumber.getText().toString(),
                                        gender.getText().toString(), birthday.getText().toString(),
                                        Integer.parseInt(salary.getText().toString()));
                                try {
                                    int rowEffect = TeacherDAO.getInstance(Activity_Add_Staff.this)
                                            .insertTeacher(Activity_Add_Staff.this, teacherDTO);
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Staff.this, "Thêm giáo viên " +
                                                "mới thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Staff.this, "Thêm giáo viên " +
                                                "mới thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Insert new teacher error: ", e.getMessage());
                                }

                            } else if (type.getText().toString().contains("Nhân viên"))  {
                                int typeStaff = -1;
                                if (type.getText().toString().equals("Nhân viên điểm danh")) {
                                    typeStaff = 1;
                                } else if (type.getText().toString().equals("Nhân viên học vụ")) {
                                    typeStaff = 2;
                                } else if (type.getText().toString().equals("Quản lý"))  {
                                    typeStaff = 3;
                                } else {
                                    Toast.makeText(Activity_Add_Staff.this, "Hãy nhập" +
                                            " đúng loại nhân viên", Toast.LENGTH_SHORT).show();
                                }

                                StaffDTO staff = new StaffDTO(null, fullName.getText().toString(),
                                        address.getText().toString(), phoneNumber.getText().toString(),
                                        gender.getText().toString(), birthday.getText().toString(),
                                        Integer.parseInt(salary.getText().toString()),
                                        String.valueOf(typeStaff), 0);
                                try {
                                    int rowEffect = StaffDAO.getInstance(Activity_Add_Staff.this)
                                            .insertStaff(Activity_Add_Staff.this, staff);
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Staff.this, "Thêm nhân viên " +
                                                "mới thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Staff.this, "Thêm giáo viên " +
                                                "mới thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e)  {
                                    Log.d("Insert new teacher error: ", e.getMessage());
                                }

                            } else {
                                Toast.makeText(Activity_Add_Staff.this, "Hãy nhập" +
                                        " đúng loại nhân viên/giáo viên", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
                    builder.show();
                }
            });
        }  else if (!message1.equals("")) {

            List<StaffDTO> listStaff = StaffDAO.getInstance(Activity_Add_Staff.this).SelectStaffVer2(
                    Activity_Add_Staff.this, "ID_STAFF = ? AND STATUS = ?",
                    new String[]{message1, "0"});

            if (listStaff.size() <= 0) {
                Toast.makeText(Activity_Add_Staff.this, "Có lỗi xảy ra!",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            fullName.setText(listStaff.get(0).getFullName());

            if(Integer.parseInt(listStaff.get(0).getType()) == 1) {
                type.setText("Nhân viên ghi danh");
            } else if (Integer.parseInt(listStaff.get(0).getType()) == 2) {
                type.setText("Nhân viên học vụ");
            } else {
                type.setText("Quản lý");
            }
            type = findViewById(R.id.type);
            typeAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, typeItem);
            type.setAdapter(typeAdapter);

            gender.setText(listStaff.get(0).getGender());
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);

            phoneNumber.setText(listStaff.get(0).getPhoneNumber());
            address.setText(listStaff.get(0).getAddress());
            birthday.setText(listStaff.get(0).getBirthday());

            salary.setText(String.valueOf(listStaff.get(0).getSalary()));
          //  Log.d("Come here: ", "true");
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Staff.this);
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
                    if (fullName.getText().equals("")
                            || address.getText().equals("") || phoneNumber.getText().equals("")
                            || birthday.getText().equals("") || salary.getText().equals("")) {
                        Toast.makeText(Activity_Add_Staff.this, "Hãy nhập" +
                                " đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        if (!Activity_Add_Official_Student.isValidPhoneNumber(phoneNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Staff.this, "Định dạng số điện thoại " +
                                    "chưa chính xác!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (Integer.parseInt(salary.getText().toString()) <= 0) {
                            Toast.makeText(Activity_Add_Staff.this, "Tiền lương âm là không hợp lệ",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        int typeInt = -1;
                        if (type.getText().toString().equals("Nhân viên ghi danh")) {
                            typeInt = 1;
                        } else if (type.getText().toString().equals("Nhân viên học vụ")) {
                            typeInt = 2;
                        } else {
                            typeInt = 3;
                        }
                        StaffDTO staffUpdate = new StaffDTO(listStaff.get(0).getIdStaff(),
                                fullName.getText().toString(), address.getText().toString(),
                                phoneNumber.getText().toString(), gender.getText().toString(),
                                birthday.getText().toString(), Integer.parseInt(salary.getText().toString()),
                                String.valueOf(typeInt), 0);
                        try {
                            int rowEffect = StaffDAO.getInstance(Activity_Add_Staff.this)
                                    .updateStaff(Activity_Add_Staff.this, staffUpdate,
                                            "ID_STAFF = ? AND STATUS = ?",
                                            new String[] {staffUpdate.getIdStaff(), "0"});
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Staff.this, "Sửa thông tin " +
                                        "nhân viên thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Staff.this, "Sửa thông tin " +
                                        "nhân viên thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Update staff information error: ", e.getMessage());
                        }
                    }
                }
            });
        } else if (!message2.equals(""))   {
            type.setText("Giáo viên");
            List<TeacherDTO> listTeacher = TeacherDAO.getInstance(Activity_Add_Staff.this).SelectTeacher(
                    Activity_Add_Staff.this, "ID_TEACHER = ? AND STATUS  = ?",
                    new String[] {message2, "0"});

            gender.setText(listTeacher.get(0).getGender());
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);
            phoneNumber.setText(listTeacher.get(0).getPhoneNumber());
            address.setText(listTeacher.get(0).getAddress());
            birthday.setText(listTeacher.get(0).getBirthday());
            salary.setText(String.valueOf(listTeacher.get(0).getSalary()));
            if (Integer.parseInt(salary.getText().toString()) <= 0) {
                Toast.makeText(Activity_Add_Staff.this, "Tiền lương âm là không hợp lệ",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Staff.this);
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
                    if (fullName.getText().equals("")
                            || address.getText().equals("") || phoneNumber.getText().equals("")
                            || birthday.getText().equals("")) {
                        Toast.makeText(Activity_Add_Staff.this, "Hãy nhập " +
                                "đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        if (!Activity_Add_Official_Student.isValidPhoneNumber(phoneNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Staff.this, "Định dạng số điện thoại " +
                                    "chưa chính xác!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (Integer.parseInt(salary.getText().toString()) <= 0) {
                            Toast.makeText(Activity_Add_Staff.this, "Tiền lương âm là không hợp lệ",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        TeacherDTO teacherUpdate = new TeacherDTO(listTeacher.get(0).getIdTeacher(),
                                fullName.getText().toString(), address.getText().toString(),
                                phoneNumber.getText().toString(), gender.getText().toString(),
                                birthday.getText().toString(),
                                Integer.parseInt(salary.getText().toString()));
                        try {
                            int rowEffect = TeacherDAO.getInstance(Activity_Add_Staff.this)
                                    .updateTeacher(Activity_Add_Staff.this, teacherUpdate,
                                            "ID_TEACHER = ? AND STATUS = ?",
                                            new String[] {teacherUpdate.getIdTeacher(), "0"});
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Staff.this, "Sửa thông " +
                                        "tin giáo viên thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Staff.this, "Sửa thông " +
                                        "tin giáo viên thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Update teacher information error: ", e.getMessage());
                        }
                    }
                }
            });
        }

    }

    @Override
    protected void onStart()  {
        super.onStart();
    }
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}