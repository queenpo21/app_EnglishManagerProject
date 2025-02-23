package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.model.AccountDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.util.Calendar;

public class Activity_Change_Setting extends AppCompatActivity {
    String[] genderItem = {"Nam", "Nữ"};
    AutoCompleteTextView genderInp;
    ArrayAdapter<String> genderAdapter;
    private Button done;
    private EditText phoneInp, addrInp, nameInp;
    TextView password;
    TextView birthdayInp;
    DatePickerDialog.OnDateSetListener birthDt;

    TextView position;
    private int flag;
    EditText oldPass, newPass, retypePass;

    String fullName = "";
    String address = "";
    String phoneNumber = "";
    String gender = "";
    int salary;
    boolean isUpdate;
    int type;
    String birthday = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_setting);

        birthdayInp = findViewById(R.id.input_birthday);
        birthdayInp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Activity_Change_Setting.this,
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
                birthdayInp.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        genderInp = findViewById(R.id.input_gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        genderInp.setAdapter(genderAdapter);
        genderInp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        phoneInp = findViewById(R.id.input_phone);
        addrInp = findViewById(R.id.input_addr);
        password = findViewById(R.id.password);
        password.setText(Activity_Login.password);
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePasswordDialog(Gravity.CENTER);
            }
        });

        nameInp = findViewById(R.id.name);
        position = findViewById(R.id.position);


        done = findViewById(R.id.doneBtn);

        String idUser = Activity_Login.idUser;
        String titleIdAccount = idUser.substring(0, idUser.length() - 1) ;

         type = 0;
        String positionText = "";

        if (titleIdAccount.equals("STU")) {
            flag = 1;

            String whereClause = "ID_STUDENT = ?";
            String[] whereArgs = new String[] {idUser};
            Cursor cursor = OfficialStudentDAO.getInstance(Activity_Change_Setting.this).SelectStudent(Activity_Change_Setting.this, whereClause, whereArgs);

            if (cursor.moveToFirst()) {
                do {

                    int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                    if (fullNameIndex!= -1) {
                        fullName = cursor.getString(fullNameIndex);
                    }
                    int birthdaysIndex = cursor.getColumnIndex("BIRTHDAY");
                    if (birthdaysIndex!= -1) {
                        birthday = cursor.getString(birthdaysIndex);
                    }
                    int addressIndex = cursor.getColumnIndex("ADDRESS");
                    if (addressIndex!= -1) {
                        address = cursor.getString(addressIndex);
                    }
                    int phoneNumberIndex = cursor.getColumnIndex("PHONE_NUMBER");
                    if (phoneNumberIndex != -1) {
                        phoneNumber = cursor.getString(phoneNumberIndex);
                    }
                    int genderIndex = cursor.getColumnIndex("GENDER");
                    if (genderIndex != -1) {
                        gender = cursor.getString(genderIndex);
                    }
                    positionText = "Học viên";
                    Log.d("Find Student", fullName + " " + address + " " + phoneNumber + " " + gender);

                } while (cursor.moveToNext());
            }

        }else {
            flag = 2;

            String whereClause = "ID_STAFF = ?";
            String[] whereArgs = new String[] {idUser};
            Cursor cursor = StaffDAO.getInstance(Activity_Change_Setting.this).SelectStaff(Activity_Change_Setting.this, whereClause, whereArgs);

            if (cursor.moveToFirst()) {
                do {
                    int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                    if (fullNameIndex!= -1) {
                        fullName = cursor.getString(fullNameIndex);
                    }
                    int birthdayIndex = cursor.getColumnIndex("BIRTHDAY");
                    if (birthdayIndex!= -1) {
                        birthday = cursor.getString(birthdayIndex);
                    }
                    int addressIndex = cursor.getColumnIndex("ADDRESS");
                    if (addressIndex!= -1) {
                        address = cursor.getString(addressIndex);
                    }
                    int phoneNumberIndex = cursor.getColumnIndex("PHONE_NUMBER");
                    if (phoneNumberIndex != -1) {
                        phoneNumber = cursor.getString(phoneNumberIndex);
                    }
                    int genderIndex = cursor.getColumnIndex("GENDER");
                    if (genderIndex != -1) {
                        gender = cursor.getString(genderIndex);
                    }
                    int salaryIndex = cursor.getColumnIndex("SALARY");
                    if (salaryIndex != -1) {
                        salary = cursor.getInt(salaryIndex);
                    }
                    int typeIndex = cursor.getColumnIndex("TYPE");
                    if (typeIndex != -1) {
                        type = cursor.getInt(typeIndex);
                        if (type == 1) {
                            positionText = "Quản lý";
                        } else if (type == 2) {
                            positionText = "Nhân viên học vụ";
                        } else {
                            positionText = "Nhân viên điểm danh";
                        }
                    }
                } while (cursor.moveToNext());
            }
        }

        genderInp.setText(gender);
        genderAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, genderItem);
        genderInp.setAdapter(genderAdapter);
        phoneInp.setText(phoneNumber);
        addrInp.setText(address);
        nameInp.setText(fullName);
        birthdayInp.setText(birthday);
        position.setText(positionText);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (genderInp.getText().toString().equals("") || phoneInp.getText().toString().equals("")
                        || addrInp.getText().toString().equals("") || nameInp.getText().toString().equals("") || birthday.isEmpty()) {
                    Toast.makeText(Activity_Change_Setting.this, "Nhập lại", Toast.LENGTH_SHORT).show();
                } else {

                    // Handle updating user information

                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Change_Setting.this);
                    builder.setTitle("Bạn có chắc chắn muốn cập nhật dữ liệu không ?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String genderUpdate = genderInp.getText().toString();
                            String phoneNumberUpdate = phoneInp.getText().toString();
                            String addressUpdate = addrInp.getText().toString();
                            String birthdayUpdate = birthdayInp.getText().toString();

                            // Update password

                            AccountDTO updateAccount = new AccountDTO(Activity_Login.idAccount,
                                    Activity_Login.idUser, Activity_Login.username, password.getText().toString());
                            String whereClause = "ID_ACCOUNT = ?";
                            String[] whereArg =  new String[]{Activity_Login.idAccount};

                            Activity_Login.password = password.getText().toString();

                            int rowEffect = AccountDAO.getInstance(Activity_Change_Setting.this).updateAccount(Activity_Change_Setting.this,
                                    updateAccount, whereClause, whereArg);
                            Log.d("Change account: ", String.valueOf(rowEffect));
                            if (rowEffect > 0) {
                                isUpdate = true;
                            }

                            // Update individual information

                            if (flag == 1) {
                                OfficialStudentDTO student = new OfficialStudentDTO(idUser,
                                        fullName, addressUpdate, phoneNumberUpdate, genderUpdate, birthdayUpdate, 0);
                                String whereClauseUpdateInf = "ID_STUDENT = ?";
                                String[] whereArgUpdateInf =  new String[]{Activity_Login.idUser};

                                int rowEffectStudent = OfficialStudentDAO.getInstance(Activity_Change_Setting.this).updateOfficialStudent(Activity_Change_Setting.this,
                                        student, whereClauseUpdateInf, whereArgUpdateInf);
                                if (rowEffectStudent > 0) {
                                    isUpdate = true;
                                }
                            } else {
                                StaffDTO staff = new StaffDTO(idUser, fullName, addressUpdate, phoneNumberUpdate,
                                        genderUpdate, birthdayUpdate, salary,  String.valueOf(type), 0);
                                String whereClauseUpdateInf = "ID_STAFF = ?";
                                String[] whereArgUpdateInf =  new String[]{Activity_Login.idUser};

                                int rowEffectStaff = StaffDAO.getInstance(Activity_Change_Setting.this).updateStaff(Activity_Change_Setting.this,
                                        staff, whereClauseUpdateInf, whereArgUpdateInf);
                                if(rowEffectStaff > 0) {
                                    isUpdate = true;
                                }
                            }
                            Toast.makeText(Activity_Change_Setting.this, "Cập nhật dữ liệu thành công !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });

                    builder.setNegativeButton("Hủy", null);

                    builder.show();

                }
            }

        });

    }
    private void openChangePasswordDialog(int gravity) {
        final Dialog dialog = new Dialog(Activity_Change_Setting.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password_dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.change_password_dialog, null);

        Button exitBtn, doneBtn;
        oldPass = dialog.findViewById(R.id.oldPass);
        newPass = dialog.findViewById(R.id.newPass);
        retypePass = dialog.findViewById(R.id.retypePass);

        exitBtn = dialog.findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        doneBtn = dialog.findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Thêm cho t cái check sai mk nha
               // oldPass = findViewById(R.id.oldPass);

                if (oldPass.getText().toString() == "" || !password.getText().toString().equals(oldPass.getText().toString()))  {
                    Toast.makeText(Activity_Change_Setting.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
                }
                else if (newPass == null || newPass.getText().toString().length() < 8) {
                    Toast.makeText(Activity_Change_Setting.this, "Mật khẩu phải có ít nhất 8 ký tự", Toast.LENGTH_SHORT).show();
                }
                else if (oldPass.getText().toString() == ""  || !newPass.getText().toString().equals(retypePass.getText().toString())) {
                    Toast.makeText(Activity_Change_Setting.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    Log.d("All passwords: ", newPass.getText().toString() + "  " + retypePass.getText().toString());
                } else {


                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Activity_Change_Setting.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Bạn có chắn chắn muốn đổi mật khẩu không?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            AccountDTO account = new AccountDTO(Activity_Login.idAccount, Activity_Login.idUser,
                                    Activity_Login.username, newPass.getText().toString());
                            try {
                                int rowEffect = AccountDAO.getInstance(Activity_Change_Setting.this)
                                        .updateAccount(Activity_Change_Setting.this, account,
                                                "ID_ACCOUNT = ? AND STATUS = ?",
                                                new String[] {account.getIdAccount(), "0"});
                                if (rowEffect > 0) {
                                    Log.d("All passwords: ", oldPass.getText().toString() + "  " + newPass.getText().toString());
                                    Toast.makeText(Activity_Change_Setting.this, "Đổi mật khẩu " +
                                            "thành công!", Toast.LENGTH_SHORT).show();
                                   // dialog.dismiss();
                                } else {
                                    Toast.makeText(Activity_Change_Setting.this, "Đổi mật khẩu " +
                                            "thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e)  {
                                Log.d("Update password error: ", e.getMessage());
                            }
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
                    builder.show();

                    //dialog.dismiss();
                }


            }
        });

        dialog.show();
    }
}