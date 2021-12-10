<template>
  <form @submit.prevent="addReport">
    <div class="input-group mb-3">
      <span class="input-group-text">CHOOSE A COTTAGE</span>
      <b-form-select v-model="cottage.id" class="mb-3">
        <b-form-select-option v-for="cot in cottages" :key="cot.id" value="cot.id">{{cot.name}}</b-form-select-option>
      </b-form-select>
    </div>
    <div class="input-group mb-3">
      <span class="input-group-text">RESERVATION REPORT TEXT</span>
      <input type="text" class="form-control" v-model="cottage.reportText">
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
import CottageOwnerService from "../../Services/CottageOwnerService";
import LogInService from "../../Services/LogInService";

export default {
  name: "ReservationReport",
  data(){
    return{
      cottages:[],
      cottage: {
        id:'',
        reportText: '',
      }
    }
  },mounted() {
    CottageOwnerService.getMyCottages(LogInService.userId).then(res => {
      this.cottages = res.data;
    });
  },
  methods: {
    addReport(){


    }

  }
}
</script>

<style scoped>

</style>