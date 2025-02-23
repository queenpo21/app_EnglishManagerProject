package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;

import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.CertificateDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.CertificateDTO;
import com.example.app.model.ClassCollectingFees;
import com.example.app.model.ClassDTO;

import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.NotificationDTO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.StaffDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        String message = getIntent().getStringExtra("message");

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dataArrayList = new ArrayList<>();

        String whereClause = "STATUS = ?";
        String[] whereArgs = new String[] {"0"};

        int type = -1;

        switch (message) {
            //Học viên
            case "Thông báo hệ thống":
                List<NotificationDTO> listNotification = NotificationDAO.getInstance(Activity_Notifications.this).SelectNotification(
                        Activity_Notifications.this, whereClause, whereArgs
                );
                Log.d("List notification to show: ", listNotification.toString());
                for (int i = 0; i < listNotification.size(); i++) {
                    dataArrayList.add(listNotification.get(i));
                }
               /// dataArrayList.add(new NotificationDTO("1","1","1","1"));
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_notification_item, dataArrayList);

                break;

            case "Tra cứu điểm":

                type = AccountDAO.getInstance(Activity_Notifications.this).GetObjectLogin(Activity_Notifications.this,
                        Activity_Login.username, Activity_Login.password);
                List<ExamScoreDTO> listExamScore = ExamScoreDAO.getInstance(Activity_Notifications.this).SelectExamScoreById(
                        Activity_Notifications.this, Activity_Login.idUser, type);
                // dataArrayList.add(new ExamScoreDTO("1","1","1","1","1","1","1"));
                for (int i = 0; i < listExamScore.size(); i++)  {
                    dataArrayList.add(listExamScore.get(i));
                }
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_score_item, dataArrayList);
                break;
            case "Tra cứu chương trình đào tạo":
                String whereClauseProgram = "STATUS = ?";
                String[] whereArgsProgram = new String[] {"0"};
                List<ProgramDTO> listProgram = new ArrayList<>();

                listProgram = ProgramDAO.getInstance(Activity_Notifications.this).SelectProgram(
                        Activity_Notifications.this, whereClauseProgram, whereArgsProgram);
                for (int i = 0; i < listProgram.size(); i++) {
                    dataArrayList.add(listProgram.get(i));
                }
                /*
                dataArrayList.add(new ProgramDTO("PRO1",
                        "Hê hê",
                        "10", "10", "Đào tạo tiếng Anh", "10",
                        "10",
                        "10", "10"));*/
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_education_program_item, dataArrayList);
                break;
            case "Lịch học":
                type = AccountDAO.getInstance(Activity_Notifications.this).GetObjectLogin(Activity_Notifications.this,
                        Activity_Login.username, Activity_Login.password);
                List<ScheduleDTO> listSchedule = ScheduleDAO.getInstance(
                        Activity_Notifications.this).SelectScheduleByIdStudent(Activity_Notifications.this,
                        Activity_Login.idUser, type);
                for (int i = 0; i < listSchedule.size(); i++) {
                    dataArrayList.add(listSchedule.get(i));
                }

                // dataArrayList.add(new ScheduleDTO("1", "1", "1", "1", "1", "1"));
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_schedule_item, dataArrayList);
                break;

            case "Quản lý thông tin phòng học":

               // dataArrayList.add(new ClassroomDTO("1", "1"));

                List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_Notifications.this).SelectClassroom(
                        Activity_Notifications.this, "STATUS = ?", new String[] {"0"});
                for (int i = 0; i < listClassroom.size(); i++) {
                    dataArrayList.add(listClassroom.get(i));
                }

                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_classroom_item, dataArrayList);
                break;

            case "Xem các lớp học":
                /*dataArrayList.add(new ClassDTO("IS201","Môn gì đó",
                        "Đại học", "Tuyết Loan",
                        "10 buổi", "10.000.000",
                        "Hehe","Đoán coi"));*/
               /* dataArrayList.add(new ClassDTO("IS201","Môn gì đó",
                        "Đại học", "Tuyết Loan",
                        "10 buổi", "10.000.000",
                        "Hehe","Đoán coi"));
                dataArrayList.add(new ClassDTO("IS201","Môn gì đó",
                        "Đại học", "Tuyết Loan",
                        "10 buổi", "10.000.000",
                        "Hehe","Đoán coi"));
                        */
                //dataArrayList.add(new ClassroomDTO("1","1"));
                int typeClass = AccountDAO.getInstance(Activity_Notifications.this).GetObjectLogin(Activity_Notifications.this,
                        Activity_Login.username, Activity_Login.password);
                List<ClassDTO> listClass = ClassDAO.getInstance(
                        Activity_Notifications.this).SelectClassByIdUser(Activity_Notifications.this,
                        Activity_Login.idUser, typeClass);
                for (int i = 0; i < listClass.size(); i++) {
                    dataArrayList.add(listClass.get(i));
                }
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_class_for_teacher_item, dataArrayList);
                break;

            case "Xem chứng chỉ":
                // dataArrayList.add(new CertificateDTO("1", "1", "1"));

                List<CertificateDTO> listCertificate = CertificateDAO.getInstance(
                        Activity_Notifications.this).SelectCertificate(Activity_Notifications.this,
                        "STATUS = ?", new String[] {"0"});
                for (int i = 0; i < listCertificate.size(); i++) {
                    dataArrayList.add(listCertificate.get(i));
                }
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_certificate_item, dataArrayList);
                break;
            case "Quản lý tài khoản" :
                List<AccountDTO> listAccount = AccountDAO.getInstance(Activity_Notifications.this).selectAccountVer2(
                        Activity_Notifications.this, "STATUS = ?", new String[] {"0"});
                Log.d("List Account: ", listAccount.toString());
                for (int i = 0; i < listAccount.size(); i++) {
                    String idUser = listAccount.get(i).getIdUser();
                    if (idUser.substring(0, 3) == "STU") {
                        List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(
                                Activity_Notifications.this).SelectStudentVer2(Activity_Notifications.this,
                                "ID_STUDENT = ?", new String[] {idUser});
                        listAccount.get(i).setIdAccount(student.get(0).getFullName());
                    }else if (idUser.substring(0, 3) == "STA") {
                        List<StaffDTO> staff = StaffDAO.getInstance(
                                Activity_Notifications.this).SelectStaffVer2(Activity_Notifications.this,
                                "ID_STAFF = ?", new String[] {idUser});
                        listAccount.get(i).setIdAccount(staff.get(0).getFullName());
                    }
                    Log.d("List Account: ", listAccount.get(i).toString());
                    dataArrayList.add(listAccount.get(i));
                }
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_account_item, dataArrayList);
                break;
            case "Lịch sử thu":
                dataArrayList.add(new CollectionTuitionFeesDTO("1","1","1","1"));
                listAdapter = new List_Adapter(Activity_Notifications.this, R.layout.list_collecting_tuition_fees_item, dataArrayList);
                break;
        }
        listView.setAdapter(listAdapter);
    }
}