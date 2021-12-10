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

    <b-form-group id="input-promo" label="Your promo:" label-for="promo">
      <b-form-input
          id="promo"
          v-model="form.promo"
      ></b-form-input>
    </b-form-group>

    <b-form-group id="input-price" label="Price for day:" label-for="price">
      <b-form-input
          id="price"
          v-model="form.price"
          required
      ></b-form-input>
    </b-form-group>

    <b-button type="submit" variant="primary">Submit</b-button>
  </b-form>
</template>

<script>
import CottageService from "../../Services/CottageService";
import LogInService from "../../Services/LogInService";

export default {
  name: "CreateCottage",
  data() {
    return {
      form: {
        name: '',
        conductRules: '',
        street: '',
        city: '',
        country: '',
        promo: '',
        price: ''
      },
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()


      CottageService.create({
        "name": this.form.name,
        "conductRules": this.form.conductRules,
        "street": this.form.street,
        "city": this.form.city,
        "country": this.form.country,
        "promo": this.form.promo,
        "cottageOwnerId": LogInService.userId,
        "price": this.form.price
      }).then(res => {this.user = res.data}).catch(() => {
        alert("SERVER ERROR");
      });

    }
  }
}
</script>

<style scoped>

</style>