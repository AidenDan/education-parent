<template>
  <div class="app_container">
    <!--查询表单-->
    <el-form :inline="true" class="el-breadcrumb query_form">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称"/>
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="发布状态">
          <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getCourseList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row class="el-table_class">
      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (current - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="课程名称" align="center"/>
      <el-table-column prop="name" label="课程老师" align="center"/>
      <el-table-column label="发布状态" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>
      <el-table-column prop="price" label="价格" width="80" align="center"/>
      <el-table-column prop="lessonNum" label="课时数" width="160" align="center"/>
      <el-table-column prop="buyCount" label="购买量" width="80" align="center"/>
      <el-table-column prop="viewCount" label="浏览量" width="80" align="center"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete"
                     @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="current"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getCourseList"
    />
  </div>

</template>

<script>
  import course from "../../../api/course/course";

  export default {
    name: "List",
    data() {
      return {
        list: null,
        total: 0,
        current: 1,
        limit: 5,
        courseQuery: {}
      }
    },
    created() {
      this.getCourseList()
    },
    methods: {
      getCourseList(current = 1) {  // current = 1为默认值
        this.current = current;
        course.getCourseList(this.current, this.limit, this.courseQuery)
          .then(response => {
            this.list = response.data.rows;
            this.total = response.data.total;
          })  // 请求成功处理逻辑
          .catch(error => {
          })  // 请求失败处理逻辑
      },

      // 重置查询参数
      resetData() {
        // 1.清空查询参数
        this.courseQuery = {};
        // 2.查询所有数据
        this.getCourseList()
      },

      // 讲师删除功能
      removeDataById(id) {
        this.$confirm('此操作将永久删除该课程, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => { // 点击确定执行
          course.deleteCourseById(id)
            .then((response) => {
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getCourseList();
            })
        })
      }
    }
  }
</script>

<style scoped>
  .app_container {
    margin-left: 5%;
    width: 90%;
    text-align: center;
  }

</style>
