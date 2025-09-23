package com.ruoyi.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.quartz.domain.SysJob;
import com.ruoyi.quartz.service.ISysJobService;
import com.ruoyi.system.domain.JhcnWearingParts;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnMaintenancePlanMapper;
import com.ruoyi.system.domain.JhcnMaintenancePlan;
import com.ruoyi.system.service.IJhcnMaintenancePlanService;

import javax.validation.Validator;

/**
 * 维护计划Service业务层处理
 *
 * @author russel
 * @date 2025-09-22
 */
@Service
public class JhcnMaintenancePlanServiceImpl implements IJhcnMaintenancePlanService
{
    @Autowired
    private JhcnMaintenancePlanMapper jhcnMaintenancePlanMapper;
    @Autowired
    private Validator validator;

    @Autowired
    ISysJobService sysJobService;

    /**
     * 查询维护计划
     *
     * @param id 维护计划主键
     * @return 维护计划
     */
    @Override
    public JhcnMaintenancePlan selectJhcnMaintenancePlanById(Long id)
    {
        return jhcnMaintenancePlanMapper.selectJhcnMaintenancePlanById(id);
    }

    /**
     * 查询维护计划列表
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 维护计划
     */
    @Override
    public List<JhcnMaintenancePlan> selectJhcnMaintenancePlanList(JhcnMaintenancePlan jhcnMaintenancePlan)
    {
        return jhcnMaintenancePlanMapper.selectJhcnMaintenancePlanList(jhcnMaintenancePlan);
    }

    /**
     * 新增维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    @Override
    public int insertJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan) throws SchedulerException, TaskException {
        int result = jhcnMaintenancePlanMapper.insertJhcnMaintenancePlan(jhcnMaintenancePlan);
        if(result>0 && jhcnMaintenancePlan.getReminded()==1){
            addScheduleJob(jhcnMaintenancePlan);
        }
        return result;

    }

    public void addScheduleJob(JhcnMaintenancePlan jhcnMaintenancePlan) throws SchedulerException, TaskException {
        // 添加定时任务
        SysJob job = new SysJob();
        job.setJobName("维护计划提醒-设备编号："+jhcnMaintenancePlan.getDeviceNo()+" 机台号："+jhcnMaintenancePlan.getJtNo());
        // 维护计划提醒任务组
        job.setJobGroup("MAINTENANCE");
        // 当月开始第一个工作日的九点
        job.setCronExpression(getEmailCornStringByDate(jhcnMaintenancePlan.getMaintanceDate()));
        job.setMisfirePolicy("3");
        job.setStatus("0");// 正常
        job.setInvokeTarget("planScheduleTask.planReminded('maintenance','" +jhcnMaintenancePlan.getDeviceNo()
                +"','"+jhcnMaintenancePlan.getJtNo()+"')");
        if(sysJobService.insertJob(job)>0){
            job.setStatus("0");
            sysJobService.changeStatus(job);
        }
    }

    // 当日提醒
    public String getEmailCornStringByDate(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int month = calendar.get(Calendar.MONTH) + 1; // 月份从0开始，所以要加1
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return String.format("%d %d %d %d %d ? %d", 0, 0, 8, day, month, year);
    }


    /**
     * 修改维护计划
     *
     * @param jhcnMaintenancePlan 维护计划
     * @return 结果
     */
    @Override
    public int updateJhcnMaintenancePlan(JhcnMaintenancePlan jhcnMaintenancePlan)
    {
        return jhcnMaintenancePlanMapper.updateJhcnMaintenancePlan(jhcnMaintenancePlan);
    }

    /**
     * 批量删除维护计划
     *
     * @param ids 需要删除的维护计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnMaintenancePlanByIds(Long[] ids)
    {
        return jhcnMaintenancePlanMapper.deleteJhcnMaintenancePlanByIds(ids);
    }

    /**
     * 删除维护计划信息
     *
     * @param id 维护计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnMaintenancePlanById(Long id)
    {
        return jhcnMaintenancePlanMapper.deleteJhcnMaintenancePlanById(id);
    }

    @Override
    public String importList(List<JhcnMaintenancePlan> mList, Boolean updateSupport, String operName) {
        if (StringUtils.isNull(mList) || mList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (JhcnMaintenancePlan plan : mList)
        {
            try
            {
                JhcnMaintenancePlan model = jhcnMaintenancePlanMapper.selectJhcnMaintenancePlanByDeviceNoAndJtNo(plan.getDeviceNo(), plan.getJtNo());
                // 不存在则新增
                if (StringUtils.isNull(model))
                {
                    BeanValidators.validateWithException(validator, plan);
                    // 导入的开启通知
                    plan.setReminded(1);
                    insertJhcnMaintenancePlan(plan);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、 维护计划 " +plan.getDeviceNo() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、维护计划 " +plan.getDeviceNo() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、维护计划 " +plan.getDeviceNo()+ " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

}
