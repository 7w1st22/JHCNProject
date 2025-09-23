package com.ruoyi.quartz.mapper;

import com.ruoyi.quartz.domain.CheckPlan;
import com.ruoyi.quartz.domain.MaintenancePlan;

import java.util.List;

public interface PlanMapper {

    public List<MaintenancePlan> selectMaintenancePlanListByMonth();
//    public List<CheckPlan> selectCheckPlanListByMonth(JhcnMaintenancePlan jhcnMaintenancePlan);
}
