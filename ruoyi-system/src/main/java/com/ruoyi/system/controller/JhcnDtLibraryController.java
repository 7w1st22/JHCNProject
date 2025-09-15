package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.mapper.JhcnModuleBindMapper;
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
import com.ruoyi.system.domain.JhcnDtLibrary;
import com.ruoyi.system.service.IJhcnDtLibraryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 单体库Controller
 *
 * @author ruoyi
 * @date 2025-08-25
 */
@RestController
@RequestMapping("/system/library")
public class JhcnDtLibraryController extends BaseController
{
    @Autowired
    private IJhcnDtLibraryService jhcnDtLibraryService;
    @Autowired
    private JhcnModuleBindMapper jhcnModuleBindMapper;

    /**
     * 查询单体库列表
     */
    @PreAuthorize("@ss.hasPermi('system:library:list')")
    @GetMapping("/list")
    public TableDataInfo list(JhcnDtLibrary jhcnDtLibrary)
    {
        startPage();
        List<JhcnDtLibrary> list = jhcnDtLibraryService.selectJhcnDtLibraryList(jhcnDtLibrary);
        return getDataTable(list);
    }

    /**
     * 导出单体库列表
     */
    @PreAuthorize("@ss.hasPermi('system:library:export')")
    @Log(title = "单体库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JhcnDtLibrary jhcnDtLibrary)
    {
        List<JhcnDtLibrary> list = jhcnDtLibraryService.selectJhcnDtLibraryList(jhcnDtLibrary);
        ExcelUtil<JhcnDtLibrary> util = new ExcelUtil<JhcnDtLibrary>(JhcnDtLibrary.class);
        util.exportExcel(response, list, "单体库数据");
    }

    /**
     * 获取单体库详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:library:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(jhcnDtLibraryService.selectJhcnDtLibraryById(id));
    }

    @PreAuthorize("@ss.hasPermi('system:library:query')")
    @GetMapping("/getLibraryByDtCode/{dtCode}")
    public AjaxResult getLibraryByDtCode(@PathVariable("dtCode") String dtCode)
        {
            AjaxResult ajax = AjaxResult.success(jhcnDtLibraryService.selectJhcnDtLibraryByDtCode(dtCode));
            ajax.put("bindedInfo",jhcnModuleBindMapper.selectJhcnModuleBindByDtCode(dtCode));
        return ajax;
    }


    /**
     * 新增单体库
     */
    @PreAuthorize("@ss.hasPermi('system:library:add')")
    @Log(title = "单体库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JhcnDtLibrary jhcnDtLibrary)
    {
        return toAjax(jhcnDtLibraryService.insertJhcnDtLibrary(jhcnDtLibrary));
    }

    /**
     * 修改单体库
     */
    @PreAuthorize("@ss.hasPermi('system:library:edit')")
    @Log(title = "单体库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JhcnDtLibrary jhcnDtLibrary)
    {
        return toAjax(jhcnDtLibraryService.updateJhcnDtLibrary(jhcnDtLibrary));
    }

    /**
     * 删除单体库
     */
    @PreAuthorize("@ss.hasPermi('system:library:remove')")
    @Log(title = "单体库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(jhcnDtLibraryService.deleteJhcnDtLibraryByIds(ids));
    }


    @PreAuthorize("@ss.hasPermi('system:library:import')")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<JhcnDtLibrary> util = new ExcelUtil<JhcnDtLibrary>(JhcnDtLibrary.class);
        util.importTemplateExcel(response, "单体数据");
    }

    @PreAuthorize("@ss.hasPermi('system:library:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<JhcnDtLibrary> util = new ExcelUtil<JhcnDtLibrary>(JhcnDtLibrary.class);
        List<JhcnDtLibrary> dtList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = jhcnDtLibraryService.importDT(dtList, updateSupport, operName);
        return success(message);
    }

}
