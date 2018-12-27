package com.jmj.app.journeymakerjobs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Visit website http://www.whats-online.info
 * **/

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "savedjoblist";

    // Contacts table name
    private static final String TABLE_CONTACTS = "savedjobs";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_JOBID = "job_id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_COM = "company";
    private static final String KEY_DES = "description";
    private static final String KEY_ADDINFO = "add_info";
    private static final String KEY_NATURE = "nature";
    private static final String KEY_LOC = "location";
    private static final String KEY_EDU = "education";
    private static final String KEY_EXP = "experience";
    private static final String KEY_SAL = "salary";
    private static final String KEY_VAC = "vacancy";
    private static final String KEY_LD = "last_date";
    private static final String KEY_POTO = "poto";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "("
               + KEY_ID +" INTEGER PRIMARY KEY,"
               + KEY_JOBID +" TEXT,"
               + KEY_TITLE +" TEXT,"
               + KEY_COM +" TEXT,"
               + KEY_DES +" TEXT,"
               + KEY_ADDINFO +" TEXT,"
               + KEY_NATURE +" TEXT,"
               + KEY_LOC +" TEXT,"
               + KEY_EDU +" TEXT,"
               + KEY_EXP +" TEXT,"
               + KEY_SAL +" TEXT,"
               + KEY_VAC +" TEXT,"
               + KEY_LD +" TEXT,"
               + KEY_POTO  +" BLOB" + ")";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addContacts(Contact contact){
      SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_JOBID, contact.getJob_id());
        values.put(KEY_TITLE, contact.getTitle());
        values.put(KEY_COM, contact.getCompany());
        values.put(KEY_DES, contact.getDescription());
        values.put(KEY_ADDINFO, contact.getAdd_info());
        values.put(KEY_NATURE, contact.getNature());
        values.put(KEY_LOC, contact.getLocation());
        values.put(KEY_EDU, contact.getEducation());
        values.put(KEY_EXP, contact.getExperience());
        values.put(KEY_SAL, contact.getSalary());
        values.put(KEY_VAC, contact.getVacancy());
        values.put(KEY_LD, contact.getLast_date());
        values.put(KEY_POTO, contact.getImage() );

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    /**
     *Getting All Contacts
     **/

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setJob_id(cursor.getString(1));
                contact.setTitle(cursor.getString(2));
                contact.setCompany(cursor.getString(3));
                contact.setDescription(cursor.getString(4));
                contact.setAdd_info(cursor.getString(5));
                contact.setNature(cursor.getString(6));
                contact.setLocation(cursor.getString(7));
                contact.setEducation(cursor.getString(8));
                contact.setExperience(cursor.getString(9));
                contact.setSalary(cursor.getString(10));
                contact.setVacancy(cursor.getString(11));
                contact.setLast_date(cursor.getString(12));
                contact.setImage(cursor.getBlob(13));

                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        return contactList;
    }

    public void deleteContact(int Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(Id) });
        db.close();
    }

}
