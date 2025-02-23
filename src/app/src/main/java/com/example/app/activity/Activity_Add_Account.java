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
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Activity_Add_Account extends AppCompatActivity {
    EditText idAccount, idUser, username, password;
    Button exitBtn, doneBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
       // idAccount = findViewById(R.id.idAccount);
        idUser = findViewById(R.id.idUser);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);

        String message = getIntent().getStringExtra("idAccount");


        if (!message.equals("")) {

            Log.d("Put message: ", message);
            idUser.setEnabled(false);

            List<AccountDTO> listAccount = AccountDAO.getInstance(
                    Activity_Add_Account.this).selectAccountVer2(Activity_Add_Account.this,
                    "ID_ACCOUNT = ?", new String[]{message});
            AccountDTO account = null;
            if (listAccount.size() != 0) {
                account = listAccount.get(0);
                idUser.setText(account.getIdUser());
                username.setText(account.getUserName());
                password.setText(account.getPassWord());
                Log.d("Account ID: ", message);
            }

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (idUser.getText().toString().equals("")
                            || username.getText().toString().equals("") || password.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Account.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Account.this);
                        builder.setTitle("Thông báo")
                                .setMessage("Bạn có chắn chắn muốn chỉnh sửa thông tin tài khoản không?");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        AccountDTO accountUpdate = new AccountDTO(message, idUser.getText().toString(),
                                                username.getText().toString(), password.getText().toString());
                                        try {
                                            int rowEffect = AccountDAO.getInstance(Activity_Add_Account.this).updateAccount(Activity_Add_Account.this,
                                                    accountUpdate, "ID_ACCOUNT = ?", new String[] {message});
                                            if (rowEffect > 0) {
                                                Toast.makeText(Activity_Add_Account.this, "Sửa thông tin tài " +
                                                        "khoản thành công!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d("Update account: ", "failed");
                                            }
                                        } catch (Exception e) {
                                            Log.d("Update account error: ", e.getMessage());
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

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Account.this);
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


        }  else {

            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String idUserNew = idUser.getText().toString();
                    String usernameNew = username.getText().toString();
                    String passwordNew = password.getText().toString();
                    AccountDTO accountNew = new AccountDTO(null, idUserNew, usernameNew, passwordNew);
                    if (idUser.getText().toString().equals("")
                            || username.getText().toString().equals("") || password.getText().toString().equals("")) {
                        Toast.makeText(Activity_Add_Account.this, "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else {

                        String subString = idUserNew.substring(0, 3);
                        String[] subIdStaffArr = {"STA", "STU"};
                        ArrayList<String> subIdStaffList = new ArrayList<>(Arrays.asList(subIdStaffArr));
                        if (! subIdStaffList.contains(subString)) {
                            Toast.makeText(Activity_Add_Account.this, "Hãy viết đúng định " +
                                    "dạng mã của người dùng!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<AccountDTO> listAccount = AccountDAO.getInstance(Activity_Add_Account.this)
                                .selectAccountVer2(Activity_Add_Account.this,
                                        "ID_USER = ? AND STATUS = ?",
                                        new String[] {idUserNew, "0"});
                      //  Log.d("List account to add account: ", String.valueOf(listAccount.isEmpty()));

                        if (!listAccount.isEmpty()) {
                            Toast.makeText(Activity_Add_Account.this, "Người dùng " +
                                    "này đã có tài khoản trên hệ thống!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (subString.equals("STA")) {
                            List<StaffDTO> listStaff = StaffDAO.getInstance(Activity_Add_Account.this)
                                    .SelectStaffVer2(Activity_Add_Account.this,
                                            "ID_STAFF = ? AND STATUS = ?",
                                            new String[] {idUserNew, "0"});
                            if (listStaff.isEmpty()) {
                                Toast.makeText(Activity_Add_Account.this, "Mã nhân viên này" +
                                        " không tồn tại trên hệ thống", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } else {
                            List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(Activity_Add_Account.this)
                                    .SelectStudentVer2(Activity_Add_Account.this,
                                            "ID_STUDENT = ? AND STATUS = ?",
                                            new String[] {idUserNew, "0"});
                            if (listStudent.isEmpty()) {
                                Toast.makeText(Activity_Add_Account.this, "Mã học viên này" +
                                        " không tồn tại trên hệ thống", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        try {

                            int rowEffect = AccountDAO.getInstance(Activity_Add_Account.this).insertAccount(
                                    Activity_Add_Account.this, accountNew);
                            if (rowEffect > 0) {
                                Toast.makeText(Activity_Add_Account.this, "Thêm tài khoản mới thành công",
                                        Toast.LENGTH_SHORT).show();

                                idUser.setText("");
                                username.setText("");
                                password.setText("");

                            } else {
                                Log.d("Add new account: ", "failed");
                            }
                        } catch (Exception e) {
                            Log.d("Add new account error", e.getMessage());
                        }
                    }
                }
            });

            exitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }
}