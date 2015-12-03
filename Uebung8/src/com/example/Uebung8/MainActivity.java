package com.example.Uebung8;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    final String LOG_TAG = "myLogs";
    ListView lvMain;
    Button btnShowPeople;
    List<List<String>> table;
    DBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lvMain = (ListView) findViewById(R.id.lvMain);
        btnShowPeople = (Button) findViewById(R.id.btnShowPeople);
        btnShowPeople.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 3, 4);
        dbHelper.createCompleteDataBase(db);
        table = dbHelper.getAllInformation(db.rawQuery(dbHelper.RAWQUERY, null));
        dbHelper.clearAllTables(db);
        db.close();
    }

    @Override
    public void onClick(View v) {
        List<String> resultList = new ArrayList<>();
        Iterator<List<String>> listIterator = table.iterator();
        while (listIterator.hasNext()) {
            resultList.add(concatStringListToString(listIterator.next()));
        }

        String[] stringArray = resultList.toArray(new String[resultList.size()]);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, stringArray);
        lvMain.setAdapter(adapter);
    }

    class DBHelper extends SQLiteOpenHelper {

        public static final String RAWQUERY = "select name, roomName, staticPhoneNumber, mobilePhoneNumber "
                + "from people as PL "
                + "left join room as RM on PL.roomID = RM.roomID "
                + "left join staticPhone as SP on PL.staticPhoneID = SP.staticPhoneID "
                + "left join mobilePhone as MP on PL.mobilePhoneID = MP.mobilePhoneID ";

        public DBHelper(Context context) {
            super(context, "myDB1", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate Database ---");
            //Create Table people with some columns
            db.execSQL("create table people("
                    + "peopleID text primary key,"
                    + "name text,"
                    + "roomID text,"
                    + "staticPhoneID text,"
                    + "mobilePhoneID text" + ");");
            
            db.execSQL("create table room("
                    + "roomID text primary key,"
                    + "roomName text" + ");");

            db.execSQL("create table staticPhone("
                    + "staticPhoneID text primary key,"
                    + "staticPhoneNumber text,"
                    + "roomID text" + ");");

            db.execSQL("create table mobilePhone("
                    + "mobilePhoneID text primary key,"
                    + "mobilePhoneNumber text,"
                    + "peopleID text" + ");");

            db.execSQL("create table position("
                    + "positionID text primary key,"
                    + "mobilePhoneNumber text,"
                    + "peopleID text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, "Upgrading database to " + newVersion);
            db.execSQL("drop table if exists " + "people");
            db.execSQL("drop table if exists " + "room");
            db.execSQL("drop table if exists " + "staticPhone");
            db.execSQL("drop table if exists " + "mobilePhone");
            onCreate(db);
        }

        public void addPeople(SQLiteDatabase db, String peopleID, String name, String roomID, String staticPhoneID, String mobilePhoneID) {
            Log.d(LOG_TAG, "--- on addPeople ---");
            ContentValues cv = new ContentValues();
            cv.put("peopleID", peopleID);
            cv.put("name", name);
            cv.put("roomID", roomID);
            cv.put("staticPhoneID", staticPhoneID);
            cv.put("mobilePhoneID", mobilePhoneID);
            long rowID = db.insert("people", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = peopleID_" + padding + rowID);
        }

        public void addRoom(SQLiteDatabase db, String roomID, String roomName) {
            Log.d(LOG_TAG, "--- on addRoom ---");
            ContentValues cv = new ContentValues();
            cv.put("roomID", roomID);
            cv.put("roomName", roomName);
            long rowID = db.insert("room", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = roomID_" + padding + rowID);
        }
        public void addStaticPhone(SQLiteDatabase db, String staticPhoneID, String staticPhoneNumber, String roomID) {
            Log.d(LOG_TAG, "--- on addStaticPhone ---");
            ContentValues cv = new ContentValues();
            cv.put("staticPhoneID", staticPhoneID);
            cv.put("staticPhoneNumber", staticPhoneNumber);
            cv.put("roomID", roomID);
            long rowID = db.insert("staticPhone", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = staticPhoneID_" + padding + rowID);
        }
        public void addMobilePhone(SQLiteDatabase db, String mobilePhoneID, String mobilePhoneNumber, String peopleID) {
            Log.d(LOG_TAG, "--- on addMobilePhone ---");
            ContentValues cv = new ContentValues();
            cv.put("mobilePhoneID", mobilePhoneID);
            cv.put("mobilePhoneNumber", mobilePhoneNumber);
            cv.put("peopleID", peopleID);
            long rowID = db.insert("mobilePhone", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = mobilePhoneID_" + padding + rowID);
        }

        public List<List<String>> getAllInformation(Cursor c) {
            Log.d(LOG_TAG, "--- on getAllInformation ---");
            List<List<String>> table = new ArrayList<>();
            if (c.moveToFirst()) {
                //find the column numbers using names
                int nameColIndex = c.getColumnIndex("name");
                int roomColIndex = c.getColumnIndex("roomName");
                int staticPhoneColIndex = c.getColumnIndex("staticPhoneNumber");
                int mobilePhoneColIndex = c.getColumnIndex("mobilePhoneNumber");
                do {
                    List<String> temp = new ArrayList<>();
                    temp.add("Name: " + c.getString(nameColIndex));
                    temp.add("Room: " + c.getString(roomColIndex));
                    temp.add("StaticPhoneNr: " + c.getString(staticPhoneColIndex));
                    temp.add("MobilePhoneNr: " + c.getString(mobilePhoneColIndex));
                    table.add(temp);

                    Log.d(LOG_TAG,
                            "NAME = " + c.getString(nameColIndex) +
                            ", STATIC-PHONE = " + c.getString(staticPhoneColIndex) +
                            ", MOBILE-PHONE = " + c.getString(mobilePhoneColIndex) +
                            ", ROOM = " + c.getString(roomColIndex));
                } while (c.moveToNext());
            }
            else {
                Log.d(LOG_TAG, "0 rows");
            }
            return table;
        }
        
        public void clearAllTables(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- on clearAllTables ---");
            int clearCountPeople = db.delete("people", null, null);
            int clearCountRoom = db.delete("room", null, null);
            int clearCountStaticPhone = db.delete("staticPhone", null, null);
            int clearCountMobilePhone = db.delete("mobilePhone", null, null);
            //db.delete("SQLITE_SEQUENCE", "name = 'people'", null);
            Log.d(LOG_TAG, "deleted rows count people      = " + clearCountPeople);
            Log.d(LOG_TAG, "deleted rows count room        = " + clearCountRoom);
            Log.d(LOG_TAG, "deleted rows count staticPhone = " + clearCountStaticPhone);
            Log.d(LOG_TAG, "deleted rows count mobilePhone = " + clearCountMobilePhone);
        }

        public void createCompleteDataBase(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- Creating Complete DataBase ---");
            addPeople(db, "peopleID_001", "Peter Griffin", "roomID_001", "staticPhoneID_001", "mobilePhoneID_001");
            addPeople(db, "peopleID_002", "Mark Wallberg", "roomID_002", "staticPhoneID_002", "mobilePhoneID_002");
            addPeople(db, "peopleID_003", "Bill Gates", "roomID_003", "staticPhoneID_003", "mobilePhoneID_003");
            addPeople(db, "peopleID_004", "Dieter Bohlen", "roomID_003", "staticPhoneID_004", "mobilePhoneID_004");
            addPeople(db, "peopleID_005", "Jason Sttham", "roomID_003", "staticPhoneID_005", "");
            addPeople(db, "peopleID_006", "Brad Pit", "roomID_004", "staticPhoneID_006", "");
            addPeople(db, "peopleID_007", "Michael Mueller", "roomID_004", "staticPhoneID_007", "");
            addPeople(db, "peopleID_008", "Andre Marr", "roomID_005", "staticPhoneID_008", "");
            addPeople(db, "peopleID_009", "Jason Marz", "roomID_005", "staticPhoneID_009", "");
            addPeople(db, "peopleID_010", "Doloris Potter", "roomID_006", "staticPhoneID_010", "");

            addRoom(db, "roomID_001", "C-601");
            addRoom(db, "roomID_002", "C-602");
            addRoom(db, "roomID_003", "C-603");
            addRoom(db, "roomID_004", "C-604");
            addRoom(db, "roomID_005", "C-605");
            addRoom(db, "roomID_006", "C-606");

            addStaticPhone(db, "staticPhoneID_001", "030-01", "roomID_001");
            addStaticPhone(db, "staticPhoneID_002", "030-02", "roomID_002");
            addStaticPhone(db, "staticPhoneID_003", "030-03", "roomID_003");
            addStaticPhone(db, "staticPhoneID_004", "030-04", "roomID_003");
            addStaticPhone(db, "staticPhoneID_005", "030-05", "roomID_003");
            addStaticPhone(db, "staticPhoneID_006", "030-06", "roomID_004");
            addStaticPhone(db, "staticPhoneID_007", "030-07", "roomID_004");
            addStaticPhone(db, "staticPhoneID_008", "030-08", "roomID_005");
            addStaticPhone(db, "staticPhoneID_009", "030-09", "roomID_005");
            addStaticPhone(db, "staticPhoneID_010", "030-10", "roomID_006");

            addMobilePhone(db, "mobilePhoneID_001", "0172-01", "peopleID_001");
            addMobilePhone(db, "mobilePhoneID_002", "0172-02", "peopleID_002");
            addMobilePhone(db, "mobilePhoneID_003", "0172-03", "peopleID_003");
            addMobilePhone(db, "mobilePhoneID_004", "0172-04", "peopleID_004");
        }
    }

    private String concatStringListToString(List<String> stringList) {
        String result = "";
        Iterator<String> stringIterator = stringList.iterator();
        while (stringIterator.hasNext()) {
            result += stringIterator.next() + ", ";
        }
        return result;
    }
}
