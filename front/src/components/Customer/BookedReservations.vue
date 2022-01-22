<template>
  <div class="container w-100">
    <br>
    <p>Future adventure adventures</p>
    <div v-for="reservation in adventures" :key="reservation.id">
      <b-card
          name="adventure"
          style="max-width: 70rem; margin-left: 15rem; margin-right: 15rem"
          class="mb-2"
      >
        <b-card-title>
          {{reservation.adventure.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Description: {{reservation.adventure.instructorName}}
        </b-card-sub-title>

        <b-card-text>
          {{ reservation.reservationStart | formatDate}} - length in hours: {{ reservation.length}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <br>
        <b-button v-if="isInNext3Days(reservation.reservationStart)" @click="cancelReservation(reservation)" variant="danger">Cancel</b-button>
      </b-card>
    </div>
    <p>Future cottage reservations:</p>
    <div v-for="cottage in cottages" :key="cottage.id">
      <b-card
          name="cottage"
          style="max-width: 70rem; margin-left: 15rem; margin-right: 15rem"
          class="mb-2"
      >
        <b-card-title>
          {{cottage.cottage.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Description: {{cottage.cottage.promo}}
        </b-card-sub-title>

        <b-card-text>
          {{ cottage.reservationStart | formatDate}} - {{ cottage.reservationEnd | formatDate}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ cottage.price }}
        </b-card-text>
        <br>
        <b-button v-if="isInNext3Days(cottage.reservationStart)" @click="cancelCottageReservation(cottage)" variant="danger">Cancel</b-button>
      </b-card>
    </div>

    <p>Future boat reservations:</p>
    <div v-for="boat in boats" :key="boat.id">
      <b-card
          name="boat"
          style="max-width: 70rem; margin-left: 15rem; margin-right: 15rem"
          class="mb-2"
      >
        <b-card-title>
          {{boat.boat.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Description: {{boat.boat.promo}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Address: {{boat.boat.address.country}}, {{boat.boat.address.city}}, {{boat.boat.address.street}}
        </b-card-sub-title>

        <b-card-text>
          {{ boat.reservationStart | formatDate}} - {{ boat.reservationEnd | formatDate}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ boat.price }}
        </b-card-text>
        <br>
        <b-button v-if="isInNext3Days(boat.reservationStart)" @click="cancelBoatReservation(boat)" variant="danger">Cancel</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>

import moment from "moment";
import AdventureReservationService from "@/Services/AdventureReservationService";
import CottageReservationService from "@/Services/CottageReservationService";
import LogInService from "@/Services/LogInService";
import BoatReservationService from "@/Services/BoatReservationService";

export default {
  name: "BookedReservations",
  data() {
    return {
      adventures : [],
      cottages : [],
      boats: [],
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
    AdventureReservationService.getAllFutureTermsByCustomerId(LogInService.userId).then(res =>  {
      this.adventures = res.data;
    });
    CottageReservationService.getAllFutureTermsByCustomerId(LogInService.userId).then(res =>  {
      this.cottages = res.data;
    });
    BoatReservationService.getAllFutureTermsByCustomerId(LogInService.userId).then(res => {
      this.boats = res.data;
    })
  },
  methods:{
    isInNext3Days(date){
      let inThreeDays = new Date(new Date().getTime()+(3*24*60*60*1000));
      return moment(inThreeDays).isBefore(moment(date));
    },
    cancelReservation(adventure){
      AdventureReservationService.cancelReservation(adventure).then(() => {
        alert("Reservation canceled");
        this.$router.push('/');
      }).catch(() =>{
        alert("Something went wrong!");
        this.$router.push('/');
      })
    },
    cancelCottageReservation(cottageReservation){
      CottageReservationService.cancelReservation(cottageReservation).then(() => {
        alert("Reservation canceled");
        this.$router.push('/');
      }).catch(() =>{
        alert("Something went wrong!");
        this.$router.push('/');
      })
    },
    cancelBoatReservation(boatReservation){
      BoatReservationService.cancelReservation(boatReservation).then(() => {
        alert("Reservation canceled");
        this.$router.push('/');
      }).catch(() =>{
        alert("Something went wrong!");
        this.$router.push('/');
      })
    },
  }
}
</script>

<style scoped>

</style>

