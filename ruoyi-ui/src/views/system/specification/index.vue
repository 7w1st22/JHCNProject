<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模组规格" prop="specification">
        <el-input
          v-model="queryParams.specification"
          placeholder="请输入模组规格"
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
          v-hasPermi="['system:specification:add']"
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
          v-hasPermi="['system:specification:edit']"
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
          v-hasPermi="['system:specification:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:specification:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="specificationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="模组规格" align="center" prop="specification">
        <template slot-scope="scope">
          <el-tag type="primary" effect="dark">{{ scope.row.specification }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="num" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime ? scope.row.createTime.split('.')[0] : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime ? scope.row.updateTime.split('.')[0] : '' }}</span>
        </template>
      </el-table-column>
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
            v-hasPermi="['system:specification:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:specification:remove']"
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

    <!-- 添加或修改模组规格对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模组规格" prop="specification" required>
          <el-input v-model="form.specification" placeholder="请输入模组规格" />
        </el-form-item>
        <el-form-item label="数量" prop="num">
          <el-input v-model="form.num" placeholder="请输入模组对应数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情弹窗 -->
    <el-dialog title="模组详情" :visible.sync="detailVisible" width="80%" append-to-body>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="关联规格">
          <el-tag type="primary" effect="dark">{{ detailForm.specification }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="数量">{{ detailForm.num }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ detailForm.createTime }}</el-descriptions-item>
        <el-descriptions-item label="更新时间">{{ detailForm.updateTime }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">关联模组编号列表</el-divider>

      <el-table v-loading="detailLoading" :data="relatedModuleList" style="margin-top: 20px;">
        <el-table-column label="模组条码" align="center" prop="module">
          <template slot-scope="scope">
            <el-tag type="info" effect="dark">{{ scope.row.module }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="数量" align="center" prop="num" />
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.createTime ? scope.row.createTime.split('.')[0] : '' }}</span>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="relatedTotal > 0"
        :total="relatedTotal"
        :page.sync="relatedQueryParams.pageNum"
        :limit.sync="relatedQueryParams.pageSize"
        @pagination="getRelatedModuleList"
      />

      <div slot="footer" class="dialog-footer">
        <el-button @click="detailVisible = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSpecification, getSpecification, delSpecification, addSpecification, updateSpecification } from "@/api/system/specification";
import { listModule } from "@/api/system/module"

export default {
  name: "Specification",
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
      // 模组规格表格数据
      specificationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        specification: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        specification: [
          { required: true, message: "模组规格不能为空", trigger: "blur" }
        ],
        num: [
          { pattern: /^[1-9]\d*$/, message: "真实数量必须为正整数", trigger: "blur" }
        ]
      },
      // 详情弹窗相关
      detailVisible: false,
      detailLoading: false,
      detailForm: {},
      relatedModuleList: [],
      relatedTotal: 0,
      relatedQueryParams: {
        pageNum: 1,
        pageSize: 10,
        specificationId: null, // 根据接口需要调整参数名
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询模组规格列表 */
    getList() {
      this.loading = true;
      listSpecification(this.queryParams).then(response => {
        this.specificationList = response.rows;
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
        specification: null,
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
      this.title = "添加模组规格";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpecification(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模组规格";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 额外验证数量是否为正整数
          if (this.form.num && this.form.num !== '') {
            const numValue = parseInt(this.form.num, 10);
            if (isNaN(numValue) || numValue <= 0 ) {
              this.$modal.msgError("数量必须为正整数");
              return false;
            }
          }

          if (this.form.id != null) {
            updateSpecification(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpecification(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模组规格编号为"' + ids + '"的数据项？').then(function() {
        return delSpecification(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/specification/export', {
        ...this.queryParams
      }, `specification_${new Date().getTime()}.xlsx`)
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.detailVisible = true;
      this.detailLoading = true;

      // 获取详情数据
      getSpecification(row.id).then(response => {
        this.detailForm = response.data;
        this.detailLoading = false;

        // 获取关联模组编号列表
        this.relatedQueryParams.specificationId = row.id;
        this.relatedQueryParams.pageNum = 1;
        this.getRelatedModuleList();
      }).catch(() => {
        this.detailLoading = false;
      });
    },

    /** 查询关联模组编号列表 */
    getRelatedModuleList() {
      this.detailLoading = true;
      listModule(this.relatedQueryParams).then(response => {
        this.relatedModuleList = response.rows;
        this.relatedTotal = response.total;
        this.detailLoading = false;
      }).catch(() => {
        this.detailLoading = false;
      });
    },

    // /** 时间格式化方法 */
    // parseTime(time) {
    //   if (time) {
    //     return this.$parseTime(time, '{y}-{m}-{d} {h}:{i}:{s}');
    //   }
    //   return '';
    // },
  }
};
</script>
