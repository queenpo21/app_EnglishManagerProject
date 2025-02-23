package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.CertificateDTO;
import com.example.app.model.ClassDTO;

import java.util.ArrayList;
import java.util.List;

public class CertificateDAO {
    public static CertificateDAO instance;
    private CertificateDAO(Context context) {}
    public static synchronized CertificateDAO getInstance(Context context) {
        if (instance == null) {
            instance = new CertificateDAO(context);
        }
        return instance;
    }
    public int InsertCertificate(Context context, CertificateDTO certificate) {
        int rowEffect = -1;
        int maxId = DataProvider.getInstance(context).getMaxId("CERTIFICATE", "ID_CERTIFICATE");
        ContentValues values = new ContentValues();

        values.put("ID_CERTIFICATE", "CER" + String.valueOf(maxId + 1));
        values.put("NAME", certificate.getName());
        values.put("CONTENT", certificate.getContent());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).insertData("CERTIFICATE", values);
            if (rowEffect > 0) {
                Log.d("Insert Certificate: ", "success");
            } else {
                Log.d("Insert Certificate: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Certificate Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public int UpdateCertificate(Context context, CertificateDTO certificate, String whereClause, String[] whereArg) {
        int rowEffect = -1;
        ContentValues values = new ContentValues();


        values.put("NAME", certificate.getName());
        values.put("CONTENT", certificate.getContent());
        values.put("STATUS", 0);

        try {
            rowEffect = DataProvider.getInstance(context).updateData("CERTIFICATE", values, whereClause, whereArg);
            if (rowEffect > 0) {
                Log.d("Update Certificate: ", "success");
            } else {
                Log.d("Update Certificate: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Update Certificate Error: ", e.getMessage());
        }

        return rowEffect;
    }

    public List<CertificateDTO> SelectCertificate(Context context, String whereClause, String[] whereArgs) {
        List<CertificateDTO> listCertificate = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataProvider.getInstance(context).selectData("CERTIFICATE", new String[]{"*"}, whereClause, whereArgs, null);
        } catch (SQLException e) {
            Log.d("Select Class: ", e.getMessage());
        }

        String idCertificate = "", name = "", content = "";

        if (cursor.moveToFirst()) {
            do {

                int idCertificateIndex = cursor.getColumnIndex("ID_CERTIFICATE");
                if (idCertificateIndex != -1) {
                    idCertificate = cursor.getString(idCertificateIndex);
                }
                int nameIndex = cursor.getColumnIndex("NAME");
                if (nameIndex!= -1) {
                    name = cursor.getString(nameIndex);
                }
                int contentIndex = cursor.getColumnIndex("CONTENT");
                if (contentIndex != -1) {
                    content = cursor.getString(contentIndex);
                }

                listCertificate.add(new CertificateDTO(idCertificate, name, content));

            } while (cursor.moveToNext());
        }

        return listCertificate;
    }

}
