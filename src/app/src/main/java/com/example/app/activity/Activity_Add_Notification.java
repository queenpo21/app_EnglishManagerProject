package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;

import java.util.List;

public class Activity_Add_Notification extends AppCompatActivity {
    EditText title, content;
    Button exitBtn, doneBtn;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        message = getIntent().getStringExtra("idNotification");

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);


     //   Log.d("Notification found: ", "success");

        if (!message.equals("")) {

            Log.d("I'm im create;","Yea");

            List<NotificationDTO> notification = NotificationDAO.getInstance(Activity_Add_Notification.this)
                            .SelectNotification(Activity_Add_Notification.this,
                                    "ID_NOTIFICATION = ? AND STATUS = ?",
                                    new String[] {message, "0"});

            title.setText(notification.get(0).getTitle());
            content.setText(notification.get(0).getDescription());


            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
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
                    if (title.equals("") || content.equals("")) {
                        Toast.makeText(Activity_Add_Notification.this,
                                "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông báo không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                NotificationDTO notificationUpdate = new NotificationDTO(null, Activity_Login.idAccount,
                                        title.getText().toString(), content.getText().toString());
                                try {
                                    int rowEffect = NotificationDAO.getInstance(Activity_Add_Notification.this)
                                            .UpdateNotification(Activity_Add_Notification.this,
                                                    notificationUpdate, "ID_NOTIFICATION = ? AND STATUS = ?",
                                                    new String[] {message, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Notification.this, "Sửa thông" +
                                                "báo thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Notification.this, "Sửa thông" +
                                                "báo thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch(Exception e)  {
                                    Log.d("Update notification information: ", e.getMessage());
                                }

                            }
                        });
                        builder.setNegativeButton("Hủy", null);
                        builder.show();

                    }
                }
            });
        } else {

            Log.d("Hello", "I'm im create;");
            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
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
                    if (title.getText().toString().equals("") || content.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Notification.this,
                                "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {

                        NotificationDTO notificationNew = new NotificationDTO(null, Activity_Login.idAccount,
                                title.getText().toString(), content.getText().toString());
                        try {
                            int rowEffect = NotificationDAO.getInstance(Activity_Add_Notification.this)
                                    .InsertNotification(Activity_Add_Notification.this, notificationNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Notification.this, "Thêm thông" +
                                        "báo mới thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_Add_Notification.this, "Thêm thông" +
                                        "báo mới thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        } catch(Exception e)  {
                            Log.d("Add new notification information: ", e.getMessage());
                        }

                        title.setText("");
                        content.setText("");

                    }
                }
            });

        }

    }
/*
   @Override

    public void onStart() {
        super.onStart();

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        if (!message.equals("")) {

            List<NotificationDTO> notification = NotificationDAO.getInstance(Activity_Add_Notification.this)
                    .SelectNotification(Activity_Add_Notification.this,
                            "ID_NOTIFICATION = ? AND STATUS = ?",
                            new String[] {message, "0"});

            title.setText(notification.get(0).getTitle());
            content.setText(notification.get(0).getDescription());

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
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
                    if (title.equals("") || content.equals("")) {
                        Toast.makeText(Activity_Add_Notification.this,
                                "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
                        builder.setTitle("Xác nhận")
                                .setMessage("Bạn có chắc chắn muốn sửa thông báo không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                NotificationDTO notificationUpdate = new NotificationDTO(null, Activity_Login.idAccount,
                                        title.getText().toString(), content.getText().toString());
                                try {
                                    int rowEffect = NotificationDAO.getInstance(Activity_Add_Notification.this)
                                            .UpdateNotification(Activity_Add_Notification.this,
                                                    notificationUpdate, "ID_NOTIFICATION = ? AND STATUS = ?",
                                                    new String[] {message, "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(Activity_Add_Notification.this, "Sửa thông" +
                                                "báo thành công!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Activity_Add_Notification.this, "Sửa thông" +
                                                "báo thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                } catch(Exception e)  {
                                    Log.d("Update notification information: ", e.getMessage());
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Notification.this);
            builder.setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn thêm thông báo không?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    NotificationDTO notificationNew = new NotificationDTO(null, Activity_Login.idAccount,
                            title.getText().toString(), content.getText().toString());
                    try {
                        int rowEffect = NotificationDAO.getInstance(Activity_Add_Notification.this)
                                .InsertNotification(Activity_Add_Notification.this, notificationNew);
                        if (rowEffect > 0) {
                            Toast.makeText(Activity_Add_Notification.this, "Thêm thông" +
                                    "báo mới thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Activity_Add_Notification.this, "Thêm thông" +
                                    "báo mới thất bại!", Toast.LENGTH_SHORT).show();
                        }
                    } catch(Exception e)  {
                        Log.d("Add new notification information: ", e.getMessage());
                    }

                }
            });
            builder.setNegativeButton("Hủy", null);
            builder.show();
        }

    }*/

}