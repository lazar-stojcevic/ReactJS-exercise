<template>
  <div>
    <b-button v-if="this.subcribed && LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="danger" v-on:click="unsubscribe">Unsubscribe</b-button>
    <b-button v-else-if="LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="success" v-on:click="subscribe">
      Subscribe
    </b-button>
    <br>
    <h1>Name: {{cottage.name}}</h1>
    <br>
    <h2>Rules: {{cottage.conductRules}} </h2>
    <br>
    <h3>Address: {{cottage.address.street}},{{cottage.address.city}},{{cottage.address.country}} </h3>
    <br>
    <p>Description: {{cottage.promo}} </p>
    <br>
    <h3>Rating: {{cottage.rating}} </h3>
    <br>
    <router-link class="btn btn-success" :to='"/cottageActions/" + this.id'>ACTIONS</router-link>

    <div style="margin-top: 20px; background-color: beige">
      <FullCalendar :options="calendarOptions"/>
    </div>
  </div>
</template>

<script>
import CottageService from "@/Services/CottageService";
import LogInService from "@/Services/LogInService";
import FullCalendar from "@fullcalendar/vue";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import CottageReservationService from "../../../Services/CottageReservationService";
export default {
  name: "OneCottage",
  components: {
    FullCalendar // make the <FullCalendar> tag available
  },
  data(){
    return{
      LogInService,
      subcribed : false,
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
      id: '',
      cottage: {
        name: '',
        conductRules: '',
        address: {
          street: '',
          city: '',
          country: ''
        },
        promo: '',
        rating: 0,
      },
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    CottageService.getCottageById(this.id).then(res => {
      this.cottage = res.data;
    }).then(() => {
          if (LogInService.userId !== ''){
            CottageService.IsUserSubscribed(LogInService.userId, this.id).
            then(res => {
              this.subcribed = res.data;
            }).catch(() => alert("Some error ocured!"))
          }
    });
    CottageReservationService.getAllReservationsOfCottageForCalendar(this.id).then(res => {
      this.calendarOptions.events = res.data;
    })
  },
  methods:{
    subscribe(){
      CottageService.subscribeCottage(LogInService.userId, this.id);
      this.subcribed = true;
    },
    unsubscribe(){
      CottageService.unsubscribeCottage(LogInService.userId, this.id);
      this.subcribed = false;
    },
    handleEventClick(){
    }
  }
}
</script>

<style scoped>

</style>
