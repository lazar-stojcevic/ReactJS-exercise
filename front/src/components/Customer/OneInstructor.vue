<template>
  <div>
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
  </div>
</template>

<script>
import FishingInstructorService from "@/Services/FishingInstructorService";
import AdventureService from "@/Services/AdventureService";
export default {
  name: "OneInstructor",
  data(){
    return{
      id: '',
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
    })
    });
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
