package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ClassDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.ProgramDTO;
import com.example.app.model.RevenueReportByYear;
import com.example.app.model.TeachingDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTuitionFeesDAO {
    public static CollectionTuitionFeesDAO instance;
    private CollectionTuitionFeesDAO(Context context) {}
    public static synchronized CollectionTuitionFeesDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CollectionTuitionFeesDAO(context);
        }
        return instance;
    }
/*    "ID_BILL TEXT PRIMARY KEY , " +
            "ID_STUDENT TEXT, " +
            "COLLECTION_DATE TEXT, " +
            "TOTAL_MONEY INTEGER, " +
            "STATUS INTEGER," +*/

    public int InsertCollection_Tuition_Fees(Context context, CollectionTuitionFeesDTO collection) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("COLLECTING_TUITION_FEES", "ID_BILL");

        values.put("ID_BILL", "CTF" + String.valueOf(maxId + 1));
        values.put("ID_TEACHING", collection.getIdStudent());
        values.put("COLLECTION_DATE", collection.getCollectionDate());
        values.put("TOTAL_MONEY", collection.getMoney());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("COLLECTING_TUITION_FEES", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Collecting Tuition Fees: ", "success");
            } else {
                Log.d("Insert Collecting Tuition Fees: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Collecting Tuition Fees: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateCollection_Tuition_Fees(Context context, CollectionTuitionFeesDTO collection,
                                             String whereClause, String[] whereArg) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();


       // values.put("ID_TEACHING", collection.getIdStudent());
       // values.put("COLLECTION_DATE", collection.getCollectionDate());
        values.put("TOTAL_MONEY", collection.getMoney());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("COLLECTING_TUITION_FEES",
                    values, whereClause, whereArg);
            if (rowEffect > 0 ) {
                Log.d("Update Collecting Tuition Fees: ", "success");
            } else {
                Log.d("Update Collecting Tuition Fees: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Collecting Tuition Fees: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<CollectionTuitionFeesDTO> SelectCollectionTuitionFees(Context context, String month, String year) {
        List<CollectionTuitionFeesDTO> listCollection = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("COLLECTING_TUITION_FEES",
                    new String[]{"*"},  "STATUS = ?", new String[] {"0"}, null);
        } catch(SQLException e) {
            Log.d("Select Collection Tuition Fees: ", e.getMessage());
        }

        String idBill = "", idStudent = "", collectionDate = "", money = "";
        if (cursor.moveToFirst()) {
            do {

                int idBillIndex = cursor.getColumnIndex("ID_BILL");
                if (idBillIndex != -1) {
                    idBill = cursor.getString(idBillIndex);
                }
                int idStudentIndex = cursor.getColumnIndex("ID_TEACHING");
                if (idStudentIndex!= -1) {
                    idStudent = cursor.getString(idStudentIndex);
                }
                int collectionDateIndex = cursor.getColumnIndex("COLLECTION_DATE");
                if (collectionDateIndex != -1) {
                    collectionDate = cursor.getString(collectionDateIndex);
                }
                int moneyIndex = cursor.getColumnIndex("TOTAL_MONEY");
                if (moneyIndex != -1) {
                    money = cursor.getString(moneyIndex);
                }

                String[] yearStringArr = collectionDate.split(" ");
                String yearString = yearStringArr[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
                LocalDate date = LocalDate.parse(yearString, formatter);
                if (date.getMonth().getValue() == Integer.parseInt(month)
                        && date.getYear() == Integer.parseInt(year)) {
                    listCollection.add(new CollectionTuitionFeesDTO(idBill, idStudent, collectionDate, money));
                }

            } while (cursor.moveToNext());
        }

        return listCollection;
    }

    public List<CollectionTuitionFeesDTO> SelectCollectionTuitionFeesToGetList(Context context, String whereClause,
                                                                               String[] whereArgs) {
        List<CollectionTuitionFeesDTO> listCollection = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("COLLECTING_TUITION_FEES",
                    new String[]{"*"},  whereClause, whereArgs, null);
        } catch(SQLException e) {
            Log.d("Select Collection Tuition Fees: ", e.getMessage());
        }

        String idBill = "", idStudent = "", collectionDate = "", money = "";
        if (cursor.moveToFirst()) {
            do {

                int idBillIndex = cursor.getColumnIndex("ID_BILL");
                if (idBillIndex != -1) {
                    idBill = cursor.getString(idBillIndex);
                }
                int idStudentIndex = cursor.getColumnIndex("ID_TEACHING");
                if (idStudentIndex!= -1) {
                    idStudent = cursor.getString(idStudentIndex);
                }
                int collectionDateIndex = cursor.getColumnIndex("COLLECTION_DATE");
                if (collectionDateIndex != -1) {
                    collectionDate = cursor.getString(collectionDateIndex);
                }
                int moneyIndex = cursor.getColumnIndex("TOTAL_MONEY");
                if (moneyIndex != -1) {
                    money = cursor.getString(moneyIndex);
                }

                listCollection.add(new CollectionTuitionFeesDTO(idBill, idStudent, collectionDate, money));

            } while (cursor.moveToNext());
        }

        return listCollection;
    }

    public Map<Integer, Integer> SelectCollectionTuitionFeesByYear(Context context, String year) {
        // List<CollectionTuitionFeesDTO> listCollection = new ArrayList<>();
        Cursor cursor = null;
        int yearNum = Integer.parseInt(year);
        Map<Integer, Integer> collectingTuition = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            collectingTuition.put(i + 1, 0);
        }

        try {
            cursor = DataProvider.getInstance(context).selectData("COLLECTING_TUITION_FEES",
                    new String[]{"*"}, "STATUS = ?", new String[] {"0"}, null);
        } catch(SQLException e) {
            Log.d("Select Collection Tuition Fees: ", e.getMessage());
        }

        String collectionDate = "", money = "";
        if (cursor.moveToFirst()) {
            do {

                int collectionDateIndex = cursor.getColumnIndex("COLLECTION_DATE");
                if (collectionDateIndex != -1) {
                    collectionDate = cursor.getString(collectionDateIndex);
                }
                int moneyIndex = cursor.getColumnIndex("TOTAL_MONEY");
                if (moneyIndex != -1) {
                    money = cursor.getString(moneyIndex);
                }

                String[] yearStringArr = collectionDate.split(" ");
                String yearString = yearStringArr[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
                LocalDate date = LocalDate.parse(yearString, formatter);
                int yearParse = date.getYear();
                if (yearParse == yearNum)  {
                   // listCollection.add(new CollectionTuitionFeesDTO(idBill, idStudent, collectionDate, money));
                    collectingTuition.put(date.getMonth().getValue(),
                            collectingTuition.get(date.getMonth().getValue()) + Integer.parseInt(money));
                }
                Log.d("Time get revenue: ", String.valueOf(yearParse));

            } while (cursor.moveToNext());
        }

        return collectingTuition;
    }


    public List<RevenueReportByYear>SelectCollectionTuitionFeesToSummarizeRevenueByYear(Context context, String year) {

        List<RevenueReportByYear> listRevenue = new ArrayList<>();
        int yearNum = Integer.parseInt(year);
        Map<Integer, List<String>> idClassByMonth = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            idClassByMonth.put(i + 1, new ArrayList<>());
        }

        List<ClassDTO> listClassByYear = ClassDAO.getInstance(context).selectClassByYear(context, yearNum);

        Log.d("Test data for list class by year: ", listClassByYear.toString());

        if (listClassByYear.size() != 0)  {
            for(int i = 0; i < listClassByYear.size(); i++)  {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateTime = LocalDate.parse(listClassByYear.get(i).getStartDate(),
                        formatter);
                switch (dateTime.getMonth().getValue()) {
                    case 1: {
                        List<String> idClassTemp = idClassByMonth.get(1);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(1, idClassTemp);
                        break;
                    }
                    case 2: {
                        List<String> idClassTemp = idClassByMonth.get(2);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(2, idClassTemp);
                        break;
                    }
                    case 3: {
                        List<String> idClassTemp = idClassByMonth.get(3);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(3, idClassTemp);
                        break;
                    }
                    case 4: {
                        List<String> idClassTemp = idClassByMonth.get(4);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(4, idClassTemp);
                        break;
                    }
                    case 5: {
                        List<String> idClassTemp = idClassByMonth.get(5);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(5, idClassTemp);
                        break;
                    }
                    case 6: {
                        List<String> idClassTemp = idClassByMonth.get(6);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(6, idClassTemp);
                        break;
                    }
                    case 7: {
                        List<String> idClassTemp = idClassByMonth.get(7);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(7, idClassTemp);
                        break;
                    }
                    case 8: {
                        List<String> idClassTemp = idClassByMonth.get(8);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(8, idClassTemp);
                        break;
                    }
                    case 9: {
                        List<String> idClassTemp = idClassByMonth.get(9);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(9, idClassTemp);
                        break;
                    }
                    case 10: {
                        List<String> idClassTemp = idClassByMonth.get(10);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(10, idClassTemp);
                        break;
                    }
                    case 11: {
                        List<String> idClassTemp = idClassByMonth.get(11);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(11, idClassTemp);
                        break;
                    }
                    case 12: {
                        List<String> idClassTemp = idClassByMonth.get(12);
                        idClassTemp.add(listClassByYear.get(i).getClassID());
                        idClassByMonth.put(12, idClassTemp);
                        break;
                    }
                }
            }
        }

        int index = 1;
        for (Map.Entry<Integer, List<String>> entry : idClassByMonth.entrySet()) {
           /* public RevenueReportByYear(int index, int month, String idClass, String nameClass,
                    String nameProgram, int tuition, int numberOfStudents, int revenue) {*/
            Integer key = entry.getKey();
            List<String> value = entry.getValue();
            Log.d("Key and value: ", String.valueOf(key) + "  " + value.toString());

            int indexTemp = 1;

            if (value.size() > 0)  {
                for (int i = 0; i < value.size(); i++)  {
                    String idClass = value.get(i);
                    List<ClassDTO> listClass = ClassDAO.getInstance(context).selectClass(context,
                            "ID_CLASS = ? AND STATUS = ?",
                            new String[] {idClass, "0"});

                    List<ProgramDTO> program = ProgramDAO.getInstance(context)
                            .SelectProgram(context, "ID_PROGRAM = ? AND STATUS = ?",
                                    new String[] {listClass.get(0).getIdProgram(), "0"});

                    List<TeachingDTO> listTeaching = TeachingDAO.getInstance(context).SelectTeaching(
                            context, "ID_CLASS = ? AND STATUS = ?",
                            new String[] {listClass.get(0).getClassID(), "0"});

                    RevenueReportByYear revenueElement = null;
                    if (listTeaching.size() != 0)  {
                        List<CollectionTuitionFeesDTO> listCollecting = CollectionTuitionFeesDAO.getInstance(context)
                                .SelectCollectionTuitionFeesToGetList(context,
                                        "ID_TEACHING = ? AND STATUS = ?",
                                        new String[] {listTeaching.get(0).getIdTeaching(), "0"});
                        Log.d("List CLass Error: ", listCollecting.toString());
                        if (listCollecting.size() != 0)  {
                            revenueElement = new RevenueReportByYear(index,
                                    key, idClass, listClass.get(0).getClassName(),
                                    program.get(0).getNameProgram(), Integer.parseInt(listCollecting.get(0).getMoney()),
                                    listTeaching.size(), listTeaching.size() * Integer.parseInt(listCollecting.get(0).getMoney()));
                        } else {
                            revenueElement = new RevenueReportByYear(index,
                                    key, idClass, listClass.get(0).getClassName(),
                                    program.get(0).getNameProgram(), 0,
                                    listTeaching.size(), listTeaching.size() * 0);
                        }
                    } else {
                        revenueElement = new RevenueReportByYear(index,
                                key, idClass, listClass.get(0).getClassName(),
                                program.get(0).getNameProgram(), 0, 0, 0);
                    }

                    // Log.d("Key and value: ", String.valueOf(index));
                    listRevenue.add(revenueElement);
                    Log.d("List revenue element: ", revenueElement.toString());
                    index = index + 1;
                }
            } else {
                RevenueReportByYear revenue = new RevenueReportByYear(index, key, "", "",
                        "", 0, 0, 0);
                Log.d("List revenue element: ", revenue.toString());
                index = index + 1;
                listRevenue.add(revenue);
            }
        }
        return listRevenue;
    }

    public int DeleteCollectingTuition(Context context, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("COLLECTING_TUITION_FEES", values,
                    whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete collecting tuition fees ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete collecting tuition fees Error: ", e.getMessage());
        }
        return rowEffect;
    }

}
