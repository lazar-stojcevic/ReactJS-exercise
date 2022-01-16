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
      <td class="col-md-1"><button class="btn-sm btn-danger" @click="deleteGrade(grade.id)">DISABLE</button></td>
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
      alert("Please wait...")
      GradeService.enableGrade(gradeId).then(() => {
        alert("GRADE IS ENABLED");
        this.getAllGradesForRevision();
      }).catch(() => {
        alert("THERE IS SOME ERROR WITH ENABLING GRADE");
      })
    },

    deleteGrade(gradeId){
      GradeService.disableGrade(gradeId).then(() => {
        alert("GRADE IS DISABLED");
        this.getAllGradesForRevision();
      }).catch(() => {
        alert("THERE IS SOME ERROR WITH DISABLE GRADE");
      })
    }
  }
}
</script>

<style scoped>

</style>