package net.yorksoultions.processbe.service;

import net.yorksoultions.processbe.entity.ProcessConfig;
import net.yorksoultions.processbe.repository.ProcessConfigRepository;
import net.yorksoultions.processbe.repository.StageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ProcessConfigService {

    StageRepository stageRepository;
    ProcessConfigRepository processConfigRepository;

    public ProcessConfigService(StageRepository stageRepository, ProcessConfigRepository processConfigRepository) {
        this.stageRepository = stageRepository;
        this.processConfigRepository = processConfigRepository;
    }

    public ProcessConfig create(ProcessConfig requestBody) {

        /*
             requestBody = {
                    id: null
                    title: "some text",
                    stageList: [
                        {id: 2, text: "some text 3"}
                        {id: 0, text: "some text 1"},
                        {id: 1, text: "some text 2"}
                    ]
                }
         */

        ProcessConfig newProcess = new ProcessConfig(requestBody.getTitle());
        newProcess.setStageList(requestBody.getStageList());

        ProcessConfig savedProcess = this.processConfigRepository.save(newProcess);
        return savedProcess;

        // requestBody -> is the data from the frontend (the thing that made the request)
        /*
                requestBody = {
                    id: null
                    title: "some text",
                    stageList: [
                        {id: 2, text: "some text 3"}
                        {id: 0, text: "some text 1"},
                        {id: 1, text: "some text 2"}
                    ]
                }

                1. we need to create a row in the process table
                {
                    id: 100,
                    title: "some text"
                }

                2. we need to create a row in the stage table for every stage in the process's stageList
                {
                    id: 100
                    text: "some text"
                    index: 1,
                    responseType: 3
                    optionList: ["option 1", "option 2", "option 3" ]
                }



         */

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

//        // STEP 1
//        String processTitle = requestBody.getTitle();
//        ProcessConfig processEntity = new ProcessConfig(processTitle);
//
//
//        // STEP 2
//        Set<Stage> stageEntityArray = new HashSet<>();
//
//        // STEP 3
//        Set<Stage> requestBodyStageList = requestBody.getStageList();
//        if (requestBodyStageList == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//
//        // STEP 4
//
//        /*
//         [
//                        {id: 2, text: "some text 3", index: 0, responseType: 3, optionList: ["1", "2", "3"]}
//                        {id: 0, text: "some text 1", index: 2, responseType: 1, optionList: []},
//                        {id: 1, text: "some text 2", index: 3, responseType: 2, optionList: []}
//          ]
//         */
//
//
//        for (Stage stage : requestBodyStageList) {
//            // STEP 4.a -> 4.b
//            String stageText = stage.getText();
//            int stageIndex = stage.getIndex();
//            int responseType = stage.getResponseType();
//            List<String> optionList = stage.getOptionList();
//
//            Stage newStage = new Stage(stageText, stageIndex, responseType, optionList);
//
//            // STEP 4.c (this gives us the entity WITH the id)
//            Stage savedStage = this.stageRepository.save(newStage);
//
//            // STEP 4.d
//            stageEntityArray.add(savedStage);
//        }
//
//        // STEP 5
//        processEntity.setStageList(stageEntityArray);
//
//        // STEP 6
//        ProcessConfig savedProcessConfig = processConfigRepository.save(processEntity);
//
//        // STEP 7
//        return  savedProcessConfig;
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


        // HOW TO FIX
        /*
                A collection with cascade=”all-delete-orphan”
                was no longer referenced by the owning entity instance: com.children
         */

        // OLD CODE
        /*
            processEntity.setStageList(requestBody.getStageList());
        */

        // NEW CODE
        /*

           // step 1 -> clear existing children list so that they are removed from database
            processEntity.getStageList().clear();
           // step 2 -> add the new children list created above to the existing list
            processEntity.getStageList().addAll(requestBody.getStageList());

         */

        // REASON
        /*
            in the old code we are REPLACING the old array with an entirety new array.
            Remember that an array just like an object is stored in a location in memory and when you replace the
            existing array you are using a new place in memory. This causes an issues for spring boot specifically
            with orphan removal due to how the system works internally.
         */

        // SOLUTION
        /*
                We can fix this issue by not creating a new array and therefor changing the location in memory the
                list of stages is stored, but by keeping the same array object and just changing the items its holding.

                so we first clear the existing content WITHOUT deleting or changing the array object itself. (step 1)
                then we fill the SAME array object with its new values (step 2)
         */

        processEntity.setStageList(requestBody.getStageList());
        ProcessConfig savedProcessConfig = this.processConfigRepository.save(processEntity);

        return savedProcessConfig;
    }
}
