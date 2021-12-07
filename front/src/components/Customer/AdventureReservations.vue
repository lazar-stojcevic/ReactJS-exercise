<template>
  <div class="container w-100">
    <div class="search">
      <b-form-select v-model="sort" class="m-3">
        <b-form-select-option value="dateASC">date descending</b-form-select-option>
        <b-form-select-option value="dateDESC">date ascending</b-form-select-option>
        <b-form-select-option value="priceDESC">price descending</b-form-select-option>
        <b-form-select-option value="priceASC">price ascending</b-form-select-option>
        <b-form-select-option value="lengthDESC">length descending</b-form-select-option>
        <b-form-select-option value="lengthASC">length ascending</b-form-select-option>
      </b-form-select>
      <b-input-group >
        <b-button variant="info" @click="search">Sort</b-button>
      </b-input-group>
    </div>
    <br>
    <p>Adventure reservations</p>
    <div v-for="reservation in reservations" :key="reservation.id">
      <b-card
          tag="adventure"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{reservation.adventureName}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Instructor name: {{reservation.instructorName}}
        </b-card-sub-title>
        <b-card-text>
          {{ reservation.dateFrom | formatDate}} - {{ reservation.dateTo | formatDate}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <br>
      </b-card>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "AdventureReservations",
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
    //DUMMY
    this.reservations = [
      {id: 1, dateFrom: new Date(2020, 12,1, 10, 0, 0), dateTo: new Date(2020, 12,1, 12, 0, 0), price: 200, adventureName: "Adventure through Sicevackom klisura",instructorName: "Bojan Lupolov", length: 2},
      {id: 2, dateFrom: new Date(2020, 7,1,1, 0, 0, 0), dateTo: new Date(2020, 7,1, 5, 0 ,0), price: 150, adventureName: "Fishing on Palic lake", instructorName: "Stefan Stefic", length: 4},
      {id: 3, dateFrom: new Date(2020, 6,1,1, 0, 0, 0), dateTo: new Date(2020, 6,3, 0, 0, 0), price: 100, adventureName: "Fishing on DTD", instructorName: "Momcilo Ivanovic", length: 49},
    ];
    this.reservations.sort((a,b) => (a.dateFrom > b.dateFrom) ? 1 : ((b.dateFrom > a.dateFrom) ? -1 : 0))
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
    }
  }
}
</script>

<style scoped>
.search{
  display: flex;
  align-items: center;
}
.datepicker{
  align-items: normal;
  flex-wrap: nowrap;
}
</style>

