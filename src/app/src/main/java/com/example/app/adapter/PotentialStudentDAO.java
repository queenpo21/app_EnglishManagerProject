package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;

import java.util.ArrayList;
import java.util.List;

public class PotentialStudentDAO {
    public static PotentialStudentDAO instance;
    private PotentialStudentDAO(Context context) {}
    public static synchronized PotentialStudentDAO getInstance(Context context) {
        if (instance == null) {
            instance = new PotentialStudentDAO(context);
        }
        return instance;
    }

    public int InsertPotentialStudent(Context context, PotentialStudentDTO student) {
        ContentValues values = new ContentValues();
        int rowEffect = -1;

        int maxId = DataProvider.getInstance(context).getMaxId("POTENTIAL_STUDENT", "ID_STUDENT");

        values.put("ID_STUDENT", "PST" + String.valueOf(maxId + 1));
        values.put("FULLNAME", student.getStudentName());
        values.put("ADDRESS", student.getAddress());
        values.put("PHONE_NUMBER", student.getPhoneNumber());
        values.put("GENDER", student.getGender());
        values.put("LEVEL", student.getLevel());
        values.put("NUMBER_OF_APPOINTMENTS", Integer.valueOf(student.getAppointmentNumber()));
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("POTENTIAL_STUDENT", values);
            Log.d("Potential Student information: ", student.toString());
            if (rowEffect > 0 ) {
                Log.d("Insert Potential: ", "success");
            } else {
                Log.d("Insert Potential: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Potential Error: ", e.getMessage());
        }
        return rowEffect;
    }

    public int updatePotentialStudent(Context context, PotentialStudentDTO student, String whereClause, String[] whereArgs) {

        ContentValues values = new ContentValues();

        values.put("FULLNAME", student.getStudentName());
        values.put("ADDRESS", student.getAddress());
        values.put("PHONE_NUMBER", student.getPhoneNumber());
        values.put("GENDER", student.getGender());
        values.put("LEVEL", student.getLevel());
        values.put("NUMBER_OF_APPOINTMENTS", student.getAppointmentNumber());
        values.put("STATUS", "0");
        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("POTENTIAL_STUDENT", values, whereClause, whereArgs);
            return rowsUpdated;
        } catch (SQLException e) {
            Log.e("Update Potential Student Error: ", e.getMessage());
        }
        return 0;
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

    public List<PotentialStudentDTO> SelectStudent (Context context, String whereClause, String[] whereArgs) {
        List<PotentialStudentDTO> listStudent = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("POTENTIAL_STUDENT", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Potential Student: ", e.getMessage());
        }

        // private String studentName, phoneNumber, gender, address, state, level, appointmentNumber;
        String id = "", name = "", phoneNumber = "", gender = "", address = "", level = "", appointmentNumber = "";

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
                int levelIndex = cursor.getColumnIndex("LEVEL");
                if (levelIndex != -1) {
                    level = cursor.getString(levelIndex);
                }
                int appointmentIndex = cursor.getColumnIndex("NUMBER_OF_APPOINTMENTS");
                if (appointmentIndex != -1) {
                    appointmentNumber = cursor.getString(appointmentIndex);
                }

                PotentialStudentDTO student = new PotentialStudentDTO(id, name, phoneNumber, gender,
                        address,  level, appointmentNumber);
                listStudent.add(student);

                Log.d("Find Potential Student", name + " " + address + " " + phoneNumber + " " + gender);

            } while (cursor.moveToNext());
        }

        return listStudent;
    }


}
