package net.yorksoultions.processbe.entity;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

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

//    @ElementCollection
//    private List<String> optionList;
    // ["option 1", "option 2", "option 3"]

    public Stage() {
    }

    public Stage(String text, int index, int responseType, List<String> optionList) {
        this.setText(text);
        this.setIndex(index);
        this.setResponseType(responseType);
//        this.setOptionList(optionList);
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        if (index < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        this.index = index;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        if (responseType != 1 && responseType != 2 && responseType != 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.responseType = responseType;
    }

//    public List<String> getOptionList() {
//        return optionList;
//    }

//    public void setOptionList(List<String> optionList) {
//
//        if (optionList == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//
//        if (this.responseType == 3) {
//            if (optionList.size() == 0) {
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        this.optionList = optionList;
//    }
}
