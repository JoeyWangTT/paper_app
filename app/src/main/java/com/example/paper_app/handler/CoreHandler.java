package com.example.paper_app.handler;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paper_app.AnswerActivity;
import com.example.paper_app.R;
import com.example.paper_app.ScoreActivity;
import com.example.paper_app.container.Choice;
import com.example.paper_app.container.ChoiceCache;
import com.example.paper_app.container.ChoiceOption;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CoreHandler {
    private final static   String  ChoicesURL = "http://192.168.0.114:8080/getQChoiceById/";

    private  static ChoiceCache cache = new ChoiceCache();

    public  static  String getChoiceLevel(){
        return  cache.getChoiceLevel();
    }

    public  static  int getChoiceIndex(){
        return  cache.getChoiceIndex();
    }

    public  static  void setChoiceLevel(String choiceLevel){
        cache.setChoiceLevel(choiceLevel);
    }


    public  static boolean addChoice(Choice c){
        return cache.getChoices().add(c);
    }

    public  static Choice getChoice(Integer index){
        if (cache.getChoices() == null && cache.getChoices().size() <= index  )
            return  null;
        return cache.getChoices().get(index);
    }

    public  static int choiceSize(){
        if (cache.getChoices() == null)
            return  0;
        return cache.getChoices().size();
    }

    public  static boolean initChoices(final AnswerActivity answerActivity){
        OkHttpClient client = new OkHttpClient();
        String choicesUrl = answerActivity.getString(R.string.api_choice_url) + getChoiceLevel();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "value")
                .build();
        final Request request = new Request.Builder()
                .url(choicesUrl)
                .post(requestBody)
                .build();
        Log.i("++CoreHandler ", choicesUrl);
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //ToastUtil.showToast(PostFormActivity.this, "Post Form 失败");
                Log.i("++CoreHandler onFailure", answerActivity.getString(R.string.error_choice_init));
                answerActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(answerActivity,answerActivity.getString(R.string.error_choice_init), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final String responseStr = response.body().string();
                    // ToastUtil.showToast(PostFormActivity.this, "Code：" + String.valueOf(response.code()));
                    JSONTokener jsonParse = new  JSONTokener(responseStr);
                    JSONArray result  = (JSONArray)jsonParse.nextValue();
                    if (result != null && result.length() > 0){
                        for(int i = 0; i<result.length(); i++){
                            Choice choice = new Choice();
                            JSONObject jsonChoice = (JSONObject)result.get(i);
                            JSONArray  jsonChoicesOption = jsonChoice.getJSONArray("choiceOptiontemp");
                            for(int j = 0; j<jsonChoicesOption.length(); j++){
                                JSONObject jsonChoiceOption = jsonChoicesOption.getJSONObject(j);
                                ChoiceOption choiceOption = new ChoiceOption();
                                choiceOption.setChoiceOptionId(jsonChoiceOption.getString("id"));
                                choiceOption.setChoiceOptionTitle(jsonChoiceOption.getString("value"));
                                choice.getChoiceOptions().add(choiceOption);
                            }
                            choice.setChoiceId(jsonChoice.getString("choiceId"));
                            choice.setChoiceLevelId(jsonChoice.getString("levelId"));
                            choice.setChoiceTitle(jsonChoice.getString("choiceTitle"));
                            choice.setChoiceType(jsonChoice.getInt("choiceType"));
                            String choiceAnswersStr = jsonChoice.getString("choiceAnswer");
                            if (choiceAnswersStr == null ||  choiceAnswersStr.trim().length() == 0){
                                answerActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(answerActivity,answerActivity.getString(R.string.error_choice_init), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            String[] answers = choiceAnswersStr.trim().split(",");
                            if (answers.length == 0){
                                answerActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(answerActivity,answerActivity.getString(R.string.error_choice_init), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }

                            for (int j = 0; j<answers.length;j++ ){
                                choice.getChoiceAnswers().put(answers[j],answers[j]);
                            }
                            Log.i("++CoreHandler　answers", choice.getChoiceAnswers().toString());
                            CoreHandler.addChoice(choice);
                        }
                        answerActivity.initView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("++CoreHandler　onResp", answerActivity.getString(R.string.error_choice_init));
                    answerActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(answerActivity,answerActivity.getString(R.string.error_choice_init), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return true;
    }

    public  static void resetChoices(){
        cache.getChoices().clear();
    }

    public  static void setChoicesIndex(int index){
        cache.setChoiceIndex(index);
    }

    public  static boolean score(final LinearLayout layout,final ScoreActivity scoreActivity){
        List<Choice> choicesTemp =  new ArrayList<>();
        choicesTemp.addAll(cache.getChoices());
        final List<TextView>  textViews = new ArrayList<>();
        int score = 0;
        if (choicesTemp.size() > 0){
            for (int i = 0;i<choicesTemp.size();i++){
                Choice c = choicesTemp.get(i);
                HashMap<String,String> answerMap = c.getChoiceAnswers();
                HashMap<String,String> userAnswerMap = c.getUserAnswers();
                if (answerMap.size() != userAnswerMap.size()){
                    TextView textView = new TextView(scoreActivity.getBaseContext());
                    StringBuilder sb = new StringBuilder("");
                    sb.append((i+1) + "Y[" + answerMap.toString() +"]" );
                    sb.append( "   User[" + userAnswerMap.toString() +"]" );
                    sb.append( "   Error" );
                    textView.setText(sb.toString());
                    textViews.add(textView);
                    continue;
                }
                boolean check = true;
                for (Map.Entry entry : answerMap.entrySet()){
                    if (!userAnswerMap.containsKey(entry.getKey())){
                        check = false;
                    }
                }
                if (!check){
                    TextView textView = new TextView(scoreActivity.getBaseContext());
                    StringBuilder sb = new StringBuilder("");
                    sb.append((i+1) + "Y[" + answerMap.toString() +"]" );
                    sb.append( "   User[" + userAnswerMap.toString() +"]" );
                    sb.append( "   Error" );
                    textView.setText(sb.toString());
                    textViews.add(textView);
                    continue;
                }

                TextView textView = new TextView(scoreActivity.getBaseContext());
                StringBuilder sb = new StringBuilder("");
                sb.append((i+1) + "Y[" + answerMap.toString() +"]" );
                sb.append( "   User[" + userAnswerMap.toString() +"]" );
                sb.append( "   YES" );
                textView.setText(sb.toString());
                textViews.add(textView);
                score += 10;
            }
        }
        TextView textView = new TextView(scoreActivity.getBaseContext());
        textView.setText("" + score);
        textViews.add(textView);
        scoreActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<textViews.size();i++){
                    textViews.get(i).setId(i);
                    layout.addView(textViews.get(i),i, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 40));
                }
            }
        });
        return  false;
    }
}
