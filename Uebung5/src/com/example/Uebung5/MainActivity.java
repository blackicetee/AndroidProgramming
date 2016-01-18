package com.example.Uebung5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    String[] names = {""};
    List<String> resultList = new ArrayList<>();
    Button btnCalc;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Find View
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        // Create an Adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, names);

        lvMain.setAdapter(adapter);

        btnCalc = (Button) findViewById(R.id.btnClac);

        btnCalc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClac:
                if (resultList.size() < 15) {
                    Intent intent = new Intent("htw.berlin.s0539757.intent.action.Calculator");
                    startActivityForResult(intent, 1);
                } else {
                    Toast.makeText(this, "You can`t add more items to the List, because you reached the maximum of 10 items!", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ListView lvMain = (ListView) findViewById(R.id.lvMain);
        if (data == null) {
            return;
        }
        Double result = data.getDoubleExtra("CalculatedResult", 0);
        //Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();

        resultList.add(result.toString());
        String[] stringArray = resultList.toArray(new String[resultList.size()]);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, stringArray);

        lvMain.setAdapter(adapter);
    }
}
