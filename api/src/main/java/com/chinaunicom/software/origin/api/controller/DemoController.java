package com.chinaunicom.software.origin.api.controller;

import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapperUtils;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.chinaunicom.software.origin.common.utils.AppLogHubFactory;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@LogMarker
//@RestController
//@RequestMapping("demo")
public class DemoController {

    static LogHub logger = AppLogHubFactory.staticBuild();

    public static class Model {
        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        private String Name;
        private Date date;
    }

    @GetMapping("model")
    public Model model(@RequestBody Model model) {
        model.setDate(new Date());
        model.setName("name");
        return model;
    }


    @GetMapping("getModel")
    public Model getModel(@RequestBody Model model) {
        model = new Model();
        model.setDate(new Date());
        model.setName("name");
        return model;
    }

    @PostMapping("postModel")
    public Model postModel(@RequestBody Model model) {
        model = new Model();
        model.setDate(new Date());
        model.setName("name");
        return model;
    }

    @GetMapping("date")
    public Date date() {
        return new Date();
    }

    private List<String> getStrings() {
        List<String> list = new ArrayList<>();
        list.add("数据1");
        list.add("数据2");
        list.add("数据3");
        return list;
    }

    @ApiOperation(value = "返回集合用例")
    @GetMapping("foo")
    public ResponseWrapper foo() {
        List<String> list = null;
        try {
            String content = "请求获取数据";
            list = getStrings();
            logger.i("返回信息", list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.e("错误信息", e);
        }
        ResponseWrapper responseWrapper = ResponseWrapperUtils.succeed(list);
        return responseWrapper;
    }


    @ApiOperation(value = "日志记录用例1（不带参数）")
    @ApiImplicitParam(name = "log1", paramType = "query")
    @GetMapping("log1")
    public String log1() {
        logger.info("log1", "some information");
        int i = 0;
        i = 1 / 0;
        return "log1";
    }

    @ApiOperation(value = "日志记录用例1（带参数）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "summary", required = true, paramType = "path", value = "日志摘要", dataType = "string"),
            @ApiImplicitParam(name = "msg", required = true, paramType = "path", value = "日志内容", dataType = "string")
    })
    @GetMapping("log1/{summary}/{msg}")
    public String log1(@PathVariable String summary, @PathVariable String msg) {
        logger.info(summary, msg);
        return msg;
    }


    @GetMapping("log2")
    public String log2() {
        logger.info("log2", "some information");
        return "log2";
    }


//    @Autowired(required = false)
//    private AmqpTemplate rabbitTemplate;
//
//    @ApiOperation(value = "消息队列用例")
//    @ApiImplicitParam(name = "msg", required = true, paramType = "query", value = "消息内容", dataType = "string")
//    @GetMapping("mq")
//    public String mq() {
//        String msg = "111111111111";
//        rabbitTemplate.convertAndSend("application-log-queue", msg);
//        return msg;
//    }


    public static void logStyle() {
        logger.i("用户中心", "这里是待记录的日志内容");
        logger.i("项目中心", "这里是待记录的日志内容：{}，{}", new Object[]{"这里是参数1", "这里是参数2"});
        Model model = new Model();
        model.setDate(new Date());
        model.setName("名称");
        logger.i("过程管理", "这里可以记录一个数据对象", model);
        logger.info("源码管理", "这里是待记录的日志内容，info与i方法两者相同");



        logger.d("测试管理", "这里是待记录的日志内容");
        logger.debug("流水线", "这里是待记录的日志内容");
        logger.t("配置管理", "这里是待记录的日志内容");
        logger.trace("自定义分类", "这里是待记录的日志内容");
        List<Integer> list = new ArrayList<>();
        try {
            list.add(0);
            list.add(1);
            int i = list.get(0);
            int result = i / i;
        } catch (Exception e) {
            logger.e("error日志", list, e);
            logger.error("error日志", list, e);
            logger.w("warn日志", list, e);
            logger.warn("warn日志", list, e);
            logger.f("fatal日志", list, e);
            logger.fatal("fatal日志", list, e);
        }
    }
}
