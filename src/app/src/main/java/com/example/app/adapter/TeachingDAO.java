package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.AccountDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.List;

public class TeachingDAO {
    public static TeachingDAO instance;
    private TeachingDAO(Context context) {}
    public static synchronized TeachingDAO getInstance(Context context) {
        if (instance == null) {
            instance = new TeachingDAO(context);
        }
        return instance;
    }

    public int InsertTeaching(Context context, TeachingDTO teaching) {

        int rowEffect = -1;
        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("TEACHING", "ID_TEACHING");

        values.put("ID_TEACHING", "TEC" + String.valueOf(maxId + 1));
        values.put("ID_STUDENT", teaching.getIdStudent());
        values.put("ID_CLASS",teaching.getIdClass());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("TEACHING", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Teaching: ", "success");
            } else {
                Log.d("Insert Teaching: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Teaching Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateTeaching(Context context, TeachingDTO teaching, String whereClause, String[] whereArgs)  {
        int rowEffect = -1;
        ContentValues values = new ContentValues();


        values.put("ID_STUDENT", teaching.getIdStudent());
        values.put("ID_CLASS",teaching.getIdClass());
        values.put("STATUS", 0);

        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("TEACHING", values, whereClause, whereArgs);
            if (rowsUpdated > 0) {
                Log.d("Update Teaching: ", "Success");
            } else {
                Log.d("Update Teaching: ", "No rows updated");
            }
        } catch (SQLException e) {
            Log.e("Update Teaching Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<TeachingDTO> SelectTeaching(Context context, String whereClause, String[] whereArgs)  {
        List<TeachingDTO> listTeaching = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("TEACHING",
                    new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Teaching: ", e.getMessage());
        }

        String idTeaching = "", idStudent = "", idClass = "";

        if (cursor.moveToFirst()) {
            do {
                int idTeachingIndex = cursor.getColumnIndex("ID_TEACHING");
                if (idTeachingIndex!= -1) {
                    idTeaching = cursor.getString(idTeachingIndex);
                }
                int idStudentIndex = cursor.getColumnIndex("ID_STUDENT");
                if (idStudentIndex != -1) {
                    idStudent = cursor.getString(idStudentIndex);
                }
                int idClassIndex = cursor.getColumnIndex("ID_CLASS");
                if (idClassIndex != -1) {
                    idClass = cursor.getString(idClassIndex);
                }
                listTeaching.add(new TeachingDTO(idTeaching, idStudent, idClass));

            } while (cursor.moveToNext());
        }

        return listTeaching;
    }

    public int DeleteTeaching(Context context, TeachingDTO teaching, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("TEACHING", values,
                    "ID_TEACHING = ? AND STATUS = ?", new String[] {teaching.getIdTeaching(), "0"});
            if (rowEffect > 0) {
                Log.d("Delete teaching ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete teaching Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int DeleteTeachingByIdClass(Context context, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("TEACHING", values,
                    whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete teaching ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete teaching Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int DeleteAccount(Context context, AccountDTO account, String whereClause, String[] whereArgs)  {
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).updateData("ACCOUNT", values,
                    "ID_ACCOUNT = ?", new String[] {account.getIdAccount()});
            if (rowEffect > 0) {
                Log.d("Delete account ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete account Error: ", e.getMessage());
        }

        return  rowEffect;
    }

}
