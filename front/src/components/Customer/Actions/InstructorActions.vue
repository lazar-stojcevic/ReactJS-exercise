<template>
  <div>
    <b-card v-for="action in this.actions" :key="action.id" bg-variant="dark" text-variant="white">
      <b-card-title>
        {{action.adventure.name}}
      </b-card-title>
      <b-card-sub-title style="margin: 5px">
        Time of start: {{action.reservationStart}}
      </b-card-sub-title>
      <b-card-sub-title style="margin: 5px">
        Maximum persons: {{action.adventure.maxPersons}}
      </b-card-sub-title>
      <b-card-text style="margin: 5px">
        Description: {{action.adventure.description}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        Equipment: {{action.adventure.equipment}}
      </b-card-text>
      <b-card-text style="margin: 5px">
        RULES: {{action.adventure.conductRules}}
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
import AdventureReservationService from "@/Services/AdventureReservationService";
import LogInService from "@/Services/LogInService";
import moment from "moment";
export default {
  name: "InstructorActions",
  data(){
    return{
      LogInService,
      id: '',
      actions: [],
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    AdventureReservationService.getAllNextActionsOfInstructor(this.id).then((res) =>{
      this.actions = res.data;
    });
  },
  methods:{
    search(){
      if (this.sort === 'dateASC')
        this.actions.sort((a,b) =>
            (moment(a.reservationStart).format("dd-mm-yyyy") > moment(b.reservationStart).format("dd-mm-yyyy"))
                ? 1 : ((moment(b.reservationStart).format("dd-mm-yyyy") > moment(a.reservationStart).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'dateDESC')
        this.actions.sort((a,b) =>
            (moment(a.reservationStart).format("dd-mm-yyyy") < moment(b.reservationStart).format("dd-mm-yyyy"))
                ? 1 : ((moment(b.reservationStart).format("dd-mm-yyyy") < moment(a.reservationStart).format("dd-mm-yyyy")) ? -1 : 0))
      else if (this.sort === 'priceASC')
        this.actions.sort((a,b) => (a.price > b.price) ? 1 : ((b.price > a.price) ? -1 : 0))
      else if (this.sort === 'priceDESC')
        this.actions.sort((a,b) => (a.price < b.price) ? 1 : ((b.price < a.price) ? -1 : 0))
      else if (this.sort === 'lengthASC')
        this.actions.sort((a,b) => (a.length > b.length) ? 1 : ((b.length > a.length) ? -1 : 0))
      else if (this.sort === 'lengthDESC')
        this.actions.sort((a,b) => (a.length < b.length) ? 1 : ((b.length < a.length) ? -1 : 0))
    },
    reserve(id){
      AdventureReservationService.reserveAdventureOnAction(id).then(() => {
        alert("Reservation created");
        this.$router.push('/');
      })
    }
  }
}
</script>

<style scoped>

</style>
