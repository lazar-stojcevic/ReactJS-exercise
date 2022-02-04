<template>
  <div>
    <b-form inline style="display: flex" @submit.prevent="searchReservations">
  <div class="input-group mb-3">
    <span class="input-group-text">BEGINNING OF RESERVATION PERIOD</span>
    <input type="datetime-local" class="form-control" v-model="period.date1">
  </div>
  <div class="input-group mb-3">
    <span class="input-group-text">END OF RESERVATION PERIOD</span>
    <input type="datetime-local" class="form-control" v-model="period.date2">
  </div>
      <button type="submit" class="btn-info">Search</button>
    </b-form>

    <br>
    <h3>Income for period: {{money}}</h3>
    <br>

    <br>
    <h3>Cottage reservations</h3>
    <br>
    <br>
    <table class="table table-striped">
      <thead>
      <tr>
        <td>COTTAGE</td>
        <td>FROM DATE</td>
        <td>TO DATE</td>
        <td>PRICE</td>
      </tr>
      </thead>
      <tbody>
      <tr v-for="reservation in reservations" :key="reservation.id">
        <td>{{reservation.cottage.name}}</td>
        <td>{{reservation.reservationStart}}</td>
        <td>{{reservation.reservationEnd}}</td>
        <td>{{reservation.price}}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import LogInService from "../../Services/LogInService";
import IncomeService from "../../Services/IncomeService";

export default {
  name: "EarningsReportCottage",
  data(){
    return{
      reservations:[],
      money: '',
      period: {
        date1: Date.now(),
        date2:Date.now()
      }
    }
  },
  methods: {
    searchReservations() {
      if(this.period.date1 > this.period.date2){
        alert("Lose uneti datumi");
      }else {
        IncomeService.getCottageOwnerIncome({
          "startTime": this.period.date1,
          "endTime": this.period.date2,
          "ownerId": LogInService.userId,

        }).then(res => {
          this.reservations = res.data.reservationList;
          this.money = res.data.price;

        });
      }

    }
  }
}
</script>

<style scoped>

</style>