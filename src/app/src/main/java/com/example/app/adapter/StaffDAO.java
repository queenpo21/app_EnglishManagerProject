package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;

import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    public static StaffDAO instance;
    private StaffDAO(Context context) {}
    public static synchronized StaffDAO getInstance(Context context) {
        if (instance == null) {
            instance = new StaffDAO(context);
        }
        return instance;
    }

    public int insertStaff(Context context, StaffDTO staff) {
        ContentValues values = new ContentValues();
        int rowEffect = -1;

        int maxId = DataProvider.getInstance(context).getMaxId("STAFF", "ID_STAFF");

        values.put("ID_STAFF", "STA" + String.valueOf(maxId + 1));
        values.put("FULLNAME", staff.getFullName());
        values.put("ADDRESS",staff.getAddress());
        values.put("PHONE_NUMBER", staff.getPhoneNumber());
        values.put("GENDER", staff.getGender());
        values.put("BIRTHDAY", staff.getBirthday());
        values.put("SALARY", staff.getSalary());
        values.put("TYPE", staff.getType());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("STAFF", values);
            Log.d("Staff information: ", staff.toString());
            if (rowEffect > 0 ) {
                Log.d("Insert Staff: ", "success");
            } else {
                Log.d("Insert Staff: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Staff Error: ", e.getMessage());
        }
        return rowEffect;
    }

    public void deleteStaff(Context context, String whereClause, String[] whereArgs)  {
        try {
            int rowEffect = DataProvider.getInstance(context).deleteData("STAFF",whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete Staff: ", "success");
            } else {
                Log.d("Delete Staff: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Staff Error: ", e.getMessage());
        }
    }

    public int updateStaff(Context context, StaffDTO staff, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();

        values.put("FULLNAME", staff.getFullName());
        values.put("ADDRESS",staff.getAddress());
        values.put("PHONE_NUMBER", staff.getPhoneNumber());
        values.put("GENDER", staff.getGender());
        values.put("BIRTHDAY", staff.getBirthday());
        values.put("SALARY", staff.getSalary());
        values.put("TYPE", staff.getType());
        values.put("STATUS", staff.getStatus());

        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("STAFF", values, whereClause, whereArgs);
            return rowsUpdated;
        } catch (SQLException e) {
            Log.e("Update Staff Error: ", e.getMessage());
        }
        return 0;
    }

    public Cursor SelectStaff(Context context, String whereClause, String[] whereArgs) {
        Cursor cursor = null;
        try {
            cursor = DataProvider.getInstance(context).selectData("STAFF", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Staff: ", e.getMessage());
        }
        return cursor;
    }

    public List<StaffDTO> SelectStaffVer2 (Context context, String whereClause, String[] whereArgs) {
        List<StaffDTO> listStaff = new ArrayList<>();
        Cursor cursor = StaffDAO.getInstance(context).SelectStaff(context, whereClause, whereArgs);

        String idStaff = "", fullName = "", address = "", phoneNumber = "", gender = "", birthday = "";
        int salary = 0, type = -1;

        if (cursor.moveToFirst()) {
            do {
                int idStaffIndex = cursor.getColumnIndex("ID_STAFF");
                if (idStaffIndex != -1) {
                    idStaff = cursor.getString(idStaffIndex);
                }
                int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                if (fullNameIndex != -1) {
                    fullName = cursor.getString(fullNameIndex);
                }
                int addressIndex = cursor.getColumnIndex("ADDRESS");
                if (addressIndex != -1) {
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
                int birthdayIndex = cursor.getColumnIndex("BIRTHDAY");
                if (birthdayIndex != -1) {
                    birthday = cursor.getString(birthdayIndex);
                }
                int salaryIndex = cursor.getColumnIndex("SALARY");
                if (salaryIndex != -1) {
                    salary = cursor.getInt(salaryIndex);
                }
                int typeIndex = cursor.getColumnIndex("TYPE");
                if (typeIndex != -1) {
                    type = cursor.getInt(typeIndex);
                }

                listStaff.add(new StaffDTO(idStaff, fullName, address, phoneNumber, gender, birthday,
                        salary, String.valueOf(type), 0));

            } while (cursor.moveToNext());
        }

        return listStaff;
    }

    public int DeleteStaff(Context context, StaffDTO staff, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).updateData("STAFF", values,
                    whereClause, whereArgs);
        } catch (SQLException e) {
            Log.d("Delete staff Error: ", e.getMessage());
        }

        return  rowEffect;
    }

    public int deletePotentialStudent(Context context, PotentialStudentDTO student, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).updateData("POTENTIAL_STUDENT", values,
                    "ID_STUDENT = ?", new String[] {student.getStudentID()});
        } catch (SQLException e) {
            Log.d("Delete potential Student Error: ", e.getMessage());
        }

        return  rowEffect;
    }

}
