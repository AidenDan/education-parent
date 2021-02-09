<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="3" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <div class="ccInfo">
      <img :src="coursePublish.cover">
      <div class="main">
        <h2>{{ coursePublish.title }}</h2>
        <p class="gray"><span>共{{ coursePublish.lessonNum }}课时</span></p>
        <p><span>所属分类：{{ coursePublish.subjectLevelOne }} — {{coursePublish.subjectLevelTwo }}</span></p>
        <p>课程讲师：{{ coursePublish.teacherName }}</p>
        <h3 class="red">￥{{ coursePublish.price }}</h3>
      </div>
    </div>
    <div>
      <el-button @click="previous">返回修改</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="publish">发布课程</el-button>
    </div>
  </div>
</template>
<script>
  import course from "../../../api/course/course";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        coursePublish: {}, // 课程发布的信息
        courseId: '' // 课程id
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id;
        // 显示要发布的课程信息
        course.publishCourseInfo(this.courseId)
          .then(response => {
            this.coursePublish = response.data.publishCourseInfo;
          })
      }
    },
    methods: {
      previous() {
        this.$router.push({path: '/chapter/' + this.courseId})
      },
      publish() {
        this.finalPublish();
        this.$router.push({path: '/courseList'});
      },
      // 最终发布课程，即修改课程状态
      finalPublish() {
        course.finalPublish(this.courseId)
          .then(response => {
            this.$message.success("发布课程成功!")
          })
      }
    }
  }
</script>

<style scoped>
  .ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #DDD;
    margin-bottom: 40px;
    position: relative;
  }

  .ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
  }

  .ccInfo .main {
    margin-left: 520px;
  }

  .ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
  }

  .ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
  }
</style>
