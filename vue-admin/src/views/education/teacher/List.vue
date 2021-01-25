<template>
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
        limit: 10,
        teacherQuery: {}
      }
    },
    created() {
      this.getTeacherList()
    },
    methods: {
      getTeacherList() {
        teacher.getTeacherList(this.current, this.limit, this.teacherQuery)
          .then(response => {
            console.log(response)
          })  // 请求成功处理逻辑
          .catch(error => {
            console.log(error)
          })  // 请求失败处理逻辑
      }
    }
  }
</script>

<style scoped>

</style>
