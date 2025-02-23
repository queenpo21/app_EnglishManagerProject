package com.example.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapter.ClassroomDAO;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.adapter.ScheduleDAO;
import com.example.app.model.ClassroomDTO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.List_Adapter;
import com.example.app.model.ScheduleDTO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Activity_List_Room extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    ArrayList<String> roomItem;
    AutoCompleteTextView room;
    ArrayAdapter<String> roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_room);

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        roomItem = new ArrayList<>();

        List<ClassroomDTO> listClassroom = ClassroomDAO.getInstance(Activity_List_Room.this)
                .SelectClassroom(Activity_List_Room.this, "STATUS = ?",
                        new String[]{"0"});
        for (int i = 0; i < listClassroom.size(); i++) {
            roomItem.add(listClassroom.get(i).getName());
            Log.d("List room in english center: ", roomItem.get(i));
        }
        room = findViewById(R.id.room);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, roomItem);
        room.setAdapter(roomAdapter);

        LocalDate today = LocalDate.now();
        int dayOfWeek = today.getDayOfWeek().getValue();
        dataArrayList = new ArrayList<>();
        List<ScheduleDTO> schedule = ScheduleDAO.getInstance(Activity_List_Room.this)
                .SelectSchedule(Activity_List_Room.this,
                        "DAY_OF_WEEK = ? AND STATUS = ?",
                        new String[]{String.valueOf(dayOfWeek + 1), "0"});
        for (int i = 0; i < schedule.size(); i++) {
            dataArrayList.add(schedule.get(i));
        }
        listAdapter = new List_Adapter(Activity_List_Room.this, R.layout.list_schedule_for_manager_item, dataArrayList);
        listView.setAdapter(listAdapter);

        room.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String item = parent.getItemAtPosition(position).toString();
                List<ClassroomDTO> getClassroom = ClassroomDAO.getInstance(Activity_List_Room.this)
                        .SelectClassroom(Activity_List_Room.this,
                                "NAME = ? AND STATUS = ?",
                                new String[] {room.getText().toString(), "0"});
                String idClassroom = "";

                if (getClassroom.size() > 0) {
                    idClassroom = getClassroom.get(0).getIdRoom();
                    Log.d("Classroom get: ", idClassroom);
                    LocalDate today = LocalDate.now();
                    int dayOfWeek = today.getDayOfWeek().getValue();
                    Log.d("List Schedule select: ", String.valueOf(dayOfWeek));
                    List<ScheduleDTO> listScheduleSelect = ScheduleDAO.getInstance(Activity_List_Room.this)
                            .SelectSchedule(Activity_List_Room.this,
                                    "DAY_OF_WEEK = ? AND STATUS = ? AND ID_CLASSROOM = ?",
                                    new String[] {String.valueOf(dayOfWeek + 1), "0", idClassroom});

                    Log.d("List Schedule select: ", listScheduleSelect.toString());

                    dataArrayList = new ArrayList<>();
                    for (int i = 0; i < listScheduleSelect.size(); i++) {
                        dataArrayList.add(listScheduleSelect.get(i));
                    }
                    listAdapter = new List_Adapter(Activity_List_Room.this, R.layout.list_schedule_for_manager_item, dataArrayList);
                    listView.setAdapter(listAdapter);
                }

            }
        });

    }
}

        /*List<ScheduleDTO> schedule = ScheduleDAO.getInstance(Activity_List_Room.this)
                .SelectSchedule(Activity_List_Room.this, "STATUS = 0",
                        new String[] {"0"});

        listAdapter = new List_Adapter(Activity_List_Room.this, R.layout.list_schedule_for_manager_item, dataArrayList);
        listView.setAdapter(listAdapter);

        room = findViewById(R.id.room);
        roomAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, roomItem);
        room.setAdapter(roomAdapter);

    }
    }

         */