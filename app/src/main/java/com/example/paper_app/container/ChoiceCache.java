package com.example.paper_app.container;

import java.util.ArrayList;
import java.util.List;

public class ChoiceCache {
    private int choiceIndex = 0;
    private String choiceLevel;
    private List<Choice> choices = new ArrayList<>();

    public int getChoiceIndex() {
        return choiceIndex;
    }

    public void setChoiceIndex(int choiceIndex) {
        this.choiceIndex = choiceIndex;
    }

    public String getChoiceLevel() {
        return choiceLevel;
    }

    public void setChoiceLevel(String choiceLevel) {
        this.choiceLevel = choiceLevel;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }
}
