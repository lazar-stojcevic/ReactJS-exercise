<template>
  <div>
    <b-button v-if="this.subcribed && LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="danger" v-on:click="unsubscribe">Unsubscribe</b-button>
    <b-button v-else-if="LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="success" v-on:click="subscribe">
      Subscribe
    </b-button>
    <br>
  <div class="myClass" v-for="adventure in adventures" :key="adventure.id">
    <strong>{{adventure.name}}</strong>
    <table style="margin: 10px">
      <tr style="height: 20px"><td></td></tr>
      <tr>
        <td class="mainCols">MAXIMUM PERSONS: </td>
        <td>{{adventure.maxPersons}}</td>
      </tr>
      <tr>
        <td class="mainCols">ADDRESS: </td>
        <td>{{adventure.address.street}} {{adventure.address.city}} {{adventure.address.country}}</td>
      </tr>
      <tr>
        <td class="mainCols">INSTRUCTOR: </td>
        <td>{{user.firstname}} {{user.lastName}}</td>
      </tr>
      <tr>
        <td class="mainCols">DESCRIPTION: </td>
        <td>{{adventure.description}}</td>
      </tr>
    </table>
  </div>
    <br>
    <router-link class="btn btn-success" :to='"/instructorActions/" + this.id'>ACTIONS</router-link>
  </div>
</template>

<script>
import FishingInstructorService from "@/Services/FishingInstructorService";
import AdventureService from "@/Services/AdventureService";
import LogInService from "@/Services/LogInService";
export default {
  name: "OneInstructor",
  data(){
    return{
      LogInService,
      id: '',
      subcribed : false,
      user: '',
      adventures: []
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    FishingInstructorService.getFishingInstructorById(this.id).then(res => {
      this.user = res.data;
    }).then(() => {
    AdventureService.getAllAdventuresOfFishingInstructor(this.id).then(res =>{
      this.adventures = res.data;
    }).then(() =>{
      if (LogInService.userId !== ''){
        FishingInstructorService.isCustomerSubscribedToInstructor(LogInService.userId, this.id).
        then(res => {
          this.subcribed = res.data;
        }).catch(() => alert("Some error ocured!"))
      }
    })
    });
  },
  methods:{
    subscribe(){
      FishingInstructorService.subscribeInstructor(LogInService.userId, this.id);
      this.subcribed = true;
    },
    unsubscribe(){
      FishingInstructorService.unsubscribeInstructor(LogInService.userId, this.id);
      this.subcribed = false;
    }
  }
}

</script>

<style scoped>
.myClass{
  margin: 10px;
  background: gainsboro;
  border: dotted 1px silver;
}
.myClass:hover{
  border-color: darkred;
  cursor: pointer;
}
.mainCols{
  font-weight: bolder;
}
</style>
