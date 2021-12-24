package id.progmob.modul34;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BiodataDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "db_form";
    public static final int DATABASE_VERSION = 1;
    public static final String tabel_biodata = "tabel_biodata";
    public static final String nim = "nim";
    public static final String nama = "nama";
    public static final String jk = "jk";
    public static final String hobby = "hobby";
    public static final String umur = "umur";
    private  SQLiteDatabase db;

    public BiodataDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        db = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query ="CREATE TABLE " + tabel_biodata + "("
                + nim + " INTEGER PRIMARY KEY,"
                + nama + " TEXT,"
                + jk + " TEXT,"
                + hobby + " TEXT,"
                + umur + " INTEGER)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "drop table if exists "+tabel_biodata+" " ;
        db.execSQL(query);
        onCreate(db);
    }
    public void insertData(ContentValues values){
        db.insert(tabel_biodata,null,values);
    }
    public void updateData(ContentValues values, long id){
        db.update(tabel_biodata, values, nim + "=" + id, null);
    }
//    public void updateData(ContentValues values, long id){
//        db.update(table_name,values,row_id + "=" + id, null);
//    }
    public void deleteData(long id){
        db.delete(tabel_biodata, nim + "=" + id,null);
    }
    public Cursor getAllData(){
        return db.query(tabel_biodata,null,null,
                null,null, null,null);
    }
    public Cursor getData(long id){
        return db.rawQuery("select * from " + tabel_biodata + " where " + nim + "=" + id, null);
    }
}

