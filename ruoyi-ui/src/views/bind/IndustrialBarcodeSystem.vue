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

    <!-- 动态组件 -->
    <component :is="currentComponent" @trigger-alert="triggerAlert"></component>

    <!-- 隐藏音频播放器 -->
    <audio id="alertAudio" hidden>
      <source src="@/assets/audio/alert.wav" type="audio/wav">
    </audio>
  </div>
</template>

<script>
import ModuleBinding from './ModuleBinding.vue';
import SingleReplacement from './SingleReplacement.vue';

export default {
  name: 'IndustrialBarcodeSystem',
  components: {
    ModuleBinding: ModuleBinding,
    SingleReplacement
  },
  data() {
    return {
      activeTab: 'moduleBinding'
    };
  },
  computed: {
    currentComponent() {
      return this.activeTab === 'moduleBinding' ? 'ModuleBinding' : 'SingleReplacement';
    }
  },
  methods: {
    triggerAlert() {
      // 触发警报
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
</style>
