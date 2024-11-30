package com.example.calculatortask;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView result;
    private String input = "0";
    private double firstOperand = 0;
    private double secondOperand = 0;
    private String currentOperator = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        result = findViewById(R.id.tvdisplay);
        result.setText("0");
    }

    public void numFunction(View view) {
        Button btn = (Button) view;
        if(!input.equals("-0") && !input.equals("0")) {
            input += btn.getText().toString();
        }
        else{
            if(input.equals("-0")) {
                input = "-";
                input += btn.getText().toString();
            }
            else{
                input = btn.getText().toString();
            }
        }
        result.setText(input);
    }

    public void DotFunction(View view) {

        if(!input.equals("") && Double.parseDouble(input) == (long)Double.parseDouble(input) && !input.contains(".")) {
            input += ".";
            result.setText(input);
        }
    }
    public void handleOperator(View view) {
        Button button = (Button) view;
        String operator = button.getText().toString();

        if (!input.isEmpty() && (currentOperator.equals("/") || currentOperator.equals("+") || currentOperator.equals("*") || currentOperator.equals("-"))) {
            calculate(view);
            currentOperator = operator;
            input = "";
        } else {
            if (currentOperator.equals("/") || currentOperator.equals("+") || currentOperator.equals("*") || currentOperator.equals("-")) {
                currentOperator = operator;
                input = "";
                result.setText(operator);
            }
            else {
                if (!input.isEmpty()) {
                    firstOperand = Double.parseDouble(input);
                    currentOperator = operator;
                    input = "";
                    result.setText(operator);
                }
            }
        }
    }

    public void handleOperatorPM(View view) {

        if((input.equals("0") || input.equals("0.0"))){

            input = "-0";
            result.setText(input);
        }
        else {
            if(input.equals("-0") || input.equals("-0.0")){
                input = "0";
                result.setText(input);
            }

            else{
                if (currentOperator.equals(" ")) {

                    firstOperand = Double.parseDouble(input);
                    firstOperand *= -1;
                    input = Double.toString(firstOperand);
                    result.setText(CheckNum(firstOperand));
                } else {
                    if (result.getText().equals("+") || result.getText().equals("-") || result.getText().equals("*") || result.getText().equals("/")) {

                        input = "-0";
                        secondOperand = 0;
                        result.setText(input);
                    } else {
                        secondOperand = Double.parseDouble(input);
                        secondOperand *= -1;
                        input = Double.toString(secondOperand);
                        result.setText(CheckNum(secondOperand));
                    }
                }
            }
        }
    }
    public void calculate(View view) {

        if (!input.isEmpty() && !currentOperator.isEmpty()) {
            secondOperand = Double.parseDouble(input);

            double res = 0;
            int flag = 0;

            switch (currentOperator) {
                case "+":
                    res = firstOperand + secondOperand;
                    break;
                case "-":
                    res = firstOperand - secondOperand;
                    break;
                case "*":
                    res = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        res = firstOperand / secondOperand;
                    } else {

                        flag = 1;
                        result.setText("Cannot divide by zero");
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            resetCalculator();
                        }, 3000);

                    }
                    break;
                case "%":
                    res = firstOperand % secondOperand;
                    break;
            }
            if(flag == 0) {
                String res_s = CheckNum(res);
                result.setText(res_s);
                input = res_s;//Double.toString(res);
                firstOperand = res;
                secondOperand = 0;
                currentOperator = "";
            }
        }
    }

    private String CheckNum(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        } else {
            return String.valueOf(number);
        }
    }

    private void resetCalculator() {
        input = "0";
        currentOperator = " ";
        firstOperand = 0;
        secondOperand = 0;
        result.setText("0");
    }
    public void clear(View view) {
        input = "0";
        currentOperator = " ";
        firstOperand = 0;
        secondOperand = 0;
        result.setText("0");
    }
}