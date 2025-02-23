package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;
import com.example.app.model.TeacherDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.List;

public class TeacherDAO {
    public static TeacherDAO instance;
    private TeacherDAO(Context context) {}
    public static synchronized TeacherDAO getInstance(Context context) {
        if (instance == null) {
            instance = new TeacherDAO(context);
        }
        return instance;
    }

    public int insertTeacher(Context context, TeacherDTO teacher) {
        ContentValues values = new ContentValues();
        int rowEffect = -1;

        int maxId = DataProvider.getInstance(context).getMaxId("TEACHERS", "ID_TEACHER");

        values.put("ID_TEACHER", "TEA" + String.valueOf(maxId + 1));
        values.put("FULLNAME", teacher.getFullName());
        values.put("ADDRESS",teacher.getAddress());
        values.put("PHONE_NUMBER", teacher.getPhoneNumber());
        values.put("GENDER", teacher.getGender());
        values.put("SALARY", teacher.getSalary());
        values.put("BIRTHDAY", teacher.getBirthday());
        values.put("STATUS", 0);

        Log.d("Id max of teacher: ", String.valueOf(maxId + 1));

        try {
            rowEffect = DataProvider.getInstance(context).insertData("TEACHERS", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Teacher: ", "success");
            } else {
                Log.d("Insert Teacher: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Teacher Error: ", e.getMessage());
        }
        return rowEffect;
    }

    public void deleteTeacher(Context context, String whereClause, String[] whereArgs)  {
        try {
            int rowEffect = DataProvider.getInstance(context).deleteData("TEACHERS",whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete Teacher: ", "success");
            } else {
                Log.d("Delete Teacher: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Teacher Error: ", e.getMessage());
        }
    }

    public int updateTeacher(Context context, TeacherDTO teacher, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        int rowsUpdated= -1;

        values.put("FULLNAME", teacher.getFullName());
        values.put("ADDRESS",teacher.getAddress());
        values.put("PHONE_NUMBER", teacher.getPhoneNumber());
        values.put("GENDER", teacher.getGender());
        values.put("SALARY", teacher.getSalary());
        //values.put("STATUS", teacher.getStatus());

        try {
            rowsUpdated = DataProvider.getInstance(context).updateData("TEACHERS", values, whereClause, whereArgs);
            if (rowsUpdated > 0) {
                Log.d("Update Teacher: ", "Success");
            } else {
                Log.d("Update Teacher: ", "No rows updated");
            }
        } catch (SQLException e) {
            Log.e("Update Teacher Error: ", e.getMessage());
        }

        return rowsUpdated;
    }

    public List<TeacherDTO> SelectTeacher(Context context, String whereClause, String[] whereArg) {
        List<TeacherDTO> teachers = new ArrayList<>();

        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("TEACHERS",
                    new String[]{"*"},  whereClause, whereArg, null);
        }catch(SQLException e) {
            Log.d("Select Teacher: ", e.getMessage());
        }

        String idTeacher = "", fullName = "", address = "", phoneNumber = "", gender = "", birthday = "";
        int salary = 0;

        if (cursor.moveToFirst()) {
            do {
                int idTeacherIndex = cursor.getColumnIndex("ID_TEACHER");
                if (idTeacherIndex!= -1) {
                    idTeacher = cursor.getString(idTeacherIndex);
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
                }int genderIndex = cursor.getColumnIndex("GENDER");
                if (genderIndex != -1) {
                    gender = cursor.getString(genderIndex);
                }
                int salaryIndex = cursor.getColumnIndex("SALARY");
                if (salaryIndex != -1) {
                    salary = cursor.getInt(salaryIndex);
                }

                teachers.add(new TeacherDTO(idTeacher, fullName, address, phoneNumber,gender, birthday, salary));

            } while (cursor.moveToNext());
        }

        return teachers;
    }

    public int DeleteTeacher(Context context, TeacherDTO teacher, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).updateData("TEACHERS", values,
                    whereClause, whereArgs);
        } catch (SQLException e) {
            Log.d("Delete teacher Error: ", e.getMessage());
        }

        return  rowEffect;
    }

}
