package com.example.Calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */

    TextView tvMain;

    Button btnZero;
    Button btnOne;
    Button btnTwo;
    Button btnThree;
    Button btnFour;
    Button btnFive;
    Button btnSix;
    Button btnSeven;
    Button btnEight;
    Button btnNine;
    Button btnDot;

    Button btnAdd;
    Button btnSub;
    Button btnMul;
    Button btnDiv;

    Button btnEquals;

    Double result = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tvMain = (TextView) findViewById(R.id.tvMain);
        btnZero = (Button) findViewById(R.id.btn0);
        btnOne = (Button) findViewById(R.id.btn1);
        btnTwo = (Button) findViewById(R.id.btn2);
        btnThree = (Button) findViewById(R.id.btn3);
        btnFour = (Button) findViewById(R.id.btn4);
        btnFive = (Button) findViewById(R.id.btn5);
        btnSix = (Button) findViewById(R.id.btn6);
        btnSeven = (Button) findViewById(R.id.btn7);
        btnEight = (Button) findViewById(R.id.btn8);
        btnNine = (Button) findViewById(R.id.btn9);
        btnDot = (Button) findViewById(R.id.btnDot);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnEquals = (Button) findViewById(R.id.btnEquals);

        btnZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);
        btnDot.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMul.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
        btnEquals.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                addTerm("0");
                break;
            case R.id.btn1:
                addTerm("1");
                break;
            case R.id.btn2:
                addTerm("2");
                break;
            case R.id.btn3:
                addTerm("3");
                break;
            case R.id.btn4:
                addTerm("4");
                break;
            case R.id.btn5:
                addTerm("5");
                break;
            case R.id.btn6:
                addTerm("6");
                break;
            case R.id.btn7:
                addTerm("7");
                break;
            case R.id.btn8:
                addTerm("8");
                break;
            case R.id.btn9:
                addTerm("9");
                break;
            case R.id.btnDot:
                if (!tvMain.getText().toString().contains("."))
                    addTerm(".");
                break;
            case R.id.btnAdd:
                result += Double.parseDouble(tvMain.getText().toString());
                tvMain.setText("");
                break;
            case R.id.btnSub:

                break;
            case R.id.btnMul:

                break;
            case R.id.btnDiv:

                break;
            case R.id.btnEquals:
                Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void addTerm(String number) {
        if (tvMain.getText().toString().equals("Large Text")) {
            tvMain.setText(number);
        } else {
            String tmp = tvMain.getText().toString();
            tmp += number;
            tvMain.setText(tmp);
        }
    }
}
