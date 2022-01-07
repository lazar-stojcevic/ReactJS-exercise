<template>
<div class="container">

  <!--MODAL-->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">CUSTOMER PROFILE</h5>
          <button @click="changeModeToNeutral" type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <table class="table-bordered">
            <tr><td>FIRSTNAME:</td><td/><td><strong>{{customer.firstname}}</strong></td></tr>
            <tr><td>LASTNAME:</td><td/><td><strong>{{customer.lastName}}</strong></td></tr>
            <tr><td>EMAIL:</td><td/><td><strong>{{customer.email}}</strong></td></tr>
            <tr><td>ADDRESS:</td><td/><td><strong>{{customer.address.street}}, {{customer.address.city}}, {{customer.address.country}}</strong></td></tr>
            <tr><td>PHONE:</td><td/><td><strong>{{customer.phone}}</strong></td></tr>
            <tr><td>PENALTY POINTS:</td><td/><td><strong>{{customer.penaltyPoints}}</strong></td></tr>
            <tr v-if="mode === 'neutral' && reservation.reported === false">
              <td><button class="btn btn-success" @click="showReportForm">ADD REPORT</button></td>
            </tr>
          </table>
          <div v-if="mode === 'report' && reservation.reported === false">
            <h4>ADD REPORT</h4>
            <form @submit.prevent="saveReport">
              <div class="input-group mb-3">
                <span class="input-group-text">COMMENT</span>
                <input type="text" class="form-control" v-model="report.comment">
              </div>
              <div class="form-group form-check">
                <input type="checkbox" v-model="report.customerAppear" id="customerAppear"/>
                <label class="form-check-label" for="customerAppear">IS CUSTOMER APPEAR</label>
              </div>
              <div class="form-group form-check">
                <input type="checkbox" v-model="report.badReport" id="badReport">
                <label class="form-check-label" for="badReport">IS BAD REPORT</label>
              </div>
              <div class="input-group mb-3" style="margin-top: 10px">
                <div>
                  <button data-bs-dismiss="modal" type="submit" class="btn btn-success">CONFIRM</button>
                  <button @click="changeModeToNeutral" type="reset" class="btn btn-warning">CLOSE</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div style="margin-top: 15px">
    <table class="table table-striped">
      <thead>
      <tr>
        <td>START TIME</td>
        <td>PRICE</td>
        <td>CUSTOMER MAIL</td>
      </tr>
      </thead>
      <tbody>
        <tr v-for="(pr, index) in pastReservations" :key="pr.id" class="showCustomer" @click="showCustomer(index)"
            data-bs-toggle="modal" data-bs-target="#exampleModal">
          <td class="col-md-2">{{pr.reservationStart}}</td>
          <td class="col-md-1">{{pr.price}}</td>
          <td class="col-md-2">{{pr.customer.email}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
import LogInService from "@/Services/LogInService";
import ReportService from "@/Services/ReportService";
export default {
  data(){
    return{
      pastReservations:[],
      mode: 'neutral',
      customer: {firstname: '', address: {street: ''}, penaltyPoints: ''},
      reservation: {reported: ''},
      report: {comment: '', reservationType: 'AR', badReport: false, customerAppear: true, reservationId: ''}
    }
  },
  mounted() {
    AdventureReservationService.getAllPastReservationsOfInstructor(LogInService.userId).then(res => {
      this.pastReservations = res.data;
    }).catch(() => {alert("THERE IS SOME ERROR WITH LOADING PAST RESERVATIONS")});
  },
  methods:{
    showCustomer(index){
      this.customer = this.pastReservations[index].customer;
      this.reservation = this.pastReservations[index];
      this.changeModeToNeutral();
    },

    showReportForm(){
      this.mode = 'report';
      this.report.reservationId = this.reservation.id;
    },

    changeModeToNeutral(){
      this.mode = 'neutral';
      this.report = {comment: '', reservationType: 'AR', badReport: false, customerAppear: true, reservationId: ''};
    },

    saveReport() {
      if(this.reservation.report === true){
        alert("THIS RESERVATION IS ALREADY REPORTED");
        this.changeModeToNeutral();
        return;
      }
      ReportService.makeReport(this.report).then(() => {
        this.loadAllPastReservations();
        this.changeModeToNeutral();
      }).catch(() => {
        alert("THERE IS SOME ERROR WITH SAVING REPORT");
      })
    },

    loadAllPastReservations(){
      AdventureReservationService.getAllPastReservationsOfInstructor(LogInService.userId).then(res => {
        this.pastReservations = res.data;
      }).catch(() => {alert("THERE IS SOME ERROR WITH LOADING PAST RESERVATIONS")});
    }
  }
}
</script>

<style scoped>
.showCustomer:hover{
  background-color: antiquewhite;
  font-weight: bolder;
  cursor: pointer;
}
</style>