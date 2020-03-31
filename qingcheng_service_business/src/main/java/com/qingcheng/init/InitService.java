package com.qingcheng.init;

import com.qingcheng.service.business.AdService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class InitService implements InitializingBean {

    @Autowired
    private AdService adService;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("将广告加入到redis。。。");
        adService.saveAdToRedis();
    }
}
