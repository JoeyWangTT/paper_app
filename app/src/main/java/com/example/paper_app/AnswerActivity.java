package com.example.paper_app;

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
import android.widget.TextView;

import com.example.paper_app.container.Choice;
import com.example.paper_app.container.ChoiceCache;
import com.example.paper_app.container.ChoiceOption;
import com.example.paper_app.handler.CoreHandler;

import java.util.List;

public class AnswerActivity extends AppCompatActivity {

    private Choice choice;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        CheckBox checkBox5 = new CheckBox(getBaseContext());
        checkBox5.setText("Android Design [4] 里把主流设备的 dpi 归成了四个档次，320 dpi");
        linearLayout.addView(checkBox5, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        init();

    }

    public boolean initView (){
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        int index = CoreHandler.getChoiceIndex();
        choice = CoreHandler.getChoice(index);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CheckBox checkBox6 = new CheckBox(getBaseContext());
                checkBox6.setText("Android Design [4] 里把主流设备的 dpi 归成了四个档次，320 dpi6666");
                linearLayout.addView(checkBox6, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
            }
        });

        final List<ChoiceOption> choiceOptions = choice.getChoiceOptions();
        if (choice != null){
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(choice.getChoiceTitle());
                    if (choiceOptions.size() > 0){
                        for(int i = 0; i<choiceOptions.size(); i++){
                            ChoiceOption choiceOption = choiceOptions.get(i);
                            CheckBox checkBox = new CheckBox(getBaseContext());
                            checkBox.setText(choiceOption.getChoiceOptionTitle());
                            linearLayout.addView(checkBox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
                        }
                    }
                }
            });

        }else{
            return  false;
        }
        return  true;
    }

    private  boolean init(){
        CoreHandler.initChoices(this);
        final LinearLayout linearLayout = findViewById(R.id.linearLayout);
        CheckBox checkBox3 = new CheckBox(getBaseContext());
        checkBox3.setText("Android Design [4] 里把主流设备的 dpi 归成了四个档次，320 dpi3333");
        linearLayout.addView(checkBox3, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
        return  false;
    }

}
