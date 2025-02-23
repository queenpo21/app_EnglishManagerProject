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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.ClassDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Activity_Add_Official_Student extends AppCompatActivity {
    private static final String PHONE_NUMBER_REGEX = "^(0[3-9][0-9]{8}|(\\+84|84)[3-9][0-9]{8})$";
    EditText studentName, phoneNumber, address, totalMoney;
    TextView birthday, collectingDate;
    Button doneBtn, exitBtn;
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView gender;
    ArrayAdapter<String> genderAdapter;
    String genderText = "";
    DatePickerDialog.OnDateSetListener birthDt, collectDt;
    LinearLayout showCollectingDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_official_student);
        showCollectingDate = findViewById(R.id.showCollectingDate);
        showCollectingDate.setVisibility(View.GONE);

        gender = findViewById(R.id.gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        gender.setAdapter(genderAdapter);

        gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                genderText = parent.getItemAtPosition(position).toString();
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
                        Activity_Add_Official_Student.this,
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

        collectingDate = findViewById(R.id.collectingDate);
        collectingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Add_Official_Student.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        collectDt,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        collectDt = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;
                collectingDate.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        studentName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        address = findViewById(R.id.address);
        totalMoney = findViewById(R.id.totalMoney);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        String studentId = getIntent().getStringExtra("studentID");
       // String classId = getIntent().getStringExtra("classID");

        if (!studentId.equals("")) {
            LinearLayout showTuitionLayout = findViewById(R.id.layoutShowTuition);
            showTuitionLayout.setVisibility(View.GONE);

            List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                    Activity_Add_Official_Student.this).SelectStudentVer2(
                            Activity_Add_Official_Student.this, "ID_STUDENT = ?" +
                            "AND STATUS = ?", new String[] {studentId, "0"});

            studentName.setText(student.get(0).getFullName().toString());
            phoneNumber.setText(student.get(0).getPhoneNumber());
            gender.setText("Nam");
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);
            address.setText(student.get(0).getAddress());
            birthday.setText(student.get(0).getBirthday());

            List<TeachingDTO> getTeachingToShowTuition = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                    .SelectTeaching(Activity_Add_Official_Student.this,
                            "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                            new String[] {studentId, List_Adapter.idClassClick, "0"});
            List<CollectionTuitionFeesDTO> showTuition = CollectionTuitionFeesDAO.getInstance(Activity_Add_Official_Student.this)
                            .SelectCollectionTuitionFeesToGetList(Activity_Add_Official_Student.this,
                                    "ID_TEACHING = ? AND STATUS = ?",
                                    new String[] {getTeachingToShowTuition.get(0).getIdTeaching(), "0"});
            if (showTuition.size() != 0) {
                totalMoney.setText(showTuition.get(0).getMoney());
            } else {
                totalMoney.setText("");
            }

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().toString().equals("")
                            || birthday.getText().toString().equals("")
                            || studentName.getText().toString().equals("")
                            || gender.getText().toString().equals("")
                            || phoneNumber.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        if (!isValidPhoneNumber(phoneNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Official_Student.this, "Định dạng số điện thoại " +
                                    "chưa chính xác!", Toast.LENGTH_SHORT).show();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin của học viên không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                OfficialStudentDTO studentUpdate = new OfficialStudentDTO(null, studentName.getText().toString(),
                                        address.getText().toString(),
                                        phoneNumber.getText().toString(), gender.getText().toString(),
                                        birthday.getText().toString(), 0);
                                try {
                                    int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this).updateOfficialStudent(
                                            Activity_Add_Official_Student.this, studentUpdate,
                                            "ID_STUDENT = ? AND STATUS = ?",
                                            new String[] {studentId, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Update student information:", "success");
                                }

                                List<TeachingDTO> getTeaching = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                        .SelectTeaching(Activity_Add_Official_Student.this,
                                                "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                                new String[] {studentId, List_Adapter.idClassClick, "0"});

                               /* if (getTeaching.size() != 0) {
                                    CollectionTuitionFeesDTO collectingMoney = new CollectionTuitionFeesDTO(null, null,
                                            null, totalMoney.getText().toString());
                                    try {
                                        int row = CollectionTuitionFeesDAO.getInstance(Activity_Add_Official_Student.this)
                                                .UpdateCollection_Tuition_Fees(Activity_Add_Official_Student.this, collectingMoney,
                                                        "ID_TEACHING = ? AND STATUS = ? ",
                                                        new String[] {getTeaching.get(0).getIdTeaching(), "0"});
                                        if (row > 0) {
                                            Log.d("Update Collecting Tuition: ", "success");
                                        } else {
                                            Log.d("Update Collecting Tuition: ", "failed");
                                        }
                                    } catch (Exception e) {
                                        Log.d("Update Collecting Tuition Error: ", e.getMessage());
                                    }
                                }*/

                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();
                    }
                }
            });

        } else {
            LinearLayout showCollectingDate = findViewById(R.id.showCollectingDate);
            showCollectingDate.setVisibility(View.GONE);
            LinearLayout showTuition = findViewById(R.id.layoutShowTuition);
            showTuition.setVisibility(View.GONE);
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().equals("")
                            || birthday.getText().equals("") || studentName.getText().equals("")
                            || gender.getText().equals("") || phoneNumber.getText().equals("")
                            || totalMoney.getText().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else if (!isValidPhoneNumber(phoneNumber.getText().toString())) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Định dạng số điện thoại " +
                                "chưa chính xác!", Toast.LENGTH_SHORT).show();
                    } else {

                        List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(
                                Activity_Add_Official_Student.this).SelectStudentVer2(
                                Activity_Add_Official_Student.this,
                                "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                        "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                        birthday.getText().toString(), gender.getText().toString(), "0",
                                        address.getText().toString()}
                        );
                        String idStudent = "";
                        if (listStudent.size() == 0) {
                            OfficialStudentDTO studentNew = new OfficialStudentDTO(null, studentName.getText().toString(),
                                    address.getText().toString(), phoneNumber.getText().toString(),
                                    gender.getText().toString(), birthday.getText().toString(), 0 );
                            try {
                                int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this)
                                        .insertOfficialStudent(Activity_Add_Official_Student.this, studentNew);
                                if (rowEffect > 0) {
                                    Log.d("Add new official student: ", "success");
                                } else {
                                    Log.d("Add new official student: ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Add new official student error", e.getMessage());
                            }
                            List<OfficialStudentDTO> findStudent = OfficialStudentDAO.getInstance(
                                    Activity_Add_Official_Student.this).SelectStudentVer2(
                                    Activity_Add_Official_Student.this,
                                    "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                            "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                    new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                            birthday.getText().toString(), gender.getText().toString(), "0",
                                            address.getText().toString()}
                            );
                            idStudent = findStudent.get(0).getIdStudent();
                        } else {
                            idStudent = listStudent.get(0).getIdStudent();
                        }

                        TeachingDTO teachingNew = new TeachingDTO(null, idStudent,
                                List_Adapter.idClassClick);
                        try {
                            int rowEffect = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertTeaching(Activity_Add_Official_Student.this, teachingNew);
                            if (rowEffect > 0) {
                                Log.d("Collecting tuition object: ", "Success");
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new teaching relationship: ", "failed");
                        }

                        List<TeachingDTO> getTeaching = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                .SelectTeaching(Activity_Add_Official_Student.this,
                                        "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                        new String[] {teachingNew.getIdStudent(), teachingNew.getIdClass(), "0"});

                        List<ClassDTO> listClassGetTuition = ClassDAO.getInstance(Activity_Add_Official_Student.this)
                                .selectClass(Activity_Add_Official_Student.this,
                                        "ID_CLASS = ? AND STATUS = ?",
                                        new String[] {List_Adapter.idClassClick, "0"});
                        String idProgram = "";
                        if (listClassGetTuition.size() != 0) {
                            idProgram = listClassGetTuition.get(0).getIdProgram();
                        }
                        List<ProgramDTO> listProgram = ProgramDAO.getInstance(Activity_Add_Official_Student.this)
                                .SelectProgram(Activity_Add_Official_Student.this,
                                        "ID_PROGRAM = ? AND STATUS = ?",
                                        new String[] {idProgram, "0"});

                        LocalDateTime roundedDateTime = LocalDateTime.now().with(LocalTime.from(LocalDateTime.now().toLocalTime().withSecond(LocalDateTime.now().getSecond()).withNano(0)));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss");
                        String formattedDateTime = roundedDateTime.format(formatter);

                        CollectionTuitionFeesDTO collectingTuition = new CollectionTuitionFeesDTO(null,
                                getTeaching.get(0).getIdTeaching(),
                                formattedDateTime.replace("T", " "),
                                String.valueOf(listProgram.get(0).getTuitionFees()));
                        Log.d("Collecting tuition object: ", collectingTuition.toString());
                        try {
                            int row = CollectionTuitionFeesDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertCollection_Tuition_Fees(Activity_Add_Official_Student.this, collectingTuition);
                            if (row > 0)  {
                                Log.d("Add new collection tuition: ", "success");
                            } else {
                                Log.d("Add new collection tuition: ", "failed");
                            }
                        } catch (Exception e) {
                            Log.d("Add new collecting tuition fees error", e.getMessage());
                        }

                    }
                }
            });

        }
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    @Override

    protected void onStart(){
        super.onStart();

        showCollectingDate = findViewById(R.id.showCollectingDate);
        showCollectingDate.setVisibility(View.GONE);

        String studentId = getIntent().getStringExtra("studentID");
        String classId = getIntent().getStringExtra("classID");

        Log.d("Push clas id to activity add student: ", classId + " ok");

        if (!studentId.equals("")) {

            List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                    Activity_Add_Official_Student.this).SelectStudentVer2(
                    Activity_Add_Official_Student.this, "ID_STUDENT = ?" +
                            "AND STATUS = ?", new String[] {studentId, "0"});

            studentName.setText(student.get(0).getFullName().toString());
            phoneNumber.setText(student.get(0).getPhoneNumber());
            gender.setText(student.get(0).getGender());
            genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
            gender.setAdapter(genderAdapter);
            address.setText(student.get(0).getAddress());
            birthday.setText(student.get(0).getBirthday());
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.equals("")
                            || birthday.equals("") || studentName.equals("")
                            || gender.getText().toString().equals("") || phoneNumber.equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        if (!isValidPhoneNumber(phoneNumber.getText().toString())) {
                            Toast.makeText(Activity_Add_Official_Student.this, "Định dạng số điện thoại " +
                                    "chưa chính xác!", Toast.LENGTH_SHORT).show();
                        }

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông tin của học viên không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                OfficialStudentDTO studentUpdate = new OfficialStudentDTO(null, studentName.getText().toString(),
                                        address.getText().toString(),
                                        phoneNumber.getText().toString(), gender.getText().toString(),
                                        birthday.getText().toString(), 0);
                                try {
                                    int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this).updateOfficialStudent(
                                            Activity_Add_Official_Student.this, studentUpdate,
                                            "ID_STUDENT = ? AND STATUS = ?",
                                            new String[] {studentId, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Official_Student.this,
                                                "Sửa thông tin học viên thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Update student information:", "success");
                                }

                                List<TeachingDTO> getTeaching = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                        .SelectTeaching(Activity_Add_Official_Student.this,
                                                "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                                new String[] {studentId, List_Adapter.idClassClick, "0"});

                                /*if (getTeaching.size() != 0) {
                                    CollectionTuitionFeesDTO collectingMoney = new CollectionTuitionFeesDTO(null, null,
                                            null, totalMoney.getText().toString());
                                    try {
                                        int row = CollectionTuitionFeesDAO.getInstance(Activity_Add_Official_Student.this)
                                                .UpdateCollection_Tuition_Fees(Activity_Add_Official_Student.this, collectingMoney,
                                                        "ID_TEACHING = ? AND STATUS = ? ",
                                                        new String[] {getTeaching.get(0).getIdTeaching(), "0"});
                                        if (row > 0) {
                                            Log.d("Update Collecting Tuition: ", "success");
                                        } else {
                                            Log.d("Update Collecting Tuition: ", "failed");
                                        }
                                    } catch (Exception e) {
                                        Log.d("Update Collecting Tuition Error: ", e.getMessage());
                                    }
                                }*/
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Official_Student.this);
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

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (address.getText().equals("")
                            || birthday.getText().equals("") || studentName.getText().equals("")
                            || gender.getText().equals("") || phoneNumber.getText().equals("")) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Hãy nhập đầy " +
                                "đủ thông tin", Toast.LENGTH_SHORT).show();
                    }else if (!isValidPhoneNumber(phoneNumber.getText().toString())) {
                        Toast.makeText(Activity_Add_Official_Student.this, "Định dạng số điện thoại " +
                                "chưa chính xác!", Toast.LENGTH_SHORT).show();
                    } else {

                        List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(
                                Activity_Add_Official_Student.this).SelectStudentVer2(
                                        Activity_Add_Official_Student.this,
                                "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                        "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                        birthday.getText().toString(), gender.getText().toString(), "0",
                                        address.getText().toString()}
                                );
                        String idStudent = "";
                        if (listStudent.size() == 0) {
                            OfficialStudentDTO studentNew = new OfficialStudentDTO(null, studentName.getText().toString(),
                                    address.getText().toString(), phoneNumber.getText().toString(),
                                    gender.getText().toString(), birthday.getText().toString(), 0 );
                            try {
                                int rowEffect = OfficialStudentDAO.getInstance(Activity_Add_Official_Student.this)
                                        .insertOfficialStudent(Activity_Add_Official_Student.this, studentNew);
                                if (rowEffect > 0) {
                                    Log.d("Add new official student: ", "success");
                                } else {
                                    Log.d("Add new official student: ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Add new official student error", e.getMessage());
                            }
                            List<OfficialStudentDTO> findStudent = OfficialStudentDAO.getInstance(
                                    Activity_Add_Official_Student.this).SelectStudentVer2(
                                    Activity_Add_Official_Student.this,
                                    "FULLNAME = ? AND PHONE_NUMBER = ? AND BIRTHDAY = ?" +
                                            "AND GENDER = ? AND STATUS = ? AND ADDRESS = ?",
                                    new String[] {studentName.getText().toString(), phoneNumber.getText().toString(),
                                            birthday.getText().toString(), gender.getText().toString(), "0",
                                            address.getText().toString()}
                            );
                            idStudent = findStudent.get(0).getIdStudent();
                        } else {
                            idStudent = listStudent.get(0).getIdStudent();
                            List<TeachingDTO> checkExistTeaching = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                    .SelectTeaching(Activity_Add_Official_Student.this,
                                            "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                            new String[] {idStudent, List_Adapter.idClassClick, "0"});
                            if (checkExistTeaching.size() > 0) {
                                Toast.makeText(Activity_Add_Official_Student.this, "Sinh viên này đã được thêm vào " +
                                        "lớp!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        TeachingDTO teachingNew = new TeachingDTO(null, idStudent,
                                List_Adapter.idClassClick);
                        try {
                            int rowEffect = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertTeaching(Activity_Add_Official_Student.this, teachingNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Official_Student.this, "Thêm học viên mới " +
                                        "vào lớp thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Add new teaching relationship: ", "failed");
                        }

                        List<TeachingDTO> getTeaching = TeachingDAO.getInstance(Activity_Add_Official_Student.this)
                                .SelectTeaching(Activity_Add_Official_Student.this,
                                        "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                        new String[] {teachingNew.getIdStudent(), teachingNew.getIdClass(), "0"});

                        LocalDateTime timeNow = LocalDateTime.now();
                        DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                        String timeNowString = timeNow.format(formatterNew);

                        List<ClassDTO> listClassGetTuition = ClassDAO.getInstance(Activity_Add_Official_Student.this)
                                .selectClass(Activity_Add_Official_Student.this,
                                        "ID_CLASS = ? AND STATUS = ?",
                                        new String[] {List_Adapter.idClassClick, "0"});
                        String idProgram = "";
                        if (listClassGetTuition.size() != 0) {
                            idProgram = listClassGetTuition.get(0).getIdProgram();
                        }
                        List<ProgramDTO> listProgram = ProgramDAO.getInstance(Activity_Add_Official_Student.this)
                                .SelectProgram(Activity_Add_Official_Student.this,
                                        "ID_PROGRAM = ? AND STATUS = ?",
                                        new String[] {idProgram, "0"});

                        LocalDateTime roundedDateTime = LocalDateTime.now().with(LocalTime.from(timeNow.toLocalTime().withSecond(timeNow.getSecond()).withNano(0)));
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy'T'HH:mm:ss");
                        String formattedDateTime = roundedDateTime.format(formatter);

                        CollectionTuitionFeesDTO collectingTuition = new CollectionTuitionFeesDTO(null,
                                getTeaching.get(0).getIdTeaching(),
                                formattedDateTime.replace("T", " "),
                                String.valueOf(listProgram.get(0).getTuitionFees()));
                        Log.d("Collecting tuition object: ", collectingTuition.toString());

                        try {
                            int row = CollectionTuitionFeesDAO.getInstance(Activity_Add_Official_Student.this)
                                    .InsertCollection_Tuition_Fees(Activity_Add_Official_Student.this, collectingTuition);
                            if (row > 0)  {
                                Log.d("Add new collection tuition: ", "success");
                            } else {
                                Log.d("Add new collection tuition: ", "failed");
                            }
                        } catch (Exception e) {
                            Log.d("Add new collecting tuition fees error", e.getMessage());
                        }



                    }
                }
            });
        }
    }

}