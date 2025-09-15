<template>
  <div class="tab-content">
    <div class="card shadow">
      <div class="card-body">
        <!-- 外箱条码输入 -->
        <div class="input-group">
          <span class="input-group-text">外箱条码</span>
          <input
            type="text"
            v-model="scanData.wxCode"
            class="form-control"
            placeholder="扫描外箱条码"
            autofocus
            @keypress.enter="handleInput('wxCode', $event)"
          >
          <span class="input-group-text" @click="clearInput('wxCode')" style="cursor: pointer;">
            <i class="fas fa-times"></i>
          </span>
        </div>

        <!-- 板子条码输入 -->
        <div class="input-group">
          <span class="input-group-text">板子条码</span>
          <input
            type="text"
            v-model="scanData.bzCodes"
            class="form-control"
            placeholder="扫描板子条码"
            @keypress.enter="handleInput('bzCode', $event)"
          >
          <span class="input-group-text" @click="clearInput('bzCode')" style="cursor: pointer;">
            <i class="fas fa-times"></i>
          </span>
        </div>

        <!-- 单体条码输入 -->
        <div class="input-group">
          <span class="input-group-text">单体条码</span>
          <input
            type="text"
            v-model="currentDtCode"
            class="form-control"
            placeholder="扫描单体条码"
            @keypress.enter="handleInput('dtCode', $event)"
          >
        </div>

        <!-- 提交和清空按钮 -->
        <div class="btn-group">
          <button class="btn-submit" @click="bindModule">提交</button>
          <button class="btn-clear" @click="clearData">清空</button>
        </div>

        <!-- 数据展示表格 -->
        <table class="table">
          <thead>
          <tr>
            <th>条码类型</th>
            <th>条码值</th>
            <th>条码档位</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in paginatedData" :key="index">
            <td>单体</td>
            <td>{{ item.dtCode }}</td>
            <td :class="{ 'error-level': item.status === 'error' }">{{ item.level }}</td>
            <td>
              <button class="btn-delete" @click="deleteRow(index)">删除</button>
            </td>
          </tr>
          </tbody>
        </table>

        <!-- 分页控件 -->
        <nav aria-label="Page navigation">
          <ul class="pagination">
            <li class="page-item" :class="{ disabled: currentPage === 1 }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">上一页</a>
            </li>
            <li
              v-for="page in totalPages"
              :key="page"
              class="page-item"
              :class="{ active: page === currentPage }"
            >
              <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
            </li>
            <li class="page-item" :class="{ disabled: currentPage === totalPages }">
              <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">下一页</a>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ModuleBinding',
  data() {
    return {
      scanData: {
        wxCode: '',
        bzCodes: '',
        dt: [],
        baseLevel: null
      },
      currentDtCode: '',
      rowsPerPage: 10,
      currentPage: 1
    };
  },
  computed: {
    totalPages() {
      return Math.ceil(this.scanData.dt.length / this.rowsPerPage);
    },
    paginatedData() {
      const start = (this.currentPage - 1) * this.rowsPerPage;
      const end = start + this.rowsPerPage;
      return this.scanData.dt.slice(start, end);
    }
  },
  methods: {
    handleInput(type, event) {
      const value = event.target.value.trim();
      if (!value) {
        alert('输入码不应为空');
        return;
      }

      // 验证输入顺序
      if (type === 'bzCode' && !this.scanData.wxCode) {
        alert('请先输入外箱条码');
        return;
      }

      if (type === 'dtCode' && (!this.scanData.wxCode || !this.scanData.bzCodes)) {
        alert('请先输入外箱条码和板子条码');
        return;
      }

      // 处理输入
      if (type === 'dtCode') {
        this.addDtCode(value);
        this.currentDtCode = ''; // 清空输入框
      }

      // 自动聚焦到下一个输入框
      this.focusNextField(type);
    },

    addDtCode(code) {
      // 检查重复
      if (this.scanData.dt.some(item => item.dtCode === code)) {
        alert('该单体条码已存在');
        return;
      }

      // 模拟获取档位信息（实际应调用API）
      const level = this.getMockBatteryLevel(code);

      // 添加到数据列表
      this.scanData.dt.push({
        dtCode: code,
        level: level,
        status: level === "无档位信息" ? 'error' : 'normal'
      });

      // 设置基准档位
      if (!this.scanData.baseLevel && level !== "无档位信息") {
        this.scanData.baseLevel = level;
      }

      // 触发报警（如果需要）
      if (level === "无档位信息" || (this.scanData.baseLevel && level !== this.scanData.baseLevel)) {
        this.$emit('trigger-alert');
      }
    },

    getMockBatteryLevel(code) {
      // 模拟档位信息，实际应从API获取
      const levels = ['A', 'B', 'C', '无档位信息'];
      return levels[Math.floor(Math.random() * levels.length)];
    },

    focusNextField(currentType) {
      const fields = ['wxCode', 'bzCode', 'dtCode'];
      const currentIndex = fields.indexOf(currentType);

      if (currentIndex < fields.length - 1) {
        this.$nextTick(() => {
          const nextField = this.$el.querySelector(`[v-model="${fields[currentIndex + 1] === 'dtCode' ? 'currentDtCode' : `scanData.${fields[currentIndex + 1]}`}"]`);
          if (nextField) nextField.focus();
        });
      }
    },

    clearInput(type) {
      if (type === 'wxCode') {
        this.scanData.wxCode = '';
      } else if (type === 'bzCode') {
        this.scanData.bzCodes = '';
      }
    },

    deleteRow(index) {
      if (!confirm('确定要删除这个单体信息吗？')) return;

      // 获取要删除的条目
      const item = this.scanData.dt[index];

      // 如果删除的是基准档位，重新设置基准档位
      if (item.level === this.scanData.baseLevel) {
        const firstNormalLevel = this.scanData.dt
          .filter((dt, i) => i !== index && dt.status === 'normal')
          .find(item => item.level !== "无档位信息")?.level;

        this.scanData.baseLevel = firstNormalLevel || null;
      }

      // 删除条目
      this.scanData.dt.splice(index, 1);

      // 调整分页
      if (this.currentPage > this.totalPages) {
        this.currentPage = this.totalPages;
      }
    },

    bindModule() {
      // 模拟提交操作
      console.log('提交绑定:', this.scanData);
      alert('模组绑定提交成功（模拟）');
      this.clearData();
    },

    clearData() {
      if (!confirm('确定要清空所有录入信息吗？')) return;

      this.scanData = {
        wxCode: '',
        bzCodes: '',
        dt: [],
        baseLevel: null
      };
      this.currentPage = 1;
    },

    changePage(page) {
      if (page < 1 || page > this.totalPages) return;
      this.currentPage = page;
    }
  }
};
</script>

<style scoped>
.tab-content {
  opacity: 1;
  transition: opacity 0.5s ease;
}

.card {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.input-group {
  margin-bottom: 1.5rem;
}

.btn-group {
  display: flex;
  gap: 10px;
  justify-content: center;
  margin-top: 2rem;
}

.btn-group button {
  padding: 12px 24px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.btn-submit {
  background-color: #28a745;
  color: white;
}

.btn-clear {
  background-color: #dc3545;
  color: white;
}

.btn-delete {
  background-color: #ffc107;
  color: black;
}

.table {
  margin-top: 1rem;
  font-size: 14px;
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 12px;
  border: 1px solid #dee2e6;
  text-align: center;
}

.table thead th {
  background-color: #3A4F63;
  color: white;
  border-bottom: 2px solid #090808;
}

.table tbody tr:hover {
  background-color: #f8f9fa;
}

.table td.error-level {
  background-color: #ffcccc;
}

.pagination {
  margin-top: 1.5rem;
  justify-content: center;
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
}

.page-item {
  margin: 0;
}

.page-item.active .page-link {
  background-color: #007bff;
  border-color: #007bff;
  color: white;
}

.page-link {
  color: #007bff;
  border: 1px solid #dee2e6;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.page-link:hover {
  background-color: #f8f9fa;
  color: #0056b3;
  text-decoration: none;
}

.page-item.disabled .page-link {
  color: #6c757d;
  pointer-events: none;
  background-color: #f8f9fa;
  border-color: #090808;
}
</style>
