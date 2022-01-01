<template>
<div class="container">
  <!--MODAL-->
  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">CUSTOMER PROFILE</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <table class="table-bordered">
            <tr><td>FIRSTNAME:</td><td/><td><strong>{{customer.firstname}}</strong></td></tr>
            <tr><td>LASTNAME:</td><td/><td><strong>{{customer.lastName}}</strong></td></tr>
            <tr><td>EMAIL:</td><td/><td><strong>{{customer.email}}</strong></td></tr>
            <tr><td>ADDRESS:</td><td/><td><strong>{{customer.address.street}}, {{customer.address.city}}, {{customer.address.country}}</strong></td></tr>
            <tr><td>PHONE:</td><td/><td><strong>{{customer.phone}}</strong></td></tr>
            <tr><td>PENALTY POINTS:</td><td/><td><strong>{{customer.penaltyPoints}}</strong></td></tr>
          </table>
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
export default {
  data(){
    return{
      pastReservations:[],
      mode: 'neutral',
      customer: {firstname: '', address: {street: ''}}
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