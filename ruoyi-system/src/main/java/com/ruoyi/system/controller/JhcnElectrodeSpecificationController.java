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
import com.ruoyi.system.domain.JhcnElectrodeSpecification;
import com.ruoyi.system.service.IJhcnElectrodeSpecificationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 电极规格管理Controller
 * 
 * @author Russel
 * @date 2025-10-22
 */
@RestController
@RequestMapping("/system/edlcSpecification")
public class JhcnElectrodeSpecificationController extends BaseController
{
    @Autowired
    private IJhcnElectrodeSpecificationService jhcnElectrodeSpecificationService;

    /**
     * 查询电极规格管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        startPage();
        List<JhcnElectrodeSpecification> list = jhcnElectrodeSpecificationService.selectJhcnElectrodeSpecificationList(jhcnElectrodeSpecification);
        return getDataTable(list);
    }

    /**
     * 导出电极规格管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:export')")
    @Log(title = "电极规格管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        List<JhcnElectrodeSpecification> list = jhcnElectrodeSpecificationService.selectJhcnElectrodeSpecificationList(jhcnElectrodeSpecification);
        ExcelUtil<JhcnElectrodeSpecification> util = new ExcelUtil<JhcnElectrodeSpecification>(JhcnElectrodeSpecification.class);
        util.exportExcel(response, list, "电极规格管理数据");
    }

    /**
     * 获取电极规格管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnElectrodeSpecificationService.selectJhcnElectrodeSpecificationById(id));
    }

    /**
     * 新增电极规格管理
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:add')")
    @Log(title = "电极规格管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        return toAjax(jhcnElectrodeSpecificationService.insertJhcnElectrodeSpecification(jhcnElectrodeSpecification));
    }

    /**
     * 修改电极规格管理
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:edit')")
    @Log(title = "电极规格管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnElectrodeSpecification jhcnElectrodeSpecification)
    {
        return toAjax(jhcnElectrodeSpecificationService.updateJhcnElectrodeSpecification(jhcnElectrodeSpecification));
    }

    /**
     * 删除电极规格管理
     */
    @PreAuthorize("@ss.hasPermi('system:edlcSpecification:remove')")
    @Log(title = "电极规格管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnElectrodeSpecificationService.deleteJhcnElectrodeSpecificationByIds(ids));
    }
}
