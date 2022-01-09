<template>
  <div>
    <br>
    <h2>Available periods</h2>
    <br>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>FROM DATE</td>
        <td>TO DATE</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="per in periods" :key="per.id">
        <td>{{per.fromDate}}</td>
        <td>{{per.toDate}}</td>
      </tr>
      </tbody>
    </table>
    <br>
    <br>
    <h2>Enter new period:</h2>
    <br>
    <form @submit.prevent="addAvailability">
      <div class="input-group mb-3">
        <span class="input-group-text">BEGINNING OF AVAILABLE PERIOD</span>
        <input type="datetime-local" class="form-control" v-model="period.date1">
      </div>
      <div class="input-group mb-3">
        <span class="input-group-text">END OF AVAILABLE PERIOD</span>
        <input type="datetime-local" class="form-control" v-model="period.date2">
      </div>
      <div class="input-group mb-3">
        <div class="btn-group-sm">
          <button type="submit" class="btn btn-success">CONFIRM</button>
          <button @click="goBack()" type="reset" class="btn btn-danger">BACK</button>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import CottageService from "../../Services/CottageService";

export default {
  name: "AvailablePeriods",
  data(){
    return{
      id:'',
      periods:[],
      period: {
        entityId:'',
        date1: Date.now(),
        date2:Date.now()
      }
    }
  },mounted() {
    this.id = this.$route.params.id;
    CottageService.getAvailablePeriodsOfCottage(this.id).then(res => {
      this.periods = res.data;
    });
  },
  methods: {
    addAvailability() {
      if(this.period.date1 > this.period.date2){
        alert("Lose uneti datumi");
      }else {
        CottageService.AddAvailablePeriod({
          "entityId":this.id,
          "fromDate":this.period.date1,
          "toDate":this.period.date2
        }).then(response=>{
          CottageService.getAvailablePeriodsOfCottage(this.id).then(res => {
            this.periods = res.data;
          });
          this.response = response;
        });
      }

    },

    goBack(){
      this.$router.push('/usersCottage');
    }
  }
}
</script>

<style scoped>

</style>