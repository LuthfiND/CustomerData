package com.example.py7.appcustd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String database_name = "db_custd";
    public static final String table_name = "tabel_customer";

    public static final String row_id = "_id";
    public static final String row_nomor = "Nomor";
    public static final String row_nama = "Nama";
    public static final String row_paket = "Paket";
    public static final String row_tglLahir = "Tanggal";
    public static final String row_alamat = "Alamat";

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, database_name, null, 2);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "(" + row_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + row_nomor + " TEXT, " + row_nama + " TEXT, " + row_paket
                + " TEXT, " + row_tglLahir + " TEXT, " + row_alamat + " TEXT)";
        db.execSQL(query);
        db.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int x) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        db.execSQL("DROP TABLE IF EXISTS users");
    }

    //Get All SQLite Data
    public Cursor allData(){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name, null);
        return cur;
    }

    //Get 1 Data By ID
    public Cursor oneData(Long id){
        Cursor cur = db.rawQuery("SELECT * FROM " + table_name + " WHERE " + row_id + "=" + id, null);
        return cur;
    }

    //Insert Data to Database
    public void insertData(ContentValues values){
        db.insert(table_name, null, values);
    }

    //Update Data
    public void updateData(ContentValues values, long id){
        db.update(table_name, values, row_id + "=" + id, null);
    }

    //Delete Data
    public void deleteData(long id){
        db.delete(table_name, row_id + "=" + id, null);
    }

    public Boolean insertUser(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username =?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username =? AND password =?", new String[] {username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}
