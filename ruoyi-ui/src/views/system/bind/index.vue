<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模组条码" prop="moduleCode">
        <el-input
          v-model="queryParams.moduleCode"
          placeholder="请选择模组条码"
          clearable
          readonly
        >
          <el-button slot="append" icon="el-icon-search" @click="openSearchModuleDialog"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="单体条码" prop="dtCode">
        <el-input
          v-model="queryParams.dtCode"
          placeholder="请选择单体条码"
          clearable
          readonly
        >
          <el-button slot="append" icon="el-icon-search" @click="openSearchDtDialog"></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="板子编号" prop="bzCode">
        <el-input
          v-model="queryParams.bzCode"
          placeholder="请输入板子编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:bind:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:bind:remove']"
        >解绑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:bind:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="bindList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模组条码" align="center" prop="moduleCode" />
      <el-table-column label="板子编号" align="center" prop="bzCode" />
      <el-table-column label="单体条码" align="center" prop="dtCode" />
      <el-table-column label="绑定时间" align="center" prop="bindTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.bindTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:bind:remove']"
          >解绑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改绑定管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模组条码" prop="moduleId">
          <el-input v-model="form.moduleCode" placeholder="请选择模组条码" readonly>
            <el-button slot="append" icon="el-icon-search" @click="openModuleDialog"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="单体条码" prop="dtId">
          <el-input v-model="form.dtCode" placeholder="请选择单体条码" readonly>
            <el-button slot="append" icon="el-icon-search" @click="openDtDialog"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="板子编号" prop="bzCode">
          <el-input v-model="form.bzCode" placeholder="请输入板子编号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 模组条码选择弹窗 -->
    <el-dialog title="选择模组条码" :visible.sync="moduleDialogVisible" width="70%" append-to-body>
      <el-form :model="moduleQueryParams" ref="moduleQueryForm" size="small" :inline="true">
        <el-form-item label="模组条码" prop="module">
          <el-input
            v-model="moduleQueryParams.module"
            placeholder="请输入模组条码"
            clearable
            @keyup.enter.native="handleModuleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleModuleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetModuleQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="moduleLoading" :data="moduleList" @row-click="handleModuleRowClick">
        <el-table-column label="模组条码" align="center" prop="module" />
        <el-table-column label="关联规格" align="center" prop="specification" />
        <el-table-column label="数量" align="center" prop="num" />
        <el-table-column label="已绑数量" align="center" prop="bindedNum" />
        <el-table-column label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.num === scope.row.bindedNum ? 'danger' : 'success'" size="small">
              {{ scope.row.num === scope.row.binded_num ? '已绑满' : '可绑定' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              :disabled="scope.row.num === scope.row.bindedNum"
              @click="handleModuleSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="moduleTotal>0"
        :total="moduleTotal"
        :page.sync="moduleQueryParams.pageNum"
        :limit.sync="moduleQueryParams.pageSize"
        @pagination="getModuleList"
      />
    </el-dialog>

    <!-- 模组条码搜索弹窗 -->
    <el-dialog title="选择模组条码" :visible.sync="searchModuleDialogVisible" width="70%" append-to-body>
      <el-form :model="searchModuleQueryParams" ref="searchModuleQueryForm" size="small" :inline="true">
        <el-form-item label="模组条码" prop="module">
          <el-input
            v-model="searchModuleQueryParams.module"
            placeholder="请输入模组条码"
            clearable
            @keyup.enter.native="handleSearchModuleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearchModuleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetSearchModuleQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="searchModuleLoading" :data="searchModuleList" @row-click="handleSearchModuleSelect">
        <el-table-column label="模组条码" align="center" prop="module" />
        <el-table-column label="关联规格" align="center" prop="specification" />
        <el-table-column label="数量" align="center" prop="num" />
        <el-table-column label="已绑数量" align="center" prop="bindedNum" />
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSearchModuleSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="searchModuleTotal>0"
        :total="searchModuleTotal"
        :page.sync="searchModuleQueryParams.pageNum"
        :limit.sync="searchModuleQueryParams.pageSize"
        @pagination="getSearchModuleList"
      />
    </el-dialog>

    <!-- 单体条码选择弹窗 -->
    <el-dialog title="选择单体条码" :visible.sync="dtDialogVisible" width="70%" append-to-body>
      <el-form :model="dtQueryParams" ref="dtQueryForm" size="small" :inline="true">
        <el-form-item label="单体条码" prop="dtCode">
          <el-input
            v-model="dtQueryParams.dtCode"
            placeholder="请输入单体条码"
            clearable
            @keyup.enter.native="handleDtQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleDtQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetDtQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="dtLoading" :data="dtList" @row-click="handleDtSelect">
        <el-table-column label="单体条码" align="center" prop="dtCode" />
        <el-table-column label="档位(F)" align="center" prop="level" />
        <el-table-column label="静电容量(mΩ)" align="center" prop="capacitance" />
        <el-table-column label="绑定时间" align="center" prop="bindedTime" />
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              :disabled="scope.row.bindedTime !== undefined && scope.row.bindedTime !== null"
              @click="handleDtSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="dtTotal>0"
        :total="dtTotal"
        :page.sync="dtQueryParams.pageNum"
        :limit.sync="dtQueryParams.pageSize"
        @pagination="getDtList"
      />
    </el-dialog>

    <!-- 单体条码搜索弹窗 -->
    <el-dialog title="选择单体条码" :visible.sync="searchDtDialogVisible" width="70%" append-to-body>
      <el-form :model="searchDtQueryParams" ref="searchDtQueryForm" size="small" :inline="true">
        <el-form-item label="单体条码" prop="dtCode">
          <el-input
            v-model="searchDtQueryParams.dtCode"
            placeholder="请输入单体条码"
            clearable
            @keyup.enter.native="handleSearchDtQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearchDtQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetSearchDtQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="searchDtLoading" :data="searchDtList" @row-click="handleSearchDtSelect">
        <el-table-column label="单体条码" align="center" prop="dtCode" />
        <el-table-column label="档位(F)" align="center" prop="level" />
        <el-table-column label="静电容量(mΩ)" align="center" prop="capacitance" />
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSearchDtSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="searchDtTotal>0"
        :total="searchDtTotal"
        :page.sync="searchDtQueryParams.pageNum"
        :limit.sync="searchDtQueryParams.pageSize"
        @pagination="getSearchDtList"
      />
    </el-dialog>
  </div>
</template>
<script>
import { listBind, getBind, delBind, addBind, updateBind } from "@/api/system/bind";
import { listModule } from "@/api/system/module";
import { listLibrary } from "@/api/system/library";

export default {
  name: "Bind",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 绑定管理表格数据
      bindList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        moduleCode: null,
        moduleId: null,
        dtCode: null,
        dtId: null,
        bzCode: null,
        bindTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        moduleId: [
          { required: true, message: "请选择模组条码", trigger: "change" }
        ],
        dtId: [
          { required: true, message: "请选择单体条码", trigger: "change" }
        ],
        bzCode: [
          { required: true, message: "请输入板子编号", trigger: "blur" }
        ]
      },

      // 模组条码弹窗相关
      moduleDialogVisible: false,
      moduleLoading: false,
      moduleList: [],
      moduleTotal: 0,
      moduleQueryParams: {
        pageNum: 1,
        pageSize: 10,
        module: null,
        specification: null,
      },

      // 模组条码搜索弹窗相关
      searchModuleDialogVisible: false,
      searchModuleLoading: false,
      searchModuleList: [],
      searchModuleTotal: 0,
      searchModuleQueryParams: {
        pageNum: 1,
        pageSize: 10,
        module: null,
        specification: null,
      },

      // 单体条码弹窗相关
      dtDialogVisible: false,
      dtLoading: false,
      dtList: [],
      dtTotal: 0,
      dtQueryParams: {
        pageNum: 1,
        pageSize: 10,
        dtCode: null,
      },

      // 单体条码搜索弹窗相关
      searchDtDialogVisible: false,
      searchDtLoading: false,
      searchDtList: [],
      searchDtTotal: 0,
      searchDtQueryParams: {
        pageNum: 1,
        pageSize: 10,
        dtCode: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询绑定管理列表 */
    getList() {
      this.loading = true;
      listBind(this.queryParams).then(response => {
        this.bindList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        moduleId: null,
        moduleCode: null,
        dtId: null,
        dtCode: null,
        bzCode: null,
        bindTime: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.moduleCode = null;
      this.queryParams.moduleId = null;
      this.queryParams.dtCode = null;
      this.queryParams.dtId = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加绑定管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBind(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改绑定管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBind(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBind(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除绑定管理编号为"' + ids + '"的数据项？').then(function() {
        return delBind(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/bind/export', {
        ...this.queryParams
      }, `bind_${new Date().getTime()}.xlsx`)
    },

    /** 打开模组条码选择弹窗 */
    openModuleDialog() {
      this.moduleDialogVisible = true;
      this.getModuleList();
    },

    /** 查询模组条码列表 */
    getModuleList() {
      this.moduleLoading = true;
      listModule(this.moduleQueryParams).then(response => {
        this.moduleList = response.rows;
        this.moduleTotal = response.total;
        this.moduleLoading = false;
      });
    },

    /** 模组条码搜索按钮操作 */
    handleModuleQuery() {
      this.moduleQueryParams.pageNum = 1;
      this.getModuleList();
    },

    /** 模组条码重置按钮操作 */
    resetModuleQuery() {
      this.resetForm("moduleQueryForm");
      this.handleModuleQuery();
    },

    /** 选择模组条码 */
    handleModuleSelect(row) {
      this.form.moduleId = row.id;
      this.form.moduleCode = row.module;
      this.moduleDialogVisible = false;
    },

    /** 打开模组条码搜索弹窗 */
    openSearchModuleDialog() {
      this.searchModuleDialogVisible = true;
      this.getSearchModuleList();
    },

    /** 查询搜索模组条码列表 */
    getSearchModuleList() {
      this.searchModuleLoading = true;
      listModule(this.searchModuleQueryParams).then(response => {
        this.searchModuleList = response.rows;
        this.searchModuleTotal = response.total;
        this.searchModuleLoading = false;
      });
    },

    /** 模组条码搜索按钮操作 */
    handleSearchModuleQuery() {
      this.searchModuleQueryParams.pageNum = 1;
      this.getSearchModuleList();
    },

    /** 模组条码搜索重置按钮操作 */
    resetSearchModuleQuery() {
      this.resetForm("searchModuleQueryForm");
      this.handleSearchModuleQuery();
    },

    /** 选择搜索模组条码 */
    handleSearchModuleSelect(row) {
      this.queryParams.moduleCode = row.module;
      this.queryParams.moduleId = row.id;
      this.searchModuleDialogVisible = false;
    },

    /** 打开单体条码选择弹窗 */
    openDtDialog() {
      this.dtDialogVisible = true;
      this.getDtList();
    },

    /** 查询单体条码列表 */
    getDtList() {
      this.dtLoading = true;
      listLibrary(this.dtQueryParams).then(response => {
        this.dtList = response.rows;
        this.dtTotal = response.total;
        this.dtLoading = false;
      });
    },

    /** 单体条码搜索按钮操作 */
    handleDtQuery() {
      this.dtQueryParams.pageNum = 1;
      this.getDtList();
    },

    /** 单体条码重置按钮操作 */
    resetDtQuery() {
      this.resetForm("dtQueryForm");
      this.handleDtQuery();
    },

    /** 选择单体条码 */
    handleDtSelect(row) {
      if(row.bindedTime === undefined || row.bindedTime === null){
        this.form.dtId = row.id;
        this.form.dtCode = row.dtCode;
        this.dtDialogVisible = false;
      }
    },

    /** 打开单体条码搜索弹窗 */
    openSearchDtDialog() {
      this.searchDtDialogVisible = true;
      this.getSearchDtList();
    },

    /** 查询搜索单体条码列表 */
    getSearchDtList() {
      this.searchDtLoading = true;
      listLibrary(this.searchDtQueryParams).then(response => {
        this.searchDtList = response.rows;
        this.searchDtTotal = response.total;
        this.searchDtLoading = false;
      });
    },

    /** 单体条码搜索按钮操作 */
    handleSearchDtQuery() {
      this.searchDtQueryParams.pageNum = 1;
      this.getSearchDtList();
    },

    /** 单体条码搜索重置按钮操作 */
    resetSearchDtQuery() {
      this.resetForm("searchDtQueryForm");
      this.handleSearchDtQuery();
    },

    // 在methods中添加这个方法
    handleModuleRowClick(row) {
      // 如果数量等于已绑数量，则不允许选择
      if (row.num !== row.bindedNum) {
        this.handleModuleSelect(row);
      }
    },

    /** 选择搜索单体条码 */
    handleSearchDtSelect(row) {
      this.queryParams.dtCode = row.dtCode;
      this.queryParams.dtId = row.id;
      this.searchDtDialogVisible = false;
    }
  }
};
</script>
