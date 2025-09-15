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
import com.ruoyi.system.domain.JhcnSpecification;
import com.ruoyi.system.service.IJhcnSpecificationService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 模组规格Controller
 * 
 * @author ruoyi
 * @date 2025-08-26
 */
@RestController
@RequestMapping("/system/specification")
public class JhcnSpecificationController extends BaseController
{
    @Autowired
    private IJhcnSpecificationService jhcnSpecificationService;

    /**
     * 查询模组规格列表
     */
    @PreAuthorize("@ss.hasPermi('system:specification:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnSpecification jhcnSpecification)
    {
        startPage();
        List<JhcnSpecification> list = jhcnSpecificationService.selectJhcnSpecificationList(jhcnSpecification);
        return getDataTable(list);
    }

    /**
     * 导出模组规格列表
     */
    @PreAuthorize("@ss.hasPermi('system:specification:export')")
    @Log(title = "模组规格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnSpecification jhcnSpecification)
    {
        List<JhcnSpecification> list = jhcnSpecificationService.selectJhcnSpecificationList(jhcnSpecification);
        ExcelUtil<JhcnSpecification> util = new ExcelUtil<JhcnSpecification>(JhcnSpecification.class);
        util.exportExcel(response, list, "模组规格数据");
    }

    /**
     * 获取模组规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:specification:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnSpecificationService.selectJhcnSpecificationById(id));
    }

    /**
     * 新增模组规格
     */
    @PreAuthorize("@ss.hasPermi('system:specification:add')")
    @Log(title = "模组规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnSpecification jhcnSpecification)
    {
        return toAjax(jhcnSpecificationService.insertJhcnSpecification(jhcnSpecification));
    }

    /**
     * 修改模组规格
     */
    @PreAuthorize("@ss.hasPermi('system:specification:edit')")
    @Log(title = "模组规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnSpecification jhcnSpecification)
    {
        return toAjax(jhcnSpecificationService.updateJhcnSpecification(jhcnSpecification));
    }

    /**
     * 删除模组规格
     */
    @PreAuthorize("@ss.hasPermi('system:specification:remove')")
    @Log(title = "模组规格", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnSpecificationService.deleteJhcnSpecificationByIds(ids));
    }
}
