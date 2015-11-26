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
    Button btnShowEmployees;
    List<List<String>> table;
    DBHelper dbHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lvMain = (ListView) findViewById(R.id.lvMain);
        btnShowEmployees = (Button) findViewById(R.id.btnShowEmployees);
        btnShowEmployees.setOnClickListener(this);


        dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbHelper.addColumn(db, "Peter Griffin", "030-01","0172-01", "C-601 with TV");
        dbHelper.addColumn(db, "Mark Wallberg", "030-02","0172-02", "C-601 with TV");
        dbHelper.addColumn(db, "Eva Marie", "030-03","0172-03", "C-602 without TV");
        dbHelper.addColumn(db, "Charlie Sheen", "030-04","", "C-602 without TV");
        dbHelper.addColumn(db, "Linus Torwald", "030-05","", "C-603 with TV");
        dbHelper.addColumn(db, "Bill Gates", "030-06","", "C-603 with TV");
        dbHelper.addColumn(db, "Steve Jobs RIP", "030-07","", "C-604 without TV");
        dbHelper.addColumn(db, "Mark Zuckerberg", "030-08","", "C-604 without TV");
        dbHelper.addColumn(db, "Denise Milani", "030-09","0172-09", "C-605 with TV");
        dbHelper.addColumn(db, "Jordan Carver", "030-10", "0172-10", "C-606 with TV");
        table = dbHelper.readTable(db.query("employees", null, null, null, null, null, null));
        dbHelper.clearTable(db);
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


        public DBHelper(Context context) {
            super(context, "myDB1", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- onCreate Database ---");
            //Create Table with some columns
            db.execSQL("create table employees("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "staticPhone text,"
                    + "mobilePhone text,"
                    + "room text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void addColumn(SQLiteDatabase db, String name, String staticPhone, String mobilePhone, String room) {
            Log.d(LOG_TAG, "--- on addColumn ---");
            ContentValues cv = new ContentValues();
            cv.put("name", name);
            cv.put("staticPhone", staticPhone);
            cv.put("mobilePhone", mobilePhone);
            cv.put("room", room);
            long rowID = db.insert("employees", null, cv);
            Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        }

        public List<List<String>> readTable(Cursor c) {
            Log.d(LOG_TAG, "--- on readTable ---");
            List<List<String>> table = new ArrayList<>();
            if (c.moveToFirst()) {
                //find the column numbers using names
                int idColIndex = c.getColumnIndex("id");
                int nameColIndex = c.getColumnIndex("name");
                int staticPhoneColIndex = c.getColumnIndex("staticPhone");
                int mobilePhoneColIndex = c.getColumnIndex("mobilePhone");
                int roomColIndex = c.getColumnIndex("room");
                do {
                    List<String> temp = new ArrayList<>();
                    temp.add(c.getString(idColIndex));
                    temp.add(c.getString(nameColIndex));
                    temp.add(c.getString(staticPhoneColIndex));
                    temp.add(c.getString(mobilePhoneColIndex));
                    temp.add(c.getString(roomColIndex));
                    table.add(temp);

                    Log.d(LOG_TAG,
                            "ID = " + c.getString(idColIndex) +
                            ", NAME = " + c.getString(nameColIndex) +
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
        public void clearTable(SQLiteDatabase db) {
            Log.d(LOG_TAG, "--- on clearTable ---");
            int clearCount = db.delete("employees", null, null);
            db.delete("SQLITE_SEQUENCE", "name = 'employees'", null);
            Log.d(LOG_TAG, "deleted rows count = " + clearCount);
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
