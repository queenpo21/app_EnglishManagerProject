package com.example.app.model;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.app.R;
import com.example.app.activity.Activity_Add_Account;
import com.example.app.activity.Activity_Add_Class;
import com.example.app.activity.Activity_Add_Exam_Score;
import com.example.app.activity.Activity_Add_Notification;
import com.example.app.activity.Activity_Add_Official_Student;
import com.example.app.activity.Activity_Add_Potential_Student;
import com.example.app.activity.Activity_Add_Program;
import com.example.app.activity.Activity_Add_Schedule;
import com.example.app.activity.Activity_Add_Staff;
import com.example.app.activity.Activity_Login;
import com.example.app.activity.Activity_Notifications_Second_Layer;
import com.example.app.activity.Activity_Notifications_ToolBars_Second_Layer;
import com.example.app.adapter.AccountDAO;
import com.example.app.adapter.CertificateDAO;
import com.example.app.adapter.ClassDAO;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.ExamScoreDAO;
import com.example.app.adapter.ExaminationDAO;
import com.example.app.adapter.NotificationDAO;
import com.example.app.adapter.OfficialStudentDAO;
import com.example.app.adapter.PotentialStudentDAO;
import com.example.app.adapter.ProgramDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.adapter.StaffDAO;
import com.example.app.adapter.TeacherDAO;
import com.example.app.adapter.TeachingDAO;

import java.util.ArrayList;
import java.util.List;

public class List_Adapter extends ArrayAdapter {
    private Context mContext;
    private ArrayList<Object> arrayDataList;
    int idLayout;
    public static String idClassClick;

    public List_Adapter(@NonNull Context context, int idLayout, ArrayList<Object> arrayDataList) {
        super(context, idLayout, arrayDataList);
        mContext = context;
        this.arrayDataList = arrayDataList;
        this.idLayout = idLayout;
    }

    public void setFilterList(ArrayList<Object> filterList) {
        this.arrayDataList = filterList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Object item = arrayDataList.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(idLayout, parent, false);
        if (item instanceof List_Information)
            List_Information_View(convertView, position);
        else if (item instanceof NotificationDTO)
            List_Notifications_View(convertView, position);
        else if (item instanceof ExamScoreDTO)
            List_Score_View(convertView, position);
        else if (item instanceof ProgramDTO)
            List_Education_Program_View(convertView, position);
        else if (item instanceof ClassDTO)
            List_Class_View(convertView, position);
        else if (item instanceof PotentialStudentDTO)
            PotentialStudentDTO_View(convertView, position);
        else if (item instanceof OfficialStudentDTO)
            OfficialStudentDTO_View(convertView, position);
        else if (item instanceof ScheduleDTO)
            Schedule_View(convertView, position);
        else if (item instanceof AccountDTO)
            Account_View(convertView, position);
        else if (item instanceof CertificateDTO)
            Certificate_View(convertView, position);
        else if (item instanceof ClassroomDTO)
            Classroom_View(convertView, position);
        else if (item instanceof StaffDTO)
            Staff_View(convertView, position);
        else if (item instanceof TeacherDTO)
            Teacher_View(convertView, position);
        else if (item instanceof ClassCollectingFees)
            Collecting_Fees_View(convertView, position);
        else if (item instanceof CollectionTuitionFeesDTO)
            Collecting_Tuition_Fees_View(convertView, position);
        else
            throw new IllegalArgumentException("Unknown data type: " + item.getClass().getName());

        return convertView;
    }

    private void List_Information_View(@Nullable View convertView, int position) {
        ImageView listImage = convertView.findViewById(R.id.listImage);
        TextView listName = convertView.findViewById(R.id.listName);

        List_Information listData = (List_Information) arrayDataList.get(position);

        listImage.setImageResource(listData.getImg());
        listName.setText(listData.getName());
    }

    private void List_Notifications_View(@Nullable View convertView, int position) {
        TextView title, poster, description;
        title = convertView.findViewById(R.id.title);
        poster = convertView.findViewById(R.id.poster);
        description = convertView.findViewById(R.id.content);

        NotificationDTO listNotifications = (NotificationDTO) arrayDataList.get(position);

        String idPoster = listNotifications.getPoster();
        List<AccountDTO> accountPost = AccountDAO.getInstance(mContext).selectAccountVer2(mContext,
                "ID_ACCOUNT = ? AND STATUS = ?", new String[]{idPoster, "0"});
        List<StaffDTO> staffPost = StaffDAO.getInstance(mContext).SelectStaffVer2(mContext,
                "ID_STAFF = ? AND STATUS = ?", new String[]{accountPost.get(0).getIdUser(), "0"});

        title.setText(listNotifications.getTitle().toString());
        poster.setText(staffPost.get(0).getFullName());
        description.setText(listNotifications.getDescription());

        if (convertView.findViewById(R.id.edit_notification) != null) {
            Button remove = convertView.findViewById(R.id.remove_notification);
            Button edit = convertView.findViewById(R.id.edit_notification);
            remove.setTag(position);
            edit.setTag(position);
            Log.d("Notification management", listNotifications.getPoster() + "    " + Activity_Login.idAccount);

            if (!listNotifications.getPoster().equals(Activity_Login.idAccount)) {
                remove.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
            }

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();

                            try {
                                int rowEffect = NotificationDAO.getInstance(mContext).DeleteNotification(
                                        mContext, new NotificationDTO(listNotifications.getIdNotification(),
                                                null, null, null),
                                        "ID_NOTIFICATION = ? AND STATUS = ?",
                                        new String[]{listNotifications.getIdNotification().toString(), "0"});
                                if (rowEffect > 0) {
                                    Toast.makeText(mContext, "Xóa thông báo thành công!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(mContext, "Xóa thông báo thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("Delete notification error: ", e.getMessage());
                            }

                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            Log.d("Id notification push", listNotifications.getIdNotification());

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Notification.class);
                    Log.d("Message send: ", listNotifications.getIdNotification());

                    intent.putExtra("idNotification", listNotifications.getIdNotification());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    private void List_Score_View(@Nullable View convertView, int position) {
        TextView speak, write, listen, read;
        ExamScoreDTO listScore = (ExamScoreDTO) arrayDataList.get(position);

        speak = convertView.findViewById(R.id.speaking_score);
        speak.setText(listScore.getSpeaking());
        write = convertView.findViewById(R.id.writing_score);
        write.setText(listScore.getWriting());
        listen = convertView.findViewById(R.id.listening_score);
        listen.setText(listScore.getListening());
        read = convertView.findViewById(R.id.reading_score);
        read.setText(listScore.getReading());

        if (convertView.findViewById(R.id.edit_score) != null) {
            //Giao diện nhân viên
            TextView studentName;
            //idStudent = convertView.findViewById(R.id.idStudent);
            //idStudent.setText(listScore.getIdStudent());
            studentName = convertView.findViewById(R.id.studentName);

            List<OfficialStudentDTO> listStudent = OfficialStudentDAO.getInstance(mContext)
                    .SelectStudentVer2(mContext, "ID_STUDENT = ? AND STATUS = ?",
                            new String[]{listScore.getIdStudent(), "0"});

            studentName.setText(listStudent.get(0).getFullName());
            Button editScore = convertView.findViewById(R.id.edit_score);
            editScore.setTag(position);
            editScore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Exam_Score.class);
                    intent.putExtra("idStudent", listScore.getIdExamScore());
                    Log.d("Put exam score: ", listScore.getIdExamScore());
                    mContext.startActivity(intent);
                }
            });
        } else {
            //Giao diện học viên
            TextView courseID;
            courseID = convertView.findViewById(R.id.courseID);

            List<ExaminationDTO> listExam = ExaminationDAO.getInstance(mContext).SelectExamination(
                    mContext, "ID_EXAM = ? AND STATUS = ?",
                    new String[] {listScore.getIdExam().toString(), "0"} );
            List<ClassDTO> listClassExam = ClassDAO.getInstance(mContext).selectClass(mContext,
                    "ID_CLASS = ? AND STATUS = ?",
                    new String[] {listExam.get(0).getIdClass().toString(), "0"});

            courseID.setText(listClassExam.get(0).getClassID() + " - " + listClassExam.get(0).getClassName());
        }


/*        courseID.setText(listScore.courseID);
        speak.setText(listScore.speak);
        write.setText(listScore.write);
        listen.setText(listScore.listen);
        read.setText(listScore.read);*/
    }

    private void List_Education_Program_View(@Nullable View convertView, int position) {
        TextView idProgram, programName, inputScore, outputScore, content, speak, write, read, listen, tuitionFees, certificate, studyPeriod;
        idProgram = convertView.findViewById(R.id.idProgram);
        programName = convertView.findViewById(R.id.program_name);
        inputScore = convertView.findViewById(R.id.inputScore);
        outputScore = convertView.findViewById(R.id.outputScore);
        content = convertView.findViewById(R.id.content);
        speak = convertView.findViewById(R.id.speaking);
        write = convertView.findViewById(R.id.writing);
        listen = convertView.findViewById(R.id.listening);
        read = convertView.findViewById(R.id.reading);
        studyPeriod = convertView.findViewById(R.id.studyPeriod);
        tuitionFees = convertView.findViewById(R.id.tuitionFees);
        certificate = convertView.findViewById(R.id.certificate);

        ProgramDTO listEducationProgram = (ProgramDTO) arrayDataList.get(position);

        idProgram.setText(listEducationProgram.getIdProgram());
        programName.setText(listEducationProgram.getNameProgram());
        inputScore.setText(listEducationProgram.getInputScore());
        outputScore.setText(listEducationProgram.getOutputScore());
        content.setText(listEducationProgram.getContent());
        speak.setText(listEducationProgram.getSpeakingScore());
        write.setText(listEducationProgram.getWritingScore());
        listen.setText(listEducationProgram.getListeningScore());
        read.setText(listEducationProgram.getReadingScore());
        studyPeriod.setText(listEducationProgram.getStudy_period());
        tuitionFees.setText(String.valueOf(listEducationProgram.getTuitionFees()));

        String idCertificate = listEducationProgram.getIdCertificate();
        List<CertificateDTO> listCertificate = CertificateDAO.getInstance(mContext).SelectCertificate(mContext,
                "ID_CERTIFICATE = ? AND STATUS = ?", new String[]{idCertificate, "0"});

        certificate.setText(listCertificate.get(0).getName());

        Button editProgram, removeProgram;
        if (convertView.findViewById(R.id.edit_program) != null) {
            removeProgram = convertView.findViewById(R.id.remove_program);
            removeProgram.setTag(position);
            removeProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            editProgram = convertView.findViewById(R.id.edit_program);
            editProgram.setTag(position);
            editProgram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editProgram = new Intent(getContext(), Activity_Add_Program.class);
                    editProgram.putExtra("idProgram", idProgram.getText());
                    mContext.startActivity(editProgram);
                }
            });
        }
    }

    private void List_Class_View(@Nullable View convertView, int position) {
        TextView classID, className, startDate, endDate, programID, teacherName;
        ClassDTO listClass = (ClassDTO) arrayDataList.get(position);

        classID = convertView.findViewById(R.id.classID);
        className = convertView.findViewById(R.id.class_name);
        startDate = convertView.findViewById(R.id.start_date);
        endDate = convertView.findViewById(R.id.end_date);
        programID = convertView.findViewById(R.id.programID);
        teacherName = convertView.findViewById(R.id.teacher_name);

        String idTeacher = listClass.getIdTeacher();
        String idProgram = listClass.getIdProgram();
        String idStaff = listClass.getIdStaff();
        List<TeacherDTO> teacher = TeacherDAO.getInstance(mContext).SelectTeacher(mContext,
                "STATUS = ? AND ID_TEACHER = ?", new String[]{"0", idTeacher});
        List<ProgramDTO> program = ProgramDAO.getInstance(mContext).SelectProgram(mContext,
                "ID_PROGRAM = ? AND STATUS = ?", new String[]{idProgram, "0"});
        List<StaffDTO> staff = StaffDAO.getInstance(mContext).SelectStaffVer2(mContext,
                "ID_STAFF = ? AND STATUS = ?", new String[]{idStaff, "0"});
        Log.d("Information show: ", teacher.toString() +  " \n" + program.toString() + "\n" + staff.toString());
        classID.setText(listClass.getClassID());
        className.setText(listClass.getClassName());
        startDate.setText(listClass.getStartDate());
        endDate.setText(listClass.getEndDate());
        if (program.size() > 0) {
            programID.setText(program.get(0).getNameProgram().toString());
        } else {
            programID.setText("");
        }
        if (teacher.size() > 0) {
            teacherName.setText(teacher.get(0).getFullName().toString());
        } else {
            teacherName.setText("");
        }

        if (convertView.findViewById(R.id.edit_class) != null) {

            //Tính năng thêm/xóa lớp của nhân viên ghi danh
            Button editClass = convertView.findViewById(R.id.edit_class);
            editClass.setTag(position);
            editClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Class.class);
                    intent.putExtra("classID", listClass.getClassID());
                    mContext.startActivity(intent);
                }
            });
            Button removeClass = convertView.findViewById(R.id.remove_class);
            removeClass.setTag(position);
            removeClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);

                            try {
                                int rowEffect = ClassDAO.getInstance(mContext).DeleteClass(mContext,
                                        listClass, "ID_CLASS = ? AND STATUS = ?",
                                        new String[]{listClass.getClassID().toString(), "0"});
                                if (rowEffect > 0) {
                                    Toast.makeText(mContext, "Xóa lớp học thành công!", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                Log.d("Delete class error: ", e.getMessage());
                            }

                            List<TeachingDTO> listTeaching = TeachingDAO.getInstance(mContext)
                                    .SelectTeaching(mContext, "ID_CLASS = ? AND STATUS = ?",
                                            new String[] {listClass.getClassID().toString(), "0"});

                            if (listTeaching.size() > 0) {
                                for (int i = 0; i < listTeaching.size(); i++) {
                                    String idTeachingToDeleteCollecting = listTeaching.get(0).getIdTeaching();
                                    int rowEffect = CollectionTuitionFeesDAO.getInstance(mContext)
                                            .DeleteCollectingTuition(mContext, "ID_TEACHING = ? AND STATUS = ?",
                                                    new String[] {idTeachingToDeleteCollecting, "0"});
                                    if (rowEffect > 0) {
                                        Log.d("Delete collecting tuition fees related to class ", "successful");
                                    } else {
                                        Log.d("Delete collecting tuition fees related to class ", "failed");
                                    }
                                }
                            }

                            try {
                                int rowEffect = TeachingDAO.getInstance(mContext)
                                        .DeleteTeachingByIdClass(mContext,
                                                "ID_CLASS = ? AND STATUS = ?",
                                                new String[] {listClass.getClassID().toString(), "0"});
                                if (rowEffect > 0) {
                                    Log.d("Delete teaching related to class ", "successful");
                                } else {
                                    Log.d("Delete teaching related to class ", "failed");
                                }

                            } catch (Exception e) {
                                Log.d("Delete teaching error: ", e.getMessage());
                            }

                            List<ExaminationDTO> listExamination = ExaminationDAO.getInstance(mContext)
                                    .SelectExamination(mContext, "ID_CLASS = ? AND STATUS = ?",
                                            new String[] {listClass.getClassID().toString(), "0"});
                            String idExamination = "";
                            if (listExamination.size() > 0) {
                                idExamination = listExamination.get(0).getIdExam();
                            }

                            try {
                                int rowEffect = ExaminationDAO.getInstance(mContext).DeleteExamination(
                                        mContext, "ID_CLASS = ? AND STATUS = ?",
                                        new String[] {listClass.getClassID().toString(), "0"});
                                if (rowEffect > 0) {
                                    Log.d("Delete examination related to class ", "successful");
                                } else {
                                    Log.d("Delete examination related to class ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Delete examination related to class error", e.getMessage());
                            }

                            try {
                                int rowEffect = ExamScoreDAO.getInstance(mContext).DeleteExamScore(mContext,
                                        "ID_EXAM = ? AND STATUS = ?", new String[] {idExamination, "0"});
                                if (rowEffect > 0) {
                                    Log.d("Delete exam score related to class ", "successful");
                                } else {
                                    Log.d("Delete exam score related to class ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Delete exam score related to class error", e.getMessage());
                            }

                            try {
                                int rowEffect = ScheduleDAO.getInstance(mContext).DeleteScheduleByIdClass(mContext,
                                        "ID_CLASS = ? AND STATUS = ?",
                                        new String[] {listClass.getClassID().toString(), "0"});
                                if (rowEffect > 0) {
                                    Log.d("Delete schedule related to class ", "successful");
                                } else {
                                    Log.d("Delete schedule related to class ", "failed");
                                }
                            } catch (Exception e) {
                                Log.d("Delete schedule related to class error", e.getMessage());
                            }

                            notifyDataSetChanged();
                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }

        if (convertView.findViewById(R.id.detailBtn) != null) {

            TextView staffID = convertView.findViewById(R.id.staffID);
            if (staff.size() > 0) {
                staffID.setText(staff.get(0).getFullName().toString());
            } else {
                staffID.setText("");
            }

            Button detailBtn = convertView.findViewById(R.id.detailBtn);
            detailBtn.setTag(position);
            detailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (convertView.findViewById(R.id.edit_class) != null) {
                        //Nhân viên ghi danh
                        intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                        intent.putExtra("classID", listClass.getClassID());
                        intent.putExtra("classIDtoViewSchedule", "");
                        intent.putExtra("idClass", "");
                        idClassClick = listClass.getClassID();
                        Log.d("ID class to show list student in class", idClassClick);
                    } else {
                        //Nhân viên học vụ
                        intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                        intent.putExtra("classID", "");
                        intent.putExtra("classIDtoViewSchedule", "");
                        intent.putExtra("idClass", listClass.getClassID());
                        idClassClick = listClass.getClassID();
                    }
                    mContext.startActivity(intent);
                }
            });

            if (convertView.findViewById(R.id.edit_class) == null) {
                //Nhân viên học vụ thêm lịch học
                Button viewSchedule = convertView.findViewById(R.id.viewSchedule);
                viewSchedule.setTag(position);
                viewSchedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                        Log.d("Id class put to view schedule", listClass.getClassID());
                        intent.putExtra("idClass", "");
                        intent.putExtra("classIDtoViewSchedule", listClass.getClassID());
                        intent.putExtra("classID", "");
                        idClassClick = listClass.getClassID();
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    /*private void List_Class_Manage_View (@Nullable View convertView, int position) {
        TextView classID, className, startDate, endDate, programID, teacherName, staffID;
        ClassDTO_Manage listClass = (ClassDTO_Manage) arrayDataList.get(position);

        classID = convertView.findViewById(R.id.classID);
        className = convertView.findViewById(R.id.class_name);
        startDate = convertView.findViewById(R.id.start_date);
        endDate = convertView.findViewById(R.id.end_date);
        programID = convertView.findViewById(R.id.programID);
        teacherName = convertView.findViewById(R.id.teacher_name);
        staffID = convertView.findViewById(R.id.staffID);

        classID.setText(listClass.getClassID());
        className.setText(listClass.getClassName());
        startDate.setText(listClass.getStartDate());
        endDate.setText(listClass.getEndDate());
        programID.setText(listClass.getIdProgram());
        teacherName.setText(listClass.getIdTeacher());
        teacherName.setText(listClass.getIdTeacher());
        staffID.setText(listClass.getIdStaff());

        Button removeClass, editClass;
        removeClass = convertView.findViewById(R.id.remove_class);
        removeClass.setTag(position);
        removeClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editClass = convertView.findViewById(R.id.edit_class);
        editClass.setTag(position);
        editClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editClass = new Intent(getContext(), Activity_Add_Class.class);
                editClass.putExtra("classID", classID.getText());
                mContext.startActivity(editClass);
            }
        });

        Button detailBtn = convertView.findViewById(R.id.detailBtn);
        detailBtn.setTag(position);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Notifications_ToolBars_Second_Layer.class);
                intent.putExtra("classID", classID.getText());
                intent.putExtra("idCertificate", "");
                mContext.startActivity(intent);
            }
        });
    }*/
    private void PotentialStudentDTO_View(@Nullable View convertView, int position) {
        TextView studentName, phoneNumber, gender, address, level, appointmentNumber;
        studentName = convertView.findViewById(R.id.student_name);
        phoneNumber = convertView.findViewById(R.id.phone_number);
        gender = convertView.findViewById(R.id.gender);
        level = convertView.findViewById(R.id.level);
        address = convertView.findViewById(R.id.address);
        appointmentNumber = convertView.findViewById(R.id.appointment_number);

        PotentialStudentDTO listTalentedStudent = (PotentialStudentDTO) arrayDataList.get(position);

        studentName.setText(listTalentedStudent.getStudentName());
        phoneNumber.setText(listTalentedStudent.getPhoneNumber());
        gender.setText(listTalentedStudent.getGender());
        level.setText(listTalentedStudent.getLevel());
        address.setText(listTalentedStudent.getAddress());
        appointmentNumber.setText(listTalentedStudent.getAppointmentNumber());

        Button removePotentialStudent, editPotentialStudent;

        removePotentialStudent = convertView.findViewById(R.id.remove_student);
        removePotentialStudent.setTag(position);
        removePotentialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);

                        try {
                            int rowEffect = PotentialStudentDAO.getInstance(mContext).deletePotentialStudent(
                                    mContext, listTalentedStudent, "ID_STUDENT = ?",
                                    new String[]{listTalentedStudent.getStudentID()});
                            Log.d("Delete Potential Student Error: ", String.valueOf(rowEffect));
                        } catch (Exception e) {
                            Log.d("Delete Potential Student Error: ", e.getMessage());
                        }

                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editPotentialStudent = convertView.findViewById(R.id.edit_student);
        editPotentialStudent.setTag(position);
        editPotentialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPotential = new Intent(getContext(), Activity_Add_Potential_Student.class);
                addPotential.putExtra("studentID", listTalentedStudent.getStudentID());
                mContext.startActivity(addPotential);
            }
        });
    }

    private void OfficialStudentDTO_View(@Nullable View convertView, int position) {
        TextView studentName, phoneNumber, gender, address, birthday;
        studentName = convertView.findViewById(R.id.student_name);
        phoneNumber = convertView.findViewById(R.id.phone_number);
        gender = convertView.findViewById(R.id.gender);
        address = convertView.findViewById(R.id.address);
        birthday = convertView.findViewById(R.id.birthday);

        OfficialStudentDTO officialStudentDTO = (OfficialStudentDTO) arrayDataList.get(position);

        studentName.setText(officialStudentDTO.getFullName());
        phoneNumber.setText(officialStudentDTO.getPhoneNumber());
        gender.setText(officialStudentDTO.getGender());
        address.setText(officialStudentDTO.getAddress());
        birthday.setText(officialStudentDTO.getBirthday());

        Button removeOfficialStudent, editOfficialStudent;

        removeOfficialStudent = convertView.findViewById(R.id.remove_student);
        removeOfficialStudent.setTag(position);


        removeOfficialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);

                        try {
                            List<TeachingDTO> teaching = TeachingDAO.getInstance(mContext).SelectTeaching(
                                    mContext, "ID_STUDENT = ? AND ID_CLASS = ? AND STATUS = ?",
                                    new String[]{officialStudentDTO.getIdStudent(), idClassClick, "0"});

                            int rowEffect = TeachingDAO.getInstance(mContext).DeleteTeaching(
                                    mContext, teaching.get(0), "ID_TEACHING = ?",
                                    new String[]{teaching.get(0).getIdClass().toString()});
                            if (rowEffect > 0) {
                                Toast.makeText(mContext, "Xóa học viên trong lớp thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Xóa học viên trong lớp thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Delete teaching error: ", e.getMessage());
                        }

                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        editOfficialStudent = convertView.findViewById(R.id.edit_student);
        editOfficialStudent.setTag(position);
        editOfficialStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPotential = new Intent(getContext(), Activity_Add_Official_Student.class);
                addPotential.putExtra("studentID", officialStudentDTO.getIdStudent().toString());
                addPotential.putExtra("classID", idClassClick.toString());
                mContext.startActivity(addPotential);
            }
        });
    }

    private void Schedule_View(@Nullable View convertView, int position) {
        TextView dayOfWeek, idClass, idClassroom, time;
        dayOfWeek = convertView.findViewById(R.id.day_of_week);
        idClass = convertView.findViewById(R.id.idClass);
        idClassroom = convertView.findViewById(R.id.idClassroom);
        time = convertView.findViewById(R.id.time);

        ScheduleDTO listSchedule = (ScheduleDTO) arrayDataList.get(position);

        if (Integer.parseInt(listSchedule.getDayOfWeek().toString()) > 7) {
            dayOfWeek.setText("Chủ nhật");
        } else {
            dayOfWeek.setText("Thứ " + listSchedule.getDayOfWeek());
        }


        time.setText(listSchedule.getStartTime() + "h00 - " +listSchedule.getEndTime() + "h00" );

        List<ClassDTO> listClass = ClassDAO.getInstance(getContext()).selectClass(getContext(),
                "ID_CLASS= ?", new String[]{listSchedule.getIdClass()});

        /*List<TeacherDTO> listTeacher = TeacherDAO.getInstance(mContext)
                .SelectTeacher(mContext, "ID_TEACHER = ? AND STATUS = ?",
                        new String[] {listClass.getIdTeacher})*/

        if (convertView.findViewById(R.id.teacherName) != null) {
            TextView teacherName = convertView.findViewById(R.id.teacherName);
            teacherName.setText("1");
        }
        List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(getContext()).SelectClassroom(
                getContext(), "ID_CLASSROOM = ?", new String[]{listSchedule.idClassroom});

        idClass.setText(listClass.get(0).getClassName());
        idClassroom.setText(listClassroom.get(0).getName());

        if (convertView.findViewById(R.id.edit_schedule) != null) {
            Button editSchedule, removeSchedule;
            editSchedule = convertView.findViewById(R.id.edit_schedule);
            editSchedule.setTag(position);
            editSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_Add_Schedule.class);
                    intent.putExtra("idSchedule", listSchedule.getIdSchedule());

                    Log.d("ID schedule put: ", listSchedule.getIdSchedule());

                    mContext.startActivity(intent);
                }
            });

            removeSchedule = convertView.findViewById(R.id.remove_schedule);
            removeSchedule.setTag(position);
            removeSchedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                    // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int position = (int) v.getTag();
                            arrayDataList.remove(position);
                            notifyDataSetChanged();

                            try {
                                ScheduleDTO scheduleDelete = new ScheduleDTO(listSchedule.getIdSchedule(),
                                        null, null, null, null, null);
                                try {
                                    int rowEffect = ScheduleDAO.getInstance(mContext).DeleteSchedule(
                                            mContext, scheduleDelete,
                                            "ID_SCHEDULE = ? AND STATUS = ?",
                                            new String[]{scheduleDelete.getIdSchedule(), "0"});
                                    if (rowEffect > 0) {
                                        Toast.makeText(mContext, "Xóa lịch học thành công",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "Xóa lịch học thất bại",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("Delete schedule error: ", e.getMessage());
                                }
                            } catch (Exception e) {
                                Log.d("Delete schedule error: ", e.getMessage());
                            }

                        }
                    });

                    // Nút "Hủy": Không làm gì cả, đóng dialog
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    // Tạo và hiển thị AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        }
    }

    private void Certificate_View(@Nullable View convertView, int position) {
        TextView idCertificate, name, content;
        CertificateDTO listCertificate = (CertificateDTO) arrayDataList.get(position);

      //  idCertificate = convertView.findViewById(R.id.idCertificate);
        name = convertView.findViewById(R.id.name);
        content = convertView.findViewById(R.id.content);

        //  idCertificate.setText(listCertificate.getIdCertificate());
        name.setText(listCertificate.getName());
        content.setText(listCertificate.getContent());

        Button detailBtn = convertView.findViewById(R.id.detailBtn);
        detailBtn.setTag(position);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Notifications_Second_Layer.class);
                intent.putExtra("idCertificate", listCertificate.getIdCertificate().toString());
                Log.d("ID certificate found", listCertificate.getIdCertificate());
                intent.putExtra("classID", "");
                mContext.startActivity(intent);
            }
        });

    }

    private void Account_View(@Nullable View convertView, int position) {
        TextView idAccount, idUser, username, password;
        AccountDTO listAccount = (AccountDTO) arrayDataList.get(position);

        // idAccount = convertView.findViewById(R.id.idAccount);
        idUser = convertView.findViewById(R.id.idUser);
        username = convertView.findViewById(R.id.username);
        password = convertView.findViewById(R.id.password);

        // idAccount.setText(listAccount.getIdAccount());
        idUser.setText(listAccount.getIdUser());
        username.setText(listAccount.getUserName());
        password.setText(listAccount.getPassWord());

        Button remove = convertView.findViewById(R.id.remove_account);
        remove.setTag(position);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        AccountDTO account = new AccountDTO(listAccount.getIdAccount(),
                                null, null, null);
                        try {
                            int rowEffect = AccountDAO.getInstance(mContext).DeleteAccount(mContext, account,
                                    "ID_ACCOUNT = ?", new String[]{listAccount.getIdAccount()});
                            if (rowEffect > 0) {
                                Toast.makeText(mContext, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Delete Account Error: ", e.getMessage());
                        }

                        notifyDataSetChanged();
                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button edit = convertView.findViewById(R.id.edit_account);
        edit.setTag(position);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Account.class);
                intent.putExtra("idAccount", listAccount.getIdAccount().toString());
                mContext.startActivity(intent);
            }
        });
    }

    private void Classroom_View(@Nullable View convertView, int position) {
        ClassroomDTO listClassromm = (ClassroomDTO) arrayDataList.get(position);
        TextView idClassroom, nameClassroom, name;
        //idClassroom = convertView.findViewById(R.id.idClassroom);
        nameClassroom = convertView.findViewById(R.id.nameRoom);
        name = convertView.findViewById(R.id.name);
        //idClassroom.setText(listClassromm.getIdRoom());
        nameClassroom.setText(listClassromm.getName());


        LinearLayout layout;
        layout = convertView.findViewById(R.id.linear_layout);
        if (true) {
            name.setText("Hê hê");
            layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.red_border));
        } else
            layout.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_border));
    }

    private void Staff_View(@Nullable View convertView, int position) {
        StaffDTO listStaff = (StaffDTO) arrayDataList.get(position);
        TextView fullName, address, phoneNumber, gender, birthday, type;
       // idStaff = convertView.findViewById(R.id.idStaff);
       // idStaff.setText(listStaff.getIdStaff());

        fullName = convertView.findViewById(R.id.fullName);
        fullName.setText(listStaff.getIdStaff() + " - " + listStaff.getFullName());

        address = convertView.findViewById(R.id.address);
        address.setText(listStaff.getAddress());

        phoneNumber = convertView.findViewById(R.id.phoneNumber);
        phoneNumber.setText(listStaff.getPhoneNumber());

        gender = convertView.findViewById(R.id.gender);
        gender.setText(listStaff.getGender());

        birthday = convertView.findViewById(R.id.birthday);
        birthday.setText(listStaff.getBirthday());

        type = convertView.findViewById(R.id.type);
        if (Integer.parseInt(listStaff.getType()) == 1) {
            type.setText("Nhân viên ghi danh");
        } else if (Integer.parseInt(listStaff.getType()) == 1) {
            type.setText("Nhân viên học vụ");
        } else {
            type.setText("Quản lý");
        }

        Button removeStaff = convertView.findViewById(R.id.remove_staff);
        removeStaff.setTag(position);
        removeStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();

                        StaffDTO staff = new StaffDTO(listStaff.getIdStaff().toString(),
                                null, null,
                                null, null, null, 1, null, 0);
                        try {
                            int rowEffect = StaffDAO.getInstance(mContext).DeleteStaff(mContext,
                                    staff, "ID_STAFF = ? AND STATUS = ?",
                                    new String[]{listStaff.getIdStaff().toString(), "0"});
                            if (rowEffect > 0) {
                                Toast.makeText(mContext, "Xóa nhân viên thành công!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Xóa nhân viên thất bại!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Delete staff error:", e.getMessage());
                        }

                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button editStaff = convertView.findViewById(R.id.edit_staff);
        editStaff.setTag(position);
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Staff.class);
                intent.putExtra("idStaff", listStaff.getIdStaff());
                intent.putExtra("idTeacher", "");
                mContext.startActivity(intent);
            }
        });
    }

    private void Teacher_View(@Nullable View convertView, int position) {
        TeacherDTO listStaff = (TeacherDTO) arrayDataList.get(position);
        TextView  fullName, address, phoneNumber, gender, birthday, type;

        fullName = convertView.findViewById(R.id.fullName);
        fullName.setText( listStaff.getIdTeacher() + " - " + listStaff.getFullName());

        address = convertView.findViewById(R.id.address);
        address.setText(listStaff.getAddress());

        phoneNumber = convertView.findViewById(R.id.phoneNumber);
        phoneNumber.setText(listStaff.getPhoneNumber());

        gender = convertView.findViewById(R.id.gender);
        gender.setText(listStaff.getGender());

        birthday = convertView.findViewById(R.id.birthday);
        birthday.setText(listStaff.getBirthday());

        type = convertView.findViewById(R.id.type);
        type.setText("Giáo viên");

        Button removeStaff = convertView.findViewById(R.id.remove_staff);
        removeStaff.setTag(position);
        removeStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                // Nút "Đồng ý": Thực hiện xóa và thông báo ListView
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = (int) v.getTag();
                        arrayDataList.remove(position);
                        notifyDataSetChanged();

                        TeacherDTO teacher = new TeacherDTO(listStaff.getIdTeacher(),
                                null, null, null, null,
                                null, 1);
                        try {
                            int rowEffect = TeacherDAO.getInstance(mContext).DeleteTeacher(mContext,
                                    teacher, "ID_TEACHER = ? AND STATUS = ?",
                                    new String[]{listStaff.getIdTeacher(), "0"});
                            if (rowEffect > 0) {
                                Toast.makeText(mContext, "Xóa giáo viên thành công!",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Xóa giáo viên thất bại!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("Delete teacher error: ", e.getMessage());
                        }

                    }
                });

                // Nút "Hủy": Không làm gì cả, đóng dialog
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        Button editStaff = convertView.findViewById(R.id.edit_staff);
        editStaff.setTag(position);
        editStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Add_Staff.class);
                intent.putExtra("idStaff", "");
                intent.putExtra("idTeacher", listStaff.getIdTeacher());
                mContext.startActivity(intent);
            }
        });
    }

    private void Collecting_Fees_View(@Nullable View convertView, int position) {
        ClassCollectingFees listClass = (ClassCollectingFees) arrayDataList.get(position);
        TextView className, numStudent, totalMoney;
        // idClass = convertView.findViewById(R.id.idClass);

        className = convertView.findViewById(R.id.studentId);
        numStudent = convertView.findViewById(R.id.numStudent);
        totalMoney = convertView.findViewById(R.id.totalMoney);

        // idClass.setText(listClass.getIdClass());
        className.setText(listClass.getClassName());
        numStudent.setText(listClass.getNumStudent());
        totalMoney.setText(listClass.getTotalMoney());
    }

    private void Collecting_Tuition_Fees_View(@Nullable View convertView, int position) {
        CollectionTuitionFeesDTO fees = (CollectionTuitionFeesDTO) arrayDataList.get(position);
        TextView studentName, collectionDate, totalMoney;

        studentName = convertView.findViewById(R.id.studentName);
        collectionDate = convertView.findViewById(R.id.collectionDate);
        totalMoney = convertView.findViewById(R.id.totalMoney);

        if (fees != null) {
            String idStudent = fees.getIdStudent();
            List<TeachingDTO> teaching = TeachingDAO.getInstance(mContext)
                    .SelectTeaching(mContext, "ID_TEACHING = ? AND STATUS = ?",
                            new String[] {idStudent, "0"});
            Log.d("Teaching relationship: ", teaching.toString());
            if (teaching.size() > 0) {
                List<OfficialStudentDTO> student = OfficialStudentDAO.getInstance(mContext)
                        .SelectStudentVer2(mContext, "ID_STUDENT = ? AND STATUS = ?",
                                new String[] {teaching.get(0).getIdStudent(), "0"});
                Log.d("Collecting tuition student: ", student.toString());
                // Log.d("Student found: ", student.toString());
                if (student.size() == 0) {
                    studentName.setText("");
                } else {
                    studentName.setText(student.get(0).getFullName());
                }
            }

            if (fees.getCollectionDate() == null) {
                collectionDate.setText("");
            } else {
                collectionDate.setText(fees.getCollectionDate());
            }

            if (fees.getMoney() == null) {
                totalMoney.setText("");
            } else {
                totalMoney.setText(fees.getMoney());
            }
        }

    }
}