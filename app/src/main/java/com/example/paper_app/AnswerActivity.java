package com.example.paper_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paper_app.container.Choice;
import com.example.paper_app.container.ChoiceCache;
import com.example.paper_app.container.ChoiceOption;
import com.example.paper_app.handler.CoreHandler;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    private Choice choice;

    private int choiceIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(AnswerActivity.this,"你点击了action", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        init();
    }

    public boolean initView (){
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        final int index = CoreHandler.getChoiceIndex();
        if (CoreHandler.choiceSize() == 0){
            Toast.makeText(AnswerActivity.this,getString(R.string.text_choice_type_plural), Toast.LENGTH_SHORT).show();
            return false;
        }
        choice = CoreHandler.getChoice(index);
        if (choice == null){
            Toast.makeText(AnswerActivity.this,getString(R.string.text_choice_type_plural), Toast.LENGTH_SHORT).show();
            return false;
        }
        final List<ChoiceOption> choiceOptions = choice.getChoiceOptions();

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.textView);
                String choiceTypeStr;
                if (choice.getChoiceType() != null && choice.getChoiceType() == 0){
                    choiceTypeStr = "[" +getString(R.string.text_choice_type_single) + "]";
                }else{
                    choiceTypeStr = "[" +getString(R.string.text_choice_type_plural) + "]";
                }
                textView.setText( (index + 1) + "."+ choice.getChoiceTitle() + choiceTypeStr);
                if (choiceOptions.size() > 0){
                    if (choice.getChoiceType()!= null && choice.getChoiceType() == 0) {
                        RadioGroup group = new RadioGroup(getBaseContext());
                        for (int i = 0; i < choiceOptions.size(); i++) {
                            ChoiceOption choiceOption = choiceOptions.get(i);
                            RadioButton radioButton = new RadioButton(getBaseContext());
                            radioButton.setId(i);
                            radioButton.setText(choiceOption.getChoiceOptionId() + "." + choiceOption.getChoiceOptionTitle());
                            final String key = choiceOption.getChoiceOptionId();
                            radioButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CoreHandler.getChoice(index).saveUserAnswers(key);
                                    TextView textViewTemp = findViewById(R.id.textView);
                                    textViewTemp.setText(textViewTemp.getText() + CoreHandler.getChoice(index).getUserAnswers().toString());
                                }
                            });
                            group.addView(radioButton,i, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
                        }
                        linearLayout.addView(group, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                    } else {
                        for (int i = 0; i < choiceOptions.size(); i++) {
                            ChoiceOption choiceOption = choiceOptions.get(i);
                            CheckBox checkBox = new CheckBox(getBaseContext());
                            checkBox.setId(i);
                            checkBox.setText(choiceOption.getChoiceOptionId() + "." + choiceOption.getChoiceOptionTitle());
                            final String key = choiceOption.getChoiceOptionId();
                            checkBox.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    CoreHandler.getChoice(index).saveUserAnswers(key);
                                    TextView textViewTemp = findViewById(R.id.textView);
                                    textViewTemp.setText(textViewTemp.getText() + CoreHandler.getChoice(index).getUserAnswers().toString());

                                }
                            });
                            linearLayout.addView(checkBox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
                        }

                    }
                }
            }
        });

        return  true;
    }

    private  boolean init(){
        final int index = CoreHandler.getChoiceIndex();
        Button btnEnd = findViewById(R.id.answerButtonEnd);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent=new Intent(AnswerActivity.this,MainActivity.class);
            startActivity(intent);
            }
        });

        Button btnNext = findViewById(R.id.answerButtonNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (choice ==  null || choice.getUserAnswers().size() == 0){
                    Toast.makeText(AnswerActivity.this,getString(R.string.error_not_choice), Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent;
                if (CoreHandler.choiceSize() <= (index + 1)){
                    intent=new Intent(AnswerActivity.this,MainActivity.class);
                }else {
                    CoreHandler.setChoicesIndex(index + 1);
                    intent=new Intent(AnswerActivity.this,AnswerActivity.class);
                }

                startActivity(intent);
            }
        });

        Button btnBack = findViewById(R.id.answerButtonBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (CoreHandler.choiceSize() <= 0 || index <= 0){
                    intent=new Intent(AnswerActivity.this,MainActivity.class);
                }else {
                    CoreHandler.setChoicesIndex(index - 1);
                    intent=new Intent(AnswerActivity.this,AnswerActivity.class);
                }

                startActivity(intent);
            }
        });
        if (index <= 0){
            CoreHandler.initChoices(this);
        }else{
            initView();
        }

        /*final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        CheckBox checkBox3 = new CheckBox(getBaseContext());
        checkBox3.setText("Android Design [4] 里把主流设备的 dpi 归成了四个档次，320 dpi3333" + index);
        linearLayout.addView(checkBox3, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));*/
        return  false;
    }

}
