package net.yorksoultions.processbe.entity;

import org.springframework.lang.NonNull;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<StageOptionEntity> optionList;

    public Stage() {
    }

    public Stage(String text, int index, int responseType) {
        this.text = text;
        this.index = index;
        this.responseType = responseType;
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

    public List<StageOptionEntity> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<StageOptionEntity> optionList) {
        this.optionList = optionList;
    }
}
