package com.example.user.demotablayout.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.Sampler;
import android.util.Log;
import android.widget.Switch;
import android.widget.Toast;

import com.example.user.demotablayout.model.ThoiGian;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private final String TAG = "DBManager";

    private static final String DATABASE_TIME = "time_manager";
    private static final String TABLE_NAME = "thoigian";
    private static final String ID = "id";
    private static final String gio = "gio";
    private static final String phut = "phut";
    private static final String tenbaothuc = "tenbaothuc";

    private static final int VERSION = 1;  // VERSION thay đổi thì gọi onUpgrade

    private Context context;

    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + "(" + ID +" integer primary key, "
            + gio + " TEXT, " + phut + " TEXT, " + tenbaothuc +" TEXT )" ;

    public DBManager(Context context) {
        super(context, DATABASE_TIME, null, 1);
        this.context=context;
        Log.d(TAG, "DBManager");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {   // Dùng câu truy vấn để tạo bảng
        db.execSQL(SQLQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void addThoiGian(ThoiGian thoiGian){
        SQLiteDatabase db = this.getWritableDatabase(); //Tạo và mở db để đọc và chỉnh sửa
        ContentValues values = new ContentValues(); //Nơi để truyển dữ liệu vào (Lưu table xuống đây, sau đó truyền xuống insert)

        values.put(gio,thoiGian.getMgio());
        values.put(phut,thoiGian.getMphut());
        values.put(tenbaothuc,thoiGian.getMtenbaothuc());
        db.insert(TABLE_NAME,null,values); //
        db.close();
    }

    public List<ThoiGian> getAllThoiGian(){
        List<ThoiGian> ListThoiGian = new ArrayList<>();

        String slecectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(slecectQuery,null); // Lấy dữ liệu trả về

        if (cursor.moveToFirst()){
            do {
                    ThoiGian thoiGian = new ThoiGian();
                    thoiGian.setmID(cursor.getInt(0));
                    thoiGian.setMgio(cursor.getString(1));
                    thoiGian.setMphut(cursor.getString(2));
                    thoiGian.setMtenbaothuc(cursor.getString(3));
                    ListThoiGian.add(thoiGian);

            }while (cursor.moveToNext());
        }
            db.close();
            return ListThoiGian;


    }

    public int deletetg(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[] {String.valueOf(id)});
    }


}
