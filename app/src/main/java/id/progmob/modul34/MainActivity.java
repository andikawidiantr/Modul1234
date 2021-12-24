package id.progmob.modul34;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rview;
    private FloatingActionButton fab;
    private BiodataDatabase dbcenter;
    private DataAdapter data_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbcenter = new BiodataDatabase(this);
        rview = findViewById(R.id.recyclerView);
        rview.setLayoutManager(new LinearLayoutManager(this));
        data_adapter = new DataAdapter(this,dbcenter.getAllData());
        rview.setAdapter(data_adapter);
        data_adapter.swapCursor(dbcenter.getAllData());
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditData.class);
                startActivity(intent);
            }
        });

        data_adapter.setOnItemClickListenerBiodata(new DataAdapter.OnClickListenerBiodata() {
            @Override
            public void onItemClickBiodata(long id) {

                final Cursor cur = dbcenter.getData(id);
                cur.moveToFirst();

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilih Opsi");

                String[] options = {"Lihat Data", "Edit Data", "Hapus Data"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                final AlertDialog.Builder viewData = new AlertDialog.Builder(MainActivity.this);
                                LayoutInflater inflater = getLayoutInflater();
                                View dialogView = inflater.inflate(R.layout.view_data, null);
                                viewData.setView(dialogView);
                                viewData.setTitle("Lihat Data");

                                TextView tNim = (TextView)dialogView.findViewById(R.id.tv_nim);
                                TextView tNama = (TextView)dialogView.findViewById(R.id.tv_Nama);
                                TextView tHobby = (TextView)dialogView.findViewById(R.id.tv_Hobby);
                                TextView tJk = (TextView)dialogView.findViewById(R.id.tv_JK);
                                TextView tUmur = (TextView)dialogView.findViewById(R.id.tv_Umur);

                                tNim.setText("Nomor: " + cur.getString(cur.getColumnIndex(BiodataDatabase.nim)));
                                tNama.setText("Nama: " + cur.getString(cur.getColumnIndex(BiodataDatabase.nama)));
                                tJk.setText("Jenis Kelamin: " + cur.getString(cur.getColumnIndex(BiodataDatabase.jk)));
                                tHobby.setText("Hobby: " + cur.getString(cur.getColumnIndex(BiodataDatabase.hobby)));
                                tUmur.setText("Umur: " + cur.getString(cur.getColumnIndex(BiodataDatabase.umur)));

                                viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                viewData.show();
                        }
                        switch (which){
                            case 1:
                                Intent editdata = new Intent(MainActivity.this, AddEditData.class);
                                editdata.putExtra(BiodataDatabase.nim, id);
                                startActivity(editdata);
                        }
                        switch (which){
                            case 2:
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                                builder1.setMessage("Data ini akan dihapus.");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbcenter.deleteData(id);
                                        Toast.makeText(MainActivity.this, "Data Terhapus", Toast.LENGTH_SHORT).show();
                                        data_adapter.swapCursor(dbcenter.getAllData());
                                    }
                                });
                                builder1.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder1.create();
                                alertDialog.show();
                        }
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                //Toast.makeText(getApplicationContext(), "show item", Toast.LENGTH_LONG).show();

                //batas atas
//                TextView tNim = (TextView)dialogView.findViewById(R.id.tv_nim);
//                TextView tNama = (TextView)dialogView.findViewById(R.id.tv_Nama);
//                TextView tHobby = (TextView)dialogView.findViewById(R.id.tv_Hobby);
//                TextView tJk = (TextView)dialogView.findViewById(R.id.tv_JK);
//                TextView tUmur = (TextView)dialogView.findViewById(R.id.tv_Umur);
//
//                tNim.setText("Nomor: " + cur.getString(cur.getColumnIndex(BiodataDatabase.nim)));
//                tNama.setText("Nama: " + cur.getString(cur.getColumnIndex(BiodataDatabase.nama)));
//                tJk.setText("Jenis Kelamin: " + cur.getString(cur.getColumnIndex(BiodataDatabase.jk)));
//                tHobby.setText("Hobby: " + cur.getString(cur.getColumnIndex(BiodataDatabase.hobby)));
//                tUmur.setText("Umur: " + cur.getString(cur.getColumnIndex(BiodataDatabase.umur)));
//
//                viewData.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                viewData.show();
                //batas abwah


                //                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//                View view = inflater.inflate(R.layout.popup,null);
////                Intent editBiodata = new Intent(MainActivity.this, AddEditData.class);
////                editBiodata.putExtra(BiodataDatabase.nim,id);
////                startActivity(editBiodata);
//                linearViewData = view.findViewById(R.id.linearView);
//                linearEditData = view.findViewById(R.id.linearEdit);
//                linearDeleteData = view.findViewById(R.id.linearDelete);
//                btnBatal = view.findViewById(R.id.buttonPopup);
//
//                Dialog popupData = new Dialog(MainActivity.this);
//                popupData.setContentView(view);
//                popupData.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                popupData.setOnShowListener(new DialogInterface.OnShowListener() {
//                    @Override
//                    public void onShow(DialogInterface dialog) {
//                        linearViewData.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent editBiodata = new Intent(MainActivity.this, AddEditData.class);
//                                editBiodata.putExtra(BiodataDatabase.nim,id);
//                                startActivity(editBiodata);
//                            }
//                        });
//
//                        linearEditData.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Intent editBiodata = new Intent(MainActivity.this, AddEditData.class);
//                                editBiodata.putExtra(BiodataDatabase.nim,id);
//                                startActivity(editBiodata);
//                                popupData.dismiss();
//                            }
//                        });
//
//                        linearDeleteData.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                                builder.setTitle("Konfirmasi");
//                                builder.setMessage("Apakah Yakin Menghapus Data");
//                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dbcenter.deleteData(id);
//                                        popupData.dismiss();
//                                        data_adapter.swapCursor(dbcenter.getAllData());
//
//                                    }
//                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        popupData.dismiss();
//                                    }
//                                });
//                                AlertDialog popupKonfirmasi = builder.create();
//                                popupKonfirmasi.show();
//                            }
//                        });
//                        btnBatal.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                popupData.dismiss();
//                            }
//                        });
//                    }
//                });
//                //sampe sni
            }
        });
    }

    protected void onResume(){
        super.onResume();
        data_adapter.swapCursor(dbcenter.getAllData());
    }
}

