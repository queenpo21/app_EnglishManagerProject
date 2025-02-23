package com.example.app.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app.R;
import com.example.app.adapter.CollectionTuitionFeesDAO;
import com.example.app.model.CollectionTuitionFeesDTO;
import com.example.app.model.RevenueReportByYear;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
//Import android.Manifest;


public class Activity_Total_Revenue extends AppCompatActivity {
    private static final int REQUEST_CODE_FOLDER = 1001;
    LineChart lineChart;
    List<String> xValues;
    Button detailBtn, printBtn;
    ImageButton returnFrag;
    String[] yearItem = {"2020", "2021", "2022","2023","2024"};
    AutoCompleteTextView year;
    ArrayAdapter<String> yearAdapter;
    List<RevenueReportByYear> listRevenue = new ArrayList<>();
    String selectedDirectoryPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_revenue);

        returnFrag = findViewById(R.id.return_to_frag_btn);
        returnFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });

        lineChart = findViewById(R.id.chart);

        Description description = new Description();
        description.setPosition(150f, 15f);
        description.setText("Chục triệu đồng");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("","1","2","3","4","5","6","7","8","9","10","11","12");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(13);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMaximum(10f);
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisLineWidth(0.1f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries = new ArrayList<>();
   /*     entries.add(new Entry(0,0f));
       entries.add(new Entry(1,1f));
        entries.add(new Entry(2,2f));
        entries.add(new Entry(3,3f));
        entries.add(new Entry(4,0f));
        entries.add(new Entry(5,1f));
        entries.add(new Entry(6,2f));
        entries.add(new Entry(7,3f));
        entries.add(new Entry(8,0f));
        entries.add(new Entry(9,1f));
        entries.add(new Entry(10,2f));
        entries.add(new Entry(11,3f));
        entries.add(new Entry(12,3f));*/
        entries.add(new Entry(0,0f));

        Map<Integer, Integer> collectingTuition = CollectionTuitionFeesDAO
                .getInstance(Activity_Total_Revenue.this)
                .SelectCollectionTuitionFeesByYear(Activity_Total_Revenue.this, "2024");
        Log.d("List collecting revenue: ", collectingTuition.toString());

        for (Integer value : collectingTuition.keySet()) {
            Log.d("Revenue by month", String.valueOf(collectingTuition.get(value)));
           // entries.add(new Entry(value, (collectingTuition.get(value) / 1000000000)/1.0f));
            entries.add(new Entry(value, (float)(collectingTuition.get(value) / 10000000)));
        }

        year = findViewById(R.id.year);
        year.setText("2024");
        yearAdapter = new ArrayAdapter<String>(this, R.layout.combobox_item, yearItem);
        year.setAdapter(yearAdapter);
        year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listRevenue = CollectionTuitionFeesDAO.getInstance(Activity_Total_Revenue.this)
                        .SelectCollectionTuitionFeesToSummarizeRevenueByYear(
                                Activity_Total_Revenue.this, year.getText().toString());
                Log.d("List revenue log: ", listRevenue.toString());

                Map<Integer, Integer> collectingTuition = CollectionTuitionFeesDAO
                        .getInstance(Activity_Total_Revenue.this)
                        .SelectCollectionTuitionFeesByYear(Activity_Total_Revenue.this,
                                year.getText().toString());
                Log.d("List collecting revenue: ", collectingTuition.toString());

                entries.add(new Entry(0,0f));
                for (Integer value : collectingTuition.keySet()) {
                    Log.d("Revenue by month", String.valueOf(collectingTuition.get(value)));
                    // entries.add(new Entry(value, (collectingTuition.get(value) / 1000000000)/1.0f));
                    entries.add(new Entry(value, (float)(collectingTuition.get(value) / 10000000)));

                }
                LineDataSet dataSet = new LineDataSet(entries, "");
                dataSet.setColor(Color.RED);
            }
        });
        listRevenue = CollectionTuitionFeesDAO.getInstance(Activity_Total_Revenue.this)
                .SelectCollectionTuitionFeesToSummarizeRevenueByYear(
                        Activity_Total_Revenue.this, year.getText().toString());

        LineDataSet dataSet = new LineDataSet(entries, "");
        dataSet.setColor(Color.RED);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        lineChart.invalidate();

        detailBtn = findViewById(R.id.detailBtn);
        detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Total_Revenue.this, Activity_List_Revenue.class);
                intent.putExtra("message", "Thống kê doanh thu");
                startActivity(intent);
            }
        });

        printBtn = findViewById(R.id.printBtn);
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFolderSelectionDialog();

            }
        });
    }
    private void showFolderSelectionDialog() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        startActivityForResult(intent, REQUEST_CODE_FOLDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data!= null) {
                uri = data.getData();
            }
            saveCsvFile(uri);
        }
    }

    private void saveCsvFile(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            DocumentFile directory = DocumentFile.fromTreeUri(this, uri);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String currentDateandTime = sdf.format(new Date());
            String fileName = "report_" + currentDateandTime + ".csv";

            // Tạo file mới trong thư mục được chọn
            DocumentFile csvFile = directory.createFile("text/csv", fileName);

            if (csvFile!= null && csvFile.exists()) {
                OutputStream outputStream = contentResolver.openOutputStream(csvFile.getUri());
                Writer writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                // Thêm BOM vào đầu file
                writer.write("\ufeff");

                // Header
                writer.write("STT,Tháng,Mã lớp,Tên lớp,Chương trình đào tạo,Học phí,Số lượng học viên,Doanh thu");
                writer.write(System.lineSeparator()); // Sử dụng System.lineSeparator() để thêm một dòng mới

                // Write data from listRevenue
                for (int i = 0; i < listRevenue.size(); i++) {
                    RevenueReportByYear report = listRevenue.get(i);
                    writer.write(String.format("%d,%d,%s,%s,%s,%d,%d,%d",
                            i+1,
                            report.getMonth(),
                            report.getIdClass(),
                            report.getNameClass(),
                            report.getNameProgram(),
                            report.getTuition(),
                            report.getNumberOfStudents(),
                            report.getRevenue()));
                    writer.write(System.lineSeparator()); // Sử dụng System.lineSeparator() để thêm một dòng mới
                }

                writer.close();
                Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to create file", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing to file", e);
            Toast.makeText(this, "Error saving file", Toast.LENGTH_SHORT).show();
        }
    }




}