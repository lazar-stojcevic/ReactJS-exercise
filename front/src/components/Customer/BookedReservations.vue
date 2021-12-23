<template>
  <div class="container w-100">
    <br>
    <p>Future adventure adventures</p>
    <div v-for="reservation in adventures" :key="reservation.id">
      <b-card
          tag="adventure"
          style="max-width: 20rem;"
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
  </div>
</template>

<script>

import moment from "moment";
import AdventureReservationService from "@/Services/AdventureReservationService";
import LogInService from "@/Services/LogInService";

export default {
  name: "BookedReservations",
  data() {
    return {
      adventures : [],
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
      console.log(res.data)
      this.adventures = res.data;
    });
  },
  methods:{
    isInNext3Days(date){
      let inThreeDays = new Date(new Date().getTime()+(3*24*60*60*1000));
      return moment(inThreeDays).isBefore(moment(date));
    },
    cancelReservation(adventure){
      AdventureReservationService.cancelReservation(adventure);
    }
  }
}
</script>

<style scoped>

</style>

