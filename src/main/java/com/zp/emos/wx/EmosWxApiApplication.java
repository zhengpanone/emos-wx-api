package com.zp.emos.wx;

import cn.hutool.core.util.StrUtil;
import com.zp.emos.wx.constant.SystemConstants;
import com.zp.emos.wx.dao.SysConfigDao;
import com.zp.emos.wx.pojo.SysConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.List;

/**
 * XssFilter->OAuth2Filter->Controller->TokenAspect
 * XssFilter是标准的Servlet过滤器，他的执行的优先级高于ShiroFilter和AOP拦截器。
 * XssFilter使用@WebFilter注解定义出来的过滤器，所以它的优先级比SpringMVC中注册的Filter优先级更高，所以XssFilter早于SpringMVC执行
 */
@SpringBootApplication
@ServletComponentScan
@Slf4j
public class EmosWxApiApplication {
    @Autowired
    private SysConfigDao sysConfigDao;
    @Autowired
    private SystemConstants constants;

    public static void main(String[] args) {
        SpringApplication.run(EmosWxApiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<SysConfig> list = sysConfigDao.selectAllParam();
        list.forEach(item -> {
            String key = item.getParamKey();
            key = StrUtil.toCamelCase(key);
            String value = item.getParamValue();
            try {
                Field field = constants.getClass().getDeclaredField(key);
                field.set(constants, value);
            } catch (Exception e) {
                log.error("执行异常", e);
            }
        });

    }


}
