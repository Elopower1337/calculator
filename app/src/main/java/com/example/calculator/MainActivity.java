package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

        /* Кнопки */

    Button mbut0;
    Button mbut1;
    Button mbut2;
    Button mbut3;
    Button mbut4;
    Button mbut5;
    Button mbut6;
    Button mbut7;
    Button mbut8;
    Button mbut9;

    TextView mDisplay;

    Button mBackSpace;
    Button mClear;
    Button mComma;
    Button mSing;

    Button mPlus;
    Button mMinus;
    Button mDiv;
    Button mMul;
    Button mResult;

    //состояние калькулятора
    float mValue = 0;
    String mOperator = "";
    boolean isWriteStopped = false;

    String divZero = "Делить на 0 нельзя!";
    boolean divOnZero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mbut0 = findViewById(R.id.but0);
        mbut1 = findViewById(R.id.but1);
        mbut2 = findViewById(R.id.but2);
        mbut3 = findViewById(R.id.but3);
        mbut4 = findViewById(R.id.but4);
        mbut5 = findViewById(R.id.but5);
        mbut6 = findViewById(R.id.but6);
        mbut7 = findViewById(R.id.but7);
        mbut8 = findViewById(R.id.but8);
        mbut9 = findViewById(R.id.but9);

        mDisplay = findViewById(R.id.Display);

        mBackSpace = findViewById(R.id.BackSpace);
        mClear = findViewById(R.id.Clear);
        mComma = findViewById(R.id.Comma);
        mSing = findViewById(R.id.Sing);

        mPlus = findViewById(R.id.Plus);
        mMinus = findViewById(R.id.Minus);
        mDiv = findViewById(R.id.Div);
        mMul = findViewById(R.id.Mul);
        mResult = findViewById(R.id.Result);

        //Подписки
        mbut0.setOnClickListener(numberListener);
        mbut1.setOnClickListener(numberListener);
        mbut2.setOnClickListener(numberListener);
        mbut3.setOnClickListener(numberListener);
        mbut4.setOnClickListener(numberListener);
        mbut5.setOnClickListener(numberListener);
        mbut6.setOnClickListener(numberListener);
        mbut7.setOnClickListener(numberListener);
        mbut8.setOnClickListener(numberListener);
        mbut9.setOnClickListener(numberListener);

        //операторы
        mPlus.setOnClickListener(operatorListener);
        mMinus.setOnClickListener(operatorListener);
        mMul.setOnClickListener(operatorListener);
        mDiv.setOnClickListener(operatorListener);

        //нажатие равно
        mResult.setOnClickListener(resultListener);
        // нажатие + -
        mSing.setOnClickListener(singListener);
        // очистка
        mClear.setOnClickListener(clearListener);
        // назад
        mBackSpace.setOnClickListener(backListener);
        //для запятой
        mComma.setOnClickListener(commaListener);
    }
    //Слушатель на нажатия кнопки
    View.OnClickListener numberListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onNamberClick(v);
        }

    };


    public void onNamberClick(View button){
        String number = ((Button)button).getText().toString();
        String display = mDisplay.getText().toString();

        if(display.equals("0"))
            display = number;
        else
            display += number;

        mDisplay.setText(display);
    }

    //Слушатель на операторы
    View.OnClickListener operatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onOperationListener(v);
        }
    };

    public void onOperationListener(View button){

        String operator = ((Button)button).getText().toString();
        mOperator = operator;

        String display = mDisplay.getText().toString();
        if(!display.equals(divZero))
            mValue = Float.parseFloat(display);

        mDisplay.setText("0");

    }

    //слушатель нажатия равно
    View.OnClickListener resultListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onResultListener(v);
        }
    };

    public void onResultListener(View button){
//Получаем текущее значение с дисплея, конвертируем в число и сохраняем в переменной value
        String display = mDisplay.getText().toString();
        float value = Float.parseFloat(display);
//Устанавливаем переменную value в переменную result, на случай если у нас не было выбранного оператора
        float result = value;
//Пишем обработку операторов
        switch (mOperator)
        {
            case "+":
            {
                result = value + mValue;
                break;
            }

            case "-": {
                result = mValue - value;
                break;
            }

            case "*": {
                result = mValue * value;
                break;
            }

            case "/": {
                if(value!=0)
                    result = mValue / value;
                else
                    divOnZero = true;
                break;
            }

        }

//Конвертируем получившееся
        DecimalFormat format = new DecimalFormat(  "0.######");
        format.setRoundingMode(RoundingMode.DOWN);
        String resultText;
        if(divOnZero!=true)
            resultText = format.format(result).replace(",", ".");
        else
            resultText = divZero;

//5 Устанавливаем resultText на дисплей
        mDisplay.setText(resultText);

//5 Запоминаем получившееся value в mValue 6 Очищаем оператор
        value = result;
        mOperator = "";

    }
// +- нажатие
    View.OnClickListener singListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onSingListener(v);
        }
    };

    public void onSingListener(View button){

        float value = Float.parseFloat(mDisplay.getText().toString());
        value = value*-1;

        DecimalFormat format = new DecimalFormat("0.######");
        //format.setRoundingMode(RoundingMode.DOWN);

        String resValue = format.format(value).replace(",", ".");

        mDisplay.setText(String.valueOf(resValue));
    }
// для очистки
    View.OnClickListener clearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onClearListener(v);
        }
    };
// назад
    public void onClearListener(View button){
        mDisplay.setText("0");
        mValue = 0;
        mOperator = "";
    }
// бэк
    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackSpaceListener(v);
        }
    };

    public void onBackSpaceListener(View button){
        String text = mDisplay.getText().toString().replace(",", ".");
        System.out.println(text);
        System.out.println(isWriteStopped);
        if(isWriteStopped==false && text.length()>1)
            mDisplay.setText(removeLastChar(text));
        else if(isWriteStopped==false && !text.equals("0"))
            mDisplay.setText("0");
    }
    //обработка нажатия на .
    View.OnClickListener commaListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onCommaListener(v);
        }
    };

    public void onCommaListener(View button){
        String text = mDisplay.getText().toString();

        String lastSymbol = Character.toString(text.charAt(text.length()-1));
        String addComma = text + ".";

        boolean isHasComma = false;
        for(int i = 0; i < text.length(); i++)
            if(Character.toString(text.charAt(i)).equals("."))
                isHasComma = true;

        if (isHasComma == false)
            mDisplay.setText(addComma);
    }


    public static String removeLastChar(String str) {
        return removeLastChars(str, 1);
    }
    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
    }

}