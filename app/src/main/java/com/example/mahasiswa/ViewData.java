package com.example.mahasiswa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {

    private ListView listView;
    private DBMahasiswa MyDatabase;
    private ArrayList<String> ListData;
    AlertDialog.Builder dialog;
    SwipeRefreshLayout doRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        doRefresh = findViewById(R.id.swipeRefresh);
        tampilData();
        doRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light,android.R.color.holo_red_light);

        doRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tampilData();
                doRefresh.setRefreshing(false);
            }
        });
    }

    public void tampilData(){
        getSupportActionBar().setTitle("Daftar Mahasiswa");
        listView = findViewById(R.id.list);
        ListData = new ArrayList<>();
        MyDatabase = new DBMahasiswa(getBaseContext());
        getData();
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListData));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = ((TextView)view).getText().toString();
                Mahasiswa Mhs = new Mahasiswa();
                Mhs.setNama(item);

                Intent intenObjek = new Intent(ViewData.this, detailData.class);
                intenObjek.putExtra("MAHASISWA", Mhs);
                startActivity(intenObjek);
                closeContextMenu();
//                Toast.makeText(getBaseContext(), item, Toast.LENGTH_SHORT).show();
            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final CharSequence[] dialogitem = {"Edit", "Delete"};
//                dialog = new AlertDialog.Builder(ViewData.this);
//                dialog.setCancelable(true);
//                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
////                                Intent intent = new Intent(MainActivity.this, AddEdit.class);
////                                intent.putExtra(TAG_ID, idx);
////                                intent.putExtra(TAG_NAME, name);
////                                intent.putExtra(TAG_ADDRESS, address);
////                                startActivity(intent);
//                                break;
//                            case 1:
////                                SQLiteDatabase.delete(Integer.parseInt(idx));
//                                SQLiteDatabase db = MyDatabase.getWritableDatabase();
//                                db.execSQL("delete from "+ DBMahasiswa.MyColumns.NamaTabel+ " where nama = '"+ListData+"'");
//                                tampilData();
//                                break;
//                        }
//                    }
//                });
//                return false;
//            }
//        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final String selection = ListData.get(position);
//                final CharSequence[] dialogitem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(ViewData.this);
//                builder.setTitle("Pilihan");
//                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                Intent i = new Intent(getApplicationContext(), DetailBiodata.class);
//                                i.putExtra("nama", selection);
//                                startActivity(i);
//                                break;
//                            case 1 :
//                                Intent in = new Intent(getApplicationContext(), UpdateBiodata.class);
//                                in.putExtra("nama", selection);
//                                startActivity(in);
//                                break;
//                            case 2 :
//                                SQLiteDatabase db = MyDatabase.getWritableDatabase();
//                                db.execSQL("delete from biodata where nama = '"+selection+"'");
//                                tampilData();
//                                break;
//                        }
//                    }
//                });
//            }
//        });
    }

    private void refresh() {
        doRefresh.setRefreshing(true);
        tampilData();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e){
            System.out.println("Got Interrupted!");
        }

        doRefresh.setRefreshing(false);
    }

    public void onResume(){
        super.onResume();
        refresh();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    private void getData(){
        //Mengambil Repository dengan Mode Membaca
        SQLiteDatabase ReadData = MyDatabase.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT "+ DBMahasiswa.MyColumns.Nama +" FROM "+ DBMahasiswa.MyColumns.NamaTabel,null);
//        Cursor cursor = ReadData.rawQuery("SELECT * FROM "+ DBMahasiswa.MyColumns.NamaTabel,null);

        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
            ListData.add(cursor.getString(0));//Mengambil Data Dari Kolom 1 (Nama)
            //Lalu Memasukan Semua Datanya kedalam ArrayList
        }
    }
}
