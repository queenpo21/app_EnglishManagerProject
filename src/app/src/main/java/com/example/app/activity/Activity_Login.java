package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.CertificateDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.DataProvider;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.CertificateDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

public class Activity_Login extends AppCompatActivity {
    EditText usernameInput, passwordInput;
    TextView fogotPassBtn;
    Button loginBtn;
    public static String idUser;
    public static String password;
    public static String username;
    public static String idAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);
        fogotPassBtn = findViewById(R.id.fogotPassBtn);

        fogotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_Login.this, Activity_Forgot_Password.class));
            }
        });

        // Initialize database

        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Hãy nhập đúng " +
                                "tên đăng nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }

            }
        });

/*

        // Potential Student
        PotentialStudentDTO pStudent1 = new PotentialStudentDTO(null,"Nguyễn Văn Y", "0343884343",
                "Nam", "Bình Dương", "5.5-6", "10");
        PotentialStudentDTO pStudent2 = new PotentialStudentDTO(null, "Nguyen Thị Z", "0977748343",
                "Nữ", "TP Hồ Chí Minh", "5.5-6", "13");
        PotentialStudentDTO pStudent3 = new PotentialStudentDTO(null,"Nguyen Thị Z", "0977748343",
                "Nữ", "Bình Định",  "5.5-6", "13");
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent3);
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent1);
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent2);

        // Official Student
        OfficialStudentDTO student1 = new OfficialStudentDTO(null, "Nguyen Van A", "Binh Dinh", "0312345678", "Nam", "22/2/2022", 0);
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student1);
        OfficialStudentDTO student2 = new OfficialStudentDTO(null, "Le Thi B", "Binh Duong","0332323222", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student2);
        OfficialStudentDTO student3 = new OfficialStudentDTO(null, "Ho Thi C", "TP Hồ Chí Minh","036723222", "Nam", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student3);
        OfficialStudentDTO student4 = new OfficialStudentDTO(null, "Nguyen Thi D", "Hà Nội","0332323222", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student4);
        OfficialStudentDTO student5 = new OfficialStudentDTO(null, "Le Thi E", "Phú Yên","0345678901", "Nam", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student5);
        OfficialStudentDTO student6 = new OfficialStudentDTO(null, "Hoang Thi F", "Đồng Nai","0345623777", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student6);

        // Insert data in STAFF
        StaffDTO staff1 = new StaffDTO(null, "Nguyen Thi C", "TP HCM", "0343333333", "Nữ", "12/6/2022", 10000000, "1",0);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff1);
        StaffDTO staff2 = new StaffDTO(null, "Nguyen Thi D", "Đồng Nai", "03466555333", "Nữ","14/2/2022", 2000000, "2",0);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff2);
        StaffDTO staff3 = new StaffDTO(null, "Nguyen Thi E", "Bình Định", "03435555333", "Nữ","27/2/2022", 15000000, "3",0);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff3);

        // Insert data in TEACHERS
        TeacherDTO teacher1 = new TeacherDTO(null, "Nguyen Thi G", "Binh Duong", "0312345678", "Nữ", "22/5/2022", 5000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher1);
        TeacherDTO teacher2 = new TeacherDTO(null, "Nguyen Thi H", "Đồng Nai", "0346699966", "Nam", "14/2/2022", 15000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher2);

        // Insert data in Certificate
        CertificateDTO certificate1 = new CertificateDTO(null, "Ielts Academic",
                "Ielts Academic được công nhận rộng rãi như là yêu cầu ngôn ngữ đầu " +
                        "vào cho tất cả các khóa học Đại học và Sau Đại học");
        CertificateDTO certificate2 = new CertificateDTO(null, "Ielts General",
                "Ielts General hích hợp cho tất cả những ai chuẩn bị tới các nước nói " +
                        "tiếng Anh để hoàn tất chương trình trung học, các chương trình đào " +
                        "tạo hoặc với mục đích nhập cư.");
        CertificateDTO certificate3 = new CertificateDTO(null, "Toeic",
                "TOEIC là một chứng chỉ tiếng " +
                        "Anh quốc tế, đánh giá khả năng sử dụng tiếng Anh trong môi trường làm " +
                        "việc toàn cầu");
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate3);
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate1);
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate2);

        // Program
        ProgramDTO program1 = new ProgramDTO(null, "Chứng chỉ ielts academic",
                "5.0", "6.5", "Cải thiện khả năng nghe",
                "5.5", "6.5", "7.0", "7.0",
                3000000, "6 tháng", "CER1");
        ProgramDTO program2 = new ProgramDTO(null, "Chứng chỉ ielts academic",
                "5.0", "6.5", "Cải thiện khả năng nói",
                "6.5", "6.5", "7.0", "7.0",
                10000000, "6 tháng", "CER1");
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program1);
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program2);
        ProgramDTO program3 = new ProgramDTO(null, "Chứng chỉ ielts general",
                "5.0", "6.5", "Cải thiện khả năng nghe",
                "5.5", "6.5", "7.0", "7.0",
                5000000, "6 tháng", "CER2");
        ProgramDTO program4 = new ProgramDTO(null, "Chứng chỉ ielts general",
                "5.0", "6.5", "Cải thiện khả năng nói",
                "6.5", "6.5", "7.0", "7.0",
                10000000, "6 tháng", "CER2");
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program3);
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program4);
        ProgramDTO program5 = new ProgramDTO(null, "Chứng chỉ toeic 500-880",
                "5.0", "6.5", "Cải thiện khả năng nghe",
                "5.5", "6.5", "7.0", "7.0",
                5000000, "6 tháng", "CER3");
        ProgramDTO program6 = new ProgramDTO(null, "Chứng chỉ toeic 500-700",
                "5.0", "6.5", "Cải thiện khả năng nói",
                "6.5", "6.5", "7.0", "7.0",
                10000000, "6 tháng", "CER3");
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program5);
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program6);

        // Insert data in CLASSROOM

        ClassroomDTO classroom1 = new ClassroomDTO(null, "B1.11");
        ClassroomDTO classroom2 = new ClassroomDTO(null, "B1.12");
        ClassroomDTO classroom3 = new ClassroomDTO(null, "B1.13");
        ClassroomDTO classroom4 = new ClassroomDTO(null, "B1.14");
        ClassroomDTO classroom5 = new ClassroomDTO(null, "B1.15");
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom1);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom2);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom3);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom4);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom5);

        // Insert data in CLASS

        ClassDTO class1 = new ClassDTO(null, "Lớp ielts từ 5-6.5 giúp cải thiện kĩ năng nghe",
                "22/01/2024", "22/09/2024", "PRO1", "TEA1", "STA2", "0");
        ClassDTO class2 = new ClassDTO(null, "Lớp ielts từ 5-6.5 giúp cải thiện kĩ năng đọc",
                "22/01/2024", "22/10/2024", "PRO2", "TEA2", "STA1", "0");
        ClassDTO class3 = new ClassDTO(null, "Lớp ielts từ 6-7.5 giúp cải thiện kĩ năng nói",
                "22/02/2024", "22/12/2024", "PRO3", "TEA1", "STA2", "0");
        ClassDTO class4 = new ClassDTO(null, "Lớp ielts từ 5.5-6.5 giúp cải thiện kĩ năng đọc",
                "22/03/2024", "22/11/2024", "PRO4", "TEA1", "STA2", "0");
        ClassDTO class5 = new ClassDTO(null, "Lớp toeic cải thiện kĩ năng nghe 700-990",
                "22/04/2024", "22/12/2024", "PRO5", "TEA2", "STA1", "0");
        ClassDTO class6 = new ClassDTO(null, "Lớp toeic cải thiện kĩ năng đọc 600-990",
                "22/04/2024", "22/12/2024", "PRO6", "TEA2", "STA1", "0");
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class4);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class6);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class5);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class3);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class2);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class1);

        // Insert data in Schedule

        ScheduleDTO schedule1 = new ScheduleDTO(null, "2", "9", "11",
                "CLS1", "CLA1");
        ScheduleDTO schedule2= new ScheduleDTO(null, "3", "7", "9",
                "CLS1", "CLA2");
        ScheduleDTO schedule3= new ScheduleDTO(null, "4", "13", "15",
                "CLS2", "CLA3");
        ScheduleDTO schedule4= new ScheduleDTO(null, "5", "15", "17",
                "CLS2", "CLA4");
        ScheduleDTO schedule5= new ScheduleDTO(null, "6", "7", "9",
                "CLS3", "CLA4");
        ScheduleDTO schedule6 = new ScheduleDTO(null, "7", "9", "11",
                "CLS3", "CLA5");
        ScheduleDTO schedule7 = new ScheduleDTO(null, "7", "9", "11",
                "CLS4", "CLA2");
        ScheduleDTO schedule8 = new ScheduleDTO(null, "7", "9", "11",
                "CLS5", "CLA3");
        ScheduleDTO schedule9 = new ScheduleDTO(null, "8", "13", "15",
                "CLS4", "CLA4");
        ScheduleDTO schedule10 = new ScheduleDTO(null, "8", "9", "11",
                "CLS2", "CLA5");
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule10);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule9);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule8);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule7);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule6);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule5);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule4);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule3);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule1);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule2);

        // Collecting Tuition Fees
        CollectionTuitionFeesDTO collectionTuitionFees2 = new CollectionTuitionFeesDTO(
                null, "TEC1", "23/1/2024 15:23:22", "10000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees2);
        CollectionTuitionFeesDTO collectionTuitionFees3 = new CollectionTuitionFeesDTO(
                null, "TEC2", "23/2/2024 15:23:22", "20000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees3);
        CollectionTuitionFeesDTO collectionTuitionFees11 = new CollectionTuitionFeesDTO(
                null, "TEC3", "23/3/2024 15:23:22", "10000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees11);
        CollectionTuitionFeesDTO collectionTuitionFees4 = new CollectionTuitionFeesDTO(
                null, "TEC4", "23/4/2024 15:23:22", "40000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees4);
        CollectionTuitionFeesDTO collectionTuitionFees13 = new CollectionTuitionFeesDTO(
                null, "TEC5", "23/5/2024 15:23:22", "60000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees13);
        CollectionTuitionFeesDTO collectionTuitionFees12 = new CollectionTuitionFeesDTO(
                null, "TEC6", "23/6/2024 15:23:22", "40000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees12);
        CollectionTuitionFeesDTO collectionTuitionFees5 = new CollectionTuitionFeesDTO(
                null, "TEC7", "23/7/2024 15:23:22", "90000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees5);
        CollectionTuitionFeesDTO collectionTuitionFees6 = new CollectionTuitionFeesDTO(
                null, "TEC8", "23/8/2024 15:23:22", "10000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees6);
        CollectionTuitionFeesDTO collectionTuitionFees7 = new CollectionTuitionFeesDTO(
                null, "TEC9", "23/9/2024 15:23:22", "10000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees7);
        CollectionTuitionFeesDTO collectionTuitionFees8 = new CollectionTuitionFeesDTO(
                null, "TEC10", "23/10/2024 15:23:22", "50000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees8);
        CollectionTuitionFeesDTO collectionTuitionFees9 = new CollectionTuitionFeesDTO(
                null, "TEC11", "23/11/2024 15:23:22", "40000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees9);
        CollectionTuitionFeesDTO collectionTuitionFees10 = new CollectionTuitionFeesDTO(
                null, "TEC12", "23/12/2024 15:23:22", "10000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees10);

        // Insert data in Examination

        ExaminationDTO exam1 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Ielts", "22/09/2024", "CLS1", "CLA1");
        ExaminationDTO exam2 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Ielts", "22/10/2024", "CLS2", "CLA1");
        ExaminationDTO exam3 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Ielts", "22/12/2024", "CLS3", "CLA1");
        ExaminationDTO exam4 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Ielts", "22/11/2024", "CLS4", "CLA1");
        ExaminationDTO exam5 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Toeic", "22/12/2024", "CLS5", "CLA1");
        ExaminationDTO exam6 = new ExaminationDTO(null, "Kiểm tra cuối khóa",
                "Format Toeic", "22/12/2024", "CLS6", "CLA1");
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam6);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam5);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam4);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam3);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam1);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam2);

        // Insert data in ExamScore

        ExamScoreDTO examScore1 = new ExamScoreDTO(null, "EXA1", "STU1",
                "6", "4", "6", "6");
        ExamScoreDTO examScore2 = new ExamScoreDTO(null, "EXA2", "STU2",
                "6", "66", "7", "6");
        ExamScoreDTO examScore3 = new ExamScoreDTO(null, "EXA3", "STU3",
                "6", "7", "9", "6");
        ExamScoreDTO examScore4 = new ExamScoreDTO(null, "EXA4", "STU4",
                "7", "9", "9", "8");
        ExamScoreDTO examScore5 = new ExamScoreDTO(null, "EXA5", "STU5",
                "500", "600", "600", "400");
        ExamScoreDTO examScore6 = new ExamScoreDTO(null, "EXA6", "STU6",
                "600", "800", "600", "600");
        ExamScoreDTO examScore7 = new ExamScoreDTO(null, "EXA6", "STU1",
                "9", "8", "8", "8");
        ExamScoreDTO examScore8 = new ExamScoreDTO(null, "EXA5", "STU2",
                "6", "7", "6", "6");
        ExamScoreDTO examScore9 = new ExamScoreDTO(null, "EXA4", "STU3",
                "5", "5", "5", "5");
        ExamScoreDTO examScore10 = new ExamScoreDTO(null, "EXA3", "STU4",
                "6", "7", "6", "6");
        ExamScoreDTO examScore11 = new ExamScoreDTO(null, "EXA2", "STU5",
                "600", "700", "600", "650");
        ExamScoreDTO examScore12 = new ExamScoreDTO(null, "EXA1", "STU6",
                "660", "700", "600", "600");
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore1);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore2);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore3);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore4);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore5);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore6);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore7);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore8);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore9);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore10);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore11);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore12);

        // Insert data in Teaching

        TeachingDTO teaching1 = new TeachingDTO(null, "STU1", "CLS1");
        TeachingDTO teaching2 = new TeachingDTO(null, "STU2", "CLS2");
        TeachingDTO teaching3 = new TeachingDTO(null, "STU3", "CLS3");
        TeachingDTO teaching4 = new TeachingDTO(null, "STU4", "CLS4");
        TeachingDTO teaching5 = new TeachingDTO(null, "STU5", "CLS5");
        TeachingDTO teaching6 = new TeachingDTO(null, "STU6", "CLS6");
        TeachingDTO teaching7 = new TeachingDTO(null, "STU1", "CLS6");
        TeachingDTO teaching8 = new TeachingDTO(null, "STU2", "CLS5");
        TeachingDTO teaching9 = new TeachingDTO(null, "STU3", "CLS4");
        TeachingDTO teaching10 = new TeachingDTO(null, "STU4", "CLS3");
        TeachingDTO teaching11 = new TeachingDTO(null, "STU5", "CLS2");
        TeachingDTO teaching12 = new TeachingDTO(null, "STU6", "CLS1");
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching1);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching2);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching3);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching4);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching5);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching6);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching7);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching8);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching9);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching10);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching11);
        TeachingDAO.getInstance(Activity_Login.this).InsertTeaching(Activity_Login.this,
                teaching12);

        // Insert data ACCOUNT

        AccountDTO account1 = new AccountDTO(null, "STU1", "nguyenthia", "thia123");
        AccountDTO account2 = new AccountDTO(null, "STA1", "nguyenthic", "thic123");
        AccountDTO account5 = new AccountDTO(null, "STA2", "nguyenthid", "thid123");
        AccountDTO account7 = new AccountDTO(null, "STA3", "nguyenthie", "thie123");
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account1);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account2);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account5);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account7);

        NotificationDTO notification1 =  new NotificationDTO(null, "ACC3", "Thông báo nghi học", "Nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification1);
        NotificationDTO notification2 =  new NotificationDTO(null, "ACC2", "Thông báo nghỉ học", "Lớp CLS1 nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification2);
        NotificationDTO notification3 =  new NotificationDTO(null, "ACC4", "Thông báo học bù", "Lớp CLS2 thi tổng kết khóa vào ngày 22/9/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification3);
        NotificationDTO notification4 =  new NotificationDTO(null, "ACC2", "Thông báo học bù", "Lớp CLS3 ọc bù từ ngày 13/4/2024 đến hết ngày 16/4/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification4);
*/



    }

    @Override

    protected  void onStart()  {
        super.onStart();
        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Hãy nhập đúng tên đăng " +
                                "nhập và mật khẩu!", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }

                // Log.d("I'm on create: ", username.toString() + "  " + password + "  ");

            }
        });

    }
}

/*


        // Insert data in OFFICIAL_STUDENT

        OfficialStudentDTO student1 = new OfficialStudentDTO("STU1", "Nguyen Van A", "Binh Dinh", "034343434", "Nam", "22/2/2022", 0);
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student1);
        OfficialStudentDTO student2 = new OfficialStudentDTO("STU2", "Le Thi B", "Binh Duong","0232323222", "Nữ", "22/2/2022", 0 );
        OfficialStudentDAO.getInstance(Activity_Login.this).insertOfficialStudent(Activity_Login.this, student2);

        // Insert data in STAFF

        StaffDTO staff1 = new StaffDTO("STA1", "Nguyen Thi C", "TP HCM", "0343333333", "Nữ", "22/2/2022", 1, "1",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff1);
        StaffDTO staff2 = new StaffDTO("STA2", "Nguyen Thi D", "TP HCM", "03435555333", "Nữ","22/2/2022", 1, "2",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff2);
        StaffDTO staff3 = new StaffDTO("STA3", "Nguyen Thi E", "TP HCM", "03435555333", "Nữ","22/2/2022", 1, "3",1);
        StaffDAO.getInstance(Activity_Login.this).insertStaff(Activity_Login.this, staff3);

        // Insert data in TEACHERS

        TeacherDTO teacher1 = new TeacherDTO("TEA1", "Nguyen Thi E", "Binh Duong", "0346655566", "Nữ", "22/2/2022", 50000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher1);
        TeacherDTO teacher2 = new TeacherDTO("TEA2", "Nguyen Thi G", "Binh Duong", "0346699966", "Nam", "23/2/2022", 50000000);
        TeacherDAO.getInstance(Activity_Login.this).insertTeacher(Activity_Login.this, teacher2);

        // Insert data ACCOUNT

        AccountDTO account1 = new AccountDTO("ACC1", "STA1", "nguyenthic", "thic123");
        AccountDTO account2 = new AccountDTO("ACC2", "STA2", "nguyenthid", "thid123");
        AccountDTO account5 = new AccountDTO("ACC5", "STU1", "nguyenthia", "thia123");
        AccountDTO account6 = new AccountDTO("ACC6", "STU2", "nguyenthib", "thib123");
        AccountDTO account7 = new AccountDTO("ACC6", "STA6", "nguyenthie", "thie123");
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account1);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account2);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account5);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account6);
        AccountDAO.getInstance(Activity_Login.this).insertAccount(Activity_Login.this, account7);


        // Insert data in NOTIFICATION

        NotificationDTO notification1 =  new NotificationDTO("NOT1", "ACC1", "Thông báo nghi học", "Nghỉ học từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification1);
        NotificationDTO notification2 =  new NotificationDTO("NOT2", "ACC2", "Thông báo học bù", "Học bù từ ngày 13/4/2024 đến hết ngày 30/5/2024");
        NotificationDAO.getInstance(Activity_Login.this).InsertNotification(Activity_Login.this, notification2);

        // Insert potential student

        PotentialStudentDTO pStudent1 = new PotentialStudentDTO("Nguyễn Văn H", "0343884343",
                "Nam", "Bình Dương", "0", "5.5-6", "10");
        PotentialStudentDTO pStudent2 = new PotentialStudentDTO("Nguyen Thị I", "0977748343",
                "Nữ", "Bình Dương", "0", "5.5-6", "10");
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent1);
        PotentialStudentDAO.getInstance(Activity_Login.this).InsertPotentialStudent(Activity_Login.this, pStudent2);

         // Insert program

        ProgramDTO program1 = new ProgramDTO("PRO1", "Chứng chỉ ielts từ 5.5-6.0",
              "5.0", "6.5", "Cải thiện khả năng nghe",
              "5.5", "6.5", "7.0", "7.0",
              5000000, "6 tháng", "CER1");
        ProgramDTO program2 = new ProgramDTO("PRO2", "Chứng chỉ ielts từ 5.5-6.0",
                "5.0", "6.5", "Cải thiện khả năng nói",
                "6.5", "6.5", "7.0", "7.0",
                10000000, "6 tháng", "CER1");
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program1);
        ProgramDAO.getInstance(Activity_Login.this).InsertProgram(Activity_Login.this, program2);

        // Insert data in CLASSROOM

        ClassroomDTO classroom1 = new ClassroomDTO("CLA1", "B1.11");
        ClassroomDTO classroom2 = new ClassroomDTO("CLA2", "B1.12");
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom1);
        ClassroomDAO.getInstance(Activity_Login.this).InsertNewClassroom(Activity_Login.this, classroom2);

        // Insert data in CLASS

        ClassDTO class1 = new ClassDTO("CLS1", "Lớp ielts từ 5.9-6.5 giúp cải thiện kĩ năng nghe",
                "22/05/2024", "22/12/2024", "PRO1", "TEA1", "STA1", "0");
        ClassDTO class2 = new ClassDTO("CLS1", "Lớp ielts từ 5.9-6.5 giúp cải thiện kĩ năng đọc",
                "22/05/2024", "22/12/2024", "PRO1", "TEA1", "STA1", "0");
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class2);
        ClassDAO.getInstance(Activity_Login.this).InsertClass(Activity_Login.this, class1);

        // Insert data in Schedule

        ScheduleDTO schedule1 = new ScheduleDTO("SCH1", "2", "14", "16",
                "CLS1", "CLA1");
        ScheduleDTO schedule2= new ScheduleDTO("SCH2", "3", "7", "9",
                "CLS1", "CLA2");
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule1);
        ScheduleDAO.getInstance(Activity_Login.this).InsertSchedule(Activity_Login.this,
                schedule2);

        // Insert data in Certificate

        CertificateDTO certificate1 = new CertificateDTO(null, "Ielts Academic",
                "Ielts Academic được công nhận rộng rãi như là yêu cầu ngôn ngữ đầu " +
                        "vào cho tất cả các khóa học Đại học và Sau Đại học");
        CertificateDTO certificate2 = new CertificateDTO(null, "Ielts General",
                "Ielts General hích hợp cho tất cả những ai chuẩn bị tới các nước nói " +
                        "tiếng Anh để hoàn tất chương trình trung học, các chương trình đào " +
                        "tạo hoặc với mục đích nhập cư.");
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate1);
        CertificateDAO.getInstance(Activity_Login.this).InsertCertificate(Activity_Login.this,
                certificate2);

        // Insert data in Collecting_Tuition_Fees

        CollectionTuitionFeesDTO collectionTuitionFees1 = new CollectionTuitionFeesDTO(
                null, "STU1", "23/5/2024 15:23:22", "5000000");
        CollectionTuitionFeesDTO collectionTuitionFees2 = new CollectionTuitionFeesDTO(
                null, "STU2", "23/5/2024 15:23:22", "5000000");
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees1);
        CollectionTuitionFeesDAO.getInstance(Activity_Login.this).InsertCollection_Tuition_Fees(
                Activity_Login.this, collectionTuitionFees2);

        // Insert data in Examination

        ExaminationDTO exam1 = new ExaminationDTO(null, "Kỳ thi tuyển sinh đầu vào",
                "Format Ielts", "23/05/2024", "CLS1", "CLA1");
        ExaminationDTO exam2 = new ExaminationDTO(null, "Kiểm tra đầu ra",
                "Format Ielts", "23/07/2024", "CLS1", "CLA1");
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam1);
        ExaminationDAO.getInstance(Activity_Login.this).InsertExamination(Activity_Login.this,
                exam2);

        // Insert data in ExamScore

        ExamScoreDTO examScore1 = new ExamScoreDTO(null, "EXA1", "STU1",
                "6", "7", "6", "6");
        ExamScoreDTO examScore2 = new ExamScoreDTO(null, "EXA1", "STU2",
                "6", "7", "6", "6");
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore1);
        ExamScoreDAO.getInstance(Activity_Login.this).InsertExamScore(Activity_Login.this,
                examScore2);

        // Insert data in Teaching

        TeachingDTO teaching1 = new TeachingDTO(null, "STU1", "CLS1");
        TeachingDTO teaching2 = new TeachingDTO(null, "STU2", "CLS1");
        TeachingDTO teaching3 = new TeachingDTO(null, "STU2", "CLS2");
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching1);
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching2);
        TeachingDAO.getInstance(Activity_Login.this).InsertDate(Activity_Login.this,
                teaching3);

    }
*/
/*
    @Override
    protected void onStart() {
        super.onStart();
        //setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.input_username);
        passwordInput = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.login_btn);

        // Initialize database

        DataProvider.getInstance(Activity_Login.this).recreateDatabase(Activity_Login.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // handle login event
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Activity_Login.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {

                    String whereClause = "USERNAME = ? AND PASSWORD = ?";
                    String[] whereArgs = new String[]{username, password};
                    Cursor cursor = AccountDAO.getInstance(Activity_Login.this).selectAccount(Activity_Login.this, whereClause, whereArgs);

                    if (cursor != null && cursor.getCount() > 0) {
                        Intent intent = new Intent(Activity_Login.this, Activity_Main_Screen.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Activity_Login.this, "Please enter correct username and password", Toast.LENGTH_SHORT).show();
                    }

                    if (cursor.moveToFirst()) {
                        do {
                            int idIndex = cursor.getColumnIndex("ID_USER");
                            if (idIndex != -1) {
                                idUser = cursor.getString(idIndex);
                            }
                            int idAccIndex = cursor.getColumnIndex("ID_ACCOUNT");
                            if (idAccIndex != -1) {
                                idAccount = cursor.getString(idAccIndex);
                            }

                        } while (cursor.moveToNext());
                    }

                    cursor.close();
                }
                // Log.d("I'm on start: ", username.toString() + "  " + password + "  ");

            }
        });
        
    }
*/