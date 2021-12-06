<template>
  <div class="container">
    <!--FILTRIRANJE-->
    <form @submit.prevent="filtering">
      <table style="margin: 10px">
        <tr>
          <td>SEARCH: </td>
          <td><input type="text" placeholder="NAME" v-model="filteringName"/></td>
          <td><input type="number" placeholder="MAX PERSONS FROM" v-model="filteringPersonsFrom"/></td>
          <td><input type="number" placeholder="MAX PERSONS TO" v-model="filteringPersonsTo"/></td>
          <td><button type="submit" class="btn btn-sm btn-outline-dark">SEARCH</button></td>
        </tr>
      </table>
    </form>
    <div class="myClass" v-for="adventure in adventuresToShow" :key="adventure.id"
         @click="openAdventurePage(adventure)">
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
  </div>
</template>

<script>
import AdventureService from "@/Services/AdventureService";
import FishingInstructorService from "@/Services/FishingInstructorService";
import LogInService from "@/Services/LogInService";

export default {
  data(){
    return{
      allAdventures: '',
      adventuresToShow: '',
      user:'',
      AdventureService,
      filteringName: '',
      filteringPersonsFrom: '',
      filteringPersonsTo: ''
    }
  },
  //avanture i user
  mounted() {
    if(LogInService.userId === ''){
      this.$router.push('/login');
      return;
    }
    FishingInstructorService.getFishingInstructorById(LogInService.userId).then(res => {this.user = res.data}).catch(
        () => alert("THERE IS ERROR IN LOADING USER")
    );
    AdventureService.getAllAdventuresOfFishingInstructor(LogInService.userId).then(res => {this.allAdventures = res.data;
    this.adventuresToShow = res.data;}).catch(() => {alert("THERE IS SOME ERROR IN LOADING ADVENTURES")});
  },
  methods: {
  openAdventurePage(adventure){
    AdventureService.setAdventureId(adventure.id);
    this.$router.push('/adventureProfile');
  },
  filtering(){
    let filteredAdventures = []
    for(let adventure of this.allAdventures)
      if((adventure.name.toLowerCase().includes(this.filteringName.toLowerCase())) &&
          (adventure.maxPersons >= this.filteringPersonsFrom || this.filteringPersonsFrom === '') &&
          (adventure.maxPersons <= this.filteringPersonsTo || this.filteringPersonsTo === ''))
        filteredAdventures.push(adventure);
    this.adventuresToShow = filteredAdventures;
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