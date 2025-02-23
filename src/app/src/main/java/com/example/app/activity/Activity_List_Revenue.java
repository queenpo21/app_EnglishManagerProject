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
import android.widget.TextView;

import com.example.app.R;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.model.ClassCollectingFees;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.List_Adapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Activity_List_Revenue extends AppCompatActivity {
    private List_Adapter listAdapter;
    private ListView listView;
    private ArrayList<Object> dataArrayList;
    private ImageButton returnBtn;
    String[] yearItem = {"2020", "2021", "2022","2023","2024"};
    String[] monthItem = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    AutoCompleteTextView year, month;
    ArrayAdapter<String> yearAdapter, monthAdapter;
    TextView totalMoney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_revenue);

        listView = findViewById(R.id.notification_listview);
        returnBtn = findViewById(R.id.return_to_frag_btn);
        totalMoney = findViewById(R.id.totalMoney);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LocalDate now = LocalDate.now();
        year = findViewById(R.id.year);
        year.setText(String.valueOf(now.getYear()));
        yearAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, yearItem);
        year.setAdapter(yearAdapter);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                dataArrayList = new ArrayList<>();
                List<CollectionTuitionFeesDTO> collectingTuition = CollectionTuitionFeesDAO.getInstance(Activity_List_Revenue.this)
                        .SelectCollectionTuitionFees(Activity_List_Revenue.this, month.getText().toString(),
                                year.getText().toString());
               // Log.d("List revenue show:", collectingTuition.toString());
                int money = 0;
                for (int i = 0; i < collectingTuition.size(); i++) {
                    dataArrayList.add(collectingTuition.get(i));
                    money += Integer.parseInt(collectingTuition.get(i).getMoney());
                }
                totalMoney.setText(String.valueOf(money));
                listAdapter = new List_Adapter(Activity_List_Revenue.this, R.layout.list_collecting_tuition_fees_item, dataArrayList);
                listView.setAdapter(listAdapter);
            }
        });

        month = findViewById(R.id.month);
        month.setText(String.valueOf(now.getMonth().getValue()));
        monthAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, monthItem);
        month.setAdapter(monthAdapter);
        month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                dataArrayList = new ArrayList<>();
                List<CollectionTuitionFeesDTO> collectingTuition = CollectionTuitionFeesDAO.getInstance(Activity_List_Revenue.this)
                        .SelectCollectionTuitionFees(Activity_List_Revenue.this, month.getText().toString(),
                                year.getText().toString());
               // Log.d("List revenue show:", collectingTuition.toString());
                int money = 0;
                for (int i = 0; i < collectingTuition.size(); i++) {
                    dataArrayList.add(collectingTuition.get(i));
                    money += Integer.parseInt(collectingTuition.get(i).getMoney());
                }
                totalMoney.setText(String.valueOf(money));
                listAdapter = new List_Adapter(Activity_List_Revenue.this, R.layout.list_collecting_tuition_fees_item, dataArrayList);
                listView.setAdapter(listAdapter);
            }
        });

        dataArrayList = new ArrayList<>();
        List<CollectionTuitionFeesDTO> collectingTuition = CollectionTuitionFeesDAO.getInstance(Activity_List_Revenue.this)
                .SelectCollectionTuitionFees(Activity_List_Revenue.this, month.getText().toString(),
                        year.getText().toString());
        int money = 0;

        Log.d("List revenue show:", collectingTuition.toString());

        for (int i = 0; i < collectingTuition.size(); i++) {
            dataArrayList.add(collectingTuition.get(i));
            money += Integer.parseInt(collectingTuition.get(i).getMoney());
        }
        totalMoney.setText(String.valueOf(money));
        listAdapter = new List_Adapter(Activity_List_Revenue.this, R.layout.list_collecting_tuition_fees_item, dataArrayList);
        listView.setAdapter(listAdapter);

    }
}