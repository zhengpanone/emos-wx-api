package com.zp.emos.wx.constant;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class SystemConstants {
    public String attendanceStartTime;
    public String attendanceTime;
    public String attendanceEndTime;
    public String closingTime;
    public String closingStartTime;
    public String closingEndTime;

}
