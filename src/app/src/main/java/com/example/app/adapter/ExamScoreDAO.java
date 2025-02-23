package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.OfficialStudentDTO;

import java.util.ArrayList;
import java.util.List;

public class ExamScoreDAO {

    public static ExamScoreDAO instance;
    private ExamScoreDAO(Context context) {}
    public static synchronized ExamScoreDAO getInstance(Context context) {
        if (instance == null) {
            instance = new ExamScoreDAO(context);
        }
        return instance;
    }

    public int InsertExamScore (Context context, ExamScoreDTO score) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        int maxId = DataProvider.getInstance(context).getMaxId("EXAM_SCORE", "ID_EXAM_SCORE");

        values.put("ID_EXAM_SCORE", "ESC" + String.valueOf(maxId + 1));
        values.put("ID_EXAM", score.getIdExam());
        values.put("ID_STUDENT", score.getIdStudent());
        values.put("SPEAKING_SCORE", Double.parseDouble(score.getSpeaking()));
        values.put("LISTENING_SCORE", Double.parseDouble(score.getListening()));
        values.put("READING_SCORE", Double.parseDouble(score.getReading()));
        values.put("WRITING_SCORE", Double.parseDouble(score.getWriting()));
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("EXAM_SCORE", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Exam Score ", "success");
            } else {
                Log.d("Insert Exam Score: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Exam Score: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateExamScore(Context context, ExamScoreDTO score, String whereClause, String[] whereArgs) {
        int rowEffect = -1;

        ContentValues values = new ContentValues();
        values.put("SPEAKING_SCORE", score.getSpeaking());
        values.put("LISTENING_SCORE", score.getListening());
        values.put("READING_SCORE", score.getReading());
        values.put("WRITING_SCORE", score.getWriting());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("EXAM_SCORE", values,
                    whereClause, whereArgs);
            if (rowEffect > 0 ) {
                Log.d("Update Exam Score ", "success");
            } else {
                Log.d("Update Exam Score: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Exam Score: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<ExamScoreDTO> SelectExamScore(Context context, String whereClause, String[] whereArgs) {
        List<ExamScoreDTO> listScore = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("EXAM_SCORE",
                    new String[]{"*"},  whereClause, whereArgs, null);
        } catch(SQLException e) {
            Log.d("Select Exam Score: ", e.getMessage());
        }

        //  private String idExamScore, idExam, idStudent, speaking, writing, listening, reading;
        String idExamScore = "", idExam = "", idStudent = "", speaking = "", writing = "",
                listening = "", reading = "";
        if (cursor.moveToFirst()) {
            do {
                int idExamScoreIndex = cursor.getColumnIndex("ID_EXAM_SCORE");
                if (idExamScoreIndex != -1) {
                    idExamScore = cursor.getString(idExamScoreIndex);
                }

                int idExamIndex = cursor.getColumnIndex("ID_EXAM");
                if (idExamIndex != -1) {
                    idExam = cursor.getString(idExamIndex);
                }

                int idStudentIndex = cursor.getColumnIndex("ID_STUDENT");
                if (idStudentIndex!= -1) {
                    idStudent = cursor.getString(idStudentIndex);
                }

                int speakingIndex = cursor.getColumnIndex("SPEAKING_SCORE");
                if (speakingIndex != -1) {
                    speaking = cursor.getString(speakingIndex);
                }
                int listeningIndex = cursor.getColumnIndex("LISTENING_SCORE");
                if (listeningIndex != -1) {
                    listening = cursor.getString(listeningIndex);
                }
                int writingIndex = cursor.getColumnIndex("WRITING_SCORE");
                if (writingIndex != -1) {
                    writing = cursor.getString(writingIndex);
                }
                int readingIndex = cursor.getColumnIndex("READING_SCORE");
                if (readingIndex != -1) {
                    reading = cursor.getString(readingIndex);
                }

                listScore.add(new ExamScoreDTO(idExamScore, idExam, idStudent,
                        speaking, writing, listening, reading));

            } while (cursor.moveToNext());
        }

        return listScore;
    }

    public List<ExamScoreDTO> SelectExamScoreById(Context context, String idUser, int type) {
        List<ExamScoreDTO> listExamScore = new ArrayList<>();

        if (type == 1) {
            listExamScore = ExamScoreDAO.getInstance(context).SelectExamScore(context,
                    "ID_STUDENT = ? AND STATUS = ?", new String[] {idUser, "0"} );
        } else if (type == 2 || type == 3)  {
            listExamScore = ExamScoreDAO.getInstance(context).SelectExamScore(context,
                    "STATUS = ?", new String[] {"0"});
        } else {
            return listExamScore;
        }

        return listExamScore;
    }

    public int DeleteExamScore(Context context, String whereClause, String[] whereArgs) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();
        values.put("STATUS", 1);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("EXAM_SCORE", values,
                    whereClause, whereArgs);
            if (rowEffect > 0) {
                Log.d("Delete exam score ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete exam score error: ", e.getMessage());
        }
        return rowEffect;
    }

}
