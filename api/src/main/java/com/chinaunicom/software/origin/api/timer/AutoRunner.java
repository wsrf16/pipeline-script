package com.chinaunicom.software.origin.api.timer;

import com.aio.portable.swiss.schedule.timer.AbstractTask;
import com.aio.portable.swiss.schedule.timer.Launcher;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.LogHub;
import com.chinaunicom.software.origin.common.config.root.ApplicationConfig;
import com.chinaunicom.software.origin.common.config.root.children.LauncherConfig;
import com.chinaunicom.software.origin.common.jenkins.script.node.PipelineObject;
import com.chinaunicom.software.origin.common.utils.AppLogHubFactory;
import com.chinaunicom.software.origin.db.slave.vo.BookCondition;
import com.chinaunicom.software.origin.service.jenkins.JobService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AutoRunner implements CommandLineRunner {

//    @Autowired
//    BookMasterService bookMasterService;
//
//    @Autowired
//    BookSlaveService bookSlaveService;

    @Autowired
    ApplicationConfig config;

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    LogHub log = AppLogHubFactory.staticBuild();

    @Autowired
    JobService jobService;

    @Override
    public void run(String... args) throws Exception {
//        DemoController.logStyle();

        LauncherConfig launcherConfig = config.getLauncher();
        String cron = launcherConfig.getCron();
        Launcher launcher = new Launcher(new AbstractTask() {
            @Override
            public void run() {
                Config appConfig = ConfigService.getAppConfig();
                System.out.println("定时运行");
            }
        }, cron, threadPoolTaskScheduler, false);

        BookCondition condition = new BookCondition();
        condition.setNameLike("%heart%");
        condition.setIdGreaterThanEqual(5L);
        condition.setIdLessThanEqual(9L);
        condition.setId(1L);
        ArrayList<String> ins = new ArrayList<>();
        ins.add("Shakespeare");
        condition.setAuthorIn(ins);
//        List<Book> list = bookSlaveService.findBy(condition);

//        launcher.start();


        PipelineObject pipelineObject = jobService.buildDemo2();

        String frontPipeline = "{\n" +
                "\t\"pipeline\": {\n" +
                "\t\t\"agent\": \"any\",\n" +
                "\t\t\"stages\": {\n" +
                "\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\"name\": \"阶段1\",\n" +
                "\t\t\t\t\"parallel\": {\n" +
                "\t\t\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\t\t\"name\": \"并行1\",\n" +
                "\t\t\t\t\t\t\"stagesList\": [{\n" +
                "\t\t\t\t\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"CQM1\"\n" +
                "\t\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"CQM2\"\n" +
                "\t\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\"name\": \"并行2\",\n" +
                "\t\t\t\t\t\t\"stagesList\": [{\n" +
                "\t\t\t\t\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"soner1\"\n" +
                "\t\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"soner2\"\n" +
                "\t\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}]\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"name\": \"阶段2\",\n" +
                "\t\t\t\t\"parallel\": {\n" +
                "\t\t\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\t\t\"name\": \"CheckLine1\",\n" +
                "\t\t\t\t\t\t\"stagesList\": [{\n" +
                "\t\t\t\t\t\t\t\"stageList\": [{\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"CQM1\"\n" +
                "\t\t\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\t\t\"name\": \"CQM2\"\n" +
                "\t\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t\t}]\n" +
                "\t\t\t\t\t}]\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}]\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

//        PipelineObject pipelineObject = JacksonSugar.json2T(frontPipeline, PipelineObject.class);

        String script = pipelineObject.toScript();
        System.out.println(JacksonSugar.getObjectMapper(true, false, null).writeValueAsString(pipelineObject));
        System.out.println(script);

        ins.add("Shakespeare2");
        ins.add("Shakespeare3");
//        String sss = placeHolder("dabcd");

        System.exit(0);
        if (1 == 1)
            return;
    }




}
