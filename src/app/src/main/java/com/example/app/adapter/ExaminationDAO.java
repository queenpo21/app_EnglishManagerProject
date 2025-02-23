package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ExaminationDTO;
import com.example.app.model.NotificationDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.List;

public class ExaminationDAO {

    public static ExaminationDAO instance;
    private ExaminationDAO(Context context) {}
    public static synchronized ExaminationDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ExaminationDAO(context);
        }
        return instance;
    }

    public int InsertExamination(Context context, ExaminationDTO exam)  {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("EXAMINATION", "ID_EXAM");

        values.put("ID_EXAM", "EXA" + String.valueOf(maxId + 1));
        values.put("NAME", exam.getName());
        values.put("FORMAT", exam.getFormat());
        values.put("EXAM_DATE", exam.getExamDate());
        values.put("ID_CLASS", exam.getIdClass());
        values.put("ID_CLASSROOM", exam.getIdClass());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("EXAMINATION", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Examination ", "success");
            } else {
                Log.d("Insert Examination: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Examination: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateExamination(Context context, ExaminationDTO exam, String whereClause, String[] whereArgs)  {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("EXAMINATION", "ID_EXAM");


        values.put("NAME", exam.getName());
        values.put("FORMAT", exam.getFormat());
        values.put("EXAM_DATE", exam.getExamDate());
        values.put("ID_CLASS", exam.getIdClass());
        values.put("ID_CLASSROOM", exam.getIdClass());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("EXAMINATION", values,
                    whereClause, whereArgs);
            if (rowEffect > 0 ) {
                Log.d("Update Examination ", "success");
            } else {
                Log.d("Update Examination: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Examination Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<ExaminationDTO> SelectExamination(Context context, String whereClause, String[] whereArgs) {
        List<ExaminationDTO> listExams = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("EXAMINATION",
                    new String[]{"*"},  whereClause, whereArgs, null);
        } catch(SQLException e) {
            Log.d("Select Examination: ", e.getMessage());
        }

        String idExam = "", name = "", format = "", date = "", idClass = "", idClassroom = "";
        if (cursor.moveToFirst()) {
            do {
                int idExamIndex = cursor.getColumnIndex("ID_EXAM");
                if (idExamIndex!= -1) {
                    idExam = cursor.getString(idExamIndex);
                }

                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex!= -1) {
                   name = cursor.getString(nameIndex);
                }

                int formatIndex = cursor.getColumnIndex("FORMAT");
                if (formatIndex!= -1) {
                    format = cursor.getString(formatIndex);
                }

                int dateIndex = cursor.getColumnIndex("EXAM_DATE");
                if (dateIndex != -1) {
                    date = cursor.getString(dateIndex);
                }
                int classIndex = cursor.getColumnIndex("ID_CLASS");
                if (classIndex != -1) {
                    idClass = cursor.getString(classIndex);
                }
                int classroomIndex = cursor.getColumnIndex("ID_CLASSROOM");
                if (classroomIndex != -1) {
                    idClassroom = cursor.getString(classroomIndex);
                }

                listExams.add(new ExaminationDTO(idExam, name, format, date, idClass, idClassroom));

            } while (cursor.moveToNext());
        }

        return listExams;
    }

    public int DeleteExamination(Context context, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("EXAMINATION", values,
                    whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete examination ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete examination Error: ", e.getMessage());
        }
        return rowEffect;
    }

}
