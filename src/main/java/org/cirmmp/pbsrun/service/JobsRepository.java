package org.cirmmp.pbsrun.service;

import org.cirmmp.pbsrun.model.Jobs;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobsRepository extends CrudRepository<Jobs, Long> {
    @Query("select c from Jobs c where c.jobid like  %?1")
    Jobs findByJobid(String jobid);
}