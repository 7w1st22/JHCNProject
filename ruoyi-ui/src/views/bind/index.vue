<template>
  <div class="container py-4">
    <!-- 标题 -->
    <div class="header">
      江海储能
    </div>

    <!-- 切换栏 -->
    <div class="switch-container">
      <div class="switch-bar">
        <button
          :class="{ active: activeTab === 'moduleBinding' }"
          @click="activeTab = 'moduleBinding'"
        >
          模组绑定
        </button>
        <button
          :class="{ active: activeTab === 'singleReplacement' }"
          @click="activeTab = 'singleReplacement'"
        >
          单体解绑
        </button>
      </div>
    </div>

    <!-- 模组绑定内容 -->
    <div v-show="activeTab === 'moduleBinding'" class="tab-content">
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

    <!-- 单体解绑内容 -->
    <div v-show="activeTab === 'singleReplacement'" class="tab-content">
      <div class="card shadow">
        <div class="card-body">
          <!-- 旧单体条码输入 -->
          <div class="input-group">
            <span class="input-group-text">单体条码</span>
            <input
              type="text"
              v-model="replacementData.oldDTCode"
              class="form-control"
              placeholder="扫描单体条码"
              @keypress.enter="handleSingleUnbindInput"
              :disabled="unbindLoading"
            >
            <span class="input-group-text" @click="clearReplacementInput('oldDTCode')" style="cursor: pointer;">
              <i class="fas fa-times"></i>
            </span>
          </div>

          <!-- 绑定信息展示 -->
          <div v-if="bindingInfo" class="binding-info">
            <h4>绑定信息</h4>
            <div class="info-grid">
              <div class="info-item">
                <span class="info-label">模组:</span>
                <span class="info-value">{{ bindingInfo.moduleCode }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">板子:</span>
                <span class="info-value">{{ bindingInfo.bzCode }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">档位:</span>
                <span class="info-value">{{ bindingInfo.level }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">绑定时间:</span>
                <span class="info-value">{{ bindingInfo.bindedTime }}</span>
              </div>
            </div>
          </div>

          <!-- 错误信息展示 -->
          <div v-if="errorMessage" class="alert alert-danger mt-3">
            {{ errorMessage }}
          </div>

          <!-- 提交和清空按钮 -->
          <div class="btn-group mt-3">
            <button
              class="btn-submit"
              @click="confirmUnbind"
              :disabled="!bindingInfo || !bindingInfo.bindId || unbindLoading"
            >
              {{ unbindLoading ? '处理中...' : '提交解绑' }}
            </button>
            <button class="btn-clear" @click="clearReplacementData">清空</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 确认解绑弹窗 -->
    <div v-if="showUnbindConfirm" class="modal-backdrop">
      <div class="confirm-modal">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">确认解绑</h5>
          </div>
          <div class="modal-body">
            <p>确定要解绑单体条码 <strong>{{ replacementData.oldDTCode }}</strong> 吗？</p>
            <div v-if="bindingInfo" class="info-grid">
              <div class="info-item">
                <span class="info-label">模组:</span>
                <span class="info-value">{{ bindingInfo.moduleCode }}</span>
              </div>
              <div class="info-item">
                <span class="info-label">板子:</span>
                <span class="info-value">{{ bindingInfo.bzCode }}</span>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" @click="showUnbindConfirm = false">取消</button>
            <button class="btn btn-primary" @click="submitUnbind">确定解绑</button>
          </div>
        </div>
      </div>
    </div>

    <!-- 隐藏音频播放器 -->
    <audio id="alertAudio" hidden>
      <source src="@/assets/audio/alert.wav" type="audio/wav">
    </audio>
  </div>
</template>

<script>
import { getLibraryByDtCode } from "@/api/system/library";
import { delBind } from "@/api/system/bind";
export default {
  name: 'IndustrialBarcodeSystem',
  data() {
    return {
      activeTab: 'moduleBinding',
      scanData: {
        wxCode: '',
        bzCodes: '',
        dt: [],
        baseLevel: null
      },
      replacementData: {
        oldDTCode: ''
      },
      currentDtCode: '',
      rowsPerPage: 10,
      currentPage: 1,
      // 新增数据属性
      bindingInfo: null,
      errorMessage: '',
      showUnbindConfirm: false,
      unbindLoading: false
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
    created() {

    },
    // 模组绑定相关方法
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
        this.triggerAlert();
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
    },

    // 单体解绑相关方法
    handleSingleUnbindInput(event) {
      const value = event.target.value.trim();
      if (!value) {
        alert('输入码不应为空');
        return;
      }

      this.replacementData.oldDTCode = value;
      this.fetchBindingInfo(value);
    },

    // 获取绑定信息
    // 修改 fetchBindingInfo 方法
    async fetchBindingInfo(code) {
      this.unbindLoading = true;
      this.errorMessage = '';
      this.bindingInfo = null;

      try {
        // 调用实际接口获取绑定信息
        const response = await getLibraryByDtCode(code);

        if (response.code === 200) {
          // 检查是否有单体数据
          if (response.data) {
            // 创建基础绑定信息对象
            this.bindingInfo = {
              // 单体基本信息
              ...response.data,
              // 绑定信息（如果有的话）
              bzCode: response.bindedInfo ? response.bindedInfo.bzCode : '',
              moduleCode: response.bindedInfo ? response.bindedInfo.moduleCode : '',
              bindId: response.bindedInfo ? response.bindedInfo.id : null,
              bindedTime: response.bindedInfo ? response.bindedInfo.bindedTime : '',
              level: response.data.level || '' // 确保档位信息也显示
            };

            // 设置错误信息
            if (!response.bindedInfo) {
              this.errorMessage = '该单体未绑定';
            } else {
              this.errorMessage = ''; // 清除错误信息
            }
          } else {
            // 没有单体数据
            this.bindingInfo = null;
            this.errorMessage = '未查询到该单体';
          }
        } else {
          this.bindingInfo = null;
          this.errorMessage = response.msg || '查询绑定信息失败';
        }
      } catch (error) {
        this.bindingInfo = null;
        this.errorMessage = '网络错误，请稍后重试';
        console.error('获取绑定信息失败:', error);
      } finally {
        this.unbindLoading = false;
      }
    },

    // 确认解绑
    confirmUnbind() {
      this.showUnbindConfirm = true;
    },

    // 提交解绑
    async submitUnbind() {
      this.showUnbindConfirm = false;
      this.unbindLoading = true;

      try {
        const response = await delBind(this.bindingInfo.bindId);

        if (response.code === 200) {
          alert('解绑成功');
          this.clearReplacementData();
        } else {
          alert('解绑失败: ' + response.msg);
        }
      } catch (error) {
        alert('网络错误，解绑失败');
        console.error('解绑失败:', error);
      } finally {
        this.unbindLoading = false;
      }
    },

    // 模拟解绑API
    mockUnbindRequest(code) {
      return new Promise((resolve) => {
        setTimeout(() => {
          // 模拟90%的成功率
          if (Math.random() > 0.1) {
            resolve({ success: true, message: '解绑成功' });
          } else {
            resolve({ success: false, message: '解绑操作失败，请重试' });
          }
        }, 1000);
      });
    },

    clearReplacementInput(type) {
      this.replacementData[type] = '';
      this.bindingInfo = null;
      this.errorMessage = '';
    },

    clearReplacementData() {
      this.replacementData = {
        oldDTCode: ''
      };
      this.bindingInfo = null;
      this.errorMessage = '';
    },

    triggerAlert() {
      // 模拟报警声音
      const audio = document.getElementById('alertAudio');
      if (audio) {
        audio.play().catch(error => {
          console.error('播放失败:', error);
        });
      }
    }
  }
};
</script>

<style scoped>
.header {
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 50px;
  font-weight: bold;
}

.switch-container {
  background-color: #3A4F63;
  padding: 1rem 0;
  margin-bottom: 1.5rem;
}

.switch-bar {
  display: flex;
  justify-content: flex-start;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.switch-bar button {
  margin: 0 10px;
  padding: 15px 30px;
  border: none;
  background-color: #007bff;
  color: white;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s ease;
}

.switch-bar button.active {
  background-color: #0056b3;
}

.switch-bar button:hover {
  background-color: #004080;
}

.input-group {
  margin-bottom: 1.5rem;
}

.card {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
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

.tab-content {
  opacity: 1;
  transition: opacity 0.5s ease;
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

.table td.error-level {
  background-color: #ffcccc;
}

.input-group {
  margin-bottom: 2rem;
}

/* 新增样式 */
.binding-info {
  margin-top: 20px;
  padding: 15px;
  border: 1px solid #dee2e6;
  border-radius: 5px;
  background-color: #f8f9fa;
}

.binding-info h4 {
  margin-bottom: 15px;
  color: #3A4F63;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.info-item {
  display: flex;
}

.info-label {
  font-weight: bold;
  margin-right: 8px;
  min-width: 70px;
}

.info-value {
  color: #495057;
}

.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1050;
}

.confirm-modal {
  width: 100%;
  max-width: 500px;
}

.modal-content {
  background-color: white;
  border-radius: 5px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.modal-header {
  padding: 15px;
  border-bottom: 1px solid #dee2e6;
}

.modal-title {
  margin: 0;
}

.modal-body {
  padding: 15px;
}

.modal-footer {
  padding: 15px;
  border-top: 1px solid #dee2e6;
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
  border: none;
}

.btn-secondary {
  background-color: #6c757d;
  color: white;
}

.btn-primary {
  background-color: #007bff;
  color: white;
}

.alert {
  padding: 10px 15px;
  border-radius: 4px;
}

.alert-danger {
  background-color: #f8d7da;
  color: #721c24;
  border: 1px solid #f5c6cb;
}
</style>
