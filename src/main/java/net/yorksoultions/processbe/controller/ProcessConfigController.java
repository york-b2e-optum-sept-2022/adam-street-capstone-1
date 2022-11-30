package net.yorksoultions.processbe.controller;

import net.yorksoultions.processbe.entity.ProcessConfig;
import net.yorksoultions.processbe.service.ProcessConfigService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
public class ProcessConfigController {

    ProcessConfigService processConfigService;

    public ProcessConfigController(ProcessConfigService processConfigService) {
        this.processConfigService = processConfigService;
    }

    @PostMapping
    public ProcessConfig create(@RequestParam String title) {
        ProcessConfig savedProcessConfig = this.processConfigService.create(title);
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
        return this.processConfigService.update(processConfig);
    }

}