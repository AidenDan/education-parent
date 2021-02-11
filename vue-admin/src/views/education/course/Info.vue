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
          <tinymce :height="300" v-model="courseInfo.description" class="tinymce-container"></tinymce>
        </el-form-item>
        <!-- 课程封面 -->
        <el-form-item label="课程封面">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/oss/uploadAvatar'"
            class="avatar-uploader">
            <img :src="courseInfo.cover">
          </el-upload>
        </el-form-item>
        <el-form-item label="课程价格">
          <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="课程价格"/>
        </el-form-item>
        <el-form-item class="save_item">
          <el-button :disabled="saveBtnDisabled" type="primary" @click="next()">
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
  // 导入富文本编辑器组件
  import tinymce from "../../../components/Tinymce"

  export default {
    // 注册组件
    components: {tinymce},
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
          cover: '/static/timg.jpg',
          price: 0
        },
        BASE_API: process.env.BASE_API,
        teacherList: [], // 讲师列表
        subjectOneList: [], // 一级课程列表
        subjectTwoList: [] // 二级课程列表
      }
    },
    watch: {
      // 监听路由地址变化
      $route(to, from) {
        // 如左侧的菜单栏点击进入courseInfo组件，那么应该要重置之前默认的信息
        // 如果是由上一步操作跳转到课程信息组件，那么是含有课程id的
        if (this.$route.params && this.$route.params.id) {
          this.getCourseInfoById(this.$route.params.id);
        } else {
          this.courseInfo = {
            title: '',
            subjectId: '', //  二级分类id
            subjectParentId: '', // 一级分类id
            teacherId: '',
            lessonNum: 0,
            description: '',
            cover: '/static/timg.jpg',
            price: 0
          }
        }
      }
    },
    created() {
      // 加载这个页面时初始化讲师选择的列表
      this.getAllTeacherList();
      // 加载这个页面时初始化一级课程列表
      this.getLevelOneAndTwoSubject();
      // 课程信息回显
      if (this.$route.params && this.$route.params.id) {
        this.getCourseInfoById(this.$route.params.id);
      }
    },
    methods: {
      // 根据课程id查询课程信息进行回显
      getCourseInfoById(id) {
        course.getCourseInfoById(id)
          .then(response => {
            this.courseInfo = response.data.courseInfo;

            // 注意二级课程id是根据一级课程的变动而动态改变的
            // 这里回显数据没有去改动一级课程所以默认没有去获取对应的二级课程
            // 需要手动去获取二级课程
            for (let j = 0; j < this.subjectOneList.length; j++) {
              if (this.subjectOneList[j].id === this.courseInfo.subjectParentId) {
                this.subjectTwoList = this.subjectOneList[j].children;
              }
            }
          })
      },
      // 调用上传之前会自动调用的方法
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M
      },
      // 上传课程封面成功的回调函数
      handleAvatarSuccess(response, file) {
        console.log(response) // 上传响应
        console.log(URL.createObjectURL(file.raw)) // base64编码
        this.courseInfo.cover = response.data.url
      },
      next() {
        if (this.$route.params && this.$route.params.id) {
          course.updateCourseInfo(this.courseInfo)
            .then(response => {
              this.$message.success("更新课程信息成功!");
              this.$router.push({path: '/chapter/' + response.data.courseId});
            })
        } else {
          // 将填写的课程信息保存到数据库并转到下一步操作
          course.addCourseInfo(this.courseInfo)
            .then(response => {
              this.$message.success("添加课程信息成功!");
              this.$router.push({path: '/chapter/' + response.data.courseId});
            });
        }
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
        // this.courseInfo.subjectId = '';
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

  .tinymce-container {
    line-height: 29px;
  }
</style>
