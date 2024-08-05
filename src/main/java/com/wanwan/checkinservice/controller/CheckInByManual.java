package com.wanwan.checkinservice.controller;

import com.wanwan.checkinservice.jobs.ADrive;
import com.wanwan.checkinservice.jobs.JingBean;
import com.wanwan.checkinservice.jobs.JueJin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/checkInByManual")
public class CheckInByManual {

    @Resource
    private JueJin jueJin;
    @Resource
    private JingBean jingBean;

    @Resource
    private ADrive aDrive;

    @GetMapping("/jingDongCheckInByManual")
    public void jingDongCheckInByManual() throws Exception {
        jingBean.getJingBean();
    }

    @GetMapping("/jueJinCheckInByManual")
    public void jueJinCheckInByManual() throws Exception {
        jueJin.juejinCheckInAndDraw();
    }

    @PostMapping("/jueJinGrowth")
    public void jueJinGrowth() throws Exception {
        jueJin.jueJinGrowth();
    }

    @PostMapping("/aDrive")
    public void aDrive() throws Exception {
        aDrive.autoTask();
    }

    @PostMapping("/test")
    public void test() throws Exception {
        jueJin.isInvalidOfCookie();
    }
}
