package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.domain.JhcnEmployee;

/**
 * employeeInfoService接口
 * 
 * @author russel
 * @date 2025-04-16
 */
public interface IJhcnEmployeeService 
{
    /**
     * 查询employeeInfo
     * 
     * @param id employeeInfo主键
     * @return employeeInfo
     */
    public JhcnEmployee selectJhcnEmployeeById(Long id);

    /**
     * 查询employeeInfo列表
     * 
     * @param jhcnEmployee employeeInfo
     * @return employeeInfo集合
     */
    public List<JhcnEmployee> selectJhcnEmployeeList(JhcnEmployee jhcnEmployee);

    /**
     * 新增employeeInfo
     * 
     * @param jhcnEmployee employeeInfo
     * @return 结果
     */
    public int insertJhcnEmployee(JhcnEmployee jhcnEmployee);

    /**
     * 修改employeeInfo
     * 
     * @param jhcnEmployee employeeInfo
     * @return 结果
     */
    public int updateJhcnEmployee(JhcnEmployee jhcnEmployee);

    /**
     * 批量删除employeeInfo
     * 
     * @param ids 需要删除的employeeInfo主键集合
     * @return 结果
     */
    public int deleteJhcnEmployeeByIds(Long[] ids);

    /**
     * 删除employeeInfo信息
     * 
     * @param id employeeInfo主键
     * @return 结果
     */
    public int deleteJhcnEmployeeById(Long id);

    String importUser(List<JhcnEmployee> employeeList, Boolean updateSupport, String operName);
}
