package com.ruoyi.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.exception.job.TaskException;
import com.ruoyi.system.domain.JhcnWearingParts;
import org.quartz.SchedulerException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.JhcnMaintenancePlan;
import com.ruoyi.system.service.IJhcnMaintenancePlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 维护计划Controller
 *
 * @author russel
 * @date 2025-09-22
 */
@RestController
@RequestMapping("/system/maintenance")
public class JhcnMaintenancePlanController extends BaseController
{
    @Autowired
    private IJhcnMaintenancePlanService jhcnMaintenancePlanService;

    /**
     * 查询维护计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnMaintenancePlan jhcnMaintenancePlan,
                              @RequestParam Map<String, Object> allParams)
    {
        startPage();
        // 从所有参数中提取日期范围
        String beginDate = (String) allParams.get("maintanceDateRange[0]");
        String endDate = (String) allParams.get("maintanceDateRange[1]");
        if (beginDate != null && endDate != null) {
            Map<String, Object> params = jhcnMaintenancePlan.getParams();
            if (params == null) {
                params = new HashMap<>();
                jhcnMaintenancePlan.setParams(params);
            }
            params.put("beginDate", beginDate);
            params.put("endDate", endDate);
        }
        List<JhcnMaintenancePlan> list = jhcnMaintenancePlanService.selectJhcnMaintenancePlanList(jhcnMaintenancePlan);
        return getDataTable(list);
    }

    /**
     * 导出维护计划列表
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:export')")
    @Log(title = "维护计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnMaintenancePlan jhcnMaintenancePlan,
                       @RequestParam Map<String, Object> allParams)
    {

        // 从所有参数中提取日期范围
        String beginDate = (String) allParams.get("maintanceDateRange[0]");
        String endDate = (String) allParams.get("maintanceDateRange[1]");
        if (beginDate != null && endDate != null) {
            Map<String, Object> params = jhcnMaintenancePlan.getParams();
            if (params == null) {
                params = new HashMap<>();
                jhcnMaintenancePlan.setParams(params);
            }
            params.put("beginDate", beginDate);
            params.put("endDate", endDate);
        }

        List<JhcnMaintenancePlan> list = jhcnMaintenancePlanService.selectJhcnMaintenancePlanList(jhcnMaintenancePlan);
        ExcelUtil<JhcnMaintenancePlan> util = new ExcelUtil<JhcnMaintenancePlan>(JhcnMaintenancePlan.class);
        util.exportExcel(response, list, "维护计划数据");
    }

    /**
     * 获取维护计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnMaintenancePlanService.selectJhcnMaintenancePlanById(id));
    }

    /**
     * 新增维护计划
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:add')")
    @Log(title = "维护计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnMaintenancePlan jhcnMaintenancePlan) throws SchedulerException, TaskException {
        return toAjax(jhcnMaintenancePlanService.insertJhcnMaintenancePlan(jhcnMaintenancePlan));
    }

    /**
     * 修改维护计划
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:edit')")
    @Log(title = "维护计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnMaintenancePlan jhcnMaintenancePlan)
    {
        return toAjax(jhcnMaintenancePlanService.updateJhcnMaintenancePlan(jhcnMaintenancePlan));
    }

    /**
     * 删除维护计划
     */
    @PreAuthorize("@ss.hasPermi('system:maintenance:remove')")
    @Log(title = "维护计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnMaintenancePlanService.deleteJhcnMaintenancePlanByIds(ids));
    }

    @PreAuthorize("@ss.hasPermi('system:maintenance:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<JhcnMaintenancePlan> util = new ExcelUtil<JhcnMaintenancePlan>(JhcnMaintenancePlan.class);
        util.importTemplateExcel(response, "维护计划数据");
    }

    @PreAuthorize("@ss.hasPermi('system:maintenance:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<JhcnMaintenancePlan> util = new ExcelUtil<JhcnMaintenancePlan>(JhcnMaintenancePlan.class);
        List<JhcnMaintenancePlan> mList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = jhcnMaintenancePlanService.importList(mList, updateSupport, operName);
        return success(message);
    }
}
