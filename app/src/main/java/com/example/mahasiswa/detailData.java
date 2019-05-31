package com.example.mahasiswa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class detailData extends AppCompatActivity {

    TextView txt_nim, txt_nama, txt_jurusan, txt_jekel, txt_tgllahir, txt_alamat;
    DBMahasiswa MyDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_data);

        txt_nim = findViewById(R.id.text_nim);
        txt_nama = findViewById(R.id.text_nama);
        txt_jurusan = findViewById(R.id.text_jurusan);
        txt_jekel = findViewById(R.id.text_jekel);
        txt_tgllahir = findViewById(R.id.text_tgllahir);
        txt_alamat = findViewById(R.id.text_alamat);

        tampilData();

        Button editData = findViewById(R.id.edit);
        editData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nim = txt_nim.getText().toString();
                String nama = txt_nama.getText().toString();
                String jurusan = txt_jurusan.getText().toString();
                String jekel = txt_jekel.getText().toString();
                String tgl = txt_tgllahir.getText().toString();
                String alamat = txt_alamat.getText().toString();
                Mahasiswa Mhs1 = new Mahasiswa();
                Mhs1.setNim(nim);
                Mhs1.setNama(nama);
                Mhs1.setJurusan(jurusan);
                Mhs1.setJekel(jekel);
                Mhs1.setTgllahir(tgl);
                Mhs1.setAlamat(alamat);

                Intent intentEdit = new Intent(detailData.this, editMahasiswa.class);
                intentEdit.putExtra("Mahasiswa1", Mhs1);
                startActivity(intentEdit);
                finish();
            }
        });

        Button delData = findViewById(R.id.del);
        delData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow();
            }
        });
    }

    public void tampilData(){
        Mahasiswa intenMahasiswa = getIntent().getParcelableExtra("MAHASISWA");
        MyDatabase = new DBMahasiswa(getBaseContext());
        SQLiteDatabase bacaData = MyDatabase.getReadableDatabase();
        Cursor data = bacaData.rawQuery("SELECT * FROM "+ DBMahasiswa.MyColumns.NamaTabel +" WHERE "+ DBMahasiswa.MyColumns.Nama +" = '"+intenMahasiswa.getNama()+"'",null);

        data.moveToFirst();

        for(int count=0; count < data.getCount(); count++) {
            data.moveToPosition(count);
            txt_nim.setText(data.getString(0));
            txt_nama.setText(data.getString(1));
            txt_jurusan.setText(data.getString(2));
            txt_jekel.setText(data.getString(3));
            txt_tgllahir.setText(data.getString(4));
            txt_alamat.setText(data.getString(5));
        }
    }

    private void dialogShow() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin Ingin Menghapus Data Ini?");
        builder.setCancelable(true);

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                MyDatabase = new DBMahasiswa(getBaseContext());
                MyDatabase.delete(txt_nim.getText().toString());
                finish();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
