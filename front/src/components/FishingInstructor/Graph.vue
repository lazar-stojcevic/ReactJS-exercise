<template>
<div class="card">
  <div class="card-body">
    <h3>ENTER TIMESPAN</h3>
    <form @submit.prevent="sendRequest">
      <div class="input-group mb-lg-2">
        <span class="input-group-text">FROM DATE</span>
        <input type="date" class="form-control" v-model="request.startTime" required/>
      </div>
      <div class="input-group mb-lg-2">
        <span class="input-group-text">TO DATE</span>
        <input type="date" class="form-control" v-model="request.endTime" required/>
      </div>
      <div class="input-group mb-3">
        <div class="btn-group-sm">
          <button type="submit" class="btn-info">CONFIRM</button>
        </div>
      </div>
    </form>
  </div>
  <div class="card-footer">
    <h3>GRAPH</h3>
    <!--line-chart-->
    <column-chart :data="chartData"/>
  </div>
</div>
</template>

<script>
import LogInService from "@/Services/LogInService";
import AdventureReservationService from "@/Services/AdventureReservationService";

export default {
  name: "Graph",
  data(){
    return{
      request:{
        startTime: '',
        endTime: '',
        instructorId: ''
      },
      chartData: [
        ["Jan", 4],
        ["Feb", 2]
      ]
    }
  },

  methods:{
    sendRequest(){
      if(this.request.startTime > this.request.endTime){
        alert("REQUEST FROM TIME MUST BE IN THE PAST OF TO TIME");
        return;
      }
      this.request.startTime = this.request.startTime + ' 00:00';
      this.request.endTime = this.request.endTime + ' 00:00';
      this.request.instructorId = LogInService.userId

      AdventureReservationService.getAllReservationForGraph(this.request).then(res => {
        this.makeDataForGraphPresentation(res.data);
      }).catch(() =>{alert("THERE IS SOME ERROR WITH LOADING GRAPH DATA")});
    },

    clearRequest(){
      this.request = { startTime: '', endTime: '', instructorId: '' }
    },

    makeDataForGraphPresentation(data){
      let chartData = [];
      for(let d of data){
        let oneChart = [];
        let date = d.date[0] + '-' + d.date[1] + '-' + d.date[2];
        oneChart.push(date.toString());
        oneChart.push(d.number);
        chartData.push(oneChart);
      }
      this.chartData = chartData;
    }
  }
}
</script>

<style scoped>

</style>