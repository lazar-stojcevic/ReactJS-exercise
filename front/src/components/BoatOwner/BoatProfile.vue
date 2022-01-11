<template>
  <div class="container">
    <div class="grid">
      <!--PRVI RED-->
      <div class="row" style="margin-top: 20px">
        <div class="col">
          <div class="btn-group-sm" style="margin: 5px">
            <button @click="changeModeToInfo" v-if="mode === 'neutral'" class="btn btn-success">CHANGE YOUR INFO</button>
            <button @click="changeModeToAddPhoto" v-if="mode === 'neutral'" class="btn btn-success">ADD PHOTO</button>
            <button @click="changeModeToAddService" v-if="mode === 'neutral'" class="btn btn-success">ADD SERVICE</button>
            <button @click="deleteBoat(boat.id)" v-if="mode === 'neutral'" class="btn btn-danger">DELETE</button>
          </div>

          <div v-if="mode === 'neutral'">
            <b-card>
              <td v-for="image in photos" :key="image">
                <img :src="image" width="200" height="200">
              </td>
            </b-card>

            <table class="table table-striped">
              <tbody>
              <tr>
                <td>Name</td>
                <td>{{boat.name}}</td>
              </tr>
              <tr>
                <td>Adress</td>
                <td v-if="boat.address !== null">{{boat.address.street}}, {{boat.address.city}}, {{boat.address.country}}</td>
                <td v-else>ADDRESS IS NOT SET</td>
              </tr>
              <tr>
                <td>Description</td>
                <td>{{boat.promo}}</td>
              </tr>
              <tr>
                <td>Price</td>
                <td>{{boat.priceList.price}}</td>
              </tr>
              <tr>
                <td>Rating</td>
                <td>{{boat.rating}}</td>
              </tr>
              <tr>
                <td>Type</td>
                <td>{{boat.type}}</td>
              </tr>
              <tr>
                <td>Number of engines</td>
                <td>{{boat.numberOfEngines}}</td>
              </tr>
              <tr>
                <td>Engine power</td>
                <td>{{boat.enginePower}}</td>
              </tr>
              <tr>
                <td>Top speed</td>
                <td>{{boat.topSpeed}}</td>
              </tr>
              <tr>
                <td>Capacity</td>
                <td>{{boat.capacity}}</td>
              </tr>
              <tr>
                <td>Fishing equipment</td>
                <td>{{boat.fishingEquipment}}</td>
              </tr>
              <tr>
                <td>Are you captain: </td>
                <td :hidden="!boat.captain">Yes</td>
                <td :hidden="boat.captain">No</td>
              </tr>
              <tr>
                <td>Is canceling free: </td>
                <td :hidden="!boat.freeCancel">Yes</td>
                <td :hidden="boat.freeCancel">No</td>
              </tr>
              </tbody>
            </table>


            <h3>Additional services: </h3>
            <br>
            <table class="table table-striped">
              <thead>
              <tr>
                <th>Description</th>
                <th>Price</th>
                <th></th>
              </tr>
              </thead>
              <tbody>
              <tr v-for="service in additionalServices" :key="service.id">
                <td><p>{{service.name}} </p></td>
                <td><p>{{service.addPrice}}</p></td>
                <td><button @click="deleteService(service.id)" class="btn-close"></button></td>
              </tr>
              </tbody>
            </table>

          </div>

          <div v-if="mode === 'addPhoto'" class="container">
            <form @submit.prevent="addPhoto">
              <div class="input-group mb-3">
                <span class="input-group-text">PHOTO</span>
                <br>
                <input type="file" accept="image/jpeg/*" @change="uploadImage()"/>
              </div>
              <div class="input-group mb-3">
                <div class="btn-group-sm">
                  <button type="submit" class="btn btn-success">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn btn-danger">CLOSE</button>
                </div>
              </div>
            </form>
          </div>

          <div v-if="mode === 'addService'" class="container">
            <form @submit.prevent="addService">
              <div class="input-group mb-3">
                <span class="input-group-text">Name of service</span>
                <input type="text" class="form-control" v-model="newService.name">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">Price</span>
                <input type="number" class="form-control" v-model="newService.addPrice">
              </div>
              <div class="input-group mb-3">
                <div class="btn-group-sm">
                  <button type="submit" class="btn btn-success">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn btn-danger">CLOSE</button>
                </div>
              </div>
            </form>
          </div>

          <b-form @submit="update" v-if="mode === 'changeInfo'">

            <b-form-group id="input-name" label="Cottage name:" label-for="name">
              <b-form-input
                  id="name"
                  v-model="boat.name"
                  placeholder="Enter cottage name"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-conduct-rules" label="Conduct rules:" label-for="conduct-rules">
              <b-form-input
                  id="conduct-rules"
                  v-model="boat.conductRules"
                  placeholder="Enter conduct rules"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-street" label="Your Street:" label-for="street">
              <b-form-input
                  id="street"
                  v-model="boat.address.street"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-city" label="Your City:" label-for="city">
              <b-form-input
                  id="city"
                  v-model="boat.address.city"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-country" label="Your Country:" label-for="country">
              <b-form-input
                  id="country"
                  v-model="boat.address.country"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-promo" label="Your promo:" label-for="promo">
              <b-form-input
                  id="promo"
                  v-model="boat.promo"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-type" label="Boat type:" label-for="type">
              <b-form-input
                  id="type"
                  v-model="boat.type"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-numberOfEngines" label="Number of engines:" label-for="numberOfEngines">
              <b-form-input
                  id="numberOfEngines"
                  type="number"
                  v-model="boat.numberOfEngines"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-enginePower" label="Engine power:" label-for="enginePower">
              <b-form-input
                  id="enginePower"
                  type="number"
                  v-model="boat.enginePower"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-topSpeed" label="Top speed:" label-for="topSpeed">
              <b-form-input
                  id="topSpeed"
                  type="number"
                  v-model="boat.topSpeed"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-capacity" label="Capacity:" label-for="capacity">
              <b-form-input
                  id="capacity"
                  type="number"
                  v-model="boat.capacity"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-price" label="Price for day:" label-for="price">
              <b-form-input
                  id="price"
                  v-model="boat.priceList.price"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-price" label="Fishing equipment:" label-for="fishingEquipment">
              <b-form-input
                  id="fishingEquipment"
                  v-model="boat.fishingEquipment"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-user" label="Additional options:" label-for="freeCancel">
              <b-form-checkbox v-model="boat.freeCancel"  id="freeCancel">Free canceling</b-form-checkbox>
              <b-form-checkbox v-model="boat.captain"  id="freeCancel">I'm captain</b-form-checkbox>
            </b-form-group>

            <b-button type="submit" variant="primary" class="btn btn-success">SUBMIT CHANGES</b-button>
            <button @click="changeModeToNeutral()" type="reset" class="btn btn-danger">CLOSE</button>
          </b-form>

        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LogInService from "../../Services/LogInService";
import BoatService from "../../Services/BoatService";
import BoatImageService from "../../Services/BoatImageService";

export default {
  name: "BoatProfile",
  data(){
    return{
      id:'',
      boat: {address: {
          street: '',
          city: '',
          country: ''
        },
        priceList:{ price:0}
      },
      photos:[],
      additionalServices:[],
      mode: 'neutral',
      newBoatInfo: '',
      newService:{
        priceList: '',
        name: '',
        addPrice: ''
      },
      response:'',
      photo: []
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    BoatService.getOneBoat(this.id).then(res => {
      this.boat = res.data;
      this.newBoatInfo = res.data;
    });
    BoatImageService.getImageById(this.id).then(res=>{
      for(let img of res.data){
        this.photos.push(img.base64.replaceAll('"', ''));
      }
    });
    BoatService.getAdditionalServicesOfBoat(this.id).then(res=>{
      this.additionalServices = res.data;
    });

  },
  methods: {
    changeModeToNeutral(){
      if (this.mode == 'changeInfo'){
        BoatService.getOneBoat(this.id).then(res => {
          this.boat = res.data;
          this.newBoatInfo = res.data;
        });
      }
      this.mode = 'neutral';
    },
    changeModeToInfo(){
      this.mode = 'changeInfo';
    },
    changeModeToAddPhoto(){
      this.mode = 'addPhoto'
    },
    changeModeToAddService(){
      this.mode = 'addService'
    },

    addService(){
      BoatService.AddAdditionalService({
        "priceList": this.id,
        "name": this.newService.name,
        "addPrice": this.newService.addPrice
      }).then(response=>{
        BoatService.getAdditionalServicesOfBoat(this.id).then(res=>{
          this.additionalServices = res.data;
        });
        this.response=response;
      })
      this.changeModeToNeutral();
    },

    deleteService(id){
      BoatService.DeleteAdditionalService(id).then(response=>{
        BoatService.getAdditionalServicesOfBoat(this.id).then(res=>{
          this.additionalServices = res.data;
        });
        this.response=response;
      })
    },


    update(){
      BoatService.update({
        "id": this.id,
        "name": this.boat.name,
        "conductRules": this.boat.conductRules,
        "street": this.boat.address.street,
        "city": this.boat.address.city,
        "country": this.boat.address.country,
        "promo": this.boat.promo,
        "cottageOwnerId": LogInService.userId,
        "price": this.boat.priceList.price,
        "type": this.boat.type,
        "numberOfEngines": this.boat.numberOfEngines,
        "enginePower": this.boat.enginePower,
        "topSpeed": this.boat.topSpeed,
        "capacity": this.boat.capacity,
        "fishingEquipment": this.boat.fishingEquipment,
        "freeCancel": this.boat.freeCancel,
        "captain":this.boat.captain,
      }).then(res => {this.user = res.data;
        this.changeModeToNeutral()}).catch(() => {
        alert("SERVER ERROR");
      });
    },

    deleteBoat(id){
      BoatService.delete(id).then(response=>{
        this.$router.push('/usersBoats');
        this.response=response;
      })
    },
    uploadImage() {
      const file = document.querySelector('input[type=file]').files[0]
      const reader = new FileReader()

      reader.onloadend = () => {
        this.photo = reader.result;

      }
      reader.readAsDataURL(file);
    },


    addPhoto(){
      BoatImageService.create({
        "boatId": this.id,
        "base64": this.photo
      }).then(response=>{
        BoatImageService.getImageById(this.id).then(res=>{
          for(let img of res.data){
            this.photos.push(img.base64.replaceAll('"', ''));
          }
        });
        this.response=response;
      })
      this.changeModeToNeutral();
    },

  }
}
</script>

<style scoped>

</style>