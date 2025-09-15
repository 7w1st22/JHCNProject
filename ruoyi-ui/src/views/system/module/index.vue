<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模组条码" prop="module">
        <el-input
          v-model="queryParams.module"
          placeholder="请输入模组条码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联规格" prop="specification">
        <el-input
            v-model="queryParams.specification"
            placeholder="请选择关联规格"
            clearable
            readonly
        >
          <el-button slot="append" icon="el-icon-search" @click="openSearchSpecDialog"></el-button>
        </el-input>
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
          v-hasPermi="['system:module:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:module:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:module:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:module:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="moduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模组条码" align="center" prop="module">
        <template slot-scope="scope">
          <el-tag type="info" effect="dark">{{ scope.row.module }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="关联规格" align="center" prop="specification">
        <template slot-scope="scope">
          <el-tag type="primary" effect="dark">{{ scope.row.specification }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="关联规格数量" align="center" prop="specificationNum" />
      <el-table-column label="数量" align="center" prop="num" />
      <el-table-column label="已绑定数量" align="center" prop="bindedNum" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:module:list']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:module:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:module:remove']"
          >删除</el-button>
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

    <!-- 添加或修改模组条码对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模组条码" prop="module">
          <el-input v-model="form.module" placeholder="请输入模组条码" />
        </el-form-item>
        <el-form-item label="模组规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请选择模组规格" readonly>
            <el-button slot="append" icon="el-icon-search" @click="openSpecDialog"></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="真实数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入真实数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 规格选择弹窗 -->
    <el-dialog title="选择规格" :visible.sync="specDialogVisible" width="70%" append-to-body>
      <el-form :model="specQueryParams" ref="specQueryForm" size="small" :inline="true">
        <el-form-item label="规格名称" prop="specification">
          <el-input
              v-model="specQueryParams.specification"
              placeholder="请输入规格名称"
              clearable
              @keyup.enter.native="handleSpecQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSpecQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetSpecQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="specLoading" :data="specList" @row-click="handleSpecSelect">
        <el-table-column label="规格名称" align="center" prop="specification" />
        <el-table-column label="数量" align="center" prop="num" />
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                icon="el-icon-check"
                @click="handleSpecSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="specTotal>0"
          :total="specTotal"
          :page.sync="specQueryParams.pageNum"
          :limit.sync="specQueryParams.pageSize"
          @pagination="getSpecList"
      />
    </el-dialog>

    <!-- 规格搜索弹窗 -->
    <el-dialog title="选择规格" :visible.sync="searchSpecDialogVisible" width="70%" append-to-body>
      <el-form :model="searchSpecQueryParams" ref="searchSpecQueryForm" size="small" :inline="true">
        <el-form-item label="规格名称" prop="specification">
          <el-input
              v-model="searchSpecQueryParams.specification"
              placeholder="请输入规格名称"
              clearable
              @keyup.enter.native="handleSearchSpecQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleSearchSpecQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetSearchSpecQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-table v-loading="searchSpecLoading" :data="searchSpecList" @row-click="handleSearchSpecSelect">
        <el-table-column label="规格名称" align="center" prop="specification" />
        <el-table-column label="数量" align="center" prop="num" />
        <el-table-column label="操作" align="center" width="100">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="text"
                icon="el-icon-check"
                @click="handleSearchSpecSelect(scope.row)"
            >选择</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
          v-show="searchSpecTotal>0"
          :total="searchSpecTotal"
          :page.sync="searchSpecQueryParams.pageNum"
          :limit.sync="searchSpecQueryParams.pageSize"
          @pagination="getSearchSpecList"
      />
    </el-dialog>

    <!-- 将原有的详情对话框替换为以下抽屉代码 -->
    <!-- 将原有的详情对话框替换为以下抽屉代码 -->
    <el-drawer
      title="模组绑定单体详情"
      :visible.sync="detailDialogVisible"
      direction="rtl"
      size="500px"
      :wrapperClosable="true"
      class="module-detail-drawer"
    >
      <div class="drawer-container">
        <div class="module-info">
          <div class="info-header">
            <i class="el-icon-info"></i>
            <span>模组信息</span>
          </div>
          <div class="info-content">
            <div class="info-item">
              <span class="label">模组条码：</span>
              <span class="value">{{ currentModule.module }}</span>
            </div>
            <div class="info-item">
              <span class="label">数量：</span>
              <span class="value">{{ currentModule.num }}</span>
            </div>
            <div class="info-item">
              <span class="label">已绑数量：</span>
              <span class="value">{{ currentModule.bindedNum }}</span>
            </div>
          </div>
        </div>

        <el-divider></el-divider>

        <div class="monomer-list">
          <div class="list-header">
            <i class="el-icon-tickets"></i>
            <span>已绑定单体列表</span>
            <el-tag size="mini" type="info">{{ monomerTotal }} 条记录</el-tag>
          </div>

          <el-table
            v-loading="detailLoading"
            :data="monomerList"
            class="monomer-table"
            :header-cell-style="{background: '#f5f7fa', color: '#606266'}"
          >
            <el-table-column label="单体条码" align="center" prop="dtCode" />
            <el-table-column label="绑定时间" align="center" prop="bindTime" width="160">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.bindTime) }}</span>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="monomerTotal>0"
            :total="monomerTotal"
            :page.sync="monomerQueryParams.pageNum"
            :limit.sync="monomerQueryParams.pageSize"
            @pagination="getMonomerList"
            class="drawer-pagination"
          />
        </div>
      </div>
    </el-drawer>

  </div>
</template>

<script>
import { listModule, getModule, delModule, addModule, updateModule } from "@/api/system/module";
import {listSpecification} from "@/api/system/specification";
import {listBind} from "@/api/system/bind";

export default {
  name: "Module",
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
      // 模组条码表格数据
      moduleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        module: null,
        specificationId: null,
        specification: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        module: [
          { required: true, message: "模组条码不能为空", trigger: "blur" }
        ],
        num: [
          { pattern: /^[1-9]\d*$/, message: "真实数量必须为正整数", trigger: "blur" }
        ]
      },
      specDialogVisible: false,
      specLoading: false,
      specList: [],
      specTotal: 0,
      specQueryParams: {
        pageNum: 1,
        pageSize: 10,
        specification: null,
      },
      // 规格搜索弹窗相关
      searchSpecDialogVisible: false,
      searchSpecLoading: false,
      searchSpecList: [],
      searchSpecTotal: 0,
      searchSpecQueryParams: {
        pageNum: 1,
        pageSize: 10,
        specification: null,
      },

      // 详情展示已绑定单体对话框相关
      detailDialogVisible: false,
      detailLoading: false,
      currentModule: {}, // 当前查看的模组
      monomerList: [], // 单体列表
      monomerTotal: 0, // 单体总数
      monomerQueryParams: {
        pageNum: 1,
        pageSize: 10,
        moduleId: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询模组条码列表 */
    getList() {
      this.loading = true;
      listModule(this.queryParams).then(response => {
        this.moduleList = response.rows;
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
        module: null,
        specificationId: null,
        num: null,
        createTime: null,
        updateTime: null
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
      this.queryParams.specificationId = null;
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
      this.title = "添加模组条码";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModule(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模组条码";
      });
    },
    handleDetail(row){
      this.currentModule = row;
      this.monomerQueryParams.moduleId = row.id;
      this.detailDialogVisible = true;
      this.getMonomerList();
    },

    /** 查询模组绑定单体列表 */
    getMonomerList() {
      this.detailLoading = true;
      // 假设有一个接口可以获取模组绑定的单体列表
      listBind(this.monomerQueryParams).then(response => {
        this.monomerList = response.rows;
        this.monomerTotal = response.total;
        this.detailLoading = false;
      }).catch(() => {
        this.detailLoading = false;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateModule(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addModule(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模组条码编号为"' + ids + '"的数据项？').then(function() {
        return delModule(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/module/export', {
        ...this.queryParams
      }, `module_${new Date().getTime()}.xlsx`)
    },


    /** 打开规格选择弹窗 */
    openSpecDialog() {
      this.specDialogVisible = true;
      this.getSpecList();
    },

    /** 查询规格列表 */
    getSpecList() {
      this.specLoading = true;
      listSpecification(this.specQueryParams).then(response => {
        this.specList = response.rows;
        this.specTotal = response.total;
        this.specLoading = false;
      });
    },

    /** 规格搜索按钮操作 */
    handleSpecQuery() {
      this.specQueryParams.pageNum = 1;
      this.getSpecList();
    },

    /** 规格重置按钮操作 */
    resetSpecQuery() {
      this.resetForm("specQueryForm");
      this.handleSpecQuery();
    },

    /** 选择规格 */
    handleSpecSelect(row) {
      this.form.specification = row.specification;
      this.form.specificationId = row.id;
      this.form.num = row.num
      console.log(row);
      this.specDialogVisible = false;
    },
    /** 打开规格搜索弹窗 */
    openSearchSpecDialog() {
      this.searchSpecDialogVisible = true;
      this.getSearchSpecList();
    },

    /** 查询搜索规格列表 */
    getSearchSpecList() {
      this.searchSpecLoading = true;
      listSpecification(this.searchSpecQueryParams).then(response => {
        this.searchSpecList = response.rows;
        this.searchSpecTotal = response.total;
        this.searchSpecLoading = false;
      });
    },

    /** 规格搜索按钮操作 */
    handleSearchSpecQuery() {
      this.searchSpecQueryParams.pageNum = 1;
      this.getSearchSpecList();
    },

    /** 规格搜索重置按钮操作 */
    resetSearchSpecQuery() {
      this.resetForm("searchSpecQueryForm");
      this.handleSearchSpecQuery();
    },

    /** 选择搜索规格 */
    handleSearchSpecSelect(row) {
      this.queryParams.specificationId  = row.id;
      this.queryParams.specification = row.specification; // 保持显示规格名称
      this.searchSpecDialogVisible = false;
    },
  }
};
</script>
<style scoped>
.drawer-container {
  padding: 0 20px 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.module-info {
  margin-top: 10px;
}

.info-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  font-weight: bold;
  color: #409EFF;
}

.info-header i {
  margin-right: 8px;
}

.info-content {
  padding: 0 10px;
}

.info-item {
  display: flex;
  margin-bottom: 12px;
  line-height: 1.6;
}

.info-item .label {
  color: #606266;
  min-width: 80px;
  text-align: right;
  margin-right: 10px;
}

.info-item .value {
  color: #303133;
  font-weight: 500;
}

.monomer-list {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.list-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  font-weight: bold;
  color: #409EFF;
}

.list-header i {
  margin-right: 8px;
}

.list-header .el-tag {
  margin-left: 10px;
}

.monomer-table {
  flex: 1;
  margin-bottom: 15px;
}

.drawer-pagination {
  margin-top: auto;
}

::v-deep .module-detail-drawer .el-drawer__header {
  margin-bottom: 20px;
  padding: 20px 20px 10px;
  border-bottom: 1px solid #e6ebf5;
  color: #409EFF;
  font-size: 18px;
  font-weight: bold;
}

::v-deep .module-detail-drawer .el-drawer__body {
  padding: 0;
}
</style>
