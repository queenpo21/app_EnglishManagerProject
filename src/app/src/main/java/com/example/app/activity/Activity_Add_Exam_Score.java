package com.example.app.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.model.ExamScoreDTO;
import com.example.app.model.ExaminationDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.NotificationDTO;
import com.example.app.model.OfficialStudentDTO;

import java.util.List;

public class Activity_Add_Exam_Score extends AppCompatActivity {
    EditText speak, write, listen, read, nameStudents;
    Button exitBtn, doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam_score);

        speak = findViewById(R.id.speaking_score);
        write = findViewById(R.id.writing_score);
        listen = findViewById(R.id.listening_score);
        read = findViewById(R.id.reading_score);
        String message = getIntent().getStringExtra("idStudent");

        Log.d("Exam score found: ", message );

        exitBtn = findViewById(R.id.exit_btn);
        doneBtn = findViewById(R.id.done_btn);
        nameStudents = findViewById(R.id.nameStudent);
        if (!message.equals("")) {
            LinearLayout layoutShowName = findViewById(R.id.layoutShowName);
            layoutShowName.setVisibility(View.GONE);

           List<ExamScoreDTO> listExamScore = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                           .SelectExamScore(Activity_Add_Exam_Score.this,
                                   "ID_EXAM_SCORE = ? AND STATUS = ?",
                                   new String[] {message, "0"});

           Log.d("Exam score found: ", listExamScore.toString());

           speak.setText(listExamScore.get(0).getSpeaking());
           write.setText(listExamScore.get(0).getWriting());
           listen.setText(listExamScore.get(0).getListening());
           read.setText(listExamScore.get(0).getReading());

           exitBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Exam_Score.this);
                   builder.setTitle("Xác nhận")
                           .setMessage("Bạn có chắc chắn muốn thoát?");
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                       }
                   });
                   builder.setNegativeButton("Hủy", null);
                   builder.show();
               }
           });
           doneBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (speak.getText().toString().equals("") || write.getText().toString().equals("")
                           || listen.getText().toString().equals("") || read.getText().toString().equals("")) {
                       Toast.makeText(Activity_Add_Exam_Score.this, "Hãy nhập đầy đủ tất cả thông tin", Toast.LENGTH_SHORT).show();
                   } else {

                        if (!isValidFloat(speak.getText().toString()) ||
                        !isValidFloat(write.getText().toString()) ||
                        !isValidFloat(listen.getText().toString()) ||
                        !isValidFloat(read.getText().toString())) {
                            Toast.makeText(Activity_Add_Exam_Score.this,
                                    "Hãy nhập đúng định dạng của điểm!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Exam_Score.this);
                            builder.setTitle("Xác nhận")
                                    .setMessage("Bạn có chắc chắn muốn thêm thông báo không?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    ExamScoreDTO examScore = new ExamScoreDTO(null, null,null,
                                            speak.getText().toString(), write.getText().toString(),
                                            listen.getText().toString(), read.getText().toString());
                                    try {
                                        int rowEffect = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                                                .UpdateExamScore(Activity_Add_Exam_Score.this,
                                                        examScore, "ID_EXAM_SCORE = ? AND STATUS = ?",
                                                        new String[] {message, "0"});
                                        if (rowEffect > 0) {
                                            Toast.makeText(Activity_Add_Exam_Score.this,
                                                    "Sửa điểm học viên thành công", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Activity_Add_Exam_Score.this,
                                                    "Sửa điểm học viên thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.d("Update exam score of student", "failed");
                                    }

                                }
                            });
                            builder.setNegativeButton("Hủy", null);
                            builder.show();
                        }
                   }
               }
           });
       } else {
           exitBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Add_Exam_Score.this);
                   builder.setTitle("Xác nhận")
                           .setMessage("Bạn có chắc chắn muốn thoát?");
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int which) {
                           finish();
                       }
                   });
                   builder.setNegativeButton("Hủy", null);
                   builder.show();
               }
           });

           doneBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   if (nameStudents.getText().toString().equals("")
                           || speak.getText().toString().equals("")
                   || write.getText().toString().equals("")
                           || read.getText().toString().equals("")) {
                       Toast.makeText(Activity_Add_Exam_Score.this,
                               "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                   } else {
                       String nameStudentText = nameStudents.getText().toString();
                       List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(Activity_Add_Exam_Score.this)
                               .SelectStudentVer2(Activity_Add_Exam_Score.this,
                                       "FULLNAME = ? AND STATUS = ?",
                                       new String[] {nameStudentText, "0"});
                       if (listStudent.size() == 0) {
                           Toast.makeText(Activity_Add_Exam_Score.this,
                                   "Học viên này không tồn tại trên hệ thống!",
                                   Toast.LENGTH_SHORT).show();
                       } else if (!isValidFloat(speak.getText().toString()) ||
                               !isValidFloat(write.getText().toString()) ||
                               !isValidFloat(listen.getText().toString()) ||
                               !isValidFloat(read.getText().toString())) {
                           Toast.makeText(Activity_Add_Exam_Score.this,
                                   "Hãy nhập đúng định dạng của điểm!",
                                   Toast.LENGTH_SHORT).show();
                           return;
                       }

                       List<ExaminationDTO> listExam = ExaminationDAO.getInstance(Activity_Add_Exam_Score.this)
                               .SelectExamination(Activity_Add_Exam_Score.this,
                                       "ID_CLASS = ? AND STATUS = ?",
                                       new String[] {List_Adapter.idClassClick, "0"});
                       if (listExam.size() > 0 && listStudent.size() > 0) {
                           List<ExamScoreDTO> listScore = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                                   .SelectExamScore(Activity_Add_Exam_Score.this,
                                           "ID_EXAM = ? AND ID_STUDENT = ? AND STATUS = ?",
                                           new String[] {listExam.get(0).getIdExam(),
                                                   listStudent.get(0).getIdStudent() , "0"});
                           if (listScore.size() > 0) {
                               Toast.makeText(Activity_Add_Exam_Score.this, "Điểm của học viên " +
                                       "này đã tồn tại trên hệ thống!", Toast.LENGTH_SHORT).show();
                               return;
                           } else {
                               ExamScoreDTO examStudent = new ExamScoreDTO(null, listExam.get(0).getIdExam(),
                                       listStudent.get(0).getIdStudent(),
                                       speak.getText().toString(), write.getText().toString(),
                                       listen.getText().toString(), read.getText().toString());
                               int rowEffect = ExamScoreDAO.getInstance(Activity_Add_Exam_Score.this)
                                       .InsertExamScore(Activity_Add_Exam_Score.this, examStudent);
                               if (rowEffect > 0)  {
                                   Toast.makeText(Activity_Add_Exam_Score.this,
                                           "Thêm điểm cho học viên thành công!", Toast.LENGTH_SHORT).show();
                               } else {
                                   Toast.makeText(Activity_Add_Exam_Score.this,
                                           "Thêm điểm cho học viên thất bại!", Toast.LENGTH_SHORT).show();
                               }
                           }
                       }
                   }

               }
           });

       }
    }

    public static boolean isValidFloat(String str) {
        try {
            Float.parseFloat(str);
            Log.d("Parse float: ", "successfully");
            return true;
        } catch (NumberFormatException e) {
            Log.d("Parse float: ", "failed");
            return false;
        }
    }

}