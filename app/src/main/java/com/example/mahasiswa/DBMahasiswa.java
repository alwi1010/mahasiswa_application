package com.example.mahasiswa;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBMahasiswa extends SQLiteOpenHelper {

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    static abstract class MyColumns implements BaseColumns {
        //Menentukan Nama Table dan Kolom
        static final String NamaTabel = "Mahasiswa";
        static final String NIM = "NIM";
        static final String Nama = "Nama_Mahasiswa";
        static final String Jurusan = "Jurusan";
        static final String JenisKelamin = "Jenis_Kelamin";
        static final String TanggalLahir = "Tanggal_Lahir";
        static final String Alamat = "Alamat";
    }

    private static final String NamaDatabse = "unpi.db";
    private static final int VersiDatabase = 1;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.NIM+" TEXT PRIMARY KEY, "+MyColumns.Nama+" TEXT NOT NULL, "+MyColumns.Jurusan+
            " TEXT NOT NULL, "+MyColumns.JenisKelamin+" TEXT NOT NULL, "+MyColumns.TanggalLahir+
            " TEXT NOT NULL, "+MyColumns.Alamat+" TEXT NOT NULL)";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    DBMahasiswa(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public Integer delete(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(MyColumns.NamaTabel, MyColumns.NIM+"=?", new String[]{id});
    }
    public void update(Mahasiswa mahasiswa)
    {
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyColumns.NIM, mahasiswa.getNim());
        values.put(MyColumns.Nama, mahasiswa.getNama());
        values.put(MyColumns.Jurusan, mahasiswa.getJurusan());
        values.put(MyColumns.JenisKelamin, mahasiswa.getJekel());
        values.put(MyColumns.TanggalLahir, mahasiswa.getTgllahir());
        values.put(MyColumns.Alamat, mahasiswa.getAlamat());
        db.update(MyColumns.NamaTabel,
                values, MyColumns.NIM+"=?",
                new String[]{mahasiswa.getNim()});
        db.close();
    }
}
