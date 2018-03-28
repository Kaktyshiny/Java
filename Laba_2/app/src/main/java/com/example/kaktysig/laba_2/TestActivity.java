package com.example.kaktysig.laba_2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Kaktysig on 28.03.18.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text_question;
    private RadioGroup radio_variants;
    private RadioButton radio_variant_1;
    private RadioButton radio_variant_2;
    private RadioButton radio_variant_3;
    private Button button_get_answer;

    private int index = 0;
    private int wrong_answers = 0;
    private String answers = "";

    public static Questions question_1 = new Questions("Сколько естественных спутников у планеты Земля?", "1", "2", "3", "1");
    public static Questions question_2 = new Questions("Чему равно число A318 16-ричной в 10-чной системе счисления?",  "41 240", "4224", "41 752", "41 752");
    public static Questions question_3 = new Questions("Скорость при равноускоренном движении равна...", "V0t + (at^2)/2", "V0 + at", "S/t", "V0 + at");
    public static Questions question_4 = new Questions("Как обозначается в таблице Менделеева элемент Свинец?", "Sn", "Cv", "Pb", "Pb");
    public static Questions question_5 = new Questions("Столица США?", "Нью-йорк", "Вашингтон", "Сиэтл", "Вашингтон");
    public static Questions question_6 = new Questions("Сколько звезд на небе?", "Бесконечность", "Много", "У меня одна и мне норм!", "Бесконечность");
    public static Questions question_7 = new Questions("Какое из чисел самое больше?", "9999", "8888", "77777", "77777");

    public static Questions[] questions_array = {
            question_1,
            question_2,
            question_3,
            question_4,
            question_5,
            question_6,
            question_7
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        radio_variants = (RadioGroup) findViewById(R.id.radio_variants);

        text_question = (TextView) findViewById(R.id.text_question);
        radio_variant_1 = (RadioButton) findViewById(R.id.radio_variant_1);
        radio_variant_2 = (RadioButton) findViewById(R.id.radio_variant_2);
        radio_variant_3 = (RadioButton) findViewById(R.id.radio_variant_3);

        button_get_answer = (Button) findViewById(R.id.button_get_answer);
        button_get_answer.setOnClickListener(this);

        ChangeQuestion();

    }

    private void ChangeQuestion(){
        text_question.setText(questions_array[index].getText());
        radio_variant_1.setText(questions_array[index].getVariant_1());
        radio_variant_2.setText(questions_array[index].getVariant_2());
        radio_variant_3.setText(questions_array[index].getVariant_3());
    }

    @Override
    public void onClick(View view) {
        RadioButton answer_button = (RadioButton) findViewById(radio_variants.getCheckedRadioButtonId());

        String answer = answer_button.getText().toString().trim();
        answers += answer + "|";

        if (questions_array[index].getRight_aswer().equalsIgnoreCase(answer)) Toast.makeText(this, "В" +
                "ерный ответ", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "Неверный ответ", Toast.LENGTH_SHORT).show();
            wrong_answers++;
        }

        if (index < 6){
            index++;
            ChangeQuestion();
        }
        else{
            Intent intent = new Intent(TestActivity.this, ResultActivity.class);
            intent.putExtra("wrong_answers", wrong_answers);
            intent.putExtra("answers", answers);
            startActivity(intent);
        }
    }
}
