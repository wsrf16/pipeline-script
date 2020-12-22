package com.chinaunicom.software.origin.service.jenkins;

import com.aio.portable.swiss.global.Constant;
import com.chinaunicom.software.origin.common.jenkins.JenkinsServerFactory;
import com.chinaunicom.software.origin.common.jenkins.script.node.*;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobService {
    private JenkinsServerFactory jenkinsServerFactory;
    private JenkinsServer jenkinsServer;
    private JenkinsHttpClient jenkinsHttpClient;

//    public JobService(JenkinsServerFactory jenkinsServerFactory) {
//        this.jenkinsServerFactory = jenkinsServerFactory;
//        this.jenkinsServer = jenkinsServerFactory.server();
//        this.jenkinsHttpClient = jenkinsServerFactory.client();
//    }

    public PipelineObject buildDemo1() {
        Steps steps1 = new Steps();
        steps1.setBlock("echo 'initial....'" + Constant.LINE_SEPARATOR + "echo 'build....'" + Constant.LINE_SEPARATOR);
        steps1.setScript(new Script() {{
            setBlock("echo 'this is some scripts.'");
        }});

        Stage stage1 = new Stage();
        stage1.setName("stage1");
        stage1.setSteps(steps1);

        Stage stage2 = new Stage();
        stage2.setName("Build");

        Stage stage3 = new Stage();
        stage3.setName("Test");
        stage3.setFailFast(true);

        List<Stage> stageList = new ArrayList<>();
        stageList.add(stage2);
        stageList.add(stage3);

        Parallel parallel = new Parallel();
        parallel.setStageList(stageList);

        stage1.setParallel(parallel);

        Stages stages = new Stages();
        stages.setStage(stage1);

        Triggers triggers = new Triggers();
        triggers.setBlock("cron('H 4/* 0 0 1-5')");

        Pipeline pipeline = new Pipeline();
        pipeline.setStages(stages);
        pipeline.setAgent("any");
        pipeline.setTriggers(triggers);

        PipelineObject pipelineScript = new PipelineObject();
        pipelineScript.setPipeline(pipeline);

        return pipelineScript;
    }

    public PipelineObject buildDemo2() {
        /*
          stages
            stage('Check')
              stages
                stage('Sonar')
            stage('Code')
              stages
                parallel
                  stage('GitLabDependency')
                  stage('GitHubPayCenter')
            stage('Build')
              stage('Maven')
            stage('Upload')
              stage('Habor')
            stage('Deploy')
              stage('Habor')
            stage('Test')
              stage('JUnit')
            stage('Publish')
              stage('BlueGreen')
         */

        Steps steps = new Steps();
        steps.setBlock("echo 'initial....'" + Constant.LINE_SEPARATOR + "echo 'build....'" + Constant.LINE_SEPARATOR);
        steps.setScript(new Script() {{
            setBlock("echo 'this is some scripts.'");
        }});

//        Stage stageSonar = new Stage();
//        stageSonar.setName("Sonar");
//        stageSonar.setSteps(steps);

//        Stages stagesSonar = new Stages();
//        stagesSonar.setStageList(new ArrayList<>(Arrays.asList(new Stage[]{stageSonar})));

        Stage stageCQM1 = new Stage();
        stageCQM1.setName("CQM1");
        stageCQM1.setSteps(steps);

        Stage stageCQM2 = new Stage();
        stageCQM2.setName("CQM2");
        stageCQM2.setSteps(steps);

        Stages stagesCQM = new Stages();
        stagesCQM.setStageList(new ArrayList<>(Arrays.asList(new Stage[]{stageCQM1, stageCQM2})));

        Stage stageCheckInner = new Stage();
        stageCheckInner.setName("CheckLine1");
//        stageCheckInner.setStagesList(new ArrayList<>(Arrays.asList(new Stages[]{stagesCQM})));
        stageCheckInner.setStages(stagesCQM);

        Stage stageCheck = new Stage();
        stageCheck.setName("Check");
        stageCheck.setParallel(new Parallel(){{setStageList(new ArrayList<>(Arrays.asList(new Stage[]{stageCheckInner})));}});

        Stage stageDependency = new Stage();
        stageDependency.setName("Dependency");
        stageDependency.setSteps(steps);

        Stage stagePayCenter = new Stage();
        stagePayCenter.setName("PayCenter");
        stagePayCenter.setSteps(steps);

        Stage stageCode = new Stage();
        stageCode.setName("Code");
        Parallel parallel = new Parallel();
        parallel.setStageList(new ArrayList<>(Arrays.asList(new Stage[]{stageDependency, stagePayCenter})));
        stageCode.setParallel(parallel);

//        Steps stepsMaven = new Steps();
//        stepsMaven.setModule("api");
//        stepsMaven.setOutputFile("order-management.jar");
//        stepsMaven.setWorkDirectory("./");
//        stepsMaven.setSteps(steps);

        Stage stageMaven = new Stage();
        stageMaven.setName("Maven");
        stageMaven.setSteps(steps);


        Stage stageBuild = new Stage();
        stageBuild.setName("Build");
        stageBuild.setParallel(new Parallel(){{setStage(stageMaven);}});
//        stageBuild.setStages(new Stages(stageMaven));

        Stage stageHarbor = new Stage();
        stageHarbor.setName("Harbor");
        stageHarbor.setSteps(steps);

        Stage stageUpload = new Stage();
        stageUpload.setName("Upload");
//        stageUpload.setStages(new Stages(stageHarbor));
        stageUpload.setParallel(new Parallel(){{setStage(stageHarbor);}});

        Stage stageK8s = new Stage();
        stageK8s.setName("K8s");
        stageK8s.setSteps(steps);

        Stage stageDeploy = new Stage();
        stageDeploy.setName("Deploy");
//        stageDeploy.setStages(new Stages(stageK8s));
        stageDeploy.setParallel(new Parallel(){{setStage(stageK8s);}});

        Stage stageJUnit = new Stage();
        stageJUnit.setName("JUnit");
        stageJUnit.setSteps(steps);

        Stage stageTest = new Stage();
        stageTest.setName("Test");
        stageTest.setParallel(new Parallel() {{setStage(stageJUnit);}});

        Stage stageBlueGreen = new Stage();
        stageBlueGreen.setName("BlueGreen");
        stageBlueGreen.setSteps(steps);

        Stage stagePublish = new Stage();
        stagePublish.setName("Publish");
        stagePublish.setParallel(new Parallel(){{setStage(stageBlueGreen);}});
//        stagePublish.setStages(new Stages(stageBlueGreen));
//        stagePublish.setStages(new Stages(stageBlueGreen));/////////////////////

//        Stages stages = new Stages(stageCheck, stageCode, stageBuild, stageUpload, stageDeploy, stageTest, stagePublish);
        Stages stages = new Stages(stageCheck);

        Triggers triggers = new Triggers();
        triggers.setBlock("cron('H 4/* 0 0 1-5')");

        Pipeline pipeline = new Pipeline();
        pipeline.setAgent("any");
//        pipeline.setTriggers(triggers);
        pipeline.setStages(stages);

        PipelineObject pipelineScript = new PipelineObject();
        pipelineScript.setPipeline(pipeline);

        return pipelineScript;
    }

    public void build(String jobName, PipelineObject pipelineScript) {
        String jobXml = "";
        try {
            jenkinsServer.createJob(jobName, jobXml);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update(String jobName, PipelineObject pipelineScript) {
        String jobXml = "";
        try {
            jenkinsServer.updateJob(jobName, jobXml);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
