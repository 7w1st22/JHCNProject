package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.system.domain.JhcnProcess;
import com.ruoyi.system.service.IJhcnProcessService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 工序Controller
 *
 * @author Russel
 * @date 2025-10-09
 */
@RestController
@RequestMapping("/system/process")
public class JhcnProcessController extends BaseController
{
    @Autowired
    private IJhcnProcessService jhcnProcessService;

    /**
     * 查询工序列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnProcess jhcnProcess)
    {
        startPage();
        List<JhcnProcess> list = jhcnProcessService.selectJhcnProcessList(jhcnProcess);
        return getDataTable(list);
    }

    /**
     * 导出工序列表
     */
    @PreAuthorize("@ss.hasPermi('system:process:export')")
    @Log(title = "工序", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnProcess jhcnProcess)
    {
        List<JhcnProcess> list = jhcnProcessService.selectJhcnProcessList(jhcnProcess);
        ExcelUtil<JhcnProcess> util = new ExcelUtil<JhcnProcess>(JhcnProcess.class);
        util.exportExcel(response, list, "工序数据");
    }

    /**
     * 获取工序详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:process:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnProcessService.selectJhcnProcessById(id));
    }

    /**
     * 新增工序
     */
    @PreAuthorize("@ss.hasPermi('system:process:add')")
    @Log(title = "工序", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnProcess jhcnProcess)
    {
        // 校验不存在
        if (jhcnProcessService.selectJhcnProcessByProcess(jhcnProcess.getProcess()) != null){
            return error("工序已存在");
        }
        return toAjax(jhcnProcessService.insertJhcnProcess(jhcnProcess));
    }

    /**
     * 修改工序
     */
    @PreAuthorize("@ss.hasPermi('system:process:edit')")
    @Log(title = "工序", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnProcess jhcnProcess)
    {
        return toAjax(jhcnProcessService.updateJhcnProcess(jhcnProcess));
    }

    /**
     * 删除工序
     */
    @PreAuthorize("@ss.hasPermi('system:process:remove')")
    @Log(title = "工序", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnProcessService.deleteJhcnProcessByIds(ids));
    }
}
