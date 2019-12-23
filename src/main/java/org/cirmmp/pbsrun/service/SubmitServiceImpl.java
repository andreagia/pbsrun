package org.cirmmp.pbsrun.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SubmitServiceImpl implements SubmitService {

    @Override
    public String run(String dirrun)  throws Exception {
        List<String> stroout = new ArrayList<>();
        List<String> pbsrunin = new ArrayList<>();
        String finaljobid = "";
        Matcher found;
        pbsrunin.add("");
        pbsrunin.add("");
        String pbsrunfile = "qsub";
        Files.write(Paths.get(pbsrunfile), pbsrunin);
        List<String> cmdexe = Arrays.asList("qsub", pbsrunfile);
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(cmdexe);
        File dir = new File(dirrun);
        if (!dir.exists()) dir.mkdirs();
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
        System.out.println("\nPartito 2 ---------> : ");
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            found = pattern.matcher(line);
            if(found.find()) {
                String[] spliout = pattern1.split(line);
                finaljobid = spliout[0];
            }
        }
        String lineerror;
        System.out.println("\nPartito ERROR ---------> : ");
        while ((lineerror = errorreader.readLine()) != null) {
            System.out.println(lineerror);
        }
        errorreader.close();
        reader.close();

        exitCode = process.waitFor();
        System.out.println("Exited with error code : " + exitCode);

        return finaljobid;
    }

}
