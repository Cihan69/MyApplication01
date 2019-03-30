package com.example.myapplication01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ogrenci_database";
    private static final String TABLE_NAME = "ogrenci_tablosu";
    private static final int DATABASE_VERSION = 1;

    private static final String AD = "ad_soyad";
    private static final String MAIL = "mail";
    private static final String ADRES = "adres";
    private static final String ID = "_id";

    public VeriTabani(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table_create = " CREATE TABLE " + TABLE_NAME
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AD + " TEXT, "
                + MAIL + " TEXT, "
                + ADRES + " TEXT);";
        db.execSQL(table_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long AddRecord(Ogrenci ogrenci) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(AD, ogrenci.getNameSurname());
        cv.put(MAIL, ogrenci.getEmail());
        cv.put(ADRES, ogrenci.getAdress());

        long record_id = db.insert(TABLE_NAME, null, cv);  // Eğer long_id geriye -1 değeri döderir ise kayıt eklenemedi demektir
        db.close();

        return record_id;
    }

    public List<Ogrenci> AllRecordlist() {

        SQLiteDatabase db = this.getReadableDatabase();

        String sutunlar[] = new String[]{AD, MAIL, ADRES};
        Cursor cursor = db.query(TABLE_NAME, sutunlar, null, null, null, null, null);
        int adsirano = cursor.getColumnIndex(AD);
        int mailsirano = cursor.getColumnIndex(MAIL);
        int adressirano = cursor.getColumnIndex(ADRES);

        List<Ogrenci> ogrenciList = new ArrayList<Ogrenci>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Ogrenci ogrenci = new Ogrenci();
            ogrenci.setNameSurname(cursor.getString(adsirano));
            ogrenci.setAdress(cursor.getString(adressirano));
            ogrenci.setEmail(cursor.getString(mailsirano));

            ogrenciList.add(ogrenci);

        }

        db.close();

        return ogrenciList;
    }
}
