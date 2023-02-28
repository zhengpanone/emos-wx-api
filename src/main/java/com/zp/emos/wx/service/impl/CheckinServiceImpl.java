package com.zp.emos.wx.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.zp.emos.wx.constant.SystemConstants;
import com.zp.emos.wx.dao.TbCheckinDao;
import com.zp.emos.wx.dao.TbHolidaysDao;
import com.zp.emos.wx.dao.TbWorkdayDao;
import com.zp.emos.wx.service.CheckinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Scope("prototype")
@Slf4j
public class CheckinServiceImpl implements CheckinService {
    @Autowired
    private SystemConstants systemConstants;
    @Autowired
    private TbHolidaysDao holidaysDao;
    @Autowired
    private TbWorkdayDao workdayDao;
    @Autowired
    private TbCheckinDao checkinDao;

    @Override
    public String validCanCheckIn(int userId, String date) {
        boolean b1 = holidaysDao.selectTodayIsHolidays() != null;
        boolean b2 = workdayDao.selectTodayIsWorkday() != null;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }
        if (b1) {
            type = "节假日";
        } else if (b2) {
            type = "工作日";
        }
        if (type.equals("节假日")) {
            return "节假日不需要考勤";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + systemConstants.attendanceStartTime;
            String end = DateUtil.today() + " " + systemConstants.attendanceEndTime;
            DateTime attendanceStart = DateUtil.parse(start);
            DateTime attendanceEnd = DateUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "没有到上班考勤开始时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "超过了上班考勤结束时间";
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("date",date);
                map.put("start", attendanceStart);
                map.put("end", attendanceEnd);
                boolean b3 = checkinDao.haveCheckin(map) != null;
                return b3 ? "今天已经考勤，不用重复考勤" : "可以考勤";
            }
        }

    }
}
