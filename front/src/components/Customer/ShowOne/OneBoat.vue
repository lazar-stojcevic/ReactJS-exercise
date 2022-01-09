<template>
  <div style="margin-left: 10px">
    <b-button v-if="this.subcribed && LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="danger" v-on:click="unsubscribe">Unsubscribe</b-button>
    <b-button v-else-if="LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="success" v-on:click="subscribe">Subscribe</b-button>
    <br>
    <h3 style="color: aqua">Name: {{boat.name}}</h3>
    <br>
    <h4 style="color:cornflowerblue">Type: {{boat.type}}</h4>
    <br>
    <h5 style="color:cornflowerblue">Number of engines: {{boat.numberOfEngines}} </h5>
    <br>
    <h5 style="color:cornflowerblue">Top speed: {{boat.topSpeed}} </h5>
    <br>
    <h5 style="color:cornflowerblue">Description: {{boat.promo}} </h5>
    <br>
    <h5 style="color:cornflowerblue">Rules: {{boat.conductRules}} </h5>
    <br>
    <h5 style="color:cornflowerblue">Max capacity: {{boat.capacity}} </h5>
    <br>
    <h5 style="color:cornflowerblue">Address: {{boat.address.country}}, {{boat.address.city}}, {{boat.address.street}} </h5>
    <br>
    <br>
    <h3 style="color:green">Price per day: {{boat.priceList.price}} </h3>
    <br>
    <h3 style="color:cornflowerblue"> Additional services:</h3>
    <div>
      <h4 style="margin-left: 15px; color: chartreuse" v-for="service in this.services " :key="service.id"> {{ service.name }} - {{service.addPrice}}</h4>
    </div>
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
      boat: '',
      services: '',
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    BoatService.getOneBoat(this.id).then((res) => {
      console.log(res.data)
      this.boat = res.data;
    });
    BoatService.getAdditionalServicesOfBoat(this.id).then((res) => {
      this.services = res.data;
    });
  },
  methods:{
    subscribe(){
      BoatService.subscribeBoat(LogInService.userId, this.id);
      this.subcribed = true;
    },
    unsubscribe(){
      BoatService.unsubscribeBoat(LogInService.userId, this.id);
      this.subcribed = false;
    }
  }
}
</script>

<style scoped>

</style>
