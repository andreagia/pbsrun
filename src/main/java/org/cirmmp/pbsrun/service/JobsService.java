package org.cirmmp.pbsrun.service;

import lombok.var;
import org.cirmmp.pbsrun.model.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobsService {

    @Autowired
    private JobsRepository jobsRepository;

    public Iterable<Jobs> findAll() {

         return  jobsRepository.findAll();
    }
    public Jobs findByJobid (String jobid){
        return jobsRepository.findByJobid(jobid);
    }


}
