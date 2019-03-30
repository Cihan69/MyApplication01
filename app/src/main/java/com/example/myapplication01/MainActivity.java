package com.example.myapplication01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etnameSurname, eteEmail, etAdress;
    Button btnListele, btnKaydet;
    TextView tv_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unit();

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameSurname = etnameSurname.getText().toString();
                String adress = etAdress.getText().toString();
                String email = eteEmail.getText().toString();

                Ogrenci ogrenci = new Ogrenci(nameSurname, email, adress);


                VeriTabani veriTabani = new VeriTabani(getApplicationContext());
                long record_id = veriTabani.AddRecord(ogrenci);

                if (record_id == -1) {
                    Toast.makeText(MainActivity.this, " Oups!! Record can not add DATABASE...", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Record is succesfull...", Toast.LENGTH_SHORT).show();
                    etnameSurname.setText("");
                    eteEmail.setText("");
                    etAdress.setText("");
                }
            }
        });

        btnListele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listData();
            }
        });
    }

    public void listData() {

        VeriTabani db = new VeriTabani(getApplicationContext());

        List<Ogrenci> ogrenciList = new ArrayList<Ogrenci>();

        ogrenciList = db.AllRecordlist();

        StringBuilder stringBuilder = new StringBuilder();

        for (Ogrenci ogrenci : ogrenciList) {

             stringBuilder.append(ogrenci.getNameSurname() + "\n" + ogrenci.getEmail() + "\n" + ogrenci.getAdress() + "\n\n\n");
        }

        tv_list.setText(stringBuilder.toString());
    }

    private void unit() {

        etAdress = findViewById(R.id.etAdress);
        eteEmail = findViewById(R.id.eteEmail);
        etnameSurname = findViewById(R.id.etnameSurname);

        tv_list = findViewById(R.id.tv_list);

        btnKaydet = findViewById(R.id.btnKaydet);
        btnListele = findViewById(R.id.btnListele);

    }


}
