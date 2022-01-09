<template>
  <div>
    <b-card v-for="action in this.actions" :key="action.id" bg-variant="dark" text-variant="white">
      <b-card-title>
        {{action.boat.name}}
      </b-card-title>
      <b-card-sub-title style="margin: 5px">
        Time: {{action.reservationStart | formatDate}} - {{action.reservationEnd | formatDate}}
      </b-card-sub-title>
      <b-card-sub-title style="margin: 5px">
        Maximum persons: {{action.boat.capacity}}
      </b-card-sub-title>
      <b-card-text style="margin: 5px">
        Promo: {{action.boat.promo}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        RULES: {{action.boat.conductRules}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Address: {{action.boat.address.country}}, {{action.boat.address.city}}, {{action.boat.address.street}}
      </b-card-text>
      <b-card-text style="margin: 5px; font-size: 20px">
        PRICE: {{action.price}} Discount: {{action.discount}} % Old price: {{action.price * 100 / (100 - action.discount)}}
      </b-card-text>
      <br>
      <b-button v-if="LogInService.userRole.trim() === 'ROLE_CUSTOMER'" @click="reserve(action.id)" variant="primary">Reserve</b-button>
    </b-card>
  </div>
</template>

<script>
import LogInService from "@/Services/LogInService";
import BoatReservationService from "@/Services/BoatReservationService";
export default {
  name: "BoatActions",
  data(){
    return{
      LogInService,
      id: '',
      actions: [],
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    BoatReservationService.getAllNextActionsOfBoat(this.id).then((res) =>{
      console.log(res.data)
      this.actions = res.data;
    });
  },
  methods:{
    reserve(id){
      alert("Please wait a moment...");
      BoatReservationService.ReserveFastReservation(id).then(() => {
        alert("Reservation created");
        this.$router.push('/');
      }).catch(() => {
        alert("You can't reserve this boat");
      })
    }
  }
}
</script>

<style scoped>

</style>
