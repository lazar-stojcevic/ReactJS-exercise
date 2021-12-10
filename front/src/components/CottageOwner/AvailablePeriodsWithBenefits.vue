<template>
  <form @submit.prevent="addAvailability">
    <div class="input-group mb-3">
      <span class="input-group-text">CHOOSE A COTTAGE</span>
      <b-form-select v-model="cottage.id" class="mb-3">
        <b-form-select-option v-for="cot in cottages" :key="cot.id" value="cot.id">{{cot.name}}</b-form-select-option>
      </b-form-select>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">BEGINNING OF AVAILABLE PERIOD</span>
      <input type="date" class="form-control" v-model="cottage.date1">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">END OF AVAILABLE PERIOD</span>
      <input type="date" class="form-control" v-model="cottage.date2">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">DESCRIPTION</span>
      <input type="text" class="form-control" v-model="cottage.decription">
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">% OFF</span>
      <input type="number" class="form-control" v-model="cottage.sale">
    </div>
    <div class="input-group mb-3">
      <div class="btn-group-sm">
        <button type="submit" class="btn-info">CONFIRM</button>
        <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
      </div>
    </div>
  </form>
</template>

<script>
import CottageOwnerService from "../../Services/CottageOwnerService";
import LogInService from "../../Services/LogInService";

export default {
  name: "AvailablePeriodsWithBenefits",
  data(){
    return{
      cottages:[],
      cottage: {
        id:'',
        date1: Date.now(),
        date2:Date.now(),
        decription: '',
        sale: 0
      }
    }
  },mounted() {
    CottageOwnerService.getMyCottages(LogInService.userId).then(res => {
      this.cottages = res.data;
    });
  },
  methods: {
    addPeriod() {


    }
  }
}
</script>

<style scoped>

</style>