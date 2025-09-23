package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.EmailUtil;
import com.ruoyi.quartz.domain.MaintenancePlan;
import com.ruoyi.quartz.mapper.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("planScheduleTask")
public class PlanScheduleTask {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PlanMapper planMapper;

    /**
     * 单条计划提醒
     * @param type
     * @param deviceNo
     * @param deviceName
     * @param jtNo
     * @param deviceType
     * @param company
     * @param maintenanceTime
     * @param process
     */
    public void planReminded(String type,
                             String deviceNo,String deviceName,
                             String jtNo,String deviceType,
                             String company, String maintenanceTime,
                             String process){
        if(type.equals("maintenance")){
            System.out.println("设备编号："+deviceNo+"的维护任务已开始");
        }
        if(type.equals("check")){
            System.out.println("设备编号："+deviceNo+"的巡检任务已开始");
        }
    }


    /**
     * 下月计划提醒
     * @throws MessagingException
     */
    public void checkNextMonthMaintenance() throws MessagingException {
        // 下个月所有维护计划
        List<MaintenancePlan> plans = planMapper.selectMaintenancePlanListByMonth();
        // 计算下个月份信息
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，需要+1
        // 格式化月份显示
        String nextMonthDisplay = year + "年" + month + "月";
        String nextMonthForQuery = year + "-" + String.format("%02d", month);

        Map<String, Object> variables = new HashMap<>();
        variables.put("plans", plans);
        variables.put("nextMonthDisplay", nextMonthDisplay);
        variables.put("nextMonthForQuery", nextMonthForQuery);
        variables.put("nextYear", year);
        variables.put("nextMonth", month);


        emailUtil.sendTemplateEmail(
                nextMonthDisplay + "维护计划提醒",  // 动态标题
                "maintenance-plan-email",
                variables,
                "973830996@qq.com"
        );
    }
}
