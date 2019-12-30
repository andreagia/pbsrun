package org.cirmmp.pbsrun;

import org.cirmmp.pbsrun.model.Jobs;
import org.cirmmp.pbsrun.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class PbsrunApplication implements ApplicationRunner {

    Logger LOG = LoggerFactory.getLogger(PbsrunApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(PbsrunApplication.class, args);
    }

    @Bean
    public HelloService getHelloService(){
        return  new DefaultHelloService();
    }

    @Autowired
    JobsRepository jobsRepository;

    @Autowired
    SubmitService submitService;

    @Autowired
    CheckjobService checkjobService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Jobs u1 = new Jobs("pippo", "abcdef");
        jobsRepository.save(u1);
        Jobs u2 = new Jobs("pluto", "abcde1234");
        jobsRepository.save(u2);
        Jobs u3 = new Jobs("pantalone", "ANFRR1234");
        jobsRepository.save(u3);
        LOG.info("EXECUTING : Run method of Application Runner");
        final List<String> nonOptionArgs = args.getNonOptionArgs();
        final String[] sourceArgs = args.getSourceArgs();
        final Set<String> optionNames = args.getOptionNames();

        nonOptionArgs.forEach(nonOption -> LOG.info("## Non Option Args : "+nonOption));
        optionNames.forEach(option -> LOG.info("## Option Names    : "+option));
        Arrays.stream(sourceArgs).forEach(srcArgs ->LOG.info("## Source Args     : "+srcArgs));
        LOG.info("## Option Value of --optionalArg1 : "+args.getOptionValues("optionalArg1"));
        LOG.info("## Option Value of --optionalArg2 : "+args.getOptionValues("optionalArg2"));
        LOG.info("## Option Value of --status : "+args.getOptionValues("status"));

        getHelloService().hello();


        if(args.getOptionValues("status") != null) {
            String status = String.join("",args.getOptionValues("status"));
            LOG.info("TTTTTT "+String.join("",args.getOptionValues("status")));
            switch (status) {
                case "submit":
                    if (args.getOptionValues("dir") != null &&  args.getOptionValues("exec") != null) {
                        String dir = String.join("",args.getOptionValues("dir"));
                        String exec = String.join("",args.getOptionValues("exec"));
                        String ret = submitService.run(dir, exec);
                        Jobs savejobs = new Jobs(dir, ret);
                        jobsRepository.save(savejobs);
                    } else {
                        System.out.println("please use --dir=Directory and --exec=executable");
                    }
                    break;
                case "check":
                    LOG.info("PPPPPP "+String.join("",args.getOptionValues("status")));
                    if (args.getOptionValues("jobid") != null) {
                        Jobs findj = jobsRepository.findByJobid(args.getOptionValues("jobid").toString());
                        System.out.println(findj.getDirectory());
                        String statusc = checkjobService.check(findj.getDirectory(), findj.getJobid());
                        System.out.println(statusc);
                    } else {
                        System.out.println("please use --jobid=jobid");
                    }
                    break;

                default:
                    System.out.println("please use --status=[submit or check]");

            }
        }else {
            System.out.println("please use status");
        }
//        String ret = submitService.run("pippo");
//        LOG.info(ret);
//        Jobs savejobs = new Jobs("pippo", ret);
//        jobsRepository.save(savejobs);
//        Iterable<Jobs> jo = jobsRepository.findAll();
//        jo.forEach(System.out::println);
//        Jobs findj = jobsRepository.findByJobid(ret);
//        System.out.println(findj.getDirectory());
//        checkjobService.check(findj.getDirectory(),findj.getJobid());


    }

}
