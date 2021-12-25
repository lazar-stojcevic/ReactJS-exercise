<template>
  <div class="container">
    <!--USER REQUEST-->
    <div>
      <h3>USER REQUESTS</h3>
      <profile-requests></profile-requests>
      <hr/>
    </div>
    <!--REPORT REQUEST-->
    <div>
      <h3>REPORT REQUESTS</h3>
      <reports></reports>
      <hr/>
    </div>
  </div>
</template>

<script>
import ReportRequests from "@/components/Admin/InnerAdminComponents/ReportRequests";
import ProfileRequests from "@/components/Admin/InnerAdminComponents/ProfileRequests";
export default {
  components:{
    'reports' : ReportRequests,
    'profileRequests' : ProfileRequests
  },
  data(){
    return{
    }
  },
  mounted() {
  },
  methods:{
  }
}
</script>

<style scoped>

</style>