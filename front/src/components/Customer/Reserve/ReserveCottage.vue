<template>
    <div>
      <b-form inline style="display: flex" @submit.prevent="searchAvailableCottages">
      <span class="input-group mb-lg-2">
        <span class="input-group-text">DATE FROM</span>
        <input type="datetime-local" class="form-control" v-model="inputData.firstDay" required/>
      </span>
        <span class="input-group mb-lg-2">
        <span class="input-group-text">DATE TO</span>
        <input type="datetime-local" class="form-control" v-model="inputData.lastDay" required/>
      </span>
        <span class="input-group mb-lg-2">
        <span class="input-group-text">PERSONS</span>
        <input type="number" class="form-control" v-model="inputData.persons" required/>
      </span>
        <button type="submit" class="btn-info">Search</button>
      </b-form>
      <br>
      <div style="display: flex">
        <b-form-select v-model="sort" class="m-3">
          <b-form-select-option value="markASC">mark ascending</b-form-select-option>
          <b-form-select-option value="markDESC">mark descending</b-form-select-option>
          <b-form-select-option value="priceASC">price ascending</b-form-select-option>
          <b-form-select-option value="priceDESC">price descending</b-form-select-option>
        </b-form-select>
        <b-input-group>
          <b-button variant="info" @click="sortReservations">Sort</b-button>
        </b-input-group>
    </div>
  </div>
</template>

<script>
import CottageReservationService from "@/Services/CottageReservationService";
export default {
  name: "ReserveCottage",
  data() {
    return {
      inputData: {
        firstDay: '',
        lastDat: '',
        persons: 1
      },
      cottages: [],
      sort : 'markDESC'

    }
  },
  methods:{
    searchAvailableCottages(){
      CottageReservationService.getAllAvailableAdventureTerms(this.inputData.firstDay, this.inputData.lastDay, this.inputData.persons);
    },
    sortReservations(){

    }
  }
}
</script>

<style scoped>

</style>
