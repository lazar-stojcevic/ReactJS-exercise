<template>
  <div class="container w-100">
    <br>
    <h2 style="color: darkred">Subscriptions:</h2>
    <h3 style="color: darkred; margin-left: 20px; margin-top: 20px;">Fishing instructors: </h3>
    <div style="margin-left: 20px" v-for="instructor in instructors" :key="instructor.id">
      <b-card
          name="instructor"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{instructor.firstname}} {{instructor.lastName}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Email: {{instructor.email}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Phone number: {{instructor.phone}}
        </b-card-sub-title>
        <br>
        <b-button  variant="danger" v-on:click="unsubscribe(instructor.id)">Unsubscribe</b-button>
      </b-card>
    </div>
    <br>
    <h3 style="color: darkred; margin-left: 20px; margin-top: 20px;">Cottages: </h3>
    <div style="margin-left: 20px" v-for="cottage in cottages" :key="cottage.id">
      <b-card
          name="instructor"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{cottage.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Promo: {{cottage.promo}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Address: {{cottage.address.country}}, {{cottage.address.city}}, {{cottage.address.street}}
        </b-card-sub-title>
        <br>
        <b-button  variant="danger" v-on:click="unsubscribeCottage(cottage.id)">Unsubscribe</b-button>
      </b-card>
    </div>
    <br>
    <h3 style="color: darkred; margin-left: 20px; margin-top: 20px;">Boats: </h3>
    <div style="margin-left: 20px" v-for="boat in boats" :key="boat.id">
      <b-card
          name="instructor"
          style="max-width: 20rem;"
          class="mb-2"
      >
        <b-card-title>
          {{boat.name}}
        </b-card-title>
        <br>
        <b-card-sub-title>
          Promo: {{boat.promo}}
        </b-card-sub-title>
        <br>
        <b-card-sub-title>
          Address: {{boat.address.country}}, {{boat.address.city}}, {{boat.address.street}}
        </b-card-sub-title>
        <br>
        <b-button  variant="danger" v-on:click="unsubscribeBoat(boat.id)">Unsubscribe</b-button>
      </b-card>
    </div>
  </div>
</template>

<script>
import CustomerService from "@/Services/CustomerService";
import LogInService from "@/Services/LogInService";
import FishingInstructorService from "@/Services/FishingInstructorService";
import CottageService from "@/Services/CottageService";
import BoatService from "@/Services/BoatService";
export default {
  name: "Subscriptions",
  data() {
    return {
      instructors : [],
      cottages: [],
      boats: [],
    }
  },
  mounted() {
    CustomerService.getAllCustomerInstructorsSubscriptions(LogInService.userId).then((res) =>{
      this.instructors = res.data;
    }).then(() =>{
      CustomerService.getAllCustomerCottagesSubscriptions(LogInService.userId).then((res) =>{
        this.cottages = res.data;
      }).then(() => {
        CustomerService.getAllCustomerBoatsSubscriptions(LogInService.userId).then((res) =>{
          this.boats = res.data;
        })
      })
    })
  },
  methods:{
    unsubscribe(instructorId){
      FishingInstructorService.unsubscribeInstructor(LogInService.userId, instructorId);
      this.$router.push("/");
    },
    unsubscribeCottage(cottageId){
      CottageService.unsubscribeCottage(LogInService.userId, cottageId);
      this.$router.push("/");
    },
    unsubscribeBoat(cottageId){
      BoatService.unsubscribeBoat(LogInService.userId, cottageId);
      this.$router.push("/");
    }
  }
}
</script>

<style scoped>

</style>
