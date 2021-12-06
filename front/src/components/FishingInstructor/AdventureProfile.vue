<template>
 <div class="container">
   <!--BRISANJE I KREIRANJE AKCIJA ZA BRZEREZERVACIJE-->
   <table style="margin: 10px">
     <tr>
       <td>
         <button class="btn btn-danger" @click="deleteAdventure">DELETE ADVENTURE</button>
       </td>
       <td>
         <button class="btn btn-primary" @click="makeFastReservation">MAKE FAST RESERVATION</button>
       </td>
       <td>
         <button class="btn btn-primary" @click="makeCustomReservation">MAKE CUSTOM RESERVATION</button>
       </td>
       <td>
         <button class="btn btn-primary" @click="changeAdventure">CHANGE ADVENTURE</button>
       </td>
       <td>
         <button class="btn btn-primary" @click="goToMyAdventures">GO TO MY ADVENTURES</button>
       </td>
     </tr>
   </table>
   <!--ADVENTURE DETAILS-->
   <table class="table table-striped" style="margin: 15px 0 0 0">
     <tbody>
     <tr>
       <td>ADVENTURE NAME</td>
       <td>{{adventure.name}}</td>
     </tr>
     <tr>
       <td>FULL ADDRESS</td>
       <td>{{adventure.address.street}} {{adventure.address.city}} {{adventure.address.country}}</td>
     </tr>
     <tr>
       <td>MAX PERSONS</td>
       <td>{{adventure.maxPersons}}</td>
     </tr>
     <tr>
       <td>ADVENTURE DESCRIPTION</td>
       <td>{{adventure.description}}</td>
     </tr>
     <tr>
       <td>INSTRUCTOR BIOGRAPHY</td>
       <td>{{adventure.instructorBiography}}</td>
     </tr>
     <tr>
       <td>EQUIPMENTS</td>
       <td>{{adventure.equipment}}</td>
     </tr>
     <tr>
       <td>CANCELING TERMS</td>
       <td>{{adventure.cancelingTerms}}</td>
     </tr>
     <tr>
       <td>CONDUCT RULES</td>
       <td>{{adventure.conductRules}}</td>
     </tr>
     </tbody>
   </table>
   <!--FREE FAST RESERVATIONS-->
   <!--RESERVED TERMS-->
 </div>
</template>

<script>
import AdventureService from "@/Services/AdventureService";

export default {
  data(){
    return{
      AdventureService,
      adventure: {
        address: {
          street: ''
        }
      }
    }
  },
  mounted() {
    if(this.AdventureService.getAdventureId() === ''){
      this.$router.push('/fishingInstructorProfile');
      return;
    }
    AdventureService.getAdventureById(AdventureService.getAdventureId()).then(res => {this.adventure = res.data})
        .catch(() => { alert("THERE IS SOME ERROR IN LOADING ADVENTURE")});
  },
  methods:{
    deleteAdventure(){

    },
    makeFastReservation(){

    },
    changeAdventure(){

    },
    makeCustomReservation(){

    },
    goToMyAdventures(){
      this.$router.push('/myAdventures');
    }
  }
}
</script>

<style scoped>

</style>