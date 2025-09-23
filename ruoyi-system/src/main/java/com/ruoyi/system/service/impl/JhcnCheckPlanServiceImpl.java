package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import com.ruoyi.system.domain.JhcnMaintenancePlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnCheckPlanMapper;
import com.ruoyi.system.domain.JhcnCheckPlan;
import com.ruoyi.system.service.IJhcnCheckPlanService;

import javax.validation.Validator;

/**
 * 检查计划Service业务层处理
 *
 * @author russel
 * @date 2025-09-12
 */
@Service
public class JhcnCheckPlanServiceImpl implements IJhcnCheckPlanService
{
    @Autowired
    private JhcnCheckPlanMapper jhcnCheckPlanMapper;
    @Autowired
    private Validator validator;

    /**
     * 查询检查计划
     *
     * @param id 检查计划主键
     * @return 检查计划
     */
    @Override
    public JhcnCheckPlan selectJhcnCheckPlanById(Long id)
    {
        return jhcnCheckPlanMapper.selectJhcnCheckPlanById(id);
    }

    /**
     * 查询检查计划列表
     *
     * @param jhcnCheckPlan 检查计划
     * @return 检查计划
     */
    @Override
    public List<JhcnCheckPlan> selectJhcnCheckPlanList(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.selectJhcnCheckPlanList(jhcnCheckPlan);
    }

    /**
     * 新增检查计划
     *
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    @Override
    public int insertJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.insertJhcnCheckPlan(jhcnCheckPlan);
    }

    /**
     * 修改检查计划
     *
     * @param jhcnCheckPlan 检查计划
     * @return 结果
     */
    @Override
    public int updateJhcnCheckPlan(JhcnCheckPlan jhcnCheckPlan)
    {
        return jhcnCheckPlanMapper.updateJhcnCheckPlan(jhcnCheckPlan);
    }

    /**
     * 批量删除检查计划
     *
     * @param ids 需要删除的检查计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnCheckPlanByIds(Long[] ids)
    {
        return jhcnCheckPlanMapper.deleteJhcnCheckPlanByIds(ids);
    }

    /**
     * 删除检查计划信息
     *
     * @param id 检查计划主键
     * @return 结果
     */
    @Override
    public int deleteJhcnCheckPlanById(Long id)
    {
        return jhcnCheckPlanMapper.deleteJhcnCheckPlanById(id);
    }

    @Override
    public String importList(List<JhcnCheckPlan> checkList, Boolean updateSupport, String operName) {
        if (StringUtils.isNull(checkList) || checkList.size() == 0)
        {
            throw new ServiceException("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (JhcnCheckPlan plan : checkList)
        {
            try
            {
                JhcnCheckPlan model = jhcnCheckPlanMapper.selectJhcnCheckPlanByDeviceNo(plan.getDeviceNo());
                // 不存在则新增
                if (StringUtils.isNull(model))
                {
                    BeanValidators.validateWithException(validator, plan);
                    jhcnCheckPlanMapper.insertJhcnCheckPlan(plan);
                    System.out.println("新增一条");
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、 检定计划 " +plan.getDeviceNo() + " 导入成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、检定计划 " +plan.getDeviceNo() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、检定计划 " +plan.getDeviceNo()+ " 导入失败：";
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
