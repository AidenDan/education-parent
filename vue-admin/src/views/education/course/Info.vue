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
          <el-input v-model="courseInfo.title" placeholder="示例：机器学习项目课：从基础到搭建项目视"/>
        </el-form-item>
        <!-- 所属课程分类 -->
        <el-form-item label="课程分类">
          <el-select v-model="courseInfo.subjectParentId" placeholder="一级课程"
                     @change="getLevelTwoSubjectByLevelOneSubject(courseInfo.subjectParentId)">
            <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id"/>
          </el-select>
          <el-select v-model="courseInfo.subjectId" placeholder="二级课程">
            <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title"
                       :value="subject.id"/>
          </el-select>
        </el-form-item>
        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
          <el-select v-model="courseInfo.teacherId" placeholder="请选择">
            <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="总课时">
          <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="课时数"/>
        </el-form-item>
        <!-- 课程简介 -->
        <el-form-item label="课程简介">
          <el-input v-model="courseInfo.description" placeholder="示例：机器学习项目课：从基础到搭建项目视"/>
        </el-form-item>
        <!-- 课程封面 -->
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
  import teacherApi from "../../../api/teacher/teacher";
  import subject from "../../../api/subject/subject";

  export default {
    data() {
      return {
        saveBtnDisabled: false, // 保存按钮是否禁用
        courseInfo: {
          title: '',
          subjectId: '', //  二级分类id
          subjectParentId: '', // 一级分类id
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '',
          price: 0
        },
        teacherList: [], // 讲师列表
        subjectOneList: [], // 一级课程列表
        subjectTwoList: [] // 二级课程列表
      }
    },
    created() {
      // 初始化讲师选择的列表
      this.getAllTeacherList();
      // 初始化一级课程列表
      this.getLevelOneAndTwoSubject();
    },
    methods: {
      next() {
        // 将填写的课程信息保存到数据库并转到下一步操作
        course.addCourseInfo(this.courseInfo)
          .then(response => {
            this.$message.success("添加课程信息成功!");
            this.$router.push({path: '/chapter/' + response.data.courseId});
          });
      },
      // 查询所有的讲师列表
      getAllTeacherList() {
        teacherApi.getAllTeacherList()
          .then(response => {
            this.teacherList = response.data.itemList;
          })
      },
      // 查询所有的课程列表
      getLevelOneAndTwoSubject() {
        subject.getTotalSubject()
          .then(response => {
            this.subjectOneList = response.data.totalSubject;
          })
      },
      // 根据一级课程变动动态查询二级课程列表
      getLevelTwoSubjectByLevelOneSubject(levelOneId) {
        // 先清空二级课程下拉框列表  delete是VUE关键字
        delete this.courseInfo.subjectId;
        for (let j = 0; j < this.subjectOneList.length; j++) {
          if (this.subjectOneList[j].id === levelOneId) {
            this.subjectTwoList = this.subjectOneList[j].children;
          }
        }
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