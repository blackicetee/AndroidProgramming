package com.example.Calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends Activity implements View.OnClickListener {
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

    Button btnC;
    Button btnEquals;

    Double result = 0.0;

    char sign = 's';

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
        btnC = (Button) findViewById(R.id.btnC);
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
        btnC.setOnClickListener(this);
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
                if (tvMain.getText().toString().isEmpty()) {
                    sign = '+';
                } else {
                    result += Double.parseDouble(tvMain.getText().toString());
                    tvMain.setText("");
                    sign = '+';
                }
                break;
            case R.id.btnSub:
                if (tvMain.getText().toString().isEmpty()) {
                    sign = '-';
                } else {
                    if (sign == 's') {
                        result += Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '-';
                    } else {
                        result -= Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '-';
                    }
                }
                break;
            case R.id.btnMul:
                if (tvMain.getText().toString().isEmpty()) {
                    sign = '*';
                } else {
                    if (sign == 's') {
                        result += Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '*';
                    } else {
                        result *= Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '*';
                    }
                }
                break;
            case R.id.btnDiv:
                if (tvMain.getText().toString().isEmpty()) {
                    sign = '/';
                } else {
                    if (sign == 's') {
                        result += Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '/';
                    } else {
                        result /= Double.parseDouble(tvMain.getText().toString());
                        tvMain.setText("");
                        sign = '/';
                    }
                }
                break;
            case R.id.btnC:
                result = 0.0;
                tvMain.setText("");
                sign = 's';
                break;
            case R.id.btnEquals:
                if (tvMain.getText().toString().equals("Large Text")) {
                    Toast.makeText(this, "Please enter an equation or a single term!", Toast.LENGTH_LONG).show();
                } else {
                    if (!tvMain.getText().toString().isEmpty()) {
                        switch (sign) {
                            case '+':
                                result += Double.parseDouble(tvMain.getText().toString());
                                break;
                            case '-':
                                result -= Double.parseDouble(tvMain.getText().toString());
                                break;
                            case '*':
                                result *= Double.parseDouble(tvMain.getText().toString());
                                break;
                            case '/':
                                result /= Double.parseDouble(tvMain.getText().toString());
                                break;
                            case 's':
                                result += Double.parseDouble(tvMain.getText().toString());
                                break;
                        }
                        tvMain.setText("");
                    }
                    //Toast.makeText(this, result.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("CalculatedResult", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
