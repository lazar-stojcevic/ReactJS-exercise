<template>
  <div class="container">
    <div class="grid">
      <!--PRVI RED-->
      <div class="row" style="margin-top: 20px">
        <div class="col">
          <div class="btn-group-sm" style="margin: 5px">
            <button @click="changeModeToInfo" v-if="mode === 'neutral'" class="btn-info">CHANGE YOUR INFO</button>
            <button @click="changeModeToAddPhoto" v-if="mode === 'neutral'" class="btn-info">ADD PHOTO</button>
            <button @click="changeModeToAddRoom" v-if="mode === 'neutral'" class="btn-info">ADD ROOM</button>
            <button @click="deleteCottage(cottage.id)" v-if="mode === 'neutral'" class="btn-danger">DELETE</button>
          </div>
          <div v-if="mode === 'neutral'">
            <h1>Name: {{cottage.name}}</h1>
            <br>
            <h2>Rules: {{cottage.conductRules}} </h2>
            <br>
            <h3>Address: {{cottage.address.street}},{{cottage.address.city}},{{cottage.address.country}} </h3>
            <br>
            <p>Description: {{cottage.promo}} </p>
            <br>
            <h3>Price: {{cottage.priceList.price}} </h3>
            <br>
            <h3>Rating: {{cottage.rating}} </h3>

            <div class="container" v-for="room in rooms" :key="room.id">
              <p>Price: {{room.roomDescription}} </p>
              <br>
              <p>Rating: {{room.numberOfBeds}} </p>
              <br>
              <button @click="deleteRoom(room.id)" class="btn-info">DELETE</button>
            </div>
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
                  <button type="submit" class="btn-info">CONFIRM</button>
                  <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
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

            <b-form-group id="input-promo" label="Your promo:" label-for="promo">
              <b-form-input
                  id="promo"
                  v-model="cottage.promo"
              ></b-form-input>
            </b-form-group>

            <b-form-group id="input-price" label="Price for day:" label-for="price">
              <b-form-input
                  id="price"
                  v-model="cottage.priceList.price"
                  required
              ></b-form-input>
            </b-form-group>

            <b-button type="submit" variant="primary">SUBMIT CHANGES</b-button>
            <button @click="changeModeToNeutral()" type="reset" class="btn-danger">CLOSE</button>
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

export default {
  name: "CottageProfile",
  data(){
    return{
      id:'',
      cottage: {address: {
          street: '',
          city: '',
          country: ''
        },
      priceList:{ price:0}
      },
      rooms:[],
      newRoom:{
        description: '',
        number:''
      },
      mode: 'neutral',
      newCottageInfo: '',
      response:''
    }
  },
  mounted() {
    this.id = this.$route.params.id;
    CottageService.getCottageById(this.id).then(res => {
      this.cottage = res.data;
      this.newCottageInfo = res.data;
    });
    RoomService.getRoomById(this.id).then(res=>{
      this.rooms = res.data;
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

    deleteRoom(id){
      RoomService.delete(id).then(response=>{
        RoomService.getRoomById(this.id).then(res=>{
          this.rooms = res.data;
        });
        this.response=response;
        alert(response);
      })
    },

    update(){
      CottageService.update({
                              "id": this.id,
                              "name": this.cottage.name,
                              "conductRules": this.cottage.conductRules,
                              "street": this.cottage.address.street,
                              "city": this.cottage.address.city,
                              "country": this.cottage.address.country,
                              "promo": this.cottage.promo,
                              "cottageOwnerId": LogInService.userId,
                              "price": this.cottage.priceList.price
                            }).then(res => {this.user = res.data;
                            this.changeModeToNeutral()}).catch(() => {
  alert("SERVER ERROR");
});
    },

    deleteCottage(id){
      CottageService.delete(id).then(response=>{
        this.$router.push('/usersCottage');
        this.response=response;
      })
    }


  }
}
</script>

<style scoped>

</style>