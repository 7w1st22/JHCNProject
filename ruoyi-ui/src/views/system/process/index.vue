<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-card shadow="never" class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="工序搜索" prop="process">
          <el-input
            v-model="queryParams.process"
            placeholder="请输入工序名称"
            clearable
            prefix-icon="el-icon-search"
            @keyup.enter.native="handleQuery"
            style="width: 260px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">
            搜索
          </el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮区域 -->
    <el-card shadow="never" class="operation-card">
      <div class="operation-header">
        <div class="operation-left">
          <span class="card-title">工序管理</span>
          <span class="process-count">共 {{ total }} 个工序</span>
        </div>
        <div class="operation-right">
          <el-button
            type="primary"
            icon="el-icon-plus"
            size="small"
            @click="handleAdd"
            v-hasPermi="['system:process:add']"
          >
            添加工序
          </el-button>
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="small"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['system:process:remove']"
          >
            批量删除
          </el-button>
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="small"
            @click="handleExport"
            v-hasPermi="['system:process:export']"
          >
            导出
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 工序展示区域 -->
    <el-card shadow="never" class="process-card">
      <div v-loading="loading" class="process-container">
        <!-- 空状态 -->
        <el-empty
          v-if="processList.length === 0 && !loading"
          description="暂无工序数据"
          :image-size="100"
        >
          <el-button type="primary" size="small" @click="handleAdd">
            添加工序
          </el-button>
        </el-empty>

        <!-- 工序卡片 -->
        <div v-else class="process-grid">
          <div
            v-for="item in processList"
            :key="item.id"
            class="process-item"
            :class="{ 'process-item-selected': selectedIds.includes(item.id) }"
            @click="toggleSelect(item.id)"
          >
            <div class="process-content">
              <div class="process-name">{{ item.process }}</div>
              <div class="process-id">描述: {{ item.description }}</div>
            </div>
            <div class="process-actions">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                class="delete-btn"
                @click.stop="handleDelete(item)"
                v-hasPermi="['system:process:remove']"
              >
                删除
              </el-button>
            </div>
          </div>
        </div>
      </div>


      <!-- 修正后的分页 -->
      <div class="pagination-container">
        <el-pagination
          :current-page="queryParams.pageNum"
          :page-size="queryParams.pageSize"
          :total="total"
          :page-sizes="[14, 28, 42, 56]"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>
    </el-card>

    <!-- 添加工序对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="400px"
      append-to-body
      :close-on-click-modal="false"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="工序名称" prop="process">
          <el-input
            v-model="form.process"
            placeholder="请输入工序名称"
            maxlength="50"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            placeholder="请输入描述"
            maxlength="100"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitLoading">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProcess, getProcess, delProcess, addProcess, updateProcess } from "@/api/system/process";

export default {
  name: "Process",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 提交加载状态
      submitLoading: false,
      // 选中的工序ID
      selectedIds: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 工序列表数据
      processList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 14,
        process: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        process: [
          { required: true, message: "工序名称不能为空", trigger: "blur" },
          { min: 1, max: 50, message: "工序名称长度在 1 到 50 个字符", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工序列表 */
    getList() {
      this.loading = true;
      listProcess(this.queryParams).then(response => {
        this.processList = response.rows;
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
        process: null
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
    /** 切换选中状态 */
    toggleSelect(id) {
      const index = this.selectedIds.indexOf(id);
      if (index > -1) {
        this.selectedIds.splice(index, 1);
      } else {
        this.selectedIds.push(id);
      }
      this.multiple = this.selectedIds.length === 0;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加工序";
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let ids = [];
      if (row.id) {
        // 单个删除
        ids = [row.id];
      } else {
        // 批量删除
        ids = this.selectedIds;
      }

      if (ids.length === 0) {
        this.$modal.msgWarning("请选择要删除的工序");
        return;
      }

      this.$modal.confirm(`是否确认删除选中的 ${ids.length} 个工序？`).then(() => {
        return delProcess(ids);
      }).then(() => {
        this.getList();
        this.selectedIds = [];
        this.multiple = true;
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const request = this.form.id ? updateProcess(this.form) : addProcess(this.form);

          request.then(response => {
            this.$modal.msgSuccess(this.form.id ? "修改成功" : "新增成功");
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    /** 分页大小改变 */
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 当前页改变 */
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/process/export', {
        ...this.queryParams
      }, `工序列表_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 16px;

  ::v-deep .el-card__body {
    padding: 20px;
  }
}

.operation-card {
  margin-bottom: 16px;

  ::v-deep .el-card__body {
    padding: 16px 20px;
  }

  .operation-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .operation-left {
    display: flex;
    align-items: center;
    gap: 12px;
  }

  .card-title {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
  }

  .process-count {
    font-size: 14px;
    color: #909399;
  }

  .operation-right {
    display: flex;
    gap: 8px;
  }
}

.process-card {
  ::v-deep .el-card__body {
    padding: 0;
  }
}

.process-container {
  min-height: 400px;
  padding: 20px;
}

.process-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 16px;
}

.process-item {
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 16px;
  background: #fff;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 120px;

  &:hover {
    border-color: #409eff;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    transform: translateY(-2px);
  }

  &.process-item-selected {
    border-color: #409eff;
    background-color: #f0f7ff;
  }
}

.process-content {
  .process-name {
    font-size: 16px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 8px;
    word-break: break-all;
  }

  .process-id {
    font-size: 12px;
    color: #909399;
  }
}

.process-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 8px;

  .delete-btn {
    color: #f56c6c;

    &:hover {
      color: #f78989;
    }
  }
}

.pagination-container {
  padding: 16px 20px;
  border-top: 1px solid #e4e7ed;
}

::v-deep .el-empty__description {
  margin-top: 16px;
}
</style>
