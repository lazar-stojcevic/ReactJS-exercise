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
    <p>Cottage reservations</p>
    <div v-for="reservation in reservations" :key="reservation.id">
      <b-card
          tag="cottage"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{reservation.cottageName}}
        </b-card-title>
        <b-card-text>
          {{ reservation.dateFrom | formatDate}} - {{ reservation.dateTo | formatDate}}
        </b-card-text>
        <br>
        <b-card-text>
          PRICE:  {{ reservation.price }}
        </b-card-text>
        <br>
        <router-link class="btn btn-secondary" :to="'userReservationProfile'" style="margin: 5px">See cottage</router-link>
      </b-card>
    </div>
  </div>
</template>

<script>
import moment from "moment";

export default {
  name: "MyCottageReservation",
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
      {id: 1, dateFrom: new Date(2020, 12,1, 0, 0, 0, 0), dateTo: new Date(2020, 12,10), price: 50000, cottageName: "Mala kuca", length: 10},
      {id: 2, dateFrom: new Date(2020, 7,1,1, 0, 0, 0), dateTo: new Date(2020, 7,21), price: 100000, cottageName: "Mala kuca", length: 20},
      {id: 3, dateFrom: new Date(2020, 6,1,1, 0, 0, 0), dateTo: new Date(2020, 6,3), price: 10000, cottageName: "Mala kuca", length: 3},
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

</style>