package net.yorksoultions.processbe.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    private String text;

    private int index;

    // 1 -> text
    // 2 -> boolean
    // 3 -> multiple choice
    private int responseType;

    @ElementCollection
    private List<String> optionList;

    public Stage() {
    }

    public Stage(String text, int index, int responseType, List<String> optionList) {
        this.text = text;
        this.index = index;
        this.responseType = responseType;
        this.optionList = optionList;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public List<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
    }
}
