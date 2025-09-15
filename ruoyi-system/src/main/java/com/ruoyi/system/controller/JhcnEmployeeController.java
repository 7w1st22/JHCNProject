package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.JhcnEmployee;
import com.ruoyi.system.service.IJhcnEmployeeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * employeeInfoController
 *
 * @author russel
 * @date 2025-04-16
 */
@RestController
@RequestMapping("/system/employee")
public class JhcnEmployeeController extends BaseController
{
    @Autowired
    private IJhcnEmployeeService jhcnEmployeeService;

    /**
     * 查询employeeInfo列表
     */
    @PreAuthorize("@ss.hasPermi('system:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnEmployee jhcnEmployee)
    {
        startPage();
        List<JhcnEmployee> list = jhcnEmployeeService.selectJhcnEmployeeList(jhcnEmployee);
        return getDataTable(list);
    }

    /**
     * 导出employeeInfo列表
     */
    @PreAuthorize("@ss.hasPermi('system:employee:export')")
    @Log(title = "employeeInfo", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnEmployee jhcnEmployee)
    {
        List<JhcnEmployee> list = jhcnEmployeeService.selectJhcnEmployeeList(jhcnEmployee);
        ExcelUtil<JhcnEmployee> util = new ExcelUtil<JhcnEmployee>(JhcnEmployee.class);
        util.exportExcel(response, list, "employeeInfo数据");
    }

    /**
     * 获取employeeInfo详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:employee:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnEmployeeService.selectJhcnEmployeeById(id));
    }

    /**
     * 新增employeeInfo
     */
    @PreAuthorize("@ss.hasPermi('system:employee:add')")
    @Log(title = "employeeInfo", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnEmployee jhcnEmployee)
    {
        return toAjax(jhcnEmployeeService.insertJhcnEmployee(jhcnEmployee));
    }

    /**
     * 修改employeeInfo
     */
    @PreAuthorize("@ss.hasPermi('system:employee:edit')")
    @Log(title = "employeeInfo", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnEmployee jhcnEmployee)
    {
        return toAjax(jhcnEmployeeService.updateJhcnEmployee(jhcnEmployee));
    }

    /**
     * 删除employeeInfo
     */
    @PreAuthorize("@ss.hasPermi('system:employee:remove')")
    @Log(title = "employeeInfo", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnEmployeeService.deleteJhcnEmployeeByIds(ids));
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<JhcnEmployee> util = new ExcelUtil<JhcnEmployee>(JhcnEmployee.class);
        util.importTemplateExcel(response, "员工数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<JhcnEmployee> util = new ExcelUtil<JhcnEmployee>(JhcnEmployee.class);
        List<JhcnEmployee> employeeList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = jhcnEmployeeService.importUser(employeeList, updateSupport, operName);
        return success(message);
    }

}
