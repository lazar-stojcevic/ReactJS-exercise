<template>
  <div class="container">
    <div class="card">
      <div class="card-body">

        <select class="form-select" aria-label="Default select example" v-model="mode">
          <option selected value="user" >PROFILE VALIDATION REQUESTS</option>
          <option value="report">BAD REPORTS REQUESTS</option>
          <option value="delete">REQUESTS FOR DELETING PROFILE</option>
          <option value="grade">GRADE REVISION</option>
          <option value="adventure">COMPLAINTS REVISION</option>
        </select>

          <!--USER REQUEST-->
        <div v-if="mode === 'user'">
          <h3>PROFILE REQUESTS</h3>
          <profile-requests></profile-requests>
        </div>

        <!--REPORT REQUEST-->
        <div v-else-if="mode === 'report'">
          <h3>REPORT REQUESTS</h3>
          <reports></reports>
        </div>

        <!--DELETE REQUESTS-->
        <div v-else-if="mode === 'delete'">
          <h3>DELETING REQUESTS</h3>
          <delete></delete>
        </div>

        <!--GRADE REVISION-->
        <div v-else-if="mode === 'grade'">
          <h3>GRADE REVISION</h3>
          <grade></grade>
        </div>

        <!--ADVENTURE COMPLAINTS REVISION-->
        <div v-else-if="mode === 'adventure'">
          <h3>ADVENTURE COMPLAINTS</h3>
          <adventure-complaints></adventure-complaints>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
import ReportRequests from "@/components/Admin/InnerAdminComponents/ReportRequests";
import ProfileRequests from "@/components/Admin/InnerAdminComponents/ProfileRequests";
import GradesForRevision from "@/components/Admin/InnerAdminComponents/GradesForRevision";
import DeleteRequest from "@/components/Admin/InnerAdminComponents/DeleteRequest";
import AdventureComplaints from "@/components/Admin/InnerAdminComponents/AdventureComplaints";
export default {
  components:{
    'reports' : ReportRequests,
    'profileRequests' : ProfileRequests,
    'grade' : GradesForRevision,
    'delete' : DeleteRequest,
    'adventureComplaints' : AdventureComplaints
  },
  data(){
    return{
      mode: 'user'
    }
  },
  mounted() {
  },
  methods:{
  }
}
</script>

<style scoped>
.form-select {
  background-color: gainsboro;
  margin-bottom: 20px;
  font-size: large;
  font-weight: inherit;
}
</style>