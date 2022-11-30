package net.yorksoultions.processbe.service;

import net.yorksoultions.processbe.entity.ProcessConfig;
import net.yorksoultions.processbe.repository.ProcessConfigRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessConfigService {

    ProcessConfigRepository processConfigRepository;

    public ProcessConfigService(ProcessConfigRepository processConfigRepository) {
        this.processConfigRepository = processConfigRepository;
    }

    public ProcessConfig create(String title) {
        ProcessConfig newProcessConfig = new ProcessConfig(title);
        ProcessConfig savedProcessConfig = this.processConfigRepository.save(newProcessConfig);
        return savedProcessConfig;
    }

    public void delete(Long id) {
        this.processConfigRepository.deleteById(id);
    }

    public Iterable<ProcessConfig> getAll() {
        Iterable<ProcessConfig> processConfigList = this.processConfigRepository.findAll();
        return processConfigList;
    }

    public ProcessConfig update(ProcessConfig requestedProcessConfig) {
        Long requestedId = requestedProcessConfig.getId();
        Optional<ProcessConfig> processConfigOpt = this.processConfigRepository.findById(requestedId);
        if (processConfigOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        ProcessConfig foundProcessConfig = processConfigOpt.get();
        String newTitle = requestedProcessConfig.getTitle();
        foundProcessConfig.setTitle(newTitle);

        ProcessConfig savedProcessConfig = this.processConfigRepository.save(foundProcessConfig);
        return savedProcessConfig;
    }
}
