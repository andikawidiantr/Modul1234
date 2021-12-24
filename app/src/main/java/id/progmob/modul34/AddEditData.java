package id.progmob.modul34;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class AddEditData extends AppCompatActivity {
    public int pval = 0;
    private BiodataDatabase database;
    private long id;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_data);

        intent = getIntent();
        id = intent.getLongExtra(BiodataDatabase.nim,0);

        if (intent.hasExtra(BiodataDatabase.nim)){
            setTitle("Edit Biodata");
        }else{
            setTitle("Tambah Biodata");
        }

        EditText nim = (EditText)findViewById(R.id.isinim);
        EditText nama = (EditText)findViewById(R.id.isinama);
        RadioGroup jk = (RadioGroup)findViewById(R.id.isijeniskelamin);
        CheckBox hobby1 = (CheckBox) findViewById(R.id.checkBox);
        CheckBox hobby2 = (CheckBox) findViewById(R.id.checkBox2);
        CheckBox hobby3 = (CheckBox) findViewById(R.id.checkBox3);
        SeekBar sBar = (SeekBar) findViewById(R.id.umur);
        TextView tView = (TextView) findViewById(R.id.angkaumur);

        database = new BiodataDatabase(this);

        final Button reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {
                nim.setText(null);
                nama.setText(null);
                jk.clearCheck();
                hobby1.setChecked(false);
                hobby2.setChecked(false);
                hobby3.setChecked(false);
                sBar.setProgress(0);
                tView.setText(null);
            }
        });

        tView.setText(sBar.getProgress() + "/" + sBar.getMax());
        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText(pval + "/" + seekBar.getMax());
            }
        });

        final Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v) {

//                EditText nimuser = (EditText)findViewById(R.id.isinim);
                String usernim = nim.getText().toString();

//                EditText namauser = (EditText)findViewById(R.id.isinama);
                String username = nama.getText().toString();

//                RadioGroup jeniskelamin = (RadioGroup) findViewById(R.id.isijeniskelamin);
                // get the selected RadioButton of the group
                RadioButton selectedRadioButton  = (RadioButton)findViewById(jk.getCheckedRadioButtonId());
                //get RadioButton text
                String jeniskelamin = selectedRadioButton.getText().toString();

//                CheckBox hobby1 = (CheckBox) findViewById(R.id.checkBox);
//                CheckBox hobby2 = (CheckBox) findViewById(R.id.checkBox2);
//                CheckBox hobby3 = (CheckBox) findViewById(R.id.checkBox3);

                String msg="";

                if(hobby1.isChecked())
                    msg = msg + "Makan ";
                if(hobby2.isChecked())
                    msg = msg + "Belajar ";
                if(hobby3.isChecked())
                    msg = msg + "Tidur ";



                if (usernim.isEmpty()){
                    nim.setError("NIM tidak boleh Kosong");
                }else if (username.isEmpty()){
                    nama.setError("Nama tidak boleh Kosong");
                }else if (jeniskelamin.isEmpty()) {
                    nama.setError("Jenis Kelamin tidak boleh Kosong");
                }else if (msg.isEmpty()) {
                    nama.setError("Hobby tidak boleh Kosong");
//   umur             }else if (pval.isEmpty()) {
//                    nama.setError("Umur tidak boleh Kosong");
                }else{
                    ContentValues values = new ContentValues();
                    values.put(BiodataDatabase.nim,usernim);
                    values.put(BiodataDatabase.nama,username);
                    values.put(BiodataDatabase.jk,jeniskelamin);
                    values.put(BiodataDatabase.hobby,msg);
                    values.put(BiodataDatabase.umur,pval);
                    if (intent.hasExtra(BiodataDatabase.nim)){
                        database.updateData(values, id);
                    }else{
                        database.insertData(values);
                    }
                    finish();
                }
            }
        });
       getBiodata();
    }

//    private void getBiodata(){
//        Cursor cursor = database.getData(id);
//        if (cursor.moveToFirst()){
//            EditText nim1 = (EditText)findViewById(R.id.isinim);
//            EditText nama1 = (EditText)findViewById(R.id.isinama);
//
//            String nim = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nim));
//            String nama = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nama));
//
//            nama1.setText(nama);
//            nim1.setText(nim);
//        }
//    }

    private void getBiodata(){
        Cursor cursor = database.getData(id);
        if(cursor.moveToFirst()){
            String nimdb = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nim));
            String namadb = cursor.getString(cursor.getColumnIndex(BiodataDatabase.nama));
            String jkdb = cursor.getString(cursor.getColumnIndex(BiodataDatabase.jk));
            String hobbydb = cursor.getString(cursor.getColumnIndex(BiodataDatabase.hobby));
            int umurdb = cursor.getInt(cursor.getColumnIndex(BiodataDatabase.umur));

            EditText nim1 = (EditText)findViewById(R.id.isinim);
            EditText nama1 = (EditText)findViewById(R.id.isinama);
            RadioButton female = (RadioButton)findViewById(R.id.female);
            RadioButton  male = (RadioButton)findViewById(R.id.male);
            SeekBar seekBarUmur = (SeekBar) findViewById(R.id.umur);
            TextView angkaumur = (TextView) findViewById(R.id.angkaumur);

            nim1.setText(nimdb);
            nama1.setText(namadb);
            if(jkdb.equals("Perempuan")){
                female.setChecked(true);
            }else if(jkdb.equals("Laki - Laki")){
                male.setChecked(true);
            }

//            if(keluhandb.equals("Halusinasi") || keluhandb.equals(",Halusinasi")){
//                checkBoxHalu.setChecked(true);
//            }else if(keluhandb.equals("Stress")|| keluhandb.equals(",Stress")){
//                checkBoxStress.setChecked(true);
//            }else if(keluhandb.equals("Gangguan Makan")|| keluhandb.equals(",Gangguan Makan")){
//                checkBoxMakan.setChecked(true);
//            }else if(keluhandb.equals("Susah Tidur")|| keluhandb.equals(",Susah Tidur")){
//                checkBoxTidur.setChecked(true);
//            }

            seekBarUmur.setProgress(umurdb);
            seekBarUmur.setMax(100);
            angkaumur.setText(umurdb + "/" + seekBarUmur.getMax());
            int persentase = umurdb;

        }
    }

}