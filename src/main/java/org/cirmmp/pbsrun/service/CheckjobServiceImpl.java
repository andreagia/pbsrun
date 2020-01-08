package org.cirmmp.pbsrun.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckjobServiceImpl implements CheckjobService {

    Logger LOG = LoggerFactory.getLogger(CheckjobServiceImpl.class);

    @Override
    public String check(String dirrun, String jobid) throws Exception {
        File fcheck = new File(dirrun, "sub.o"+jobid);
        LOG.info(fcheck.getAbsolutePath());

        if (fcheck.exists()) {
            List<String> retlist = new ArrayList<>();
            Files.lines( fcheck.toPath()).map(String::trim).filter(
                    s -> s.startsWith("JOBFINISHED-JOBS")
            ).forEach(retlist::add);

            if (retlist.size() > 0) return "JobID="+jobid+" Status=Done";
        }
        return "JobID="+jobid+ " Status=Running";
    }
}
