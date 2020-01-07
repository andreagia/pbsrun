package org.cirmmp.pbsrun.service;

import org.cirmmp.pbsrun.PbsrunApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubmitServiceImpl implements SubmitService {

    Logger LOG = LoggerFactory.getLogger(SubmitServiceImpl.class);

    @Override
    public String run(String dirrun, String exec)  throws Exception {
        List<String> stroout = new ArrayList<>();
        List<String> pbsrunin = new ArrayList<>();
        File dir = new File(dirrun);
        File execf = new File(dirrun, exec);
        LOG.info(dirrun+" "+ execf+" "+ dir.getAbsolutePath() +" "+execf.getAbsolutePath());
        if (dir.exists() && execf.exists()) {
            String finaljobid = "";
            Matcher found;
            pbsrunin.add("#!/bin/bash");
            pbsrunin.add("PBS -k o");
            pbsrunin.add(" sleep 100");
            pbsrunin.add("sh " + exec);
            pbsrunin.add("echo \"JOBFINISHED-JOBS\"");
            String pbsrunfile = "sub";
            Files.write(Paths.get(dirrun, pbsrunfile), pbsrunin);
            List<String> cmdexe = Arrays.asList("/usr/bin/qsub", "-q", "cerm_short", pbsrunfile);
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command(cmdexe);
            processBuilder.directory(dir);
            int exitCode = 100;
            Pattern pattern = Pattern.compile("^[0-9]+.pbs-enmr.cerm.unifi.it");
            Pattern pattern1 = Pattern.compile("[.]");
            Process process = processBuilder.start();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorreader =
                    new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
           // System.out.println("\nPartito 2 ---------> : ");
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                found = pattern.matcher(line);
                if (found.find()) {
                    String[] spliout = pattern1.split(line);
                    System.out.println("JobID = "+spliout[0]);
                    finaljobid = spliout[0];
                }
            }
            String lineerror;
            //System.out.println("\nPartito ERROR ---------> : ");
            List<String> errout = new ArrayList<>();
            while ((lineerror = errorreader.readLine()) != null) {
                errout.add(lineerror);
               // System.out.println(lineerror);
            }
            errorreader.close();
            reader.close();
            errout.forEach(System.out::print);
            exitCode = process.waitFor();
            //System.out.println("Exited with error code : " + exitCode);
            if (errout.size() > 0) return null;
            return finaljobid;
        } else {

            LOG.info("error directory doesen't exist or exec not present");
            System.out.println("error directory doesen't exist or exec not present");
            return null;
        }

    }

}
