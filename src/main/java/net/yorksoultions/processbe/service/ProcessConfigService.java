package net.yorksoultions.processbe.service;

import net.yorksoultions.processbe.entity.ProcessConfig;
import net.yorksoultions.processbe.entity.Stage;
import net.yorksoultions.processbe.repository.ProcessConfigRepository;
import net.yorksoultions.processbe.repository.StageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProcessConfigService {

    StageRepository stageRepository;
    ProcessConfigRepository processConfigRepository;

    public ProcessConfigService(StageRepository stageRepository, ProcessConfigRepository processConfigRepository) {
        this.stageRepository = stageRepository;
        this.processConfigRepository = processConfigRepository;
    }

    public ProcessConfig create(ProcessConfig requestBody) {

        // we need to create a process

        // the requestBody is just the data that comes from the frontend
        // this body is just a object 2 properties
        //          - title
        //          - list of stages

        // a process is just a object 3 properties
        //          - id
        //          - title
        //          - list of stages

        // a sage is just a object with 2 properties
        //          - id
        //          - text -> question we are asking

        // steps
        // 1. create the process entity
        // 2. create an array to hold each new stage entity
        // 3. check if the request body stage list is null before trying to loop
        // 4. create each stage entity (loop over the request body's stage list)
        //          a. create the entity
        //          b. fill the entity with the correct data
        //          c. save the entity to the database
        //          d. add the saved entity to the stage entity array
        // 5. take each stage we just created and add that to the process entity
        // 6. save the process entity to the database
        // 7. return the saved process entity to the controller


        // STEP 1
        String processTitle = requestBody.getTitle();
        ProcessConfig processEntity = new ProcessConfig(processTitle);

        // STEP 2
        Set<Stage> stageEntityArray = new HashSet<>();

        // STEP 3
        Set<Stage> requestBodyStageList = requestBody.getStageList();
        if (requestBodyStageList == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // STEP 4
        for (Stage stage : requestBodyStageList) {
            // STEP 4.a -> 4.b
            String stageText = stage.getText();
            int stageIndex = stage.getIndex();
            int responseType = stage.getResponseType();
            List<String> optionList = stage.getOptionList();

            Stage newStage = new Stage(stageText, stageIndex, responseType, optionList);

            // STEP 4.c (this gives us the entity WITH the id)
            Stage savedStage = this.stageRepository.save(newStage);

            // STEP 4.d
            stageEntityArray.add(savedStage);
        }

        // STEP 5
        processEntity.setStageList(stageEntityArray);

        // STEP 6
        ProcessConfig savedProcessConfig = processConfigRepository.save(processEntity);

        // STEP 7
        return  savedProcessConfig;
    }

    public void delete(Long id) {
        this.processConfigRepository.deleteById(id);
    }

    public Iterable<ProcessConfig> getAll() {
        Iterable<ProcessConfig> processConfigList = this.processConfigRepository.findAll();
        return processConfigList;
    }

    public ProcessConfig update(ProcessConfig requestBody) {


        // requestBody -> is the data from the frontend (the thing that made the request)
        /*
                requestBody = {
                    id: 0
                    title: "some text",
                    processList: [
                        {id: 2, text: "some text 3"}
                        {id: 0, text: "some text 1"},
                        {id: 1, text: "some text 2"}
                    ]
                }
         */


        // step 1 -> does the id from the request body exist in the database (in the process_config table)
        Long requestedId = requestBody.getId();
        Optional<ProcessConfig> processEntityOpt = this.processConfigRepository.findById(requestedId);

        // step 2 -> if the id is not in the database then we stop processing the quest and send the frontend an error code
        if (processEntityOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // step 3 -> update the title on the processEntity we found in the database
        ProcessConfig processEntity = processEntityOpt.get();
        String newTitle = requestBody.getTitle();
        processEntity.setTitle(newTitle);

        processEntity.setStageList(requestBody.getStageList());

        ProcessConfig savedProcessConfig = this.processConfigRepository.save(processEntity);

        return savedProcessConfig;
    }
}
