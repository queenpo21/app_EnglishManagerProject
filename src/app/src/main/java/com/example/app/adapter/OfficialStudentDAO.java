package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.AccountDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;

import java.util.ArrayList;
import java.util.List;

public class OfficialStudentDAO {
    public static OfficialStudentDAO instance;
    private OfficialStudentDAO(Context context) {}
    public static synchronized OfficialStudentDAO getInstance(Context context) {
        if (instance == null) {
            instance = new OfficialStudentDAO(context);
        }
        return instance;
    }

    public int insertOfficialStudent(Context context, OfficialStudentDTO student) {
        String idStudent = student.getIdStudent();
        String fullName = student.getFullName();
        String address = student.getAddress();
        String phoneNumber = student.getPhoneNumber();
        String gender = student.getGender();
        int status = student.getStatus();
        int rowEffect = -1;

        int maxId = DataProvider.getInstance(context).getMaxId("OFFICIAL_STUDENT", "ID_STUDENT");

        ContentValues values = new ContentValues();
        values.put("ID_STUDENT", "STU" + String.valueOf(maxId + 1));
        values.put("FULLNAME", fullName);
        values.put("ADDRESS", address);
        values.put("PHONE_NUMBER", phoneNumber);
        values.put("GENDER", gender);
        values.put("BIRTHDAY", student.getBirthday());
        values.put("STATUS", status);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("OFFICIAL_STUDENT", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Official Student: ", "success");
            } else {
                Log.d("Insert Official Student: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Official Student Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int deleteOfficialStudent(Context context, String whereClause, String[] whereArgs)  {
        int rowEffect = -1;
        try {
            rowEffect = DataProvider.getInstance(context).deleteData("OFFICIAL_STUDENT",whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete Official Student: ", "success");
            } else {
                Log.d("Delete Official Student: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Official Student Error: ", e.getMessage());
        }
        return rowEffect;
    }

    public int updateOfficialStudent(Context context, OfficialStudentDTO student, String whereClause, String[] whereArgs) {
        String idStudent = student.getIdStudent();
        String fullName = student.getFullName();
        String address = student.getAddress();
        String phoneNumber = student.getPhoneNumber();
        String gender = student.getGender();
        int status = student.getStatus();

        ContentValues values = new ContentValues();
        values.put("FULLNAME", fullName);
        values.put("ADDRESS", address);
        values.put("PHONE_NUMBER", phoneNumber);
        values.put("GENDER", gender);
        values.put("BIRTHDAY", student.getBirthday());
        values.put("STATUS", status);

        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("OFFICIAL_STUDENT", values, whereClause, whereArgs);
            return rowsUpdated;
        } catch (SQLException e) {
            Log.e("Update Official Student Error: ", e.getMessage());
        }
        return 0;
    }

    public Cursor SelectStudent (Context context, String whereClause, String[] whereArgs) {
        Cursor cursor = null;
        try {
            cursor = DataProvider.getInstance(context).selectData("OFFICIAL_STUDENT", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Student: ", e.getMessage());
        }
        return cursor;
    }

    public List<OfficialStudentDTO> SelectStudentVer2(Context context, String whereClause, String[] whereArgs) {
        List<OfficialStudentDTO> listStudent = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("OFFICIAL_STUDENT", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Student: ", e.getMessage());
        }
        String name = "", phoneNumber = "", gender = "", address = "", birthday = "", id = "";

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("ID_STUDENT");
                if (idIndex!= -1) {
                    id = cursor.getString(idIndex);
                }
                int fullNameIndex = cursor.getColumnIndex("FULLNAME");
                if (fullNameIndex!= -1) {
                    name = cursor.getString(fullNameIndex);
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
                int birthdayIndex = cursor.getColumnIndex("BIRTHDAY");
                if (birthdayIndex != -1) {
                    birthday = cursor.getString(birthdayIndex);
                }
                OfficialStudentDTO student = new OfficialStudentDTO(id, name, address,
                        phoneNumber, gender, birthday, 0);
                listStudent.add(student);

                Log.d("Find Potential Student", name + " " + address + " " + phoneNumber + " " + gender);

            } while (cursor.moveToNext());
        }

        return listStudent;
    }

    public int DeleteOfficialStudent(Context context, OfficialStudentDTO student,
                                     String whereClause, String[] whereArg) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("OFFICIAL", values,
                    "ID_STUDENT = ? AND STATUS = ?", new String[] {student.getIdStudent(), "0"});
            if (rowEffect > 0) {
                Log.d("Delete account ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete official student Error: ", e.getMessage());
        }

        return rowEffect;
    }


}
