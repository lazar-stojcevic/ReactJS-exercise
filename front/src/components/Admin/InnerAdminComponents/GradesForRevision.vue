<template>
<div class="container">
  <table class="table table-striped" style="margin-top: 25px">
    <thead>
      <th>COMMENT</th>
      <th>RATING</th>
    </thead>
    <tbody>
    <tr v-for="grade in grades" :key="grade.id">
      <td class="col-md-8">{{ grade.revision }}</td>
      <td class="col-md-1">{{ grade.rating }}</td>
      <td class="col-md-1"><button class="btn-sm btn-success" @click="enableGrade(grade.id)">ENABLE</button></td>
      <td class="col-md-1"><button class="btn-sm btn-danger" @click="deleteGrade(grade.id)">DELETE</button></td>
    </tr>
    </tbody>
  </table>
</div>
</template>

<script>
import GradeService from "@/Services/GradeService";
export default {
  data(){
    return{
      grades: []
    }
  },
  mounted() {
  this.getAllGradesForRevision()
  },
  methods:{
    getAllGradesForRevision(){
      GradeService.getAllGradesForRevision().then(res => {this.grades = res.data}).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING GRADES");
      });
    },

    enableGrade(gradeId){
      GradeService.enableGrade(gradeId).then(() => {this.getAllGradesForRevision()}).catch(() => {
        alert("THERE IS SOME ERROR WITH ENABLING GRADES");
      })
    },

    deleteGrade(gradeId){
      GradeService.deleteGrade(gradeId).then(() => {this.getAllGradesForRevision()}).catch(() => {
        alert("THERE IS SOME ERROR WITH DELETING GRADES");
      })
    }
  }
}
</script>

<style scoped>

</style>