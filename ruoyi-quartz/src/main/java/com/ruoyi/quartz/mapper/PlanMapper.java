package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.CheckPlan;
import com.ruoyi.quartz.domain.MaintenancePlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanMapper {

    public List<MaintenancePlan> selectMaintenancePlanListByMonth();

    public List<MaintenancePlan> selectMaintenancePlanListToday();
//    public List<CheckPlan> selectCheckPlanListByMonth(JhcnMaintenancePlan jhcnMaintenancePlan);
    public MaintenancePlan selectMaintanceDateByDeviceNoAndJtNo(@Param("deviceNo") String deviceNo, @Param("jtNo") String jtNo);

    public List<CheckPlan> selectCheckPlanListByMonth();

    public List<CheckPlan> selectCheckPlanListToday();
    //    public List<CheckPlan> selectCheckPlanListByMonth(JhcnMaintenancePlan jhcnMaintenancePlan);
    public CheckPlan selectCheckPlanDateByDeviceNoAndJtNo(@Param("deviceNo") String deviceNo, @Param("jtNo") String jtNo);


}
