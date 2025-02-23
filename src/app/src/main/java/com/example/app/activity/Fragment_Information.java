package com.example.app.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.app.R;
import com.example.app.adapter.AccountDAO;
import com.example.app.model.List_Adapter;
import com.example.app.model.List_Information;

import java.util.ArrayList;

public class Fragment_Information extends Fragment implements AdapterView.OnItemClickListener {
    private List_Adapter listAdapter;
    private ListView listView;
    private static ArrayList<Object> dataArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        listView = view.findViewById(R.id.information_listview);
        dataArrayList = new ArrayList<>();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (dataArrayList.isEmpty()) {

            int type = AccountDAO.getInstance(getContext()).GetObjectLogin(getContext(),
                    Activity_Login.username, Activity_Login.password);

            if (type == 1)  {
                //Học viên
                dataArrayList.add(new List_Information("Tra cứu điểm", R.drawable.score_icon));
                dataArrayList.add(new List_Information("Xem chứng chỉ", R.drawable.chuong_trinh_dt));
                dataArrayList.add(new List_Information("Thông báo hệ thống", R.drawable.tb_he_thong));
                dataArrayList.add(new List_Information("Lịch học", R.drawable.baseline_schedule_24));
            } else if (type == 2) {
                //Nhân viên ghi danh
                dataArrayList.add(new List_Information("Quản lý học viên", R.drawable.quanlylophoc));
                dataArrayList.add(new List_Information("Xem chứng chỉ", R.drawable.chuong_trinh_dt));
                dataArrayList.add(new List_Information("Quản lý lớp học", R.drawable.lophoc));
                //Nhân viên hv + ghi danh
                dataArrayList.add(new List_Information("Quản lý thông báo", R.drawable.tb_he_thong));
            } else if (type == 3) {
                //Nhân viên học vụ
                dataArrayList.add(new List_Information("Xem các lớp học", R.drawable.lophoc));
                dataArrayList.add(new List_Information("Xem chứng chỉ", R.drawable.chuong_trinh_dt));
                //dataArrayList.add(new List_Information("Lịch sử thu", R.drawable.baseline_monetization_on_24));
                //Nhân viên hv + ghi danh
                dataArrayList.add(new List_Information("Quản lý thông báo", R.drawable.tb_he_thong));
            } else if (type == 4) {
                //Quản lý
                dataArrayList.add(new List_Information("Thông báo hệ thống", R.drawable.tb_he_thong));
                dataArrayList.add(new List_Information("Quản lý tài khoản", R.drawable.quanlytaikhoan));
                dataArrayList.add(new List_Information("Xem chứng chỉ", R.drawable.chuong_trinh_dt));
                dataArrayList.add(new List_Information("Quản lý thông tin phòng học", R.drawable.classroom));
                dataArrayList.add(new List_Information("Quản lý nhân viên/giáo viên", R.drawable.quanlynhansu));
                dataArrayList.add(new List_Information("Quản lý doanh thu", R.drawable.baseline_auto_graph_24));
            }
        }

        listAdapter = new List_Adapter(getActivity(), R.layout.list_item, dataArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List_Information selectedData = (List_Information) listAdapter.getItem(position);
        String selectedText = selectedData.getName();
        Intent intent;
        if (selectedText == "Thông báo hệ thống" || selectedText == "Tra cứu điểm"
                || selectedText == "Tra cứu chương trình đào tạo" || selectedText == "Lịch học"
                || selectedText == "Xem các lớp học" || selectedText == "Xem chứng chỉ"
                || selectedText == "Lịch sử thu") {
            intent = new Intent(getContext(), Activity_Notifications.class);
            intent.putExtra("message", selectedText);
        } else if (selectedText == "Quản lý thông tin phòng học")
            intent = new Intent(getContext(), Activity_List_Room.class);
        else {
            if (selectedText == "Quản lý doanh thu")
                intent = new Intent(getContext(), Activity_Total_Revenue.class);
            else {
                intent = new Intent(getContext(), Activity_Notifications_ToolBars.class);
                intent.putExtra("message", selectedText);
            }
        }
        startActivity(intent);
    }
}