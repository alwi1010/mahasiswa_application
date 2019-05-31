package com.example.mahasiswa;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class editMahasiswa extends AppCompatActivity {

    EditText NIM, Nama, TanggalLahir, Alamat;
    Spinner Jurusan;
    RadioButton MALE, FAMALE;
    String setNIMLama, setNamaLama, setTanggalLahirLama, setAlamatLama, setJurusanLama, setJenisKelaminLama,
            setNIM, setNama, setTanggalLahir, setAlamat, setJurusan, setJenisKelamin;
    //Variable Untuk Inisialisasi Database DBMahasiswa
    ArrayAdapter<String> adapter;

    String prodi[]={
            "Sistem Komputer",
            "Sistem Informasi",
            "Manajemen Informatika"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        Button updateData = findViewById(R.id.update);
        NIM = findViewById(R.id.nim);
        Nama = findViewById(R.id.nama);
        TanggalLahir = findViewById(R.id.date);
        MALE = findViewById(R.id.male);
        FAMALE = findViewById(R.id.famale);
        Jurusan = findViewById(R.id.jurusan);
        Alamat = findViewById(R.id.alamat);

        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,prodi);

        Jurusan.setAdapter(adapter);

        tampilData();

        setJurusan = Jurusan.getSelectedItem().toString();



        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData();
                updateData();
                Toast.makeText(getApplicationContext(),"Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,prodi);
//        rd.setChecked(true);
//        spProdi.setAdapter(adapter);
    }

    public void tampilData(){
        Mahasiswa intenMahasiswa1 = getIntent().getParcelableExtra("Mahasiswa1");
        setNIMLama = intenMahasiswa1.getNim();
        setNamaLama = intenMahasiswa1.getNama();
        setJurusanLama = intenMahasiswa1.getJurusan();
        setJenisKelaminLama = intenMahasiswa1.getJekel();
        setTanggalLahirLama = intenMahasiswa1.getTgllahir();
        setAlamatLama = intenMahasiswa1.getAlamat();

        NIM.setText(setNIMLama);
        Nama.setText(setNamaLama);
        TanggalLahir.setText(setTanggalLahirLama);
        Alamat.setText(setAlamatLama);
        int pos_spiner = adapter.getPosition(setJurusanLama);
        Jurusan.setSelection(pos_spiner);
        if (setJenisKelaminLama.equals("Laki-Laki")){
            MALE.setChecked(true);
        } else if (setJenisKelaminLama.equals("Perempuan")){
            FAMALE.setChecked(true);
        }
    }

    //Berisi Statement-Statement Untuk Mendapatkan Input Dari User
    private void setData(){
        setNIM = NIM.getText().toString();
        setNama = Nama.getText().toString();
        setJurusan = Jurusan.getSelectedItem().toString();
        if(MALE.isChecked()){
            setJenisKelamin = MALE.getText().toString();
        }else if (FAMALE.isChecked()){
            setJenisKelamin = FAMALE.getText().toString();
        }
        setTanggalLahir = TanggalLahir.getText().toString();
        setAlamat = Alamat.getText().toString();
    }

    //Berisi Statement-Statement Untuk Menyimpan Data Pada Database
    private void updateData(){
        //Mendapatkan Repository dengan Mode Menulis

        //Membuat Map Baru, Yang Berisi Nama Kolom dan Data Yang Ingin Dimasukan
//        ContentValues values = new ContentValues();
//        values.put(DBMahasiswa.MyColumns.NIM, setNIM);
//        values.put(DBMahasiswa.MyColumns.Nama, setNama);
//        values.put(DBMahasiswa.MyColumns.Jurusan, setJurusan);
//        values.put(DBMahasiswa.MyColumns.JenisKelamin, setJenisKelamin);
//        values.put(DBMahasiswa.MyColumns.TanggalLahir, setTanggalLahir);
//        values.put(DBMahasiswa.MyColumns.Alamat, setAlamat);

//        //Menambahkan Baris Baru, Berupa Data Yang Sudah Diinputkan pada Kolom didalam Database
//        update.update(DBMahasiswa.MyColumns.NamaTabel, values, DBMahasiswa.MyColumns.NIM+"=?",
//                new String[]{setNIM});

        DBMahasiswa dbMahasiswa = new DBMahasiswa(getApplicationContext());
        Mahasiswa mahasiswa = new Mahasiswa(setNIM,setNama,setJurusan,setJenisKelamin,setTanggalLahir,setAlamat);
        dbMahasiswa.update(mahasiswa);
    }
}
