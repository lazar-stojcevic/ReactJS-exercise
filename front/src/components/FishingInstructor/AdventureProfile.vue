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
         <button class="btn-sm btn-primary" @click="showImage">MAKE CUSTOM RESERVATION</button>
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
       <td>
         <button class="btn-sm btn-primary" @click="addImage">ADD IMAGE</button>
       </td>
     </tr>
   </table>
   <!--DODAVANJE SLIKA-->
   <div v-if="mode === 'image'">
     <form @submit.prevent="saveImage">
       <div class="input-group mb-lg-2">
         <span class="input-group-text">IMAGE</span>
         <input type="file" class="form-control" @change="uploadImage" required/>
       </div>
       <div class="input-group mb-lg-2">
         <div class="btn-group-sm">
           <button type="reset" class="btn btn-danger" @click="changeModeToNeutral">CANCEL</button>
           <button type="submit" class="btn btn-success">SUBMIT</button>
         </div>
       </div>
     </form>
   </div>
   <!--CHANGE ADVENTURE-->
   <div v-if="mode === 'change'">
     <form style="padding: 10px" @submit.prevent="updateAdventure">
       <div class="input-group mb-lg-4">
         <span class="input-group-text">ADVENTURE NAME</span>
         <input type="text" class="form-control" v-model="newAdventure.name" required>
       </div>
       <div class="row" style="margin-bottom: 20px">
         <div class="col">
           <span class="input-group-text">STREET</span>
           <input type="text" class="form-control" v-model="newAdventure.address.street" required>
         </div>
         <div class="col">
           <span class="input-group-text">CITY</span>
           <input type="text" class="form-control" v-model="newAdventure.address.city" required>
         </div>
         <div class="col">
           <span class="input-group-text">COUNTRY</span>
           <input type="text" class="form-control" v-model="newAdventure.address.country" required>
         </div>
       </div>
       <div class="input-group mb-lg-4">
         <span class="input-group-text">DESCRIPTION</span>
         <input type="text" class="form-control" v-model="newAdventure.description" required>
       </div>
       <div class="input-group mb-lg-4">
         <span class="input-group-text">INSTRUCTOR BIOGRAPHY</span>
         <input type="text" class="form-control" v-model="newAdventure.instructorBiography" required>
       </div>
       <div class="input-group mb-lg-4">
         <span class="input-group-text">EQUIPMENTS(ADDITIONAL EQUIPMENTS IF CUSTOMER DOESNT HAVE ANY)</span>
         <input type="text" class="form-control" v-model="newAdventure.equipment" required>
       </div>
       <div class="input-group mb-lg-4">
         <span class="input-group-text">CANCELING TERMS</span>
         <input type="text" class="form-control" v-model="newAdventure.cancelingTerms" required>
       </div>
       <div class="input-group mb-lg-4">
         <span class="input-group-text">CONDUCT RULES</span>
         <input type="text" class="form-control" v-model="newAdventure.conductRules" required>
       </div>
       <div class="input-group mb-1">
         <span class="input-group-text">MAXIMUM PERSONS</span>
         <input type="number" class="form-control" v-model="newAdventure.maxPersons" required>
       </div>
       <div class="input-group mb-1">
         <span class="input-group-text">BASE PRICE</span>
         <input type="number" class="form-control" v-model="newAdventure.priceList.price" required>
       </div>
       <!--LOGIKA ZA POPUNJAVANJE CENOVNIKA-->
       <div class="input-group mb-lg-4">
         <div class="btn-group-sm">
           <button type="submit" class="btn-info">CONFIRM</button>
           <button type="reset" class="btn-danger" @click="changeModeToNeutral">CANCEL</button>
         </div>
       </div>
     </form>
   </div>
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
   <hr/>
   <!--ADVENTURE DETAILS-->
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
   <!--IMAGES-->
   <div>
     <table>
       <tr v-for="image in imagesToShow" :key="image">
         <td><img :src="image" width="200" height="200"/></td>
       </tr>
     </table>
   </div>
   <hr/>
   <!--ADDITIONAL SERVICES-->
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
     <hr/>
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
      additionalServices: [],
      newAdventure: {name: '', address:{street: ''}, priceList: {price: ''}},
      newImage: '',
      imagesToShow: []
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
    .catch(() => {alert("THERE IS SOME ERROR WITH LOADING ADDITIONAL SERVICES")});

    AdventureService.getAllImagesOfAdventure(AdventureService.adventureId).then(res => {
      for(let img of res.data){
        this.imagesToShow.push(img.image.replaceAll('"', ''));
      }
    })
    .catch(() => {alert("THERE IS SOME ERROR WITH LOADING IMAGES")});
  },
  methods:{
    deleteAdventure(){
      AdventureService.deleteAdventure(this.adventure.id).then(() => this.$router.push('/myAdventures')).catch(() =>
      { alert("THERE IS SOME PROBLEM WITH DELETING")});
    },
    makeFastReservation(){

    },
    changeAdventure(){
      this.mode = 'change';
      this.newAdventure = JSON.parse(JSON.stringify(this.adventure));
    },
    updateAdventure(){
      AdventureService.updateAdventure(this.newAdventure).then(res => {
        this.adventure = res.data; this.mode = 'neutral';
      }).catch(() => {alert("THERE IS SOME ERROR WITH CHANGING ADVENTURE")});
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
    },
    addImage(){
      this.mode = 'image';
      this.newImage = '';
    },
    uploadImage(event){
      let file = event.target.files[0];
      let reader = new FileReader();

      reader.readAsDataURL(file);

      reader.onload = () => {
        //alert('RESULT: ' + reader.result)
        this.newImage = reader.result
        //alert(this.newImage)
      }
      reader.onerror = function (error) {
        console.log('Error: ', error)
      }
    },
    saveImage(){
      AdventureService.addImage(this.newImage, AdventureService.getAdventureId()).then(() => {
        this.newImage = '';
      }).catch(() => {alert("THERE IS SOME PROBLEM WITH SENDING IMAGE")});
    },
    showImage(){
      alert(this.imagesToShow[0].image.replaceAll('"', ''))
    }
  }
}
</script>

<style scoped>

</style>