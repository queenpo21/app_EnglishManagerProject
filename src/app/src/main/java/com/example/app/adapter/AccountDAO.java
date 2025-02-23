package com.example.app.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.app.model.AccountDTO;
import com.example.app.model.ClassDTO;
import com.example.app.model.OfficialStudentDTO;
import com.example.app.model.PotentialStudentDTO;
import com.example.app.model.StaffDTO;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public static AccountDAO instance;
    private AccountDAO(Context context) {}
    public static synchronized AccountDAO getInstance(Context context) {
        if (instance == null) {
            instance = new AccountDAO(context);
        }
        return instance;
    }

    public int insertAccount(Context context, AccountDTO accountDTO) {
        ContentValues values = new ContentValues();
        int rowEffect = -1;
        int maxId = DataProvider.getInstance(context).getMaxId("ACCOUNT", "ID_ACCOUNT");

        values.put("ID_ACCOUNT", "ACC" + String.valueOf(maxId + 1));
        values.put("ID_USER", accountDTO.getIdUser());
        values.put("USERNAME", accountDTO.getUserName());
        values.put("PASSWORD", accountDTO.getPassWord());
        values.put("STATUS", 0);
        try {
            rowEffect = DataProvider.getInstance(context).insertData("ACCOUNT", values);
            if (rowEffect > 0 ) {
                Log.d("Insert Account: ", "success");
            } else {
                Log.d("Insert Account: ", "Fail");
            }
        } catch (SQLException e) {
            Log.d("Insert Account Error: ", e.getMessage());
        }
        return rowEffect;
    }

    public int DeleteAccount(Context context, AccountDTO account, String whereClause, String[] whereArgs)  {
       ContentValues values = new ContentValues();
        values.put("STATUS", 1);
        int rowEffect = -1;

        try {
            rowEffect = DataProvider.getInstance(context).updateData("ACCOUNT", values,
                    "ID_ACCOUNT = ?", new String[] {account.getIdAccount()});
            if (rowEffect > 0) {
                Log.d("Delete account ", "success");
            }
        } catch (SQLException e) {
            Log.d("Delete account Error: ", e.getMessage());
        }

        return  rowEffect;
    }

    public int updateAccount(Context context, AccountDTO accountDTO, String whereClause, String[] whereArgs) {
        ContentValues values = new ContentValues();
        values.put("ID_USER", accountDTO.getIdUser());
        values.put("USERNAME", accountDTO.getUserName());
        values.put("PASSWORD", accountDTO.getPassWord());

        try {
            int rowsUpdated = DataProvider.getInstance(context).updateData("ACCOUNT", values, whereClause, whereArgs);
            return rowsUpdated;
        } catch (SQLException e) {
            Log.e("Update Account Error: ", e.getMessage());
        }
        return 0;
    }

    //String tableName, String[] columns, String whereClause, String[] whereArgs, String groupBy
    public Cursor selectAccount(Context context, String whereClause, String[] whereArgs) {
        Cursor cursor = null;
        try {
            cursor = DataProvider.getInstance(context).selectData("ACCOUNT", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Account: ", e.getMessage());
        }

        return cursor;
    }

    public List<AccountDTO> selectAccountVer2(Context context, String whereClause, String[] whereArgs) {
        List<AccountDTO> listAccount = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = DataProvider.getInstance(context).selectData("ACCOUNT", new String[]{"*"},  whereClause, whereArgs, null);
        }catch(SQLException e) {
            Log.d("Select Account: ", e.getMessage());
        }

        String idAccount = "", idUser = "", username = "", password = "";

        if (cursor.moveToFirst()) {
            do {

                int idAccountIndex = cursor.getColumnIndex("ID_ACCOUNT");
                if (idAccountIndex != -1) {
                    idAccount = cursor.getString(idAccountIndex);
                }
                int idUserIndex = cursor.getColumnIndex("ID_USER");
                if (idUserIndex!= -1) {
                    idUser = cursor.getString(idUserIndex);
                }
                int usernameIndex = cursor.getColumnIndex("USERNAME");
                if (usernameIndex != -1) {
                    username = cursor.getString(usernameIndex);
                }
                int passwordIndex = cursor.getColumnIndex("PASSWORD");
                if (passwordIndex != -1) {
                    password = cursor.getString(passwordIndex);
                }

                listAccount.add(new AccountDTO(idAccount, idUser, username, password));

            } while (cursor.moveToNext());
        }
        return listAccount;
    }

    public int GetObjectLogin (Context context, String username, String password)  {
        int flag = -1;
        String idUser = "";
        int type = -1;

        String whereClause = "USERNAME = ? AND PASSWORD = ? ";
        String[] whereArgs = new String[] {username, password};

        List<AccountDTO> listAccount = AccountDAO.getInstance(context).selectAccountVer2(context,
                whereClause, whereArgs);
        for (int i = 0; i < listAccount.size(); i++) {
            idUser = listAccount.get(i).getIdUser();
        }

        List<OfficialStudentDTO> officialStudent = OfficialStudentDAO.getInstance(context).SelectStudentVer2(
                context, "ID_STUDENT = ?", new String[] {idUser});
        if (officialStudent.size() != 0) {
            type = 1;
            Log.d("Type of login: ", String.valueOf(1));
            return 1;  // 1 standards for Official Student
        } else {
            List<StaffDTO> staff = StaffDAO.getInstance(context).SelectStaffVer2(context,
                    "ID_STAFF = ?", new String[] {idUser});

            for (int i = 0; i < staff.size(); i++) {
                type = Integer.parseInt(staff.get(i).getType());
            }
            if (type == 1) {
                Log.d("Type of login: ", String.valueOf(2));
                type = 2;
                return 2;  // 2 standards for registrar
            } else if (type == 2) {
                Log.d("Type of login: ", String.valueOf(3));
                type = 3;
                return 3;  // 3 standards for academic staff
            } else {
                type = 4;
                Log.d("Type of login: ", String.valueOf(4));
                return 4;  // 4 standards for manager
            }

        }
    }


}
