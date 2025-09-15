package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.JhcnEmployee;

/**
 * employeeInfoMapper接口
 * 
 * @author russel
 * @date 2025-04-16
 */
public interface JhcnEmployeeMapper 
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
     * 删除employeeInfo
     * 
     * @param id employeeInfo主键
     * @return 结果
     */
    public int deleteJhcnEmployeeById(Long id);

    /**
     * 批量删除employeeInfo
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJhcnEmployeeByIds(Long[] ids);

    JhcnEmployee selectJhcnEmployeeByName(String name);

    JhcnEmployee selectJhcnEmployeeByCardNo(String credentialNo);
}
