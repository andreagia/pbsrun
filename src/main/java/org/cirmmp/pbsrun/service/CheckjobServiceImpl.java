package org.cirmmp.pbsrun.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckjobServiceImpl implements CheckjobService {

    @Override
    public String check(String dirrun, String jobid) throws Exception {
        File fcheck = new File(dirrun, "sub.o"+jobid);
        if (!fcheck.exists()) {
            List<String> retlist = new ArrayList<>();
            Files.lines( fcheck.toPath()) .map(String::trim) .filter(
                    s -> s.startsWith("JOBFINISHED-JOBS")
            ) .forEach(s -> retlist.add(s));
            if (retlist.size() > 0) return "Finishedd";
        }
        return null;
    }
}
