<template>
 <div class="container">
   <!--BRISANJE I KREIRANJE AKCIJA ZA BRZEREZERVACIJE-->
   <table style="margin: 10px">
     <tr>
       <td>
         <button class="btn-sm btn-danger" @click="deleteAdventure">DELETE ADVENTURE</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="makeFastReservation">MAKE FAST RESERVATION</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="makeCustomReservation">MAKE CUSTOM RESERVATION</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="changeAdventure">CHANGE ADVENTURE</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="goToMyAdventures">GO TO MY ADVENTURES</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="addPriceList">ADD ADDITIONAL SERVICES</button>
       </td>
     </tr>
   </table>
   <!--ADDING ADDITIONAL SERVICES-->
   <div v-if="mode === 'priceList'">
     <form @submit.prevent="addAdditionalServices">
       <div class="input-group mb-lg-2">
         <span class="input-group-text">NAME OF SERVICE</span>
         <input type="text" class="form-control" v-model="addService.name">

         <span class="input-group-text">PRICE</span>
         <input type="number" class="form-control" v-model="addService.addPrice">
       </div>
       <div class="input-group mb-lg-2">
         <div class="btn-group-sm">
           <button type="reset" class="btn btn-danger" @click="changeModeToNeutral">CANCEL</button>
           <button type="submit" class="btn btn-success">SUBMIT</button>
         </div>
       </div>
     </form>
   </div>
   <!--ADVENTURE DETAILS-->
   <hr/>
   <h1><strong>ADVENTURE DETAILS</strong></h1>
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
     <tr>
       <td>BASE PRICE</td>
       <td>{{adventure.priceList.price}}</td>
     </tr>
     </tbody>
   </table>
   <hr/>
   <div v-if="additionalServices.length > 0">
     <h1><strong>ADDITIONAL SERVICES</strong></h1>
     <table class="table table-striped">
       <thead>
       <tr>
         <td>NAME</td>
         <td>ADDITIONAL PRICE</td>
       </tr>
       </thead>
       <tbody>
       <tr v-for="as in additionalServices" :key="as.id">
         <td>{{ as.name }}</td>
         <td>{{as.addPrice}}</td>
       </tr>
       </tbody>
     </table>
   </div>
   <!--FREE FAST RESERVATIONS-->
   <div v-if="fastReservations.length !== 0">
     <h2 style="margin-top: 10px"><strong>FREE FAST RESERVATIONS</strong></h2>
     <table class="table table-striped" style="margin-top: 10px">
       <thead>
       <tr>
         <td>START AT</td>
         <td>DURATION IN HOURS</td>
         <td>PRICE</td>
       </tr>
       </thead>
       <tbody>
       <tr v-for="fr in fastReservations" :key="fr.id">
         <td>{{fr.reservationStart}}</td>
         <td>{{fr.length}}</td>
         <td>{{fr.price}}</td>
       </tr>
       </tbody>
     </table>
   </div>
   <div v-else> THERE IS NO FREE FAST RESERVATIONS</div>
   <!--RESERVED TERMS-->
   <!--OVO TREBA DA VIDE SAMO INSTRUKTORI-->
   <hr style="margin-top: 10px"/>
   <div v-if="LogInService.userRole ==='ROLE_INSTRUCTOR'">
     <div v-if="reservedTerms.length !== 0">
       <h2><strong>RESERVED TERMS</strong></h2>
       <table class="table table-striped">
       <thead>
       <tr>
         <td>START AT</td>
         <td>DURATION IN HOURS</td>
         <td>PRICE</td>
       </tr>
       </thead>
       <tbody>
       <tr v-for="fr in reservedTerms" :key="fr.id">
         <td>{{fr.reservationStart}}</td>
         <td>{{fr.length}}</td>
         <td>{{fr.price}}</td>
         <!--USERNAME ONOGA KO JE REZERVISAO-->
       </tr>
       </tbody>
       </table>
     </div>
     <div v-else>THERE IS NO RESERVED TERMS</div>
     </div>
 </div>
</template>

<script>
import AdventureService from "@/Services/AdventureService";
import AdventureReservationService from "@/Services/AdventureReservationService";
import LogInService from "@/Services/LogInService";
export default {
  data(){
    return{
      LogInService,
      AdventureService,
      adventure: { address: { street: ''}, priceList:''},
      fastReservations: '',
      reservedTerms: '',
      mode: 'neutral',
      addService: { name: '', addPrice: ''},
      additionalServices: []
    }
  },
  mounted() {
    if(this.AdventureService.getAdventureId() === ''){
      this.$router.push('/fishingInstructorProfile');
      return;
    }
    AdventureService.getAdventureById(AdventureService.getAdventureId()).then(res => {this.adventure = res.data})
        .catch(() => { alert("THERE IS SOME ERROR IN LOADING ADVENTURE")});

    AdventureReservationService.getAllFreeFastReservations(AdventureService.getAdventureId()).then(res => {
      this.fastReservations = res.data;
    }).catch(() => {alert("THERE IS SOME ERROR IN LOADING FREE FAST RESERVATIONS")});

    AdventureReservationService.getAllReservedTerms(AdventureService.getAdventureId()).then(res => {
      this.reservedTerms = res.data;
    }).catch(() => {alert("THERE IS SOME PROBLEM WITH LOADING RESERVED TERMS")});
    AdventureService.getAdditionalServicesOfAdventure(AdventureService.getAdventureId()).then(res => {this.additionalServices = res.data})
    .catch(() => {alert("THERE IS SOME ERROR WITH LOADING ADDITIONAL SERVICES")})
  },
  methods:{
    deleteAdventure(){
      AdventureService.deleteAdventure(this.adventure.id).then(() => this.$router.push('/myAdventures')).catch(() =>
      { alert("THERE IS SOME PROBLEM WITH DELETING")});
    },
    makeFastReservation(){

    },
    changeAdventure(){

    },
    makeCustomReservation(){

    },
    goToMyAdventures(){
      this.$router.push('/myAdventures');
    },
    addPriceList(){
      this.mode = 'priceList'
      this.addService = {name: '', addPrice: ''};
    },
    changeModeToNeutral(){
      this.mode = 'neutral'
    },
    addAdditionalServices(){
      if(this.addService.addPrice === '' || this.addService.name === ''){
        alert("PRICE AND NAME MUST BE FILLED")
        return;
      }
      AdventureService.addAdditionalServices(this.addService, this.adventure.id).then(() => {
        this.restartAdditionalService();
      }).catch(() => {alert("THERE IS SOME PROBLEM WITH ADDING ADDITIONAL SERVICES")});
    },
    restartAdditionalService(){
      AdventureService.getAdditionalServicesOfAdventure(AdventureService.getAdventureId()).then(res => {
        this.additionalServices = res.data;
      }).catch(() => {alert("THERE IS SOME PROBLEM WITH LOADING ADDITIONAL SERVICES")});
      this.addService = {name: '', addPrice: ''};
    }
  }
}
</script>

<style scoped>

</style>