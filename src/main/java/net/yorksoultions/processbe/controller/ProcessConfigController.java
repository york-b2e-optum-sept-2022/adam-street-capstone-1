package net.yorksoultions.processbe.controller;

import net.yorksoultions.processbe.entity.ProcessConfig;
import net.yorksoultions.processbe.repository.StageRepository;
import net.yorksoultions.processbe.service.ProcessConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
@CrossOrigin
public class ProcessConfigController {
    ProcessConfigService processConfigService;

    public ProcessConfigController(ProcessConfigService processConfigService) {
        this.processConfigService = processConfigService;
    }

    // http://localhost:8080/api/process
    @PostMapping
    public ProcessConfig create(@RequestBody ProcessConfig processConfig) {

        ProcessConfig savedProcessConfig = this.processConfigService.create(processConfig);
        return savedProcessConfig;
    }

    @DeleteMapping
    public void delete(@RequestParam Long id) {
        this.processConfigService.delete(id);
    }

    @GetMapping
    public Iterable<ProcessConfig> getAll() {
        Iterable<ProcessConfig> processConfigList = this.processConfigService.getAll();
        return processConfigList;
    }

    @PutMapping
    public ProcessConfig update(@RequestBody ProcessConfig processConfig) {
        System.out.println(processConfig.toString());
        return this.processConfigService.update(processConfig);
    }

}