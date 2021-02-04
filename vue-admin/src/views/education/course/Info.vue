<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <div class="div_course_info">
      <el-form label-width="120px">
        <el-form-item label="课程标题">
          <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视"/>
        </el-form-item>
        <!-- 所属分类 TODO -->
        <!-- 课程讲师 TODO -->
        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="课时数"/>
        </el-form-item>
        <!-- 课程简介 TODO -->
        <!-- 课程封面 TODO -->
        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="课程价格"/>
        </el-form-item>
        <el-form-item class="save_item">
          <el-button :disabled="saveBtnDisabled" type="primary" @click="next">
            保存并下一步
          </el-button>
        </el-form-item>
      </el-form>
    </div>

  </div>
</template>
<script>
  import course from "../../../api/course/course";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseInfo: {
          title: '',
          subjectId: '',
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '',
          price: 0
        }
      }
    },
    created() {
      console.log('info created')
    },
    methods: {
      next() {
        // 将填写的课程信息保存到数据库并转到下一步操作
        course.addCourseInfo(this.courseInfo)
          .then(response => {
            this.$message.success("添加课程信息成功!");
            this.$router.push({path: '/chapter/' + response.data.courseId});
          });
      }
    }
  }
</script>

<style scoped>
  .save_item {
    margin: 0 auto;
    margin-left: 39%;
    margin-top: 5%;
  }

  .div_course_info {
    /*居中样式*/
    margin: 0 auto;
    width: 70%;
    margin-left: 13%;
    margin-top: 3%;
  }

</style>
