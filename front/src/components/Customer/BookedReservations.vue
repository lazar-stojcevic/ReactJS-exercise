<template>
  <div class="container w-100">
    <br>
    <p>Future reservations</p>
    <div v-for="reservation in reservations" :key="reservation.id">
      <b-card
          tag="adventure"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{reservation.name}}
        </b-card-title>
        <br>
        <b-card-sub-title v-if="reservation.type === 'ADVENTURE'">
          Instructor name: {{reservation.instructorName}}
        </b-card-sub-title>
        <b-card-sub-title v-if="reservation.type === 'COTTAGE'">
          Cottage location: {{reservation.location}}
        </b-card-sub-title>
        <b-card-sub-title v-if="reservation.type === 'BOAT'">
          Boat owner: {{reservation.boatOwner}}
        </b-card-sub-title>
        <b-card-text>
          {{ reservation.dateFrom | formatDate}} - {{ reservation.dateTo | formatDate}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <br>
        <b-button v-if="isInNext3Days(reservation.dateFrom)" variant="danger">Cancel</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>

import moment from "moment";

export default {
  name: "BookedReservations",data() {
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
    //DUMMY
    this.reservations = [
      {id: 1, dateFrom: new Date(2022, 1,1, 5, 0, 0, 0), dateTo: new Date(2022, 1,3, 12, 0, 0, 0), price: 500, type: "BOAT", name: "Yamato battleship imitation", length: 2, boatOwner: "Taisuke Yamaguci"},
      {id: 2, dateFrom: new Date(2022, 0,1,1, 0, 0, 0), dateTo: new Date(2022, 0,2, 3, 0 ,0, 0), price: 150, type: "COTTAGE",name: "Zlatibor cottage",location: "Zlatibor" ,length: 4},
      {id: 3, dateFrom: new Date(2021, 11,9,0, 0, 0,0), dateTo: new Date(2021, 11,12, 0, 0, 0, 0), price: 100, type: "ADVENTURE" ,name: "Fishing on DTD", instructorName: "Momcilo Ivanovic", length: 48},
    ];
  },
  methods:{
    isInNext3Days(date){
      let inThreeDays = new Date(new Date().getTime()+(3*24*60*60*1000));
      return moment(inThreeDays).isBefore(moment(date));
    }
  }
}
</script>

<style scoped>

</style>

