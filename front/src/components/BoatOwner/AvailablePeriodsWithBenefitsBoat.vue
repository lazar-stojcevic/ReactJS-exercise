<template>
  <form @submit.prevent="addAvailability">
    <div class="input-group mb-3">
      <span class="input-group-text">CHOOSE A COTTAGE</span>
      <b-form-select v-model="period.cottageId" class="mb-3">
        <b-form-select-option v-for="boat in boats" :key="boat.id" v-bind:value="boat.id">{{boat.name}}</b-form-select-option>
      </b-form-select>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">BEGINNING OF RESERVATION PERIOD</span>
      <input type="datetime-local" class="form-control" v-model="period.date1">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">END OF RESERVATION PERIOD</span>
      <input type="datetime-local" class="form-control" v-model="period.date2">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">DESCRIPTION</span>
      <input type="text" class="form-control" v-model="period.description">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">% OFF</span>
      <input type="number" class="form-control" v-model="period.sale">
    </div>
    <div class="input-group mb-3">
      <div class="btn-group-sm">
        <button type="submit" class="btn-info">CONFIRM</button>
        <button type="reset" class="btn-danger">CLOSE</button>
      </div>
    </div>
  </form>
</template>

<script>
import LogInService from "../../Services/LogInService";
import BoatOwnerService from "../../Services/BoatOwnerService";
import BoatReservationService from "../../Services/BoatReservationService";

export default {
  name: "AvailablePeriodsWithBenefitsBoat",
  data(){
    return{
      boats:[],
      period: {
        cottageId:'',
        date1: Date.now(),
        date2:Date.now(),
        description: '',
        sale: 0
      }
    }
  },mounted() {
    BoatOwnerService.getMyBoats(LogInService.userId).then(res => {
      this.boats = res.data;
    });
  },
  methods: {
    addAvailability() {
      if(this.period.date1 > this.period.date2){
        alert("Lose uneti datumi");
      }else {
        BoatReservationService.AddPeriodForFastReservation(this.period).then(() => {
              alert("Fast reservation created")
              this.$router.push('/');
            }
        ).catch(() => {alert("Period overlaps! Try other date.")});
      }

    }
  }
}
</script>

<style scoped>

</style>