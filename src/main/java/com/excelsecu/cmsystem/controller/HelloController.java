package com.excelsecu.cmsystem.controller;

import com.excelsecu.cmsystem.annotation.IgnoreLog;
import com.excelsecu.cmsystem.common.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/key_service/v1/api")
public class HelloController {

    /**
     * 后端测试接口
     * http://localhost:8118/key_service/v1/api/index
     * */
    @ApiOperation("检测接口状态")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @IgnoreLog
    public Result index(){
        log.info("Welcome to my api!");
        return Result.success("Welcome to my api!");
    }

}
