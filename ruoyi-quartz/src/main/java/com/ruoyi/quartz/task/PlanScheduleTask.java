package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.EmailUtil;
import com.ruoyi.quartz.domain.EmailUser;
import com.ruoyi.quartz.domain.MaintenancePlan;
import com.ruoyi.quartz.mapper.EmailUserMapper;
import com.ruoyi.quartz.mapper.PlanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.*;

/**
 * 定时任务发送提醒
 *
 * @author ruoyi
 */
@Component("planScheduleTask")
public class PlanScheduleTask {

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private PlanMapper planMapper;

    @Autowired
    private EmailUserMapper emailUserMapper;

    /**
     * 单条计划提醒
     * @param type
     * @param deviceNo
     */
    public void planReminded(String type,String deviceNo,String jtNo) throws MessagingException {
        if(type.equals("maintenance")){
            System.out.println("设备编号："+deviceNo+"的维护任务已开始");
            MaintenancePlan maintenancePlan = planMapper.selectMaintanceDateByDeviceNoAndJtNo(deviceNo,jtNo);

            if (maintenancePlan != null) {
                // 获取当前日期
                Date currentDate = new Date();

                Map<String, Object> variables = new HashMap<>();
                variables.put("plan", maintenancePlan);
                variables.put("currentDate", currentDate);

                List<EmailUser> whjh = emailUserMapper.selectEmailUserList("whjh");
                for (EmailUser emailUser : whjh){
                    if(emailUser.getEmail()!=null&&!emailUser.getEmail().equals("")){
                        emailUtil.sendTemplateEmail(
                                "设备编号："+deviceNo+"的维护计划已开始",  // 标题
                                "maintenance-plan-single",
                                variables,
                                emailUser.getEmail()
                        );
                    }
                }
            }
        }
        if(type.equals("check")){
            System.out.println("设备编号："+deviceNo+"的巡检任务已开始");
        }
    }


    /**
     * 下月计划提醒
     */
    public void checkNextMonthMaintenance() {
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

        List<EmailUser> whjh = emailUserMapper.selectEmailUserList("whjh");
        for (EmailUser emailUser : whjh){
            if(emailUser.getEmail()!=null&&!emailUser.getEmail().equals("")){
                try{
                    emailUtil.sendTemplateEmail(
                            nextMonthDisplay + "维护计划提醒",  // 动态标题
                            "maintenance-plan-email",
                            variables,
                            emailUser.getEmail()
                    );
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 每日计划提醒
     * @throws MessagingException
     */
    public void checkNextDayMaintenance(){
        // 当日所有维护计划
        List<MaintenancePlan> plans = planMapper.selectMaintenancePlanListToday();

        if(!plans.isEmpty()){
            // 获取当前日期
            Date currentDate = new Date();
            for(MaintenancePlan plan:plans){
                Map<String, Object> variables = new HashMap<>();
                variables.put("plan", plan);
                variables.put("currentDate", currentDate);

                List<EmailUser> whjh = emailUserMapper.selectEmailUserList("whjh");
                for (EmailUser emailUser : whjh){
                    if(emailUser.getEmail()!=null&&!emailUser.getEmail().equals("")){
                        try{
                            emailUtil.sendTemplateEmail(
                                    "设备编号："+plan.getDeviceNo()+"的维护计划已开始",  // 标题
                                    "maintenance-plan-single",
                                    variables,
                                    emailUser.getEmail()
                            );
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }
            }
        }
    }
}
