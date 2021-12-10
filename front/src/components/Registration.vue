<template>
  <div>
    <b-form @submit="onSubmit">
      <b-form-group id="input-email" label="Email address:" label-for="email" description="You will identify by this email."
      >
        <b-form-input
            id="email"
            v-model="form.email"
            type="email"
            placeholder="Enter email"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-password" label="Password:" label-for="input-password">
        <b-form-input
            type="password"
            id="input-password"
            v-model="form.password"
            placeholder="Password"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-password-confirm" label="Confirm password:" label-for="input-password-conf">
        <b-form-input
            type="password"
            id="input-password-conf"
            v-model="form.passwordConf"
            placeholder="Confirm your password"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-first-name" label="Your First name:" label-for="first-name">
        <b-form-input
            id="first-name"
            v-model="form.firstname"
            placeholder="Enter your first name"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-last-name" label="Your Last Name:" label-for="last-name">
        <b-form-input
            id="last-name"
            v-model="form.lastname"
            placeholder="Enter your last name"
            required
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

      <b-form-group id="input-phone" label="Your phone number:" label-for="phone">
        <b-form-input
            id="phone"
            v-model="form.phone"
            required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-user" label="User type:" label-for="type">
        <b-form-radio v-model="form.type"  id="type"  name="some-radios" value="CU">Customer</b-form-radio>
        <b-form-radio v-model="form.type"  name="some-radios" value="FI">Instructor</b-form-radio>
        <b-form-radio v-model="form.type"  name="some-radios" value="CO">Cottage owner</b-form-radio>
        <b-form-radio v-model="form.type"  name="some-radios" value="BO">Boat owner</b-form-radio>
      </b-form-group>

      <b-button type="submit" variant="primary">Submit</b-button>
    </b-form>
  </div>
</template>

<script>
import RegistrationService from "@/Services/RegistrationService";


export default {
  name: "Registration",
  data() {
    return {
      form: {
        email: '',
        password: '',
        passwordConf: '',
        firstname: '',
        lastname: '',
        street: '',
        city: '',
        country: '',
        phone: '',
        type:"CU"
      },
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault()
      if(this.form.passwordConf !== this.form.password){
        alert("PASSWORD AND CONFIRM PASSWORD MUST BE SAME")
        return;
      }

      RegistrationService.register({
        "email": this.form.email,
        "password": this.form.password,
        "firstname": this.form.firstname,
        "lastname": this.form.lastname,
        "street": this.form.street,
        "city": this.form.city,
        "country": this.form.country,
        "phone": this.form.phone,
        "userType": this.form.type
      }).then(res => {this.user = res.data}).catch(() => {
        alert("SERVER ERROR");
      });

      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>

</style>
