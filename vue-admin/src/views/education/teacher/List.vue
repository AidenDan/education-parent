<template>
  <div class="app_container">
    <!--查询表单-->
    <el-form :inline="true" class="el-breadcrumb query_form">
      <el-form-item>
        <el-input v-model="teacherQuery.name" placeholder="讲师名"/>
      </el-form-item>
      <el-form-item>
        <el-select v-model="teacherQuery.level" clearable placeholder="讲师头衔">
          <el-option :value="1" label="高级讲师"/>
          <el-option :value="2" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker
          v-model="teacherQuery.begin"
          type="datetime"
          placeholder="选择开始时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="teacherQuery.end"
          type="datetime"
          placeholder="选择截止时间"
          value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00"
        />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getTeacherList()">查询
      </el-button>
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
      <el-table-column prop="name" label="名称" width="80"/>
      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="资历"/>
      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
      <el-table-column prop="sort" label="排序" width="60"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/edu/teacher/edit/'+scope.row.id">
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
      @current-change="getTeacherList"
    />
  </div>

</template>

<script>
  import teacher from "../../../api/teacher/teacher";

  export default {
    name: "List",
    data() {
      return {
        list: null,
        total: 0,
        current: 1,
        limit: 5,
        teacherQuery: {}
      }
    },
    created() {
      this.getTeacherList()
    },
    methods: {
      getTeacherList(current = 1) {  // current = 1为默认值
        this.current = current;
        teacher.getTeacherList(this.current, this.limit, this.teacherQuery)
          .then(response => {
            this.list = response.data.rows;
            this.total = response.data.total;
            console.log(response)
          })  // 请求成功处理逻辑
          .catch(error => {
            console.log(error)
          })  // 请求失败处理逻辑
      },

      // 重置查询参数
      resetData() {
        // 1.清空查询参数
        this.teacherQuery = {}
        // 2.查询所有数据
        this.getTeacherList()
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
