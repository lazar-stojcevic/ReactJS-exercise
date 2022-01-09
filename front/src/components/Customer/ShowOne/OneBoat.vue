<template>
  <div style="margin-left: 10px">
    <b-button v-if="this.subcribed && LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="danger" v-on:click="unsubscribe">Unsubscribe</b-button>
    <b-button v-else-if="LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="success" v-on:click="subscribe">Subscribe</b-button>
    <br>
    <h2 style="color: aqua">Name: {{boat.name}}</h2>
    <br>
    <h3 style="color:cornflowerblue">Type: {{boat.type}}</h3>
    <br>
    <h4 style="color:cornflowerblue">Number of engines: {{boat.numberOfEngines}} </h4>
    <br>
    <h4 style="color:cornflowerblue">Top speed: {{boat.topSpeed}} </h4>
    <br>
    <h4 style="color:cornflowerblue">Description: {{boat.promo}} </h4>
    <br>
    <h4 style="color:cornflowerblue">Rules: {{boat.conductRules}} </h4>
    <br>
    <h4 style="color:cornflowerblue">Max capacity: {{boat.capacity}} </h4>
    <br>
    <br>
    <router-link class="btn btn-success" :to='"/boatActions/" + this.id'>ACTIONS</router-link>
  </div>
</template>

<script>
import LogInService from "@/Services/LogInService";
import BoatService from "@/Services/BoatService";
export default {
  name: "OneBoat",
  data(){
    return {
      LogInService,
      id: '',
      subcribed: false,
      boat: ''
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    BoatService.getOneBoat(this.id).then((res) => {
      this.boat = res.data;
    })
  },
  methods:{
    subscribe(){

    },
    unsubscribe(){

    }
  }
}
</script>

<style scoped>

</style>
