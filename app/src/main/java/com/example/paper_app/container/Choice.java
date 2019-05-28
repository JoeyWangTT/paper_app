package com.example.paper_app.container;

import java.util.ArrayList;
import java.util.List;

public class Choice {
    private String choiceId;

    private String choiceTitle;

    private ArrayList<Integer>  choiceAnswers = new ArrayList<>();

    private Integer choiceType;

    private String choiceLevelId;

    private String categoryId;

    private List<ChoiceOption> choiceOptions = new ArrayList<>();

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

    public ArrayList<Integer> getChoiceAnswers() {
        return choiceAnswers;
    }

    public void setChoiceAnswers(ArrayList<Integer> choiceAnswers) {
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
}
