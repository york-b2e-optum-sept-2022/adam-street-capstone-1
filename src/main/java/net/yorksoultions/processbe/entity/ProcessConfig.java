package net.yorksoultions.processbe.entity;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ProcessConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    // TODO - find out why we cannot have orphan removal set to true without error when updating a process
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Stage> stageList;

    public ProcessConfig() {}
    public ProcessConfig(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Stage> getStageList() {
        return stageList;
    }

    public void setStageList(Set<Stage> stageList) {
        this.stageList = stageList;
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
