<template>
  <div class="container">
    <h1 class="text-center">Students</h1>
      <table class="table table-striped">
        <thead>
          <th>Student id</th>
          <th>Student name</th>
          <th>Student email</th>
        </thead>
        <tbody>
          <tr v-for="student in students" v-bind:key="student.id">
            <td>{{student.id}}</td>
            <td>{{student.name}}</td>
            <td>{{student.email}}</td>
            <td><button class="bg-light" @click="deleteStudent(student.id)">Obrisi</button></td>
          </tr>
        </tbody>
      </table>
  </div>
</template>

<script>
import StudentService from "@/Services/StudentService";

export default {
  name: "Students",
  data() {
    return {
      students: []
    }
  },
  methods: {
    getStudents(){
      StudentService.getStudents().then((res)=>{this.students=res.data}).catch((err)=>{alert(err)});
    },
    deleteStudent(id){
      alert(id);
    }
  },
  created() {
   this.getStudents()
  }
}
</script>

<style scoped>

</style>