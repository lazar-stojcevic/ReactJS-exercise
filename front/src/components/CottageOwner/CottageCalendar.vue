<template>
  <div>
    <div v-if="mode === 'report'" class="card">
      <div class="card-body">
        <h3 style="text-align: center">PLEASE ENTER REPORT</h3>
        <form @submit.prevent="saveReport">
          <div class="input-group mb-3">
            <span class="input-group-text">COMMENT</span>
            <input type="text" class="form-control" v-model="report.comment">
          </div>
          <div class="form-group form-check">
            <input type="checkbox" v-model="report.customerAppear" id="customerAppear"/>
            <label class="form-check-label" for="customerAppear">IS CUSTOMER APPEAR</label>
          </div>
          <div class="form-group form-check">
            <input type="checkbox" v-model="report.badReport" id="badReport">
            <label class="form-check-label" for="badReport">IS BAD REPORT</label>
          </div>
          <div class="input-group mb-3" style="margin-top: 10px">
            <div>
              <button type="submit" class="btn btn-success">CONFIRM</button>
              <button @click="changeModeToNeutral" type="reset" class="btn btn-warning">CLOSE</button>
            </div>
          </div>
        </form>
      </div>
    </div>

    <div style="margin-top: 20px; background-color: beige">
      <FullCalendar :options="calendarOptions"/>
    </div>
  </div>
</template>

<script>
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import CottageReservationService from "../../Services/CottageReservationService";
import ReportService from "../../Services/ReportService";
import LogInService from "../../Services/LogInService";

export default {
  name: "CottageCalendar",
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data() {
    return {
      calendarOptions: {
        plugins: [ dayGridPlugin, interactionPlugin, timeGridPlugin ],
        initialView: 'dayGridMonth',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [],
        eventClick: this.handleEventClick
      },
      report: {comment: '', reservationType: 'AR', badReport: false, customerAppear: true, reservationId: ''},
      mode: 'neutral'
    }
  },
  mounted() {
    CottageReservationService.getAllReservationsOfOwnerForCalendar(LogInService.userId).then(res => {
      this.calendarOptions.events = res.data;
    })
  },
  methods: {
    handleEventClick(arg){
      let title = arg.event.title;
      if(title === 'NOT RESERVED'){
        //  alert("ADVENTURE MUST BE RESERVED FOR MAKING REPORT");
        //  return;
      }
      if(new Date() < arg.event.end){
        alert("THIS COTTAGE RESERVATION IS NOT ABLE TO BE REPORTED, BECAUSE IT IS NOT STARTED");
        return;
      }
      let titleParts = title.toString().split("\n");
      //let customer = titleParts[0].split(" ")[1].trim();
      let reported = titleParts[2].split(" ")[1].trim();
      if(reported === 'true'){
        alert("THIS RESERVATION IS ALREADY REPORTED");
        return;
      }
      alert("PLEASE ENTER A REPORT, FORM IS UPPER");
      this.makeReport(arg.event.id);
    },

    makeReport(adventureId){
      this.report = {comment: '', reservationType: 'CR', badReport: false, customerAppear: true,
        reservationId: adventureId};
      this.mode = 'report';
    },

    saveReport(){
      ReportService.makeReport(this.report).then(() => {
        this.loadAllReservations();
      }).catch(() => {alert("THERE IS SOME ERRORS WITH MAKING REPORT")});
    },

    changeModeToNeutral(){
      this.mode = 'neutral';
    },

    loadAllReservations(){
      CottageReservationService.getAllReservationsOfOwnerForCalendar(LogInService.userId).then(res => {
        this.calendarOptions.events = res.data;
        this.changeModeToNeutral();
      }).catch(() => {
        alert("THERE IS SOME ERROR WITH LOADING RESERVATIONS FOR CALENDAR");
      })
    }


}
}
</script>

<style scoped>

</style>