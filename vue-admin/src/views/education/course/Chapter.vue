<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="marginbottom:40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <h2 style="text-align: center;">
      <el-button type="text" @click="dialogChapterFormVisible=true">添加章节</el-button>
    </h2>

    <div class="chapter_container">
      <el-form label-width="120px">
        <el-form-item>
          <ul class="chapterList">
            <li v-for="chapter in chapterInfoList" :key="chapter.id">
              <p>{{chapter.title}}</p>
              <ul class="videoList">
                <li v-for="video  in chapter.children" :key="video.id">
                  <p>{{video.title}}</p>
                </li>
              </ul>
            </li>
          </ul>
        </el-form-item>

        <h2 style="text-align: center;">
          <el-form-item style="margin-left: 5%">
            <el-button @click="previous">上一步</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步
            </el-button>
          </el-form-item>
        </h2>

      </el-form>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controlsposition="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import chapter from "../../../api/chapter/chapter";

  export default {
    data() {
      return {
        chapter: {
          title: '',
          sort: 0
        },
        dialogChapterFormVisible: false,
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
      saveOrUpdate() {
        this.dialogChapterFormVisible = false
      },
      // 根据课程id获取对应的章节信息
      getChapterInfoByCourseId() {
        chapter.getChapterInfoByCourseId(this.courseId)
          .then(response => {
            console.log(response.data.chapterInfoList)
            this.chapterInfoList = response.data.chapterInfoList;
          })
      },
      previous() {
        // 返回上一步数据回显
        this.$router.push({path: '/info/' + this.$route.params.id})
      },
      next() {
        this.$router.push({path: '/publish/3'})
      }
    }
  }
</script>

<style scoped>
  .chapter_container {
    /*居中样式*/
    margin: 0 auto;
    width: 70%;
    margin-left: 10%;
    margin-top: 3%;
  }

  .save_item {
    margin-left: 38%;
    margin-top: 5%;
  }

  .chapterList {
    position: relative;
    list-style-type: none;
    margin: 0;
    padding: 0;
  }

  .chapterList li {
    position: relative;
  }

  .chapterList p {
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }

  .chapterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList {
    padding-left: 50px;
    list-style-type: none;
  }

  .videoList p {
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
