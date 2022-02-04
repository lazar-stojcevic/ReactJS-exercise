<template>
  <b-form @submit="onSubmit">

    <b-form-group id="input-name" label="Cottage name:" label-for="name">
      <b-form-input
          id="name"
          v-model="form.name"
          placeholder="Enter cottage name"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-conduct-rules" label="Conduct rules:" label-for="conduct-rules">
      <b-form-input
          id="conduct-rules"
          v-model="form.conductRules"
          placeholder="Enter conduct rules"
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-street" label="Your Street:" label-for="street">
      <b-form-input
          id="street"
          v-model="form.street"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-city" label="Your City:" label-for="city">
      <b-form-input
          id="city"
          v-model="form.city"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-country" label="Your Country:" label-for="country">
      <b-form-input
          id="country"
          v-model="form.country"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-country" label="Your longitude:" label-for="country">
      <b-form-input
          id="country"
          type="number"
          step="0.00000001"
          v-model="form.longitude"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-country" label="Your latitude:" label-for="country">
      <b-form-input
          id="country"
          type="number"
          step="0.00000001"
          v-model="form.latitude"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-promo" label="Your promo:" label-for="promo">
      <b-form-input
          id="promo"
          v-model="form.promo"
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-type" label="Boat type:" label-for="type">
      <b-form-input
          id="type"
          v-model="form.type"
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-numberOfEngines" label="Number of engines:" label-for="numberOfEngines">
      <b-form-input
          id="numberOfEngines"
          type="number"
          v-model="form.numberOfEngines"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-enginePower" label="Engine power:" label-for="enginePower">
      <b-form-input
          id="enginePower"
          type="number"
          v-model="form.enginePower"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-topSpeed" label="Top speed:" label-for="topSpeed">
      <b-form-input
          id="topSpeed"
          type="number"
          v-model="form.topSpeed"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-capacity" label="Capacity:" label-for="capacity">
      <b-form-input
          id="capacity"
          type="number"
          v-model="form.capacity"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-price" label="Price for day:" label-for="price">
      <b-form-input
          id="price"
          type="number"
          v-model="form.price"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-price" label="Fishing equipment:" label-for="fishingEquipment">
      <b-form-input
          id="fishingEquipment"
          v-model="form.fishingEquipment"
          required
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-user" label="Additional options:" label-for="freeCancel">
      <b-form-checkbox v-model="form.freeCancel"  id="freeCancel">Free canceling</b-form-checkbox>
      <b-form-checkbox v-model="form.captain"  id="freeCancel">I'm captain</b-form-checkbox>
    </b-form-group>


    <b-button type="submit" variant="primary">Submit</b-button>
  </b-form>
</template>

<script>

import LogInService from "../../Services/LogInService";
import BoatService from "../../Services/BoatService";

export default {
  name: "CreateBoat",
  data() {
    return {
      form: {
        name: '',
        conductRules: '',
        street: '',
        city: '',
        country: '',
        promo: '',
        price: '',
        type: '',
        numberOfEngines: '',
        enginePower: '',
        topSpeed: '',
        capacity: '',
        freeCancel:false,
        captain: false,
        fishingEquipment: '',
        longitude:'',
        latitude: ''
      },
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      if(this.form.latitude < -90 || this.form.latitude>90){
        alert("Invalid latitude");
      }else {
        if (this.form.longitude < -180 || this.form.longitude > 180) {
          alert("Invalid longitude");
        } else {
          BoatService.create({
            "name": this.form.name,
            "conductRules": this.form.conductRules,
            "street": this.form.street,
            "city": this.form.city,
            "country": this.form.country,
            "promo": this.form.promo,
            "boatOwnerId": LogInService.userId,
            "price": this.form.price,
            "type": this.form.type,
            "numberOfEngines": this.form.numberOfEngines,
            "enginePower": this.form.enginePower,
            "topSpeed": this.form.topSpeed,
            "capacity": this.form.capacity,
            "fishingEquipment": this.form.fishingEquipment,
            "freeCancel": this.form.freeCancel,
            "captain": this.form.captain,
            "latitude": this.form.latitude,
            "longitude": this.form.longitude
          }).then(res => {
            this.user = res.data;
            this.$router.push('/usersBoats');
          }).catch(() => {
            alert("SERVER ERROR");
          });
        }
      }

    }
  }
}
</script>

<style scoped>

</style>