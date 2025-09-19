package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanValidators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.JhcnEmployeeMapper;
import com.ruoyi.system.domain.JhcnEmployee;
import com.ruoyi.system.service.IJhcnEmployeeService;

import javax.validation.Validator;

/**
 * employeeInfoService业务层处理
 *
 * @author russel
 * @date 2025-04-16
 */
@Service
public class JhcnEmployeeServiceImpl implements IJhcnEmployeeService
{
    @Autowired
    private JhcnEmployeeMapper jhcnEmployeeMapper;

    @Autowired
    protected Validator validator;

    /**
     * 查询employeeInfo
     *
     * @param id employeeInfo主键
     * @return employeeInfo
     */
    @Override
    public JhcnEmployee selectJhcnEmployeeById(Long id)
    {
        return jhcnEmployeeMapper.selectJhcnEmployeeById(id);
    }

    /**
     * 查询employeeInfo列表
     *
     * @param jhcnEmployee employeeInfo
     * @return employeeInfo
     */
    @Override
    public List<JhcnEmployee> selectJhcnEmployeeList(JhcnEmployee jhcnEmployee)
    {
        return jhcnEmployeeMapper.selectJhcnEmployeeList(jhcnEmployee);
    }

    /**
     * 新增employeeInfo
     *
     * @param jhcnEmployee employeeInfo
     * @return 结果
     */
    @Override
    public int insertJhcnEmployee(JhcnEmployee jhcnEmployee)
    {
        return jhcnEmployeeMapper.insertJhcnEmployee(jhcnEmployee);
    }

    /**
     * 修改employeeInfo
     *
     * @param jhcnEmployee employeeInfo
     * @return 结果
     */
    @Override
    public int updateJhcnEmployee(JhcnEmployee jhcnEmployee)
    {
        return jhcnEmployeeMapper.updateJhcnEmployee(jhcnEmployee);
    }

    /**
     * 批量删除employeeInfo
     *
     * @param ids 需要删除的employeeInfo主键
     * @return 结果
     */
    @Override
    public int deleteJhcnEmployeeByIds(Long[] ids)
    {
        return jhcnEmployeeMapper.deleteJhcnEmployeeByIds(ids);
    }

    /**
     * 删除employeeInfo信息
     *
     * @param id employeeInfo主键
     * @return 结果
     */
    @Override
    public int deleteJhcnEmployeeById(Long id)
    {
        return jhcnEmployeeMapper.deleteJhcnEmployeeById(id);
    }

    @Override
    public String importUser(List<JhcnEmployee> employeeList, Boolean updateSupport, String operName) {
        if (StringUtils.isNull(employeeList) || employeeList.isEmpty())
        {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (JhcnEmployee employee : employeeList)
        {
            try
            {
                // 验证是否存在这个用户
                JhcnEmployee u = jhcnEmployeeMapper.selectJhcnEmployeeByCardNo(employee.getCredentialNo());
                if (StringUtils.isNull(u))
                {
                    BeanValidators.validateWithException(validator, employee);
                    jhcnEmployeeMapper.insertJhcnEmployee(employee);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + employee.getName() + " 导入成功");
                }
                else if (updateSupport)
                {
                    BeanValidators.validateWithException(validator, employee);
                    jhcnEmployeeMapper.updateJhcnEmployee(employee);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工 " + employee.getName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、员工 " + employee.getName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工 " + employee.getName() + " 导入失败：";
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


