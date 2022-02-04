<template>
  <div class="container w-100">

    <br>
    <h3>Boat reservations</h3>
    <br>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>BOAT</td>
        <td>FROM DATE</td>
        <td>TO DATE</td>
        <td>PRICE</td>
        <td>CUSTOMER</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="reservation in reservations" :key="reservation.id">
        <td>{{reservation.boat.name}}</td>
        <td>{{reservation.reservationStart}}</td>
        <td>{{reservation.reservationEnd}}</td>
        <td>{{reservation.price}}</td>
        <td><router-link :to="'customerReservation/'+reservation.customer.id.toString()">{{reservation.customer.firstname}} {{reservation.customer.lastName}}</router-link></td>
        <td><router-link :hidden="canReserve(reservation.reservationStart, reservation.reservationEnd)" :to="'createReservationBoatForCustomer/'+reservation.customer.id.toString()">Add reservation for customer</router-link></td>
      </tr>
      </tbody>
    </table>

  </div>
</template>

<script>
import LogInService from "../../Services/LogInService";
import moment from "moment";
import BoatReservationService from "../../Services/BoatReservationService";

export default {
  name: "MyBoatReservation",
  data() {
    return {
      reservations : [],
      filtered : [],
      filter: {
        dateFrom: new Date(),
        dateTo: new Date(),
        price : 0,
        name : '',
      },
      sort : 'nameASC'
    }
  },
  mounted() {
    BoatReservationService.getAllReservationsOfOwner(LogInService.userId).then(res => {
      this.reservations = res.data;
      this.filtered = res.data;
    });
  },
  methods:{
    search(){
      if (this.sort === 'dateASC')
        this.reservations.sort((a,b) =>
            (moment(a.dateFrom).format("dd-mm-yyyy") > moment(b.dateFrom).format("dd-mm-yyyy"))
                ? 1 : ((moment(b.dateFrom).format("dd-mm-yyyy") > moment(a.dateFrom).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'dateDESC')
        this.reservations.sort((a,b) =>
            (moment(a.dateFrom).format("dd-mm-yyyy") < moment(b.dateFrom).format("dd-mm-yyyy"))
                ? 1 : ((moment(b.dateFrom).format("dd-mm-yyyy") < moment(a.dateFrom).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.reservations.sort((a,b) => (a.price > b.price) ? 1 : ((b.price > a.price) ? -1 : 0))
      else if (this.sort === 'priceDESC')
        this.reservations.sort((a,b) => (a.price < b.price) ? 1 : ((b.price < a.price) ? -1 : 0))
      else if (this.sort === 'lengthASC')
        this.reservations.sort((a,b) => (a.length > b.length) ? 1 : ((b.length > a.length) ? -1 : 0))
      else if (this.sort === 'lengthDESC')
        this.reservations.sort((a,b) => (a.length < b.length) ? 1 : ((b.length < a.length) ? -1 : 0))
    },

    canReserve(date1, date2){
      if(moment(date1) < moment() && moment(date2) > moment()){
        return false;
      }
      return true;
    }
  }
}
</script>

<style scoped>

</style>