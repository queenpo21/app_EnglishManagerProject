package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.StaffDTO;

import java.util.List;

public class Activity_Forgot_Password extends AppCompatActivity {
    EditText username, phoneNumber;
    Button findPass;
    TextView turnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogot_password);

        username = findViewById(R.id.input_username);
        phoneNumber = findViewById(R.id.input_phone);
        findPass = findViewById(R.id.find_pass);
        turnBack = findViewById(R.id.turnBack);

        turnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idAccount = "";

                if (username.getText().toString().equals("")
                || phoneNumber.getText().toString().equals("")) {
                    Toast.makeText(Activity_Forgot_Password.this, "Hãy nhập đầy đủ thông tin!",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                List<AccountDTO> listAccount = AccountDAO.getInstance(Activity_Forgot_Password.this)
                        .selectAccountVer2(Activity_Forgot_Password.this,
                                "USERNAME = ? AND STATUS = ?",
                                new String[] {username.getText().toString(), "0"});
                if (listAccount.size() == 0)  {
                    Toast.makeText(Activity_Forgot_Password.this, "Tên đăng nhập " +
                            "không tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    idAccount = listAccount.get(0).getIdAccount();
                }

                String idUser = "";
                List<StaffDTO> listStaff = StaffDAO.getInstance(Activity_Forgot_Password.this)
                        .SelectStaffVer2(Activity_Forgot_Password.this,
                                "PHONE_NUMBER = ? AND STATUS = ?",
                                new String[] {phoneNumber.getText().toString(), "0"});
                if (listStaff.size() == 0) {
                    List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(Activity_Forgot_Password.this)
                            .SelectStudentVer2(Activity_Forgot_Password.this,
                                    "PHONE_NUMBER = ? AND STATUS = ?",
                                    new String[] {phoneNumber.getText().toString(), "0"});
                    if (listStudent.size() == 0) {
                        Toast.makeText(Activity_Forgot_Password.this, "Số điện thoại " +
                                "trên không tồn tại trong hệ thống!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        idUser = listStudent.get(0).getIdStudent();
                    }
                } else {
                    idUser = listStaff.get(0).getIdStaff();
                }
                Log.d("Account roll in:", idUser.toString());
                String idAccountByPhoneNumber = "";
                List<AccountDTO> accountByIdUser = AccountDAO.getInstance(Activity_Forgot_Password.this)
                        .selectAccountVer2(Activity_Forgot_Password.this,
                                "ID_USER = ? AND STATUS = ?",
                                new String[] {idUser, "0"});

                if (accountByIdUser.size() == 0) {
                    Toast.makeText(Activity_Forgot_Password.this, "Người dùng này chưa " +
                            "đăng kí tài khoản!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    idAccountByPhoneNumber = accountByIdUser.get(0).getIdAccount();
                }

                if (!idAccountByPhoneNumber.equals(idAccount))  {
                    Toast.makeText(Activity_Forgot_Password.this, "Tên đăng nhập và " +
                            "số điện thoại không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Forgot_Password.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Mật khẩu của bạn là: " + accountByIdUser.get(0).getPassWord())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .create()
                            .show();
                }

            }
        });

    }
}