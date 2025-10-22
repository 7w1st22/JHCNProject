<template>
  <div class="app-container home">
    <div class="top-container">
      <div class="user-container">
        <el-avatar style="width: 60px; height: 60px" :src="user.avatar" />
        <div class="np-flex np-flex-col">
          <div class="user-helloword np-pingfangSCR">
            {{ helloword }},{{ user.nickName }},祝你开心每一天
          </div>
          <div class="user-more-info np-pingfangSCR">
            {{ user.userName }} |
            {{ user.dept ? user.dept.deptName : "无" }}
          </div>
        </div>
      </div>
      <div class="num-total-container">
        <div class="num-card-container" v-for="(item, index) in numList" :key="index">
          <div class="num-card-title">{{ item.title }}</div>
          <!-- 暂时使用Element UI图标作为占位，后续可以替换为图片 -->
          <div class="num-card-icon">
            <i :class="item.iconClass" class="icon-placeholder"></i>
          </div>
          <div class="num-card-num">{{ item.num }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {getUserProfile} from "@/api/system/user";

export default {
  name: "Index",
  data() {
    return {
      user: {},
      roleGroup: {},
      postGroup: {},
      helloword: "",
      numList: [
        {
          title: "剩余维护计划数量",
          num: 12,
          iconClass: "el-icon-tools", // 使用Element UI图标作为占位
          type: 'maintenance'
        },
        {
          title: "易损件缺少数量",
          num: 8,
          iconClass: "el-icon-box", // 使用Element UI图标作为占位
          type: 'sparepart'
        },
        {
          title: "IT工单服务数量",
          num: 25,
          iconClass: "el-icon-document", // 使用Element UI图标作为占位
          type: 'it_ticket'
        },
        {
          title: "检定计划数量",
          num: 6,
          iconClass: "el-icon-finished", // 使用Element UI图标作为占位
          type: 'verification'
        }
      ],
    };
  },
  created() {
    this.getUser();
    this.getTimeState();
  },
  methods: {
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    },
    getTimeState: function () {
      // 获取当前时间
      let timeNow = new Date();
      // 获取当前小时
      let hours = timeNow.getHours();
      // 设置默认文字
      let state = ``;
      // 判断当前时间段
      if (hours >= 0 && hours <= 10) {
        state = `早上好`;
      } else if (hours > 10 && hours <= 14) {
        state = `中午好`;
      } else if (hours > 14 && hours <= 18) {
        state = `下午好`;
      } else if (hours > 18 && hours <= 24) {
        state = `晚上好`;
      }
      this.helloword = state;
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  background-color: transparent;
  overflow-y: auto;
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
  .top-container {
    margin-bottom: 1.85vh;
    .user-container {
      padding: 3.3vh 1.25vw;
      background-image: url("~@/assets/images/index/user_bg.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
      box-sizing: border-box;
      display: grid;
      grid-template-columns: minmax(30px, 3.54vw) auto;
      grid-template-rows: minmax(60px, 6.29vh);
      grid-column-gap: 0.93vw;
      .user-avatar {
        width: 100%;
        min-width: 30px;
        height: 100%;
        min-height: 30px;
        border-radius: 50%;
      }
      .user-helloword {
        font-weight: 600;
        font-size: 18px;
        margin-bottom: 1.2vh;
        color: #000;
      }
      .user-more-info {
        font-size: 12px;
        color: #4c5968;
      }
    }
    .num-total-container {
      background-image: url("~@/assets/images/index/num_total_bg.png");
      background-size: 100% 100%;
      background-repeat: no-repeat;
      background-position: center;
      box-sizing: border-box;
      display: grid;
      grid-template-rows: minmax(50px, 9.62vh);
      grid-column-gap: 0.9375vw;
      align-content: center;
      justify-content: center;
      .num-card-container {
        height: 100%;
        padding-left: 1.25vw;
        padding-right: 1.56vw;
        background-image: url("~@/assets/images/index/num_card.png");
        background-size: 100% 100%;
        background-repeat: no-repeat;
        background-position: center;
        box-sizing: border-box;
        display: grid;
        grid-template-columns: auto 3.22vw;
        grid-template-rows: minmax(20px, 2.3vh) minmax(20px, 2.3vh);
        grid-row-gap: 1.8vh;
        align-content: center;
        .num-card-title {
          font-size: 0.729vw;
          color: #4c5968;
          margin-bottom: 1.8vh;
        }
        .num-card-num {
          font-size: 1.25vw;
          font-weight: bold;
        }
        .num-card-icon {
          width: 100%;
          height: 100%;
          grid-row: 1 / span 2;
          grid-column: 2;
          display: flex;
          align-items: center;
          justify-content: center;

          .icon-placeholder {
            font-size: 24px;
            color: #409EFF; /* 使用Element UI的主题色 */
          }
        }
      }
    }
  }
}

@media screen and (min-width: 1201px) {
  .sidebarHide {
    .top-container {
      display: grid;
      grid-template-columns: 31.25vw auto;
      grid-template-rows: minmax(60px, 12.96vh);
      grid-column-gap: 1.04vw;
    }
    .num-total-container {
      grid-template-columns: repeat(4, minmax(100px, 15.1vw));
    }
  }
  .top-container {
    display: grid;
    grid-template-columns: 28.25vw auto;
    grid-template-rows: minmax(60px, 12.96vh);
    grid-column-gap: 1.04vw;
  }
  .num-total-container {
    grid-template-columns: repeat(4, minmax(100px, 15.1vw));
  }
}
@media screen and (max-width: 1201px) {
  .top-container {
    display: grid;
    grid-template-columns: 100%;
    grid-template-rows: minmax(60px, 12.96vh) minmax(60px, 12.96vh);
    grid-row-gap: 1.85vh;
  }
  .num-total-container {
    grid-template-columns: repeat(4, minmax(100px, 22vw));
  }
}
</style>
