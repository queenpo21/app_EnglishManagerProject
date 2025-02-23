package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.ScheduleDTO;
import com.example.app.model.TeachingDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScheduleDAO {
    public static ScheduleDAO instance;
    private ScheduleDAO(Context context) {}
    public static synchronized ScheduleDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ScheduleDAO(context);
        }
        return instance;
    }

    public int InsertSchedule(Context context, ScheduleDTO schedule) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("SCHEDULE", "ID_SCHEDULE");

        values.put("ID_SCHEDULE", "SCH" + String.valueOf(maxId + 1));
        values.put("DAY_OF_WEEK", schedule.getDayOfWeek());
        values.put("START_TIME",schedule.getStartTime());
        values.put("END_TIME", schedule.getEndTime());
        values.put("ID_CLASS", schedule.getIdClass());
        values.put("ID_CLASSROOM", schedule.getIdClassroom());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("SCHEDULE", values);
            Log.d("Schedule information: ", schedule.toString());
            if (rowEffect > 0 ) {
                Log.d("Insert Schedule: ", "success");
            } else {
                Log.d("Insert Schedule: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Schedule Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateSchedule(Context context, ScheduleDTO schedule, String whereClause, String[] whereArg) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();


        values.put("DAY_OF_WEEK", schedule.getDayOfWeek());
        values.put("START_TIME",schedule.getStartTime());
        values.put("END_TIME", schedule.getEndTime());
        values.put("ID_CLASS", schedule.getIdClass());
        values.put("ID_CLASSROOM", schedule.getIdClassroom());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("SCHEDULE", values, whereClause, whereArg);
            Log.d("Schedule information: ", schedule.toString());
            if (rowEffect > 0 ) {
                Log.d("Update Schedule: ", "success");
            } else {
                Log.d("Update Schedule: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Schedule Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<ScheduleDTO> SelectSchedule(Context context, String whereClause, String[] whereArgs) {
        List<ScheduleDTO> listSchedule = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("SCHEDULE", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Program: ", e.getMessage());
        }

        String idSchedule = "", day = "", start = "", end = "", idClass = "", idClassroom = "";

        if (cursor.moveToFirst()) {
            do {
                int idScheduleIndex = cursor.getColumnIndex("ID_SCHEDULE");
                if (idScheduleIndex!= -1) {
                    idSchedule = cursor.getString(idScheduleIndex);
                }
                int dayIndex = cursor.getColumnIndex("DAY_OF_WEEK");
                if (dayIndex != -1) {
                    day = cursor.getString(dayIndex);
                }
                int startIndex = cursor.getColumnIndex("START_TIME");
                if (startIndex != -1) {
                    start = cursor.getString(startIndex);
                }
                int endIndex = cursor.getColumnIndex("END_TIME");
                if (endIndex!= -1) {
                    end = cursor.getString(endIndex);
                }
                int classIndex = cursor.getColumnIndex("ID_CLASS");
                if (classIndex != -1) {
                    idClass = cursor.getString(classIndex);
                }
                int classroomIndex = cursor.getColumnIndex("ID_CLASSROOM");
                if (classroomIndex != -1) {
                    idClassroom = cursor.getString(classroomIndex);
                }
                listSchedule.add(new ScheduleDTO(idSchedule, day, start, end, idClass, idClassroom));
            } while (cursor.moveToNext());
        }

        return listSchedule;
    }


    public List<ScheduleDTO> SelectScheduleToShow(Context context, String whereClause, String[] whereArgs) {
        List<ScheduleDTO> listSchedule = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("SCHEDULE", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Program: ", e.getMessage());
        }

        String idSchedule = "", day = "", start = "", end = "", idClass = "", idClassroom = "";

        if (cursor.moveToFirst()) {
            do {
                int idScheduleIndex = cursor.getColumnIndex("ID_SCHEDULE");
                if (idScheduleIndex!= -1) {
                    idSchedule = cursor.getString(idScheduleIndex);
                }
                int dayIndex = cursor.getColumnIndex("DAY_OF_WEEK");
                if (dayIndex != -1) {
                    day = cursor.getString(dayIndex);
                }
                int startIndex = cursor.getColumnIndex("START_TIME");
                if (startIndex != -1) {
                    start = cursor.getString(startIndex);
                }
                int endIndex = cursor.getColumnIndex("END_TIME");
                if (endIndex!= -1) {
                    end = cursor.getString(endIndex);
                }
                int classIndex = cursor.getColumnIndex("ID_CLASS");
                if (classIndex != -1) {
                    idClass = cursor.getString(classIndex);
                }
                int classroomIndex = cursor.getColumnIndex("ID_CLASSROOM");
                if (classroomIndex != -1) {
                    idClassroom = cursor.getString(classroomIndex);
                }
                listSchedule.add(new ScheduleDTO(idSchedule, day, start, end, idClass, idClassroom));
            } while (cursor.moveToNext());
        }

        return listSchedule;
    }

    public List<ScheduleDTO> SelectScheduleByIdStudent(Context context, String idUser, int type) {
        List<ScheduleDTO> listSchedule = new ArrayList<>();
        Set<String> idClass = new HashSet<>();

        if (type == 1) {
            List<TeachingDTO> teaching = TeachingDAO.getInstance(context).SelectTeaching(context,
                    "ID_STUDENT = ? AND STATUS = ?", new String[] {idUser, "0"});
            for (int i = 0; i < teaching.size(); i++) {
                idClass.add(teaching.get(i).getIdClass()) ;
            }

            for (String id : idClass) {
                listSchedule.addAll(ScheduleDAO.getInstance(context).SelectSchedule(context,
                        "ID_CLASS = ?", new String[] {id}));
                Log.d("List id class in schedule: ", listSchedule.toString());
            }
        }
        else {
            listSchedule = ScheduleDAO.getInstance(context).SelectSchedule(context, "STATUS = ?",
                    new String[] {"0"});
        }

        return listSchedule;
    }

    public int DeleteSchedule(Context context, ScheduleDTO schedule, String whereClause,
                              String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("SCHEDULE", values,
                    "ID_SCHEDULE = ?", new String[] {schedule.getIdSchedule()});
        } catch (SQLException e) {
            Log.d("Delete schedule Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int DeleteScheduleByIdClass(Context context, String whereClause,
                              String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("SCHEDULE", values,
                    whereClause, whereArgs);
        } catch (SQLException e) {
            Log.d("Delete schedule Error: ", e.getMessage());
        }

        return rowEffect;
    }


}
