package com.example.Uebung2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    EditText etNum1;
    EditText etNum2;
    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;
    TextView tvResult;
    String oper = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //find Views
        etNum1 = (EditText) findViewById(R.id.editText1);
        etNum2 = (EditText) findViewById(R.id.editText2);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        tvResult = (TextView) findViewById(R.id.textView);

        //define Listener
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float num1 = 0;
        float num2 = 0;
        float result = 0;
        //Check data available
        if (TextUtils.isEmpty(etNum1.getText().toString()) || TextUtils.isEmpty(etNum2.getText().toString())) {
            Toast.makeText(this, "Enter operants!", Toast.LENGTH_LONG).show();
            return;
        }

        //Get Operands
        num1 = Float.parseFloat(etNum1.getText().toString());
        num2 = Float.parseFloat(etNum2.getText().toString());
        //Find a signal source and define an operation
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                break;
        }
        //create an output String
        tvResult.setText("=" + num1 + " " + oper + " " + num2 + " = " + result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        menu.add(0, MENU_RESET_ID, 0, "Reset");
        menu.add(0, MENU_QUIT_ID, 0, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET_ID:
                //clear input & output fields
                etNum1.setText("");
                etNum2.setText("");
                tvResult.setText("");
                break;
            case MENU_QUIT_ID:
                //exit
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
