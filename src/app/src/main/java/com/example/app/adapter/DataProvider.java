package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class DataProvider extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ENGLISH_CENTER_MANAGEMENT.db";
    private static DataProvider instance;
    private static final int DATABASE_VERSION = 126;

    private DataProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DataProvider getInstance(Context context) {
        if (instance == null) {
            instance = new DataProvider(context);
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            db.execSQL("CREATE TABLE IF NOT EXISTS CERTIFICATE (" +
                    "ID_CERTIFICATE TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "CONTENT TEXT, " +
                    "STATUS INTEGER)");
            Log.d("CREATE CERTIFICATE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("EXCEPTION CREATE CERTIFICATE",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (" +
                    "ID_STAFF TEXT PRIMARY KEY, " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT, " +
                    "BIRTHDAY TEXT," +
                    "SALARY INTEGER, " +
                    "TYPE TEXT," +
                    "STATUS INTEGER)");
            Log.d("CREATE STAFF", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE STAFF",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TEACHERS (" +
                    "ID_TEACHER TEXT PRIMARY KEY , " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT, " +
                    "BIRTHDAY TEXT," +
                    "SALARY INTEGER, " +
                    "STATUS INTEGER)");
            Log.d("CREATE TEACHERS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE TEACHERS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CLASSROOM (" +
                    "ID_CLASSROOM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "STATUS INTEGER)");
            Log.d("CREATE EXAMINATION", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAMINATION",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS POTENTIAL_STUDENT (" +
                    "ID_STUDENT TEXT PRIMARY KEY, " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT," +
                    "LEVEL TEXT, " +
                    "NUMBER_OF_APPOINTMENTS INTEGER, " +
                    "STATUS INTEGER)");
            Log.d("CREATE POTENTIAL_STUDENTS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE POTENTIAL_STUDENTS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS OFFICIAL_STUDENT (" +
                    "ID_STUDENT TEXT PRIMARY KEY , " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT," +
                    "BIRTHDAY TEXT," +
                    "STATUS INTEGER)");
            Log.d("CREATE OFFICIAL_STUDENTS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE OFFICIAL_STUDENTS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS COLLECTING_TUITION_FEES (" +
                    "ID_BILL TEXT PRIMARY KEY , " +
                    "ID_STUDENT TEXT, " +
                    "COLLECTION_DATE TEXT, " +
                    "TOTAL_MONEY INTEGER, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT))");
            Log.d("CREATE COLLECTING_TUITION_FEES", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE COLLECTING_TUITION_FEES",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CLASS (" +
                    "ID_CLASS TEXT PRIMARY KEY , " +
                    "NAME TEXT, " +
                    "START_DATE TEXT, " +
                    "END_DATE TEXT, " +
                    "ID_PROGRAM TEXT, " +
                    "ID_TEACHER TEXT, " +
                    "ID_STAFF TEXT, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_PROGRAM) REFERENCES PROGRAM(ID_PROGRAM)," +
                    "FOREIGN KEY (ID_STAFF) REFERENCES STAFF(ID_STAFF)," +
                    "FOREIGN KEY (ID_TEACHER) REFERENCES TEACHER(ID_TEACHER))");
            Log.d("CREATE CLASS", "Database created successfully");
        } catch ( SQLException e) {
            Log.d("CREATE CLASS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS SCHEDULE (" +
                    "ID_SCHEDULE TEXT PRIMARY KEY," +
                    "DAY_OF_WEEK INTEGER, " +
                    "START_TIME TEXT," +
                    "END_TIME TEXT," +
                    "ID_CLASS TEXT, " +
                    "ID_CLASSROOM TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                    "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");
        } catch( Exception e) {
            Log.d("CREATE SCHEDULE ",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS EXAMINATION (" +
                    "ID_EXAM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "FORMAT TEXT, " +
                    "EXAM_DATE TEXT, " +
                    "ID_CLASS TEXT, " +
                    "ID_CLASSROOM TEXT, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                    "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");
            Log.d("CREATE EXAMINATION", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAMINATION",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS EXAM_SCORE (" +
                    "ID_EXAM_SCORE TEXT PRIMARY KEY, " +
                    "ID_EXAM TEXT , " +
                    "ID_STUDENT TEXT, " +
                    "SPEAKING_SCORE REAL, " +
                    "WRITING_SCORE REAL, " +
                    "LISTENING_SCORE REAL, " +
                    "READING_SCORE REAL," +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_EXAM) REFERENCES EXAMINATION(ID_EXAM)," +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT) )");
            Log.d("CREATE EXAM_SCORE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }
        //db.execSQL("DELETE FROM TEACHING");
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TEACHING (" +
                    "ID_TEACHING TEXT PRIMARY KEY , " +
                    "ID_STUDENT TEXT , " +
                    "ID_CLASS TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS))");
            Log.d("CREATE EXAM_SCORE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS PROGRAM (" +
                    "ID_PROGRAM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "INPUT_SCORE REAL, " +
                    "OUTPUT_SCORE REAL, " +
                    "CONTENT TEXT, " +
                    "SPEAKING_SCORE REAL, " +
                    "WRITING_SCORE REAL, " +
                    "LISTENING_SCORE REAL, " +
                    "READING_SCORE REAL, " +
                    "TUITION_FEES INTEGER, " +
                    "STUDY_PERIOD INTEGER, " + // Lộ trình học kéo dài 6 tháng, 12 tháng,....
                    "ID_CERTIFICATE TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_CERTIFICATE) REFERENCES CERTIFICATE(ID_CERTIFICATE))");
            Log.d("CREATE PROGRAM", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE PROGRAM",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (" +
                    "ID_ACCOUNT TEXT PRIMARY KEY, " +
                    "ID_USER TEXT," +
                    "USERNAME TEXT, " +
                    "PASSWORD TEXT," +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_USER) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                    "FOREIGN KEY (ID_USER) REFERENCES TEACHERS(ID_TEACHER)," +
                    "FOREIGN KEY (ID_USER) REFERENCES STAFF(ID_STAFF) )");
            Log.d("CREATE ACCOUNT", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE ACCOUNT",  e.getMessage());
        }

/*        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS REPORT (" +
                    "ID_REPORT TEXT PRIMARY KEY, " +
                    "REPORT_TIME TEXT," +
                    "REVENUE INTEGER )");
            Log.d("CREATE REPORT", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE REPORT",  e.getMessage());
        }*/

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATION (" +
                    "ID_NOTIFICATION TEXT PRIMARY KEY, " +
                    "ID_ACCOUNT TEXT," +
                    "TITLE TEXT," +
                    "CONTENT TEXT," +
                    "STATUS TEXT," +
                    " FOREIGN KEY (ID_ACCOUNT) REFERENCES ACCOUNT(ID_ACCOUNT))");
            Log.d("CREATE NOTIFICATION: ", "Success");
        } catch (Exception e ) {
            Log.d("CREATE NOTIFICATION: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DELETE FROM STAFF");
        db.execSQL("DELETE FROM OFFICIAL_STUDENT");
        db.execSQL("DELETE FROM POTENTIAL_STUDENT");
        db.execSQL("DELETE FROM CLASS");
        db.execSQL("DELETE FROM CLASSROOM");
       db.execSQL("DELETE FROM COLLECTING_TUITION_FEES");
        db.execSQL("DELETE FROM SCHEDULE");
        db.execSQL("DELETE FROM EXAMINATION");
        db.execSQL("DELETE FROM EXAM_SCORE");
        db.execSQL("DELETE FROM ACCOUNT");
        db.execSQL("DELETE FROM CERTIFICATE");
        db.execSQL("DELETE FROM TEACHERS");
        db.execSQL("DELETE FROM TEACHING");
        db.execSQL("DELETE FROM NOTIFICATION");
        db.execSQL("DELETE FROM PROGRAM");

        try {

            db.execSQL("CREATE TABLE IF NOT EXISTS CERTIFICATE (" +
                    "ID_CERTIFICATE TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "CONTENT TEXT, " +
                    "STATUS INTEGER)");
            Log.d("CREATE CERTIFICATE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("EXCEPTION CREATE CERTIFICATE",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS STAFF (" +
                    "ID_STAFF TEXT PRIMARY KEY, " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT, " +
                    "BIRTHDAY TEXT," +
                    "SALARY INTEGER, " +
                    "TYPE TEXT," +
                    "STATUS INTEGER)");
            Log.d("CREATE STAFF", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE STAFF",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TEACHERS (" +
                    "ID_TEACHER TEXT PRIMARY KEY , " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT, " +
                    "BIRTHDAY TEXT," +
                    "SALARY INTEGER, " +
                    "STATUS INTEGER)");
            Log.d("CREATE TEACHERS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE TEACHERS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CLASSROOM (" +
                    "ID_CLASSROOM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "STATUS INTEGER)");
            Log.d("CREATE EXAMINATION", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAMINATION",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS POTENTIAL_STUDENT (" +
                    "ID_STUDENT TEXT PRIMARY KEY, " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT," +
                    "LEVEL TEXT, " +
                    "NUMBER_OF_APPOINTMENTS INTEGER, " +
                    "STATUS INTEGER)");
            Log.d("CREATE POTENTIAL_STUDENTS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE POTENTIAL_STUDENTS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS OFFICIAL_STUDENT (" +
                    "ID_STUDENT TEXT PRIMARY KEY , " +
                    "FULLNAME TEXT, " +
                    "ADDRESS TEXT, " +
                    "PHONE_NUMBER TEXT, " +
                    "GENDER TEXT," +
                    "BIRTHDAY TEXT," +
                    "STATUS INTEGER)");
            Log.d("CREATE OFFICIAL_STUDENTS", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE OFFICIAL_STUDENTS",  e.getMessage());
        }
      //  db.execSQL("DROP TABLE COLLECTING_TUITION_FEES");
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS COLLECTING_TUITION_FEES (" +
                    "ID_BILL TEXT PRIMARY KEY , " +
                    "ID_TEACHING TEXT, " +
                    "COLLECTION_DATE TEXT, " +
                    "TOTAL_MONEY INTEGER, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_TEACHING) REFERENCES TEACHING(ID_TEACHING))");
            Log.d("CREATE COLLECTING_TUITION_FEES", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE COLLECTING_TUITION_FEES",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS CLASS (" +
                    "ID_CLASS TEXT PRIMARY KEY , " +
                    "NAME TEXT, " +
                    "START_DATE TEXT, " +
                    "END_DATE TEXT, " +
                    "ID_PROGRAM TEXT, " +
                    "ID_TEACHER TEXT, " +
                    "ID_STAFF TEXT, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_PROGRAM) REFERENCES PROGRAM(ID_PROGRAM)," +
                    "FOREIGN KEY (ID_STAFF) REFERENCES STAFF(ID_STAFF)," +
                    "FOREIGN KEY (ID_TEACHER) REFERENCES TEACHER(ID_TEACHER))");
            Log.d("CREATE CLASS", "Database created successfully");
        } catch ( SQLException e) {
            Log.d("CREATE CLASS",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS SCHEDULE (" +
                    "ID_SCHEDULE TEXT PRIMARY KEY," +
                    "DAY_OF_WEEK INTEGER, " +
                    "START_TIME TEXT," +
                    "END_TIME TEXT," +
                    "ID_CLASS TEXT, " +
                    "ID_CLASSROOM TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                    "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");
        } catch( Exception e) {
            Log.d("CREATE SCHEDULE ",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS EXAMINATION (" +
                    "ID_EXAM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "FORMAT TEXT, " +
                    "EXAM_DATE TEXT, " +
                    "ID_CLASS TEXT, " +
                    "ID_CLASSROOM TEXT, " +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS)," +
                    "FOREIGN KEY (ID_CLASSROOM) REFERENCES CLASSROOM(ID_CLASSROOM))");
            Log.d("CREATE EXAMINATION", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAMINATION",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS EXAM_SCORE (" +
                    "ID_EXAM_SCORE TEXT PRIMARY KEY, " +
                    "ID_EXAM TEXT , " +
                    "ID_STUDENT TEXT, " +
                    "SPEAKING_SCORE REAL, " +
                    "WRITING_SCORE REAL, " +
                    "LISTENING_SCORE REAL, " +
                    "READING_SCORE REAL," +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_EXAM) REFERENCES EXAMINATION(ID_EXAM)," +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT) )");
            Log.d("CREATE EXAM_SCORE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }
        //db.execSQL("DELETE FROM TEACHING");
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS TEACHING (" +
                    "ID_TEACHING TEXT PRIMARY KEY , " +
                    "ID_STUDENT TEXT , " +
                    "ID_CLASS TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_STUDENT) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                    "FOREIGN KEY (ID_CLASS) REFERENCES CLASS(ID_CLASS))");
            Log.d("CREATE EXAM_SCORE", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE EXAM_SCORE",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS PROGRAM (" +
                    "ID_PROGRAM TEXT PRIMARY KEY, " +
                    "NAME TEXT, " +
                    "INPUT_SCORE REAL, " +
                    "OUTPUT_SCORE REAL, " +
                    "CONTENT TEXT, " +
                    "SPEAKING_SCORE REAL, " +
                    "WRITING_SCORE REAL, " +
                    "LISTENING_SCORE REAL, " +
                    "READING_SCORE REAL, " +
                    "TUITION_FEES INTEGER, " +
                    "STUDY_PERIOD INTEGER, " + // Lộ trình học kéo dài 6 tháng, 12 tháng,....
                    "ID_CERTIFICATE TEXT, " +
                    "STATUS INTEGER, " +
                    "FOREIGN KEY (ID_CERTIFICATE) REFERENCES CERTIFICATE(ID_CERTIFICATE))");
            Log.d("CREATE PROGRAM", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE PROGRAM",  e.getMessage());
        }

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS ACCOUNT (" +
                    "ID_ACCOUNT TEXT PRIMARY KEY, " +
                    "ID_USER TEXT," +
                    "USERNAME TEXT, " +
                    "PASSWORD TEXT," +
                    "STATUS INTEGER," +
                    "FOREIGN KEY (ID_USER) REFERENCES OFFICAL_STUDENT(ID_STUDENT)," +
                    "FOREIGN KEY (ID_USER) REFERENCES TEACHERS(ID_TEACHER)," +
                    "FOREIGN KEY (ID_USER) REFERENCES STAFF(ID_STAFF) )");
            Log.d("CREATE ACCOUNT", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE ACCOUNT",  e.getMessage());
        }

/*        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS REPORT (" +
                    "ID_REPORT TEXT PRIMARY KEY, " +
                    "REPORT_TIME TEXT," +
                    "REVENUE INTEGER )");
            Log.d("CREATE REPORT", "Database created successfully");
        } catch ( Exception e) {
            Log.d("CREATE REPORT",  e.getMessage());
        }*/

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS NOTIFICATION (" +
                    "ID_NOTIFICATION TEXT PRIMARY KEY, " +
                    "ID_ACCOUNT TEXT," +
                    "TITLE TEXT," +
                    "CONTENT TEXT," +
                    "STATUS TEXT," +
                    " FOREIGN KEY (ID_ACCOUNT) REFERENCES ACCOUNT(ID_ACCOUNT))");
            Log.d("CREATE NOTIFICATION: ", "Success");
        } catch (Exception e ) {
            Log.d("CREATE NOTIFICATION: ", e.getMessage());
        }

    }

    public void recreateDatabase(Context context) {
        File dbFile = new File(getWritableDatabase().getPath());
        Log.d("Database path: ", dbFile.toString());
        if (!dbFile.exists()) {
            // Nếu file không tồn tại, khởi tạo lại cơ sở dữ liệu
            SQLiteDatabase db = getWritableDatabase();
            onCreate(db);
        }
    }

    public int insertData(String tableName, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        int newRowId = (int)db.insert(tableName, null, contentValues);
        db.close();

        return newRowId;
    }

    public int deleteData(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(tableName, whereClause, whereArgs);
        db.close();

        return rowsDeleted;
    }

    public int updateData(String tableName, ContentValues contentValues, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsUpdated = db.update(tableName, contentValues, whereClause, whereArgs);
        db.close();
        return rowsUpdated;
    }

    public Cursor selectData (String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, columns, whereClause, whereArgs, groupBy, null, null);
        return cursor;
    }

    public int getMaxId(String tableName, String column) {
        int maxId = 0;

        // Xác minh tableName và column để tránh SQL Injection
        if (tableName!= null &&!tableName.isEmpty() && column!= null &&!column.isEmpty()) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                String selection = "1"; // Điều kiện lọc mặc định để lấy tất cả các bản ghi
                String orderByClause = "ORDER BY abs(cast(substr(" + column + ", 4, length(" + column + ") - 3) as integer)) DESC";

                Cursor cursor = db.rawQuery("SELECT " + column + " FROM " + tableName + " WHERE " + selection + " " + orderByClause, null);

                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(column);
                    if (columnIndex!= -1) {
                        String maxIdString = cursor.getString(columnIndex);
                        String substring = maxIdString.substring(3); // Giả định rằng chuỗi bắt đầu bằng 'ID_' và số sau đó là ID thực tế
                        maxId = Integer.parseInt(substring);
                        Log.d("Get max id in DataProvider: ", String.valueOf(maxId));
                    }
                }
            } catch (NumberFormatException | SQLException e) {
                Log.d("Select max id: ", e.getMessage());
            }
        }else {
            return 0;
        }

        return maxId;
    }
}
