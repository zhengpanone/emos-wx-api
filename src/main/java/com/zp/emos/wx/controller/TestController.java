package com.zp.emos.wx.controller;

import com.zp.emos.wx.common.util.R;
import com.zp.emos.wx.dto.TestSayHelloDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@Api("测试web接口")
@Validated
public class TestController {
    @PostMapping("/sayhello")
    @ApiOperation("测试swagger中调用")
    public R sayHello(@Validated @RequestBody TestSayHelloDTO testDto){
        return R.ok().put("message","hello,"+ testDto.getName());
    }
}
