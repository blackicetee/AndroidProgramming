package com.example.Uebung8;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getName();
    private static ArrayList<Activity> activities = new ArrayList<>();
    final String LOG_TAG = "myLogs";
    ListView lvMain;
    Button btnShowPeople;
    Button btnSelect;
    Button btnEdit;
    Button btnSearch;
    Button btnCall;
    Button btnDrop;
    Button btnClose;
    List<List<String>> table = null;
    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        activities.add(this);

        lvMain = (ListView) findViewById(R.id.lvMain);
        btnShowPeople = (Button) findViewById(R.id.btnShowPeople);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnDrop = (Button) findViewById(R.id.btnDrop);
        btnClose = (Button) findViewById(R.id.btnClose);

        btnShowPeople.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnCall.setOnClickListener(this);
        btnDrop.setOnClickListener(this);
        btnClose.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(db, 3, 4);
        dbHelper.createCompleteDataBase(db);
    }

    @Override
    public void onBackPressed() {
        MainActivity.finishAll();
        return;
    }

    @Override
    public void onDestroy() {
        dbHelper.clearAllTables(db);
        db.close();
        Toast.makeText(this, "DB is closed and tables are cleared!!", Toast.LENGTH_LONG).show();
        super.onDestroy();
        activities.remove(this);
    }

    public static void finishAll() {
        for (Activity activity : activities)
            activity.finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowPeople:
                table = dbHelper.getAllInformation(db.rawQuery(dbHelper.RAWQUERY, null));
                insertTableInList(table);
                break;
            case R.id.btnSelect:
                Intent selectIntent = new Intent(this, Select.class);
                startActivityForResult(selectIntent, 0);
                break;
            case R.id.btnEdit:
                if (table == null) {
                    Toast.makeText(this, "The list is empty!", Toast.LENGTH_LONG).show();
                }
                else if (!table.isEmpty()) {
                    String[] query = {table.get(table.size() - 1).get(1)};
                    String editPersonQuery = dbHelper.EDITINFOQUERY + " where name = ? ";
                    List<List<String>> tempTable = dbHelper.getAllIDsAndName(db.rawQuery(editPersonQuery, query));
                    Intent editIntent = new Intent(this, Edit.class);
                    editIntent.putExtra("UpdateName", tempTable.get(tempTable.size() - 1).get(1));
                    editIntent.putExtra("UpdateRoomID", tempTable.get(tempTable.size() - 1).get(2));
                    editIntent.putExtra("UpdateStaticPhoneID", tempTable.get(tempTable.size() - 1).get(3));
                    editIntent.putExtra("UpdateMobilePhoneID", tempTable.get(tempTable.size() - 1).get(4));
                    editIntent.putExtra("UpdatePosition", tempTable.get(tempTable.size() - 1).get(5));
                    startActivityForResult(editIntent, 1);
                }
                else {
                    Toast.makeText(this, "The list is empty!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnSearch:
                Intent searchIntent = new Intent(this, Search.class);
                startActivityForResult(searchIntent, 2);
                break;
            case R.id.btnCall:
                if (table == null) {
                    Toast.makeText(this, "No static number found!", Toast.LENGTH_LONG).show();
                }
                else if (table.size() > 0) {
                    String staticNumber = table.get(table.size() - 1).get(3);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + staticNumber));
                    startActivity(callIntent);
                }
                else {
                    Toast.makeText(this, "No static number found!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDrop:
                if (dropLastTableElement())
                    insertTableInList(table);
                break;
            case R.id.btnClose:
                MainActivity.finishAll();
                break;
        }


    }

    private void searchQueryAndInsertInListView(String[] whereArgs, String rawQuery) {
        List<List<String>> tempTable = dbHelper.getAllInformation(db.rawQuery(rawQuery, whereArgs));
        if (tempTable.size() > 0) {
            if (table == null)
                table = tempTable;
            else {
                for (int i = 0; i < tempTable.size(); i++) {
                    table.add(tempTable.get(i));
                }
            }
            insertTableInList(table);
        }
        else
            Toast.makeText(this, "Not found in DB!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 0:
                if (data == null) {
                    return;
                }
                Log.d(LOG_TAG, "--- onActivityResult Select person---");
                String[] selectWhereArgs = {data.getStringExtra("SelectQuery")};
                searchQueryAndInsertInListView(selectWhereArgs, dbHelper.RAWQUERY + " where name = ? ");
                break;
            case 1:
                if (data == null) {
                    return;
                }
                Log.d(LOG_TAG, "--- onActivityResult Edit person---");
                String lastPersonID = table.get(table.size() - 1).get(0);
                dbHelper.updatePeople(db, lastPersonID, data.getStringExtra("UpdateName"), data.getStringExtra("UpdateRoomID"), data.getStringExtra("UpdateStaticPhoneID"), data.getStringExtra("UpdateMobilePhoneID"), data.getStringExtra("UpdatePositionID"));
                if (dropLastTableElement()) {
                    String[] updateWhereArgs = {data.getStringExtra("UpdateName")};
                    searchQueryAndInsertInListView(updateWhereArgs, dbHelper.RAWQUERY + " where name = ? ");
                }
                break;
            case 2:
                if (data == null) {
                    return;
                }
                Log.d(LOG_TAG, "--- onActivityResult Search person---");
                //SearchName SearchRoom SearchPosition
                if (!data.getStringExtra("SearchName").equals("")) {
                    if (!data.getStringExtra("SearchRoom").equals("")) {
                        if (!data.getStringExtra("SearchPosition").equals("")) {
                            String[] searchWhereArgs = {data.getStringExtra("SearchName"), data.getStringExtra("SearchRoom"), data.getStringExtra("SearchPosition")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where name = ? and roomName = ? and positionName = ? ");
                        }
                        else {
                            String[] searchWhereArgs = {data.getStringExtra("SearchName"), data.getStringExtra("SearchRoom")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where name = ? and roomName = ? ");
                        }
                    }
                    else {
                        if (!data.getStringExtra("SearchPosition").equals("")) {
                            String[] searchWhereArgs = {data.getStringExtra("SearchName"), data.getStringExtra("SearchPosition")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where name = ? and positionName = ? ");
                        }
                        else {
                            String[] searchWhereArgs = {data.getStringExtra("SearchName")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where name = ? ");
                        }
                    }
                }
                else {
                    if (!data.getStringExtra("SearchRoom").equals("")) {
                        if (!data.getStringExtra("SearchPosition").equals("")) {
                            String[] searchWhereArgs = {data.getStringExtra("SearchRoom"), data.getStringExtra("SearchPosition")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where roomName = ? and positionName = ? ");
                        }
                        else {
                            String[] searchWhereArgs = {data.getStringExtra("SearchRoom")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where roomName = ? ");
                        }
                    }
                    else {
                        if (!data.getStringExtra("SearchPosition").equals("")) {
                            String[] searchWhereArgs = {data.getStringExtra("SearchPosition")};
                            searchQueryAndInsertInListView(searchWhereArgs, dbHelper.RAWQUERY + " where positionName = ? ");
                        }
                        else {
                            table = dbHelper.getAllInformation(db.rawQuery(dbHelper.RAWQUERY, null));
                            insertTableInList(table);
                        }
                    }
                }
                break;
        }

    }

    private boolean dropLastTableElement() {
        if (table == null) {
            Toast.makeText(this, "The list is empty!", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!table.isEmpty()) {
            table.remove(table.size() - 1);
            return true;
        }
        else {
            Toast.makeText(this, "The list is empty!", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    private void insertTableInList(List<List<String>> table) {
        List<String> resultList = new ArrayList<>();
        Iterator<List<String>> listIterator = table.iterator();
        while (listIterator.hasNext()) {
            resultList.add(concatStringListToString(listIterator.next()));
        }

        String[] stringArray = resultList.toArray(new String[resultList.size()]);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1, stringArray);
        lvMain.setAdapter(adapter);
    }

    class DBHelper extends SQLiteOpenHelper {

        public static final String RAWQUERY = "select PL.peopleID, name, roomName, staticPhoneNumber, mobilePhoneNumber, positionName "
                + "from people as PL "
                + "left join room as RM on PL.roomID = RM.roomID "
                + "left join staticPhone as SP on PL.staticPhoneID = SP.staticPhoneID "
                + "left join mobilePhone as MP on PL.mobilePhoneID = MP.mobilePhoneID "
                + "left join position as PS on PL.positionID = PS.positionID";

        public static final String EDITINFOQUERY = "select PL.peopleID, name, PL.roomID, PL.staticPhoneID, PL.mobilePhoneID, PL.positionID "
                + "from people as PL "
                + "left join room as RM on PL.roomID = RM.roomID "
                + "left join staticPhone as SP on PL.staticPhoneID = SP.staticPhoneID "
                + "left join mobilePhone as MP on PL.mobilePhoneID = MP.mobilePhoneID "
                + "left join position as PS on PL.positionID = PS.positionID";

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
                    + "mobilePhoneID text,"
                    + "positionID text" + ");");

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
                    + "positionName text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(LOG_TAG, "Upgrading database to " + newVersion);
            db.execSQL("drop table if exists " + "people");
            db.execSQL("drop table if exists " + "room");
            db.execSQL("drop table if exists " + "staticPhone");
            db.execSQL("drop table if exists " + "mobilePhone");
            db.execSQL("drop table if exists " + "position");
            onCreate(db);
        }

        public void addPeople(SQLiteDatabase db, String peopleID, String name, String roomID, String staticPhoneID, String mobilePhoneID, String positionID) {
            Log.d(LOG_TAG, "--- on addPeople ---");
            ContentValues cv = new ContentValues();
            cv.put("peopleID", peopleID);
            cv.put("name", name);
            cv.put("roomID", roomID);
            cv.put("staticPhoneID", staticPhoneID);
            cv.put("mobilePhoneID", mobilePhoneID);
            cv.put("positionID", positionID);
            long rowID = db.insert("people", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = peopleID_" + padding + rowID);
        }

        public void updatePeople(SQLiteDatabase db, String peopleID, String name, String roomID, String staticPhoneID, String mobilePhoneID, String positionID) {
            Log.d(LOG_TAG, "--- on updatePeople ---");
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("roomID", roomID);
            cv.put("staticPhoneID", staticPhoneID);
            cv.put("mobilePhoneID", mobilePhoneID);
            cv.put("positionID", positionID);
            String[] whereArg= {peopleID};
            long rowID = db.update("people", cv, " peopleID = ? ", whereArg);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row updated, ID = peopleID_" + padding + rowID);
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

        public void addPosition(SQLiteDatabase db, String positionID, String positionName) {
            Log.d(LOG_TAG, "--- on addPosition ---");
            ContentValues cv = new ContentValues();
            cv.put("positionID", positionID);
            cv.put("positionName", positionName);
            long rowID = db.insert("position", null, cv);
            String padding = "";
            if (rowID < 10) padding = "00";
            if (rowID >= 10 && rowID < 100) padding = "0";
            Log.d(LOG_TAG, "row inserted, ID = positionID_" + padding + rowID);
        }

        public List<List<String>> getAllInformation(Cursor c) {
            Log.d(LOG_TAG, "--- on getAllInformation ---");
            List<List<String>> table = new ArrayList<>();
            if (c.moveToFirst()) {
                //find the column numbers using names
                int peopleIDColIndex = c.getColumnIndex("peopleID");
                int nameColIndex = c.getColumnIndex("name");
                int roomColIndex = c.getColumnIndex("roomName");
                int staticPhoneColIndex = c.getColumnIndex("staticPhoneNumber");
                int mobilePhoneColIndex = c.getColumnIndex("mobilePhoneNumber");
                int positionColIndex = c.getColumnIndex("positionName");
                do {
                    List<String> temp = new ArrayList<>();
                    temp.add(c.getString(peopleIDColIndex));
                    temp.add(c.getString(nameColIndex));
                    temp.add(c.getString(roomColIndex));
                    temp.add(c.getString(staticPhoneColIndex));
                    temp.add(c.getString(mobilePhoneColIndex));
                    temp.add(c.getString(positionColIndex));
                    table.add(temp);

                    Log.d(LOG_TAG,
                            "People ID = " + c.getString(peopleIDColIndex) +
                                    ", NAME = " + c.getString(nameColIndex) +
                                    ", ROOM = " + c.getString(roomColIndex) +
                                    ", STATIC-PHONE = " + c.getString(staticPhoneColIndex) +
                                    ", MOBILE-PHONE = " + c.getString(mobilePhoneColIndex) +
                                    ", POSITION = " + c.getString(positionColIndex));
                } while (c.moveToNext());
            } else {
                Log.d(LOG_TAG, "0 rows");
            }
            return table;
        }

        public List<List<String>> getAllIDsAndName(Cursor c) {
            Log.d(LOG_TAG, "--- on getAllIDsAndName ---");
            List<List<String>> table = new ArrayList<>();
            if (c.moveToFirst()) {
                //find the column numbers using names
                int peopleIDColIndex = c.getColumnIndex("peopleID");
                int nameColIndex = c.getColumnIndex("name");
                int roomColIndex = c.getColumnIndex("roomID");
                int staticPhoneColIndex = c.getColumnIndex("staticPhoneID");
                int mobilePhoneColIndex = c.getColumnIndex("mobilePhoneID");
                int positionColIndex = c.getColumnIndex("positionID");
                do {
                    List<String> temp = new ArrayList<>();
                    temp.add(c.getString(peopleIDColIndex));
                    temp.add(c.getString(nameColIndex));
                    temp.add(c.getString(roomColIndex));
                    temp.add(c.getString(staticPhoneColIndex));
                    temp.add(c.getString(mobilePhoneColIndex));
                    temp.add(c.getString(positionColIndex));
                    table.add(temp);

                    Log.d(LOG_TAG,
                            "People ID = " + c.getString(peopleIDColIndex) +
                                    ", NAME = " + c.getString(nameColIndex) +
                                    ", ROOM ID = " + c.getString(roomColIndex) +
                                    ", STATIC-PHONE ID = " + c.getString(staticPhoneColIndex) +
                                    ", MOBILE-PHONE ID = " + c.getString(mobilePhoneColIndex) +
                                    ", POSITION ID = " + c.getString(positionColIndex));
                } while (c.moveToNext());
            } else {
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
            int clearCountPosition = db.delete("position", null, null);
            //db.delete("SQLITE_SEQUENCE", "name = 'people'", null);
            Log.d(LOG_TAG, "deleted rows count people      = " + clearCountPeople);
            Log.d(LOG_TAG, "deleted rows count room        = " + clearCountRoom);
            Log.d(LOG_TAG, "deleted rows count staticPhone = " + clearCountStaticPhone);
            Log.d(LOG_TAG, "deleted rows count mobilePhone = " + clearCountMobilePhone);
            Log.d(LOG_TAG, "deleted rows count position    = " + clearCountPosition);
        }

        public void createCompleteDataBase(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- Creating Complete DataBase ---");
            addPeople(db, "peopleID_001", "Peter Griffin", "roomID_001", "staticPhoneID_001", "mobilePhoneID_001", "positionID_001");
            addPeople(db, "peopleID_002", "Mark Wallberg", "roomID_002", "staticPhoneID_002", "mobilePhoneID_002", "positionID_002");
            addPeople(db, "peopleID_003", "Bill Gates", "roomID_003", "staticPhoneID_003", "mobilePhoneID_003", "positionID_003");
            addPeople(db, "peopleID_004", "Dieter Bohlen", "roomID_003", "staticPhoneID_004", "mobilePhoneID_004", "positionID_002");
            addPeople(db, "peopleID_005", "Jason Statham", "roomID_003", "staticPhoneID_005", "", "positionID_002");
            addPeople(db, "peopleID_006", "Brad Pit", "roomID_004", "staticPhoneID_006", "", "positionID_002");
            addPeople(db, "peopleID_007", "Michael Mueller", "roomID_004", "staticPhoneID_007", "", "positionID_002");
            addPeople(db, "peopleID_008", "Andre Marr", "roomID_005", "staticPhoneID_008", "", "positionID_002");
            addPeople(db, "peopleID_009", "Jason Marz", "roomID_005", "staticPhoneID_009", "", "positionID_003");
            addPeople(db, "peopleID_010", "Doloris Potter", "roomID_006", "staticPhoneID_010", "", "positionID_003");

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

            addPosition(db, "positionID_001", "Chef");
            addPosition(db, "positionID_002", "IT");
            addPosition(db, "positionID_003", "Economist");
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
