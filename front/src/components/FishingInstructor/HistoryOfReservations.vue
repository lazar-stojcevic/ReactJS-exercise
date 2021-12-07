<template>
<div class="container">
  <div style="margin-top: 15px">
    <table class="table table-striped">
      <thead>
      <tr>
        <td>START TIME</td>
        <td>PRICE</td>
        <td>CUSTOMER</td>
        <td>REPORT</td>
      </tr>
      </thead>
      <tbody>
        <tr v-for="pr in pastReservations" :key="pr.id">
          <td class="col-md-2">{{pr.reservationStart}}</td>
          <td class="col-md-1">{{pr.price}}</td>
          <td class="col-md-2">USERNAME</td>
          <td v-if="pr.report !== null">{{pr.report}}</td>
          <td v-else><button class="btn btn-outline-success col-md-12">ADD REPORT</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import LogInService from "@/Services/LogInService";
export default {
  data(){
    return{
      pastReservations:{
        report: ''
      },
      mode: 'neutral'
    }
  },
  mounted() {
    AdventureReservationService.getAllPastReservationsOfInstructor(LogInService.userId).then(res => {
      this.pastReservations = res.data;
    }).catch(() => {alert("THERE IS SOME ERROR WITH LOADING PAST RESERVATIONS")});
  },
  methods:{

  }
}
</script>

<style scoped>

</style>