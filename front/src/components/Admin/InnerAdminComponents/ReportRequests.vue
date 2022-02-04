<template>
  <div class="container">
    <table class="table table-striped">
      <thead>
        <th>USERNAME</th>
        <th>FULL NAME</th>
        <th>PENALTY POINTS</th>
        <th>REPORT</th>
      </thead>
      <tbody>
        <tr v-for="r in reports" :key="r.id">
          <td class="col-md-1">{{r.customer.email}}</td>
          <td class="col-md-2">{{r.customer.firstname}} {{r.customer.lastName}}</td>
          <td class="col-md-1">{{r.customer.penaltyPoints}}</td>
          <td class="col-md-6">{{r.comment}}</td>
          <td class="col-md-1"><button class="btn-sm btn-warning" @click="penalize(r.id)">PENALIZE</button></td>
          <td class="col-md-1"><button class="btn-sm btn-success" @click="mercy(r.id)">MERCY</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import ReportService from "@/Services/ReportService";
export default {
  data(){
    return{
      reports: []
    }
  },
  mounted() {
    this.loadReports();
  },
  methods:{
    penalize(reportId){
      ReportService.penalizeCustomer(reportId).then(() => {this.loadReports();}).catch(() => {
        alert("THERE IS SOME ERROR WITH PENALIZE REPORT");
      })
    },

    mercy(reportId){
      ReportService.haveMercyOnCustomer(reportId).then(() => {this.loadReports()}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH MARCY REPORT")
      })
    },

    loadReports(){
      ReportService.getAllReportsForProcessing().then(res => {this.reports = res.data}).catch(() => {
        alert("THERE IS SOME PROBLEM WITH LOADING REPORTS");
      })
    }
  }
}
</script>

<style scoped>

</style>