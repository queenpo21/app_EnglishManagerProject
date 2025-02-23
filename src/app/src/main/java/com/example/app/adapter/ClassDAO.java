package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.TeachingDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassDAO {

    public static ClassDAO instance;
    private ClassDAO(Context context) {}
    public static synchronized ClassDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ClassDAO(context);
        }
        return instance;
    }

    public int InsertClass(Context context, ClassDTO classDTO) {
        int rowEffect = -1;
        int maxId = DataProvider.getInstance(context).getMaxId("CLASS", "ID_CLASS");

        ContentValues values = new ContentValues();

        values.put("ID_CLASS", "CLS" + String.valueOf(maxId + 1));
        values.put("NAME", classDTO.getClassName());
        values.put("START_DATE", classDTO.getStartDate());
        values.put("END_DATE", classDTO.getEndDate());
        values.put("ID_PROGRAM", classDTO.getIdProgram());
        values.put("ID_TEACHER", classDTO.getIdTeacher());
        values.put("ID_STAFF", classDTO.getIdStaff());
        values.put("STATUS", "0");

        try {
            rowEffect = DataProvider.getInstance(context).insertData("CLASS", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Class: ", "success");
            } else {
                Log.d("Insert Class: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Class Error: ", e.getMessage());
        }

        return rowEffect;
    }


    public int UpdateClass(Context context, ClassDTO classDTO, String whereClause, String[] whereArgs) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();


        values.put("NAME", classDTO.getClassName());
        values.put("START_DATE", classDTO.getStartDate());
        values.put("END_DATE", classDTO.getEndDate());
        values.put("ID_PROGRAM", classDTO.getIdProgram());
        values.put("ID_TEACHER", classDTO.getIdTeacher());
        values.put("ID_STAFF", classDTO.getIdStaff());
        values.put("STATUS", "0");

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASS", values, whereClause, whereArgs);
            if (rowEffect > 0 ) {
                Log.d("Update Class: ", "success");
            } else {
                Log.d("Update Class: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Class Error: ", e.getMessage());
        }

        return rowEffect;
    }


    public List<ClassDTO> selectClass (Context context, String whereClause, String[] whereArgs) {
        List<ClassDTO> listClass = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CLASS", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Class: ", e.getMessage());
        }

        //private String classID, className, level, lecturerName, schoolTime, tuition, roomID, programID, staffID;
        String id = "", name = "", start = "", end = "", idProgram = "", idTeacher = "", idStaff = "";

        if (cursor.moveToFirst()) {
            do {
                
                int idIndex = cursor.getColumnIndex("ID_CLASS");
                if (idIndex != -1) {
                    id = cursor.getString(idIndex);
                }
                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex!= -1) {
                    name = cursor.getString(nameIndex);
                }
                int startIndex = cursor.getColumnIndex("START_DATE");
                if (startIndex!= -1) {
                    start = cursor.getString(startIndex);
                }
                int endIndex = cursor.getColumnIndex("END_DATE");
                if (endIndex != -1) {
                    end = cursor.getString(endIndex);
                }
                int programIndex = cursor.getColumnIndex("ID_PROGRAM");
                if (programIndex!= -1) {
                    idProgram = cursor.getString(programIndex);
                }
                int teacherIndex = cursor.getColumnIndex("ID_TEACHER");
                if (teacherIndex != -1) {
                    idTeacher = cursor.getString(teacherIndex);
                }
                int staffIndex = cursor.getColumnIndex("ID_STAFF");
                if (staffIndex != -1) {
                    idStaff = cursor.getString(staffIndex);
                }
                listClass.add(new ClassDTO(id, name, start, end, idProgram, idTeacher, idStaff, "0"));

            } while (cursor.moveToNext());
        }
        return listClass;
    }


    public List<ClassDTO> SelectClassByIdUser(Context context, String idUser, int type) {

        List<ClassDTO> listClass = new ArrayList<>();
        Set<String> idClass = new HashSet<>();

        if (type == 1) {
            List<TeachingDTO> teaching = TeachingDAO.getInstance(context).SelectTeaching(context,
                    "ID_STUDENT = ? and STATUS = ?", new String[] {idUser, "0"});
            for (int i = 0; i < teaching.size(); i++) {
                idClass.add(teaching.get(i).getIdClass());
            }

            for (String id : idClass) {
                listClass.addAll(ClassDAO.getInstance(context).selectClass(context,
                        "ID_CLASS = ? AND STATUS = ?", new String[] {id, "0"}));
            }
        } else {
            listClass.addAll(ClassDAO.getInstance(context).selectClass(context, "STATUS = ?",
                    new String[] {"0"}));
        }

        return listClass;

    }

    public int DeleteClass(Context context, ClassDTO classDelete, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CLASS", values,whereClause,
                    whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete class ", "success");
            } else {
                Log.d("Delete class ", "Fail");
            }
        } catch(SQLException e) {
            Log.d("Delete class error: ", e.getMessage());
        }

        return rowEffect;
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

    public List<ClassDTO> selectClassByYear (Context context, int year) {
        List<ClassDTO> listClass = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CLASS", new String[]{"*"},
                    "STATUS = ?", new String[] {"0"}, null);
        }catch(SQLException e) {
            Log.d("Select Class: ", e.getMessage());
        }

        //private String classID, className, level, lecturerName, schoolTime, tuition, roomID, programID, staffID;
        String id = "", name = "", start = "", end = "", idProgram = "", idTeacher = "", idStaff = "";

        if (cursor.moveToFirst()) {
            do {

                int idIndex = cursor.getColumnIndex("ID_CLASS");
                if (idIndex != -1) {
                    id = cursor.getString(idIndex);
                }
                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex!= -1) {
                    name = cursor.getString(nameIndex);
                }
                int endIndex = cursor.getColumnIndex("END_DATE");
                if (endIndex != -1) {
                    end = cursor.getString(endIndex);
                }
                int programIndex = cursor.getColumnIndex("ID_PROGRAM");
                if (programIndex!= -1) {
                    idProgram = cursor.getString(programIndex);
                }
                int teacherIndex = cursor.getColumnIndex("ID_TEACHER");
                if (teacherIndex != -1) {
                    idTeacher = cursor.getString(teacherIndex);
                }
                int staffIndex = cursor.getColumnIndex("ID_STAFF");
                if (staffIndex != -1) {
                    idStaff = cursor.getString(staffIndex);
                }
                int startIndex = cursor.getColumnIndex("START_DATE");
                if (startIndex!= -1) {
                    start = cursor.getString(startIndex);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    LocalDate dateTime = LocalDate.parse(start, formatter);
                    Log.d("Get date time in class DAO: ", String.valueOf(dateTime.getYear()));
                    if (dateTime.getYear() == year) {
                        listClass.add(new ClassDTO(id, name, start, end, idProgram, idTeacher, idStaff, "0"));
                    }
                } catch (DateTimeParseException e) {
                    Log.d("Parse date exception: ", e.getMessage());
                }

            } while (cursor.moveToNext());
        }
        return listClass;
    }

}
