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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.NotificationDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.util.ArrayList;
import java.util.List;

public class Activity_Notifications_ToolBars extends AppCompatActivity {
    Toolbar toolbar;
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    private String message;
    private int idLayout;
    ArrayList<Object> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_tool_bars);
        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);
        message = getIntent().getStringExtra("message");
        dataArrayList = new ArrayList<>();
        list = new ArrayList<>();

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        dataArrayList.clear();

        String[] whereArgs = new String[] {"0"};
        String whereClause = "STATUS = ?";

        switch (message) {
            //Nhân viên ghi danh
            case "Quản lý học viên":
                toolbar.setTitle("Học viên tiềm năng");
                /*dataArrayList.add(new PotentialStudentDTO("Tuyết Loan", "0912345678"
                        , "Nam", "Ký túc xá khu A"
                        , "Chưa học", "Đại học","10"));*/
                /*dataArrayList.add(new PotentialStudentDTO("1","Hoàng Thiện", "0912345678"
                        , "Nữ", "Ký túc xá khu A"
                        , "Mẫu giáo","10"));*/
                /*List<PotentialStudentDTO> listPotentialStudent = PotentialStudentDAO.getInstance(Activity_Notifications_ToolBars.this).SelectStudent(
                        Activity_Notifications_ToolBars.this, "STATUS = ?", whereArgs
                );
                for (int i = 0; i < listPotentialStudent.size(); i++) {
                    Log.d("List potential Student: " ,listPotentialStudent.get(i).toString());
                    dataArrayList.add(listPotentialStudent.get(i));
                }*/
                List<PotentialStudentDTO> listPotentialStudent = PotentialStudentDAO.getInstance(
                        Activity_Notifications_ToolBars.this).SelectStudent(Activity_Notifications_ToolBars.this, whereClause, whereArgs);

                for(int i = 0; i < listPotentialStudent.size(); i++) {
                    dataArrayList.add(listPotentialStudent.get(i));
                }
                idLayout = R.layout.list_potential_student_item;

                break;

            case "Quản lý tài khoản":
                toolbar.setTitle("Tài khoản");
                dataArrayList.clear();
                List<AccountDTO> listAccount = AccountDAO.getInstance(Activity_Notifications_ToolBars.this).selectAccountVer2(
                        Activity_Notifications_ToolBars.this, "STATUS = ?",
                        new String[] {"0"});
                for (int i = 0; i < listAccount.size(); i++) {
                    dataArrayList.add(listAccount.get(i));
                }
                idLayout = R.layout.list_account_item;

                // dataArrayList.add(new AccountDTO("1", "1", "1", "1"));
                break;

            case "Quản lý nhân viên/giáo viên":
                toolbar.setTitle("Nhân viên/giáo viên");
                dataArrayList.clear();
                //Chỗ này add cả StaffDTO với TeacherDTO được nhíe

                List<StaffDTO> listStaff = StaffDAO.getInstance(Activity_Notifications_ToolBars.this).SelectStaffVer2(
                        Activity_Notifications_ToolBars.this, "STATUS = ?", new String[] {"0"});
                List<TeacherDTO> listTeacher = TeacherDAO.getInstance(Activity_Notifications_ToolBars.this).SelectTeacher(
                        Activity_Notifications_ToolBars.this, "STATUS =?", new String[] {"0"});

                for(int i = 0; i < listStaff.size(); i++) {
                    dataArrayList.add(listStaff.get(i));
                }
                for(int i = 0; i < listTeacher.size(); i++) {
                    dataArrayList.add(listTeacher.get(i));
                }
                idLayout = R.layout.list_staff_item;


                //  dataArrayList.add(new StaffDTO("1","1","1","1","1","1",1,"1",10));
                break;
            case "Quản lý lớp học":
                toolbar.setTitle("Lớp học");
                /*dataArrayList.add(new ClassDTO("IS201","Môn gì đó",
                        "Đại học", "Tuyết Loan",
                        "10 buổi", "10.000.000",
                        "Hehe","Đoán coi"));*/

                List<ClassDTO> listClass = ClassDAO.getInstance(Activity_Notifications_ToolBars.this).selectClass(
                        Activity_Notifications_ToolBars.this, "STATUS = ?", new String[] {"0"});
                for (int i = 0; i < listClass.size(); i++)  {
                    dataArrayList.add(listClass.get(i));
                }
                idLayout = R.layout.list_class_to_manage_item;

                break;
            case "Quản lý thông báo":
                toolbar.setTitle("Thông báo");
                //dataArrayList.add(new NotificationDTO("1","1","1","1"));

                List<NotificationDTO> listNotification = NotificationDAO.getInstance(Activity_Notifications_ToolBars.this)
                        .SelectNotification(Activity_Notifications_ToolBars.this, "STATUS = ?",
                                new String[]{"0"});
                for (int i = 0; i < listNotification.size(); i++) {
                    dataArrayList.add(listNotification.get(i));
                }
                idLayout = R.layout.list_notification_manage_item;

                break;
        }

        listAdapter = new List_Adapter(Activity_Notifications_ToolBars.this, idLayout, dataArrayList);
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
        if (id == R.id.add)
            switch (message) {
                case "Quản lý học viên":
                    Intent addPotential = new Intent(Activity_Notifications_ToolBars.this, Activity_Add_Potential_Student.class);
                    addPotential.putExtra("studentID", "");
                    startActivity(addPotential);
                    break;
                case "Quản lý tài khoản":
                    Intent addAccount = new Intent(Activity_Notifications_ToolBars.this, Activity_Add_Account.class);
                    addAccount.putExtra("idAccount", "");
                    startActivity(addAccount);
                    break;
                case "Quản lý nhân viên/giáo viên":
                    Intent addStaff = new Intent(Activity_Notifications_ToolBars.this, Activity_Add_Staff.class);
                    addStaff.putExtra("idStaff", "");
                    addStaff.putExtra("idTeacher", "");
                    startActivity(addStaff);
                    break;
                case "Quản lý lớp học":
                    Intent addClass = new Intent(Activity_Notifications_ToolBars.this, Activity_Add_Class.class);
                    addClass.putExtra("classID", "");
                    startActivity(addClass);
                    break;
                case "Quản lý thông báo":
                    Intent intent = new Intent(Activity_Notifications_ToolBars.this, Activity_Add_Notification.class);
                    intent.putExtra("idNotification", "");
                    startActivity(intent);
                    break;
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
                switch (message) {
                    case "Quản lý học viên":
                        PotentialStudentDTO potentialStudentDTO = (PotentialStudentDTO) item;
                        if (potentialStudentDTO.getStudentName().toLowerCase().contains(text.toLowerCase()))
                            list.add(potentialStudentDTO);
                        break;
                    case "Quản lý tài khoản":
                        AccountDTO accountDTO = (AccountDTO) item;
                        if (accountDTO.getUserName().toLowerCase().contains(text.toLowerCase()))
                            list.add(accountDTO);
                        break;
                    case "Quản lý nhân viên/giáo viên":
                        if (item instanceof StaffDTO) {
                            StaffDTO staffDTO = (StaffDTO) item;
                            if (staffDTO.getFullName().toLowerCase().contains(text.toLowerCase()))
                                list.add(staffDTO);
                        } else if (item instanceof TeacherDTO) {
                            TeacherDTO staffDTO = (TeacherDTO) item;
                            if (staffDTO.getFullName().toLowerCase().contains(text.toLowerCase()))
                                list.add(staffDTO);
                        }
                        break;
                    case "Quản lý lớp học":
                        ClassDTO classDTO = (ClassDTO) item;
                        if (classDTO.getClassName().toLowerCase().contains(text.toLowerCase()))
                            list.add(classDTO);
                        break;
                    case "Quản lý thông báo":
                        NotificationDTO notificationDTO = (NotificationDTO) item;
                        if (notificationDTO.getTitle().toLowerCase().contains(text.toLowerCase()))
                            list.add(notificationDTO);
                        break;
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