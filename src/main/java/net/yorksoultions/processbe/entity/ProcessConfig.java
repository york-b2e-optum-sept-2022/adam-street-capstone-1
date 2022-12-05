package net.yorksoultions.processbe.entity;

import com.sun.istack.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProcessConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    // TODO - find out why we cannot have orphan removal set to true without error when updating a process
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Stage> stageList;

    public ProcessConfig() {
    }

    public ProcessConfig(String title) {
        this.setTitle(title);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.length() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        this.title = title;
    }

    public Set<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(Set<Stage> stageList) {

        // check if the stage list is empty
        if (stageList.size() == 0) {
            System.out.println("bad size");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        /*
          When we are first creating a process the stage list is null.
          Because of this we cannot run the addAll function,  so we write some logic to check if its null.
          If the stage list is null we simply set the stageList prop from the function input
         */
        if (this.getStageList() == null) {
            this.stageList = stageList;
            return;
        }

        this.getStageList().clear();
        this.getStageList().addAll(stageList);
    }

    @Override
    public String toString() {
        return "ProcessConfig{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stageList=" + stageList +
                '}';
    }
}
