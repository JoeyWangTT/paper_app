package com.example.paper_app.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Choice {
    private String choiceId;

    private String choiceTitle;

    private HashMap<String,String>  choiceAnswers = new HashMap<>();

    private Integer choiceType;

    private String choiceLevelId;

    private String categoryId;

    private List<ChoiceOption> choiceOptions = new ArrayList<>();

    private HashMap<String,String> userAnswers = new HashMap<>();

    public String getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(String choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceTitle() {
        return choiceTitle;
    }

    public void setChoiceTitle(String choiceTitle) {
        this.choiceTitle = choiceTitle;
    }

    public HashMap<String, String> getChoiceAnswers() {
        return choiceAnswers;
    }

    public void setChoiceAnswers(HashMap<String, String> choiceAnswers) {
        this.choiceAnswers = choiceAnswers;
    }

    public Integer getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(Integer choiceType) {
        this.choiceType = choiceType;
    }

    public String getChoiceLevelId() {
        return choiceLevelId;
    }

    public void setChoiceLevelId(String choiceLevelId) {
        this.choiceLevelId = choiceLevelId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<ChoiceOption> getChoiceOptions() {
        return choiceOptions;
    }

    public void setChoiceOptions(List<ChoiceOption> choiceOptions) {
        this.choiceOptions = choiceOptions;
    }

    public HashMap<String, String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(HashMap<String, String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public  void saveUserAnswers(String key){

        if (userAnswers.containsKey(key)){
            userAnswers.remove(key);
        }else{
            if (this.getChoiceType() == 0){
                userAnswers.clear();
            }
            userAnswers.put(key,key);
        }
    }
}
