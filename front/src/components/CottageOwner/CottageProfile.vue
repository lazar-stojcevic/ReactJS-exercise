<template>
  <div class="container">
    <div class="grid">
      <!--PRVI RED-->
      <div class="row" style="margin-top: 20px">
        <div class="col">
          <div class="btn-group-sm" style="margin: 5px">
            <button @click="changeModeToInfo" v-if="mode === 'neutral'" class="btn btn-success">CHANGE YOUR INFO</button>
            <button @click="changeModeToAddPhoto" v-if="mode === 'neutral'" class="btn btn-success">ADD PHOTO</button>
            <button @click="changeModeToAddRoom" v-if="mode === 'neutral'" class="btn btn-success">ADD ROOM</button>
            <button @click="changeModeToAddService" v-if="mode === 'neutral'" class="btn btn-success">ADD SERVICE</button>
            <button @click="deleteCottage(cottage.id)" v-if="mode === 'neutral'" class="btn btn-danger">DELETE</button>
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
                <td>{{cottage.name}}</td>
              </tr>
              <tr>
                <td>Adress</td>
                <td v-if="cottage.address !== null">{{cottage.address.street}}, {{cottage.address.city}}, {{cottage.address.country}}</td>
                <td v-else>ADDRESS IS NOT SET</td>
              </tr>
              <tr>
                <td>Description</td>
                <td>{{cottage.promo}}</td>
              </tr>
              <tr>
                <td>Price</td>
                <td>{{cottage.cottagePriceList.price}}</td>
              </tr>
              <tr>
                <td>Rating</td>
                <td>{{cottage.rating}}</td>
              </tr>
              </tbody>
            </table>

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

            <h3>Rooms: </h3>
            <br>
            <table class="table table-striped">
              <thead>
              <tr>
              <th>Room description</th>
              <th>Number of beds</th>
              <th></th>
              </tr>
              </thead>
              <tbody>
            <tr v-for="room in rooms" :key="room.id">
              <td><p>{{room.roomDescription}} </p></td>
              <td><p>{{room.numberOfBeds}} </p></td>
              <td><button @click="deleteRoom(room.id)" class="btn btn-close"></button></td>
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


          <div v-if="mode === 'addRoom'" class="container">
            <form @submit.prevent="addRoom">
              <div class="input-group mb-3">
                <span class="input-group-text">ROOM DESCRIPTION</span>
                <input type="text" class="form-control" v-model="newRoom.description">
              </div>
              <div class="input-group mb-3">
                <span class="input-group-text">NUMBER OF BEDS</span>
                <input type="number" class="form-control" v-model="newRoom.number">
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

            <b-form @submit="update" v-if="mode === 'changeInfo'">

            <b-form-group id="input-name" label="Cottage name:" label-for="name">
              <b-form-input
                  id="name"
                  v-model="cottage.name"
                  placeholder="Enter cottage name"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-conduct-rules" label="Conduct rules:" label-for="conduct-rules">
              <b-form-input
                  id="conduct-rules"
                  v-model="cottage.conductRules"
                  placeholder="Enter conduct rules"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-street" label="Your Street:" label-for="street">
              <b-form-input
                  id="street"
                  v-model="cottage.address.street"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-city" label="Your City:" label-for="city">
              <b-form-input
                  id="city"
                  v-model="cottage.address.city"
                  required
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-country" label="Your Country:" label-for="country">
              <b-form-input
                  id="country"
                  v-model="cottage.address.country"
                  required
              ></b-form-input>
            </b-form-group>


              <b-form-group id="input-country" label="Your longitude:" label-for="country">
                <b-form-input
                    id="country"
                    type="number"
                    step="0.00000001"
                    v-model="cottage.address.longitude"
                    required
                ></b-form-input>
              </b-form-group>

              <b-form-group id="input-country" label="Your latitude:" label-for="country">
                <b-form-input
                    id="country"
                    type="number"
                    step="0.00000001"
                    v-model="cottage.address.latitude"
                    required
                ></b-form-input>
              </b-form-group>
            <b-form-group id="input-promo" label="Your promo:" label-for="promo">
              <b-form-input
                  id="promo"
                  v-model="cottage.promo"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-price" label="Price for day:" label-for="price">
              <b-form-input
                  id="price"
                  type="number"
                  v-model="cottage.cottagePriceList.price"
                  required
              ></b-form-input>
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
import CottageService from "../../Services/CottageService";
import RoomService from "../../Services/RoomService";
import LogInService from "../../Services/LogInService";
import CottageImageService from "../../Services/CottageImageService";

export default {
  name: "CottageProfile",
  data(){
    return{
      id:'',
      cottage: {
        address: {
          street: '',
          city: '',
          country: '',
          longitude:'',
          latitude: ''
        },
        cottagePriceList: {price: 0},
      },
      center: { lat: 44.81003164358128, lng: 20.400593632559573 },
      rooms:[],
      photos:[],
      additionalServices:[],
      markers:[],
      newRoom:{
        description: '',
        number:''
      },
      newService:{
        priceList: '',
        name: '',
        addPrice: ''
      },
      mode: 'neutral',
      newCottageInfo: '',
      response:'',
      photo: []
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    CottageService.getCottageById(this.id).then(res => {
      this.cottage = res.data;
      this.newCottageInfo = res.data;
      this.center.lat = this.cottage.address.latitude;
      this.center.lng = this.cottage.address.longitude;
      this.markers.push({ position: this.center });
    });
    RoomService.getRoomById(this.id).then(res=>{
      this.rooms = res.data;
    });
    CottageImageService.getImageById(this.id).then(res=>{
      for(let img of res.data){
        this.photos.push(img.base64.replaceAll('"', ''));
      }
    });
    CottageService.getAdditionalServicesOfCottage(this.id).then(res=>{
      this.additionalServices = res.data;
    });

  },
  methods: {
    changeModeToNeutral(){
      if(this.mode == 'addRoom'){
        this.newRoom.description = '';
        this.newRoom.number = 0;
      }
      if (this.mode == 'changeInfo'){
        CottageService.getCottageById(this.id).then(res => {
          this.cottage = res.data;
          this.newCottageInfo = res.data;
        });
      }
      this.mode = 'neutral';
    },
    changeModeToInfo(){
      this.mode = 'changeInfo';
      this.newUserInfo = JSON.parse(JSON.stringify(this.user));
    },
    changeModeToAddPhoto(){
      this.mode = 'addPhoto'
    },

    changeModeToAddRoom(){
      this.mode = 'addRoom'
    },

    changeModeToAddService(){
      this.mode = 'addService'
    },


    addRoom(){
      RoomService.create({
        "cottageId": this.id,
        "roomDescription": this.newRoom.description,
        "numberOfBeds": this.newRoom.number
      }).then(response=>{
        RoomService.getRoomById(this.id).then(res=>{
          this.rooms = res.data;
        });
        this.response=response;
          })
      this.changeModeToNeutral();
    },

    addService(){
      CottageService.AddAdditionalService({
        "priceList": this.id,
        "name": this.newService.name,
        "addPrice": this.newService.addPrice
      }).then(response=>{
        CottageService.getAdditionalServicesOfCottage(this.id).then(res=>{
          this.additionalServices = res.data;
        });
        this.response=response;
      })
      this.changeModeToNeutral();
    },

    deleteRoom(id){
      RoomService.delete(id).then(response=>{
        RoomService.getRoomById(this.id).then(res=>{
          this.rooms = res.data;
        });
        this.response=response;
      })
    },

    update(){
      if(this.cottage.latitude < -90 || this.cottage.latitude>90){
        alert("Invalid latitude");
      }else {
        if (this.cottage.longitude < -180 || this.cottage.longitude > 180) {
          alert("Invalid longitude");
        } else {
          CottageService.update({
            "id": this.id,
            "name": this.cottage.name,
            "conductRules": this.cottage.conductRules,
            "street": this.cottage.address.street,
            "city": this.cottage.address.city,
            "country": this.cottage.address.country,
            "promo": this.cottage.promo,
            "cottageOwnerId": LogInService.userId,
            "price": this.cottage.cottagePriceList.price,
            "latitude": this.cottage.address.latitude,
            "longitude": this.cottage.address.longitude
          }).then(res => {
            this.user = res.data;
            this.changeModeToNeutral()
          }).catch(() => {
            alert("SERVER ERROR");
          });
        }
      }
    },

    deleteCottage(id){
      CottageService.delete(id).then(response=>{
        this.$router.push('/usersCottage');
        this.response=response;
      }).catch(() => {alert("You have future reservation! Can't delete entity!")})
    },

    deleteService(id){
      CottageService.DeleteAdditionalService(id).then(response=>{
        CottageService.getAdditionalServicesOfCottage(this.id).then(res=>{
          this.additionalServices = res.data;
        });
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
      CottageImageService.create({
        "cottageId": this.id,
        "base64": this.photo
      }).then(response=>{
        CottageImageService.getImageById(this.id).then(res=>{
          for(let img of res.data){
            this.photos.push(img.base64.replaceAll('"', ''));
          }
        });
        this.response=response;
      })
      this.changeModeToNeutral();
    },

    a(){
      alert(this.photos[0]);
    }


  }
}
</script>

<style scoped>

</style>