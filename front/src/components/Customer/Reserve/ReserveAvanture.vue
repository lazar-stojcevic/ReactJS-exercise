<template>
<div>
  <b-form inline style="display: flex" @submit.prevent="searchAvailableAdventures">
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

  <div style="margin-right: 20px; margin-left: 20px" v-for="adventure in adventures" :key="adventure.id">
    <b-card bg-variant="dark" text-variant="white">
      <b-card-title>
        {{adventure.adventure.name}}
      </b-card-title>
      <b-card-sub-title style="margin: 5px">
        Time of start: {{adventure.reservationStart}}
      </b-card-sub-title>
      <b-card-sub-title style="margin: 5px">
        Maximum persons: {{adventure.adventure.maxPersons}}
      </b-card-sub-title>
      <b-card-text style="margin: 5px">
        Description: {{adventure.adventure.description}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Equipment: {{adventure.adventure.equipment}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        RULES: {{adventure.adventure.conductRules}}
      </b-card-text>
      <b-button :to="'newAdventureReservation/'+adventure.id.toString()" variant="primary">Reserve</b-button>
    </b-card>
    <br>
  </div>

</div>
</template>

<script>
import AdventureReservationService from "@/Services/AdventureReservationService";
export default {
  name: "ReserveAvanture",
  data() {
    return {
      AdventureReservationService,
      inputData: {
        firstDay: '',
        lastDat: '',
        persons: 1
      },
      adventures: [],

    }
  },
  methods:{
    searchAvailableAdventures(){
      AdventureReservationService.getAllAvailableAdventureTerms(this.inputData.firstDay, this.inputData.lastDay, this.inputData.persons)
          .then((res) => {this.adventures = res.data})
    }
  }
}
</script>

<style scoped>

</style>
