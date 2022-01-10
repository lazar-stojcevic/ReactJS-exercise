<template>
  <div>
    <div class="search">
      <b-input-group prepend="Adventure name" class="mb-2 mt-2">
        <b-form-input aria-label="Adventure name" v-model="filter.name"></b-form-input>
      </b-input-group>
      <b-input-group prepend="Location" class="mb-2 mt-2 m-lg-2">
        <b-form-input aria-label="Adventure location" v-model="filter.location"></b-form-input>
      </b-input-group>
      <b-form-select v-model="sort" class="m-3">
        <b-form-select-option value="nameASC">name descending</b-form-select-option>
        <b-form-select-option value="nameDESC">name ascending</b-form-select-option>
        <b-form-select-option value="cityASC">city descending</b-form-select-option>
        <b-form-select-option value="cityDESC">city ascending</b-form-select-option>
      </b-form-select>
      <b-input-group class="justify-content-center">
        <b-button variant="info" @click="search">Search</b-button>
      </b-input-group>
    </div>
    <br>
    <b-button v-if="this.subcribed && LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="danger" v-on:click="unsubscribe">Unsubscribe</b-button>
    <b-button v-else-if="LogInService.getRole() === 'ROLE_CUSTOMER'" style="margin-left: 20px; margin-top: 10px" variant="success" v-on:click="subscribe">
      Subscribe
    </b-button>
    <br>
    <br>
  <div class="myClass" v-for="adventure in filtered" :key="adventure.id">
    <strong>{{adventure.name}}</strong>
    <table style="margin: 10px">
      <tr style="height: 20px"><td></td></tr>
      <tr>
        <td class="mainCols">MAXIMUM PERSONS: </td>
        <td>{{adventure.maxPersons}}</td>
      </tr>
      <tr>
        <td class="mainCols">ADDRESS: </td>
        <td>{{adventure.address.country}}, {{adventure.address.city}}, {{adventure.address.street}}</td>
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
      adventures: [],
      filtered: [],
      filter: {
        name:'',
        location:'',
        mark : 0
      },
      sort : 'nameASC'
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    FishingInstructorService.getFishingInstructorById(this.id).then(res => {
      this.user = res.data;
    }).then(() => {
    AdventureService.getAllAdventuresOfFishingInstructor(this.id).then(res =>{
      this.adventures = res.data;
      this.filtered = res.data;
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
    },
    search(){
      this.filtered = [];
      for (let adventure of this.adventures){
        if ((adventure.name.includes(this.filter.name)) && (adventure.address.city.includes(this.filter.location) ||
                adventure.address.country.includes(this.filter.location) || adventure.address.street.includes(this.filter.location))){
          this.filtered.push(adventure)
        }
      }
      if (this.sort === 'nameDESC')
        this.filtered.sort((a,b) => (a.name > b.name) ? 1 : ((b.name > a.name) ? -1 : 0))
      else if (this.sort === 'nameASC')
        this.filtered.sort((a,b) => (a.name < b.name) ? 1 : ((b.name < a.name) ? -1 : 0))

      else if (this.sort === 'cityDESC')
        this.filtered.sort((a,b) => (a.address.city > b.address.city) ? 1
            : ((b.address.city > a.address.city) ? -1 : 0))

      else if (this.sort === 'cityASC')
        this.filtered.sort((a,b) => (a.address.city < b.address.city) ? 1
            : ((b.address.city < a.address.city) ? -1 : 0))
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

.search{
  display: flex;
}
</style>
