<template>
 <div class="container">
   <!--BRISANJE I KREIRANJE AKCIJA ZA BRZEREZERVACIJE-->
   <table style="margin: 10px">
     <tr>
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
         <button class="btn-sm btn-primary" @click="showAdditionalServicesForm">ADD ADDITIONAL SERVICES</button>
       </td>
       <td>
         <button class="btn-sm btn-primary" @click="addImage">ADD IMAGE</button>
       </td>
       <td>
         <button class="btn-sm btn-danger" @click="deleteAdventure">DELETE ADVENTURE</button>
       </td>
     </tr>
   </table>
    <!--CUSTOM RESERVATION-->
    <div v-if="mode === 'makeCustomReservation'" style="margin-top: 15px">
      <form @submit.prevent="saveCustomReservation">
        <div class="input-group mb-lg-2">
          <span class="input-group-text">CUSTOMER EMAIL</span>
          <input disabled type="text" class="form-control"
                 v-model="currentReservationOwner" required/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">START DATE AND TIME</span>
          <input type="datetime-local" class="form-control" v-model="newCustomReservation.reservationStart" required/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">LENGTH (IN HOURS)</span>
          <input type="number" class="form-control" v-model="newCustomReservation.length" required/>
          <span class="input-group-text">LENGTH (IN MINUTES)</span>
          <input type="number" class="form-control" v-model="newCustomReservation.lengthMin"/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">DISCOUNT (0 IS DEFAULT)</span>
          <input type="number" class="form-control" v-model="newCustomReservation.discount" required/>
        </div>
        <div class="input-group mb-lg-2">
          <p><strong>SELECT ADDITIONAL SERVICES</strong></p>
          <div class="form-group form-check" v-for="item in additionalServices" v-bind:key="item.id">
            <input type="checkbox"  v-model="selectedAddServices" :id="item.id" :value="item.id">
            <label class="form-check-label" :for="item.id">{{item.name}}, {{item.addPrice}}</label>
          </div>
        </div>
        <div class="input-group mb-lg-2">
          <div class="btn-group-sm">
            <button type="reset" class="btn btn-danger" @click="changeModeToNeutral">CANCEL</button>
            <button type="submit" class="btn btn-success">SUBMIT</button>
          </div>
        </div>
      </form>
    </div>

   <!--MAKE FAST RESERVATIONS-->
    <div v-if="mode === 'makeFastReservation'">
      <form @submit.prevent="saveFastReservation">
        <div class="input-group mb-lg-2">
          <span class="input-group-text">LAST DATE FOR RESERVATION</span>
          <input type="datetime-local" class="form-control" v-model="newReservation.lastDateToReserve" required/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">START DATE AND TIME</span>
          <input type="datetime-local" class="form-control" v-model="newReservation.reservationStart" required/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">LENGTH (IN HOURS)</span>
          <input type="number" class="form-control" v-model="newReservation.length" required/>
          <span class="input-group-text">LENGTH (IN MINUTES)</span>
          <input type="number" class="form-control" v-model="newReservation.lengthMin"/>
        </div>
        <div class="input-group mb-lg-2">
          <span class="input-group-text">DISCOUNT (0 IS DEFAULT)</span>
          <input type="number" class="form-control" v-model="newReservation.discount"/>
        </div>
        <div class="input-group mb-lg-2" v-if="newReservation.discount > 0">
          <p><strong>SELECT ADDITIONAL SERVICES</strong></p>
          <div class="form-group form-check" v-for="item in additionalServices" v-bind:key="item.id">
            <input type="checkbox"  v-model="newReservation.addServices" :id="item.id" :value="item">
            <label class="form-check-label" :for="item.id">{{item.name}}, {{item.addPrice}}</label>
          </div>
        </div>
        <div class="input-group mb-lg-2">
          <div class="btn-group-sm">
            <button type="reset" class="btn btn-danger" @click="changeModeToNeutral">CANCEL</button>
            <button type="submit" class="btn btn-success">SUBMIT</button>
          </div>
        </div>
      </form>
    </div>

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
   <div style="display: flex">
     <div v-for="image in imagesToShow" :key="image" style="margin: 5px">
       <img :src="image" width="300" height="300"/>
     </div>
   </div>
   <hr/>
   <!--GOOGLE MAP-->
    <div>
      <GmapMap
          :center='center'
          :zoom='12'
          style='width:100%;  height: 400px;'
      >
        <GmapMarker
            :key="index"
            v-for="(m, index) in markers"
            :position="m.position"
        />
      </GmapMap>
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
         <td><button class="btn-sm btn-danger" @click="deleteFastReservation(fr.id)">DELETE</button></td>
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
      imagesToShow: [],
      newReservation: {reservationStart: '', lastDateToReserve: '', discount: '', addServices:[], lengthMin: ''},
      currentReservationOwner: '',
      newCustomReservation: {},
      selectedAddServices: [],
      center: { lat: 44.81003164358128, lng: 20.400593632559573 },
      markers: []
    }
  },
  mounted() {
    if(this.AdventureService.getAdventureId() === ''){
      this.$router.push('/fishingInstructorProfile');
      return;
    }
    AdventureService.getAdventureById(AdventureService.getAdventureId()).then(res => {
      this.adventure = res.data;
      if(res.data.address.latitude !== null){
        this.center.lat = this.adventure.address.latitude;
        this.center.lng = this.adventure.address.longitude;
      }
      this.markers.push({ position: this.center });
    })
        .catch(() => { alert("THERE IS SOME ERROR IN LOADING ADVENTURE")});

    AdventureReservationService.getAllFreeFastReservations(AdventureService.getAdventureId()).then(res => {
      this.fastReservations = res.data;
    }).catch(() => {alert("THERE IS SOME ERROR IN LOADING FREE FAST RESERVATIONS")});

    AdventureReservationService.getAllReservedTerms(AdventureService.getAdventureId()).then(res => {
      this.reservedTerms = res.data;
    }).catch(() => {alert("THERE IS SOME PROBLEM WITH LOADING RESERVED TERMS")});

    AdventureService.getAdditionalServices(AdventureService.adventureId).then(res => {this.additionalServices = res.data})
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
      if(confirm("Do you really want to delete?")){
        AdventureService.deleteAdventure(this.adventure.id).then(() => this.$router.push('/myAdventures')).catch(() =>
        { alert("THERE IS SOME PROBLEM WITH DELETING")});
      }
    },

    makeFastReservation(){
      this.mode = 'makeFastReservation';
      this.newReservation = {reservationStart: '', lastDateToReserve: '', discount: '', addServices: [], lengthMin: '',
      length: ''};
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
      this.mode = 'makeCustomReservation'
      AdventureReservationService.getCurrentReservationOfInstructor(LogInService.userId).then(res => {
        this.currentReservationOwner = res.data;
      }).catch(() => {
        alert('THERE IS NOT CURRENT RESERVATION');
        this.mode = 'neutral';
      })
      this.selectedAddServices = []
      this.newCustomReservation = {};
    },

    goToMyAdventures(){
      this.$router.push('/myAdventures');
    },

    showAdditionalServicesForm(){
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
      AdventureService.getAdditionalServicesOfReservation(AdventureService.getAdventureId()).then(res => {
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
        this.newImage = reader.result
      }
      reader.onerror = function (error) {
        console.log('Error: ', error)
      }
    },

    saveImage(){
      AdventureService.addImage(this.newImage, AdventureService.getAdventureId()).then(() => {
        this.newImage = '';
        this.loadImages();
      }).catch(() => {alert("THERE IS SOME PROBLEM WITH SENDING IMAGE")});
    },

    loadImages(){
      AdventureService.getAllImagesOfAdventure(this.adventure.id).then(res => {
        this.imagesToShow = []
        for(let img of res.data){
          this.imagesToShow.push(img.image.replaceAll('"', ''));
        }
      }).catch(() => {alert("THERE IS SOME ERROR WITH LOADING IMAGES")});
    },

    saveFastReservation(){
      if(this.newReservation.lengthMin > 59){
        alert("LENGTH IN MINUTES MUST BE LOW THAN 60")
      }
      if(this.newReservation.reservationStart < this.newReservation.lastDateToReserve){
        alert('START TIME MUST BE LATER THEN LAST DATE FOR RESERVATION')
        return;
      }
      AdventureReservationService.saveFastReservation(this.newReservation, LogInService.userId,
          AdventureService.adventureId).then(() => {
            alert('RESERVATION IS CREATED');
            this.loadFreeFastReservations();
            this.mode = 'neutral';
          }).catch(() => {
            alert('RESERVATION IS NOT CREATED, PROBABLY YOU HAVE RESERVATION IN THIS TERM')
      })
    },

    loadFreeFastReservations(){
      AdventureReservationService.getAllFreeFastReservations(AdventureService.adventureId).then(res => {
        this.fastReservations = res.data;
      }).catch(() => {
        alert('THERE IS SOME PROBLEM WITH LOADING FREE FAST RESERVATIONS');
      })
    },

    saveCustomReservation(){
      this.newCustomReservation.customerMail = this.currentReservationOwner;
      this.newCustomReservation.selectedAdditionalServicesIds = this.selectedAddServices;
      this.newCustomReservation.adventureId = this.adventure.id;
      this.newCustomReservation.reservationStart =
          this.newCustomReservation.reservationStart.replaceAll('T', ' ');
      AdventureReservationService.makeCustomReserve(this.newCustomReservation).then(() => {
        alert("SUCCESSFUL")
        this.loadReservedTerms();
      }).catch(() => {
        alert("THERE IS SOME PROBLEM WITH SAVING CUSTOM RESERVATION");
      })
      this.mode = 'neutral'
    },

    loadReservedTerms(){
      AdventureReservationService.getAllReservedTerms(AdventureService.getAdventureId()).then(res => {
        this.reservedTerms = res.data;
      }).catch(() => {alert("THERE IS SOME PROBLEM WITH LOADING RESERVED TERMS")});
    },

    deleteFastReservation(id){
      AdventureReservationService.deleteReservation(id).then(() => {this.loadFreeFastReservations()}).catch(() => {
        alert("THERE IS SOME ERROR WITH DELETING RESERVATION");
      })
    }
  }
}
</script>

<style scoped>

</style>
