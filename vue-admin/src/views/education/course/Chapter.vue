<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-form label-width="120px" class="save_item">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
  import chapter from "../../../api/chapter/chapter";

  export default {
    data() {
      return {
        courseId: '',
        chapterInfoList: [],
        saveBtnDisabled: false // 保存按钮是否禁用
      }
    },
    created() {
      // 页面跳转的时候获取courseId
      this.courseId = this.$route.params.id;
      this.getChapterInfoByCourseId();
    },
    methods: {
      // 根据课程id获取对应的章节信息
      getChapterInfoByCourseId() {
        chapter.getChapterInfoByCourseId(this.courseId)
          .then(response => {
            this.chapterInfoList = response.data.chapterInfoList;
          })
      },
      previous() {
        this.$router.push({path: '/info/1'})
      },
      next() {
        this.$router.push({path: '/publish/3'})
      }
    }
  }
</script>

<style scoped>
  .save_item {
    margin-left: 38%;
    margin-top: 5%;
  }
</style>
